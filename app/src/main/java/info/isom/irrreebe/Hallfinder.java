package info.isom.irrreebe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Hallfinder extends AppCompatActivity {
    private FirebaseAuth mAuther;
    private Toolbar mToolBar;
    private ViewPager myViewpager;
    private TabLayout mytablelayout;
    private TabsPageAdapter  myTablspageadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallfinder);
        mAuther =FirebaseAuth.getInstance();
        //setting toolbar
        mToolBar = (Toolbar)findViewById(R.id.main_page_TopBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("IREEBE");
        //table for activity by bazimya saphani

        myViewpager =(ViewPager)findViewById(R.id.main_tabs_pager);

        myTablspageadapter =new TabsPageAdapter(getSupportFragmentManager());
        myViewpager.setAdapter(myTablspageadapter);
        mytablelayout =(TabLayout)findViewById(R.id.main_tabs);
        mytablelayout.setupWithViewPager(myViewpager);
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
        Intent HallfinderIntent =new Intent(Hallfinder.this,MainActivity.class);
        HallfinderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(HallfinderIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_user,menu);
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
        if (item.getItemId() == R.id.action_settings) {
            Intent settingActivity = new Intent(Hallfinder.this, SettingActivity.class);
            startActivity(settingActivity);
        }
        if (item.getItemId() == R.id.adminID) {
            Intent AllusersIntent = new Intent(Hallfinder.this,loginactivity.class);
            startActivity(AllusersIntent);
        }
        return true;
    }
}
