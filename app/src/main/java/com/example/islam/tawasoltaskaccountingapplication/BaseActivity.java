package com.example.islam.tawasoltaskaccountingapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

import java.util.ArrayList;

/**
 * Created by islam on 31/10/2016.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Button deleteAccount, setting, signOut;
    public SharedPreferences preferences;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public TextView name, email,phone;
    public DatabaseHelper db;
    public MyTask myTask = new MyTask();
    private String dbemail,dbPhone;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        db = new DatabaseHelper(BaseActivity.this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.responsive_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });

        preferences = getSharedPreferences("name", MODE_PRIVATE);

        deleteAccount = (Button) findViewById(R.id.deleteMyAccount);
        setting = (Button) findViewById(R.id.sitting);
        signOut = (Button) findViewById(R.id.signOut);
        name = (TextView) findViewById(R.id.nav_textview_name);
        email = (TextView) findViewById(R.id.nav_textview_email);
        phone = (TextView) findViewById(R.id.nav_textview_phone);

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.START);
                final AlertDialog.Builder Mail = new AlertDialog.Builder(BaseActivity.this);
                Mail.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db = new DatabaseHelper(BaseActivity.this);
                        db.deleteMyAccount(preferences.getString("costumerName", "mmm"));
                        Intent i = new Intent(BaseActivity.this, Signin.class);
                        startActivity(i);
                        finish();
                    }
                });

                Mail.setNegativeButton("CANCEL", null);
                Mail.setTitle("Delete");
                Mail.setMessage("Are You sure you want to delete Your Account");
                Mail.show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.START);
                Intent intent = new Intent(BaseActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.START);
                Intent k = new Intent(getApplicationContext(), Signin.class);
                startActivity(k);
                finish();
            }
        });


        ArrayList<MyTask> nameEmail = db.getOneCostumer(preferences.getString("costumerName", "mmm"),
                preferences.getString("costumerPassword", "mmm"));

        for (int j = 0; j < nameEmail.size(); j++) {

            dbemail = nameEmail.get(j).getEmail();
            dbPhone = nameEmail.get(j).getPhone();


        }

        email.setText(dbemail);
        name.setText(preferences.getString("costumerName", "mmm"));
        phone.setText(dbPhone);


    }
}
