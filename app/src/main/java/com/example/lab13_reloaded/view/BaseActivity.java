package com.example.lab13_reloaded.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab13_reloaded.R;
import com.example.lab13_reloaded.utils.SharedPref;

public class BaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    protected FrameLayout view_stub; //This is the framelayout to keep your content view
    protected NavigationView navigation_view;
    protected DrawerLayout mDrawerLayout;
    protected LinearLayout mNavHeader;
    protected TextView userLogged;
    protected ActionBarDrawerToggle mDrawerToggle;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);// The base layout that contains your navigation drawer.

        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavHeader = (LinearLayout) findViewById(R.id.nav_header);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Menu drawerMenu = navigation_view.getMenu();
        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
        // and so on...

        username = SharedPref.getString(this, getString(R.string.pref_username));

        if (!username.equals("")) {
            View view = navigation_view.inflateHeaderView(R.layout.navigation_header);
            userLogged = (TextView) view.findViewById(R.id.user_logged);
            String message = "Usario:" + "\n" + username;
            userLogged.setText(message);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*
    Override all setContentView methods to put the content view to the FrameLayout view_stub
    so that, we can make other activity implementations looks like normal activity subclasses.
    */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            case R.id.item_students:
                intent = new Intent(BaseActivity.this, StudentListActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item_course:
                intent = new Intent(BaseActivity.this, CourseListActivity.class);
                startActivity(intent);
                finish();
                break;

            // and so on...
        }
        return false;
    }
}
