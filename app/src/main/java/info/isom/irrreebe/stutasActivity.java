package info.isom.irrreebe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class stutasActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button savechangesButton;
    private EditText stutasinput;
    private DatabaseReference chengstutasRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stutas);
        mToolbar = (Toolbar) findViewById(R.id.Status_app_bar);


        mAuth=FirebaseAuth.getInstance();
        String User_id=mAuth.getCurrentUser().getUid();

        chengstutasRef= FirebaseDatabase.getInstance().getReference().child("users").child(User_id);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("chang stutas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        savechangesButton =(Button) findViewById(R.id.save_stutas_bar);
        stutasinput = (EditText) findViewById(R.id.stutas_input);
        loadingBar = new ProgressDialog(this);


        savechangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String new_statas =stutasinput.getText().toString();

                changprofirestutas(new_statas);

            }

            private void changprofirestutas(String new_statas) {
                if(TextUtils.isEmpty(new_statas)){
                    Toast.makeText(stutasActivity.this,"please enter your status",Toast.LENGTH_SHORT).show();
                }
                else
                    loadingBar.setTitle("guhindura ifoto yawe");
                loadingBar.setMessage("mwihangane gato");
                loadingBar.show();
                {
                    chengstutasRef.child("user_status").setValue(new_statas)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        loadingBar.dismiss();
                                        Intent SettingIntent =new Intent(stutasActivity.this,SettingActivity.class);
                                        startActivity(SettingIntent);
                                        Toast.makeText(stutasActivity.this,"stutas updated successfull",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(stutasActivity.this, "stutas fair to uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
