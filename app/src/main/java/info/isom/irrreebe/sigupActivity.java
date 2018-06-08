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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sigupActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText Register_name;
    private EditText Register_email;
    private EditText Register_password;
    private Button createaccount;
    private FirebaseAuth mAutho;
    private DatabaseReference storeUSerDefaultDataReference;
    private ProgressDialog loadingbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);
        mAutho =FirebaseAuth.getInstance();


        mToolbar =(Toolbar) findViewById(R.id.main_page_TopBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("sigup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Register_name =(EditText) findViewById(R.id.Register_name);
        Register_email =(EditText) findViewById(R.id.Register_email);
        Register_password =(EditText) findViewById(R.id.Register_password);
        createaccount =(Button) findViewById(R.id.createaccount) ;
        loadingbar = new ProgressDialog(this);


        createaccount .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name =  Register_name.getText().toString();
                String email =  Register_email.getText().toString();
                String password = Register_password.getText().toString();

                RegisterAcount(name,email,password);

            }
        });
    }

    private void RegisterAcount(final String name, String email, String password)
    {
        if (TextUtils.isEmpty(name)){
            Toast.makeText(sigupActivity.this,"shuti wibagiwe izina ahangaha" ,Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(sigupActivity.this,"shuti wibagiwe email ahangaha" ,Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(sigupActivity.this,"shuti wibagiwe password ahangaha" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingbar.setTitle("mwihangane mugihe turimo kubakorera account yanyu");
            loadingbar.setMessage("mwihangane gakeya shuti zacu mugihe turimo kuba korera account yanyu");
            loadingbar.show();
            mAutho.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String current_user_id= mAutho.getCurrentUser().getUid();
                                storeUSerDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);
                                storeUSerDefaultDataReference.child("user_name").setValue(name);
                                storeUSerDefaultDataReference.child("user_status").setValue("iam using ireebe app");
                                storeUSerDefaultDataReference.child("us_image").setValue("defoult");
                                storeUSerDefaultDataReference.child("user_thumb_image").setValue("defoult_image")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Intent mainIntent = new Intent(sigupActivity.this, MainActivity.class);
                                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(sigupActivity.this, "habayemo ikosa ongera ugerageze", Toast.LENGTH_SHORT).show();
                            }
                            loadingbar.dismiss();
                        }
                    });
        }
    }
}

