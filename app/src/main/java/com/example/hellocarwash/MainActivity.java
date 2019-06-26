package com.example.hellocarwash;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hellocarwash.Account.PricesForm;
import com.example.hellocarwash.Account.SignInForm;
import com.example.hellocarwash.Account.SignUpForm;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getString(R.string.mainActivityTitle));
        mFirebaseAuth = FirebaseAuth.getInstance();
        navigationDrawer();
    }

    // for the navigation drawer
    public void navigationDrawer(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // code from youtube
        NavigationView nv = (NavigationView)findViewById(R.id.nav_View);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_Home):
                        finish();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case(R.id.nav_Profile):
                        finish();
                        startActivity(new Intent(MainActivity.this,SignUpForm.class));
                        break;
                    case (R.id.nav_Services):
                        finish();
                        startActivity(new Intent(MainActivity.this, PricesForm.class));
                        break;
                    case (R.id.nav_Logout):
                        mFirebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this, SignInForm.class));
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
