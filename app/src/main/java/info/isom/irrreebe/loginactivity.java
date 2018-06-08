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

public class loginactivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FirebaseAuth mAUTHOR;
    private EditText loginEmail;
    private EditText loginpassword;
    private Button signinbutton;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        mToolbar = (Toolbar) findViewById(R.id.main_page_TopBar);
        mAUTHOR=FirebaseAuth.getInstance();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("sig in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginEmail=(EditText) findViewById(R.id.EmailLoginButton);
        loginpassword=(EditText)findViewById(R.id.Password_login_batton);
        signinbutton =(Button)findViewById(R.id.Sign_in_login_button);
        loadingbar =new ProgressDialog(this);



        signinbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String email =loginEmail.getText().toString();
                String password= loginpassword.getText().toString();


                logInuserAcount(email,password);
            }

            private void logInuserAcount(String email, String password)
            {
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(loginactivity.this,"andikamo imail yawe nangwa numero yaterephone",Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(loginactivity.this,"andikamo password yawe ",Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingbar.setTitle("kwinjira kuri ccount yawe");
                    loadingbar.setMessage("mwihangane gakeya mugihe ikiri kohereza ubusabe bwanyu");
                    loadingbar.show();
                    mAUTHOR.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        Intent mainIntent = new Intent(loginactivity.this, AdminActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(loginactivity.this,"Email nangwa password birapfuye andikaneza email",Toast.LENGTH_SHORT).show();
                                    }
                                    loadingbar.dismiss();
                                }
                            });
                }
            }
        });

    }
}
