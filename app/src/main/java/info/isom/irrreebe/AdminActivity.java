package info.isom.irrreebe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuther;
    private Toolbar mToolBar;
    private ViewPager myViewpager;
    private TabLayout mytablelayout;
    private AdminTasAdapter  myTablspageadapter;
    private Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuther =FirebaseAuth.getInstance();
        //setting toolbar
        mToolBar = (Toolbar)findViewById(R.id.admin_main_page_TopBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("IREEBE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //table for activity by bazimya saphani



        myViewpager =(ViewPager)findViewById(R.id.amain_tabs_pager);
        myTablspageadapter =new AdminTasAdapter(getSupportFragmentManager());
        myViewpager.setAdapter(myTablspageadapter);
        mytablelayout =(TabLayout)findViewById(R.id.admin_main_tabls);
        mytablelayout.setupWithViewPager(myViewpager);
        signupButton =(Button) findViewById(R.id.newUSER);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =mAuther.getCurrentUser();
        if (currentUser ==null)
        {
            logoutUser();
        }
    }

    public  void logoutUser(){
        Intent AdminActivityoIntent =new Intent(AdminActivity.this,Hallfinder.class);
        AdminActivityoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(AdminActivityoIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_activity,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() ==R.id.logout)
        {
            mAuther.signOut();
            logoutUser();
        }
        if (item.getItemId() == R.id.newUSER)
        {
            Intent settingActivity = new Intent(AdminActivity.this,sigupActivity.class);
            startActivity(settingActivity);
        }


        return true;

    }

}
