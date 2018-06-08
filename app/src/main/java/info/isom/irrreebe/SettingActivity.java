package info.isom.irrreebe;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView settingsdispalyimage;
    private TextView settingdispalyename;
    private  TextView getSettingdispalystutas;
    private Button  settingimagebutton;
    private  Button settingstutasbutton;
    private DatabaseReference getuserdatabaserefarence;
    private FirebaseAuth mAuthor;
    private final static int Gallery_pick =1;
    private StorageReference storageImageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        storageImageReference = FirebaseStorage.getInstance().getReference().child("profileImages");
        settingsdispalyimage =(CircleImageView) findViewById(R.id.setting_profile);
        settingdispalyename =(TextView) findViewById(R.id.username);
        getSettingdispalystutas =(TextView) findViewById(R.id.settstustus);
        settingimagebutton =(Button) findViewById(R.id.setting_image_button);
        settingstutasbutton =(Button) findViewById(R.id.setting_stutas_button);
        mAuthor =FirebaseAuth.getInstance();
        String online_user_id =mAuthor.getCurrentUser().getUid();

        getuserdatabaserefarence= FirebaseDatabase.getInstance().getReference()
                .child("users").child(online_user_id);
        getuserdatabaserefarence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("user_name").getValue().toString();
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                String stutas =dataSnapshot.child("user_status").getValue().toString();
                String image= dataSnapshot.child("us_image").getValue().toString();
                String thumimage=dataSnapshot.child("user_thumb_image").getValue().toString();


                settingdispalyename.setText(name);
                getSettingdispalystutas.setText(stutas);
                Picasso.with(SettingActivity.this).load(image).into(settingsdispalyimage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        settingimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent  galaryIntent = new Intent();
                galaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galaryIntent.setType("image/*");
                startActivityForResult(galaryIntent,Gallery_pick);
            }
        });
        settingstutasbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StutasIntent=new Intent(SettingActivity.this,stutasActivity.class);
                startActivity(StutasIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null) {
            Uri ImageUri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();


                String user_id=mAuthor.getCurrentUser().getUid();
                StorageReference  filepath= storageImageReference.child(user_id+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            String download=task.getResult().getDownloadUrl().toString();

                            Toast.makeText(SettingActivity.this, "ifoto yawe yoherejwe neza!", Toast.LENGTH_SHORT).show();

                            getuserdatabaserefarence.child("us_image").setValue(download)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Toast.makeText(SettingActivity.this,"image updatede success full",Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        }
                        else
                        {
                            Toast.makeText(SettingActivity.this, "error in saving picture", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
