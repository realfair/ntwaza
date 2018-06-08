package info.isom.irrreebe;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class chatsFragament extends Fragment {
    EditText name, description;
    Button sendBtn;
    ImageView image;
    View view;
    private StorageReference mStorageRef;
    StorageReference storageImageReference;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String id;
    IdeasModel model;
    String title, desc;
    FirebaseAuth mAuthor;
    int Gallery_pick = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chats_fragament, container, false);
        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.desc);
        sendBtn = view.findViewById(R.id.sendBtn);
        image = view.findViewById(R.id.image);
        storageImageReference = FirebaseStorage.getInstance().getReference().child("profileImages");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("populate");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        Picasso.with(getActivity()).load("").into(image);
        title = name.getText().toString();
        desc = description.getText().toString();
        mAuthor = FirebaseAuth.getInstance();
        String id = mAuthor.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(id);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageChooser = new Intent();
                imageChooser.setAction(Intent.ACTION_GET_CONTENT);
                imageChooser.setType("image/*");
                startActivityForResult(imageChooser, 1);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), desc, Toast.LENGTH_SHORT).show();

//                id=myRef.push().getKey();
//                myRef.child(id).setValue()
//                myRef.setValue("");

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null) {
            Uri ImageUri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(getActivity());
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();


                String user_id = mAuthor.getCurrentUser().getUid();
                StorageReference filepath = storageImageReference.child(user_id + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            String download = task.getResult().getDownloadUrl().toString();
                            Toast.makeText(getActivity(), "ifoto yawe yoherejweneza", Toast.LENGTH_SHORT).show();

                            myRef.child("us_image").setValue(download)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getActivity(), "image updatede success full", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        } else {
                            Toast.makeText(getActivity(), "error in saving picture", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



}
