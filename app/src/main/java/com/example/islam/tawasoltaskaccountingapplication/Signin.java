package com.example.islam.tawasoltaskaccountingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;
import com.example.islam.tawasoltaskaccountingapplication.Tap.SlidingTabLayout;

public class Signin extends AppCompatActivity {

    private EditText userName, Password;
    private CheckBox remember;
    public Button login, register;
    public MyTask myTask = new MyTask();
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preferences = getSharedPreferences("name", MODE_PRIVATE);
        editor = preferences.edit();

        userName = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbutton);
        remember = (CheckBox) findViewById(R.id.rememberme);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {


                    DatabaseHelper db = new DatabaseHelper(Signin.this);
                    Log.v("data888", "1");
                    Intent intent = new Intent(Signin.this, MainActivity.class);
                    Log.v("data888", "12");
                    boolean verify = db.verificationUserAndPAss(userName.getText().toString().trim(), Password.getText().toString().trim());
                    Log.v("data888", String.valueOf(verify));
                    if (verify) {
                        myTask.setName(userName.getText().toString().trim());
                        myTask.setPassword(Password.getText().toString().trim());
                        Log.v("data9991", myTask.getName().trim() + " " + myTask.getPassword());
                        editor.putString("costumerName", userName.getText().toString().trim()).commit();
                        editor.putString("costumerPassword", Password.getText().toString().trim()).commit();
                        startActivity(intent);
                        finish();
                    } else {

                        Snackbar.make(v, "UserName AND Password Is Not Exit !!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }

                } catch (Exception e) {

                }

            }
        });

        if (remember.isChecked()) {

            editor.putString("costumerName", userName.getText().toString());
            editor.putString("costumerPassword", Password.getText().toString());
            editor.putBoolean("checked", true);
            editor.commit();

        }


        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signin.this, RegisterActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        userName.setText(preferences.getString("costumerName", ""));
        Password.setText(preferences.getString("costumerPassword", ""));
        remember.setChecked(preferences.getBoolean("checked", false));
    }


    //    public void GenerateData() {
//
//        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
//        db.deleteAllTransaction();
//        MyTask myTask = new MyTask();
//
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("3000");
//            myTask.setType("fixed");
//            myTask.setDate("1/1/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("900");
//            myTask.setType("services");
//            myTask.setDate("15/1/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("food");
//            myTask.setDate("19/1/2016");
//            myTask.setPlace("zara");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2500");
//            myTask.setType("fixed");
//            myTask.setDate("1/2/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("1900");
//            myTask.setType("services");
//            myTask.setDate("15/2/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("food");
//            myTask.setDate("19/1/2016");
//            myTask.setPlace("5er zaman");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2500");
//            myTask.setType("fixed");
//            myTask.setDate("1/3/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("400");
//            myTask.setType("services");
//            myTask.setDate("15/3/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("clothes");
//            myTask.setDate("19/3/2016");
//            myTask.setPlace("carfour");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2100");
//            myTask.setType("fixed");
//            myTask.setDate("1/4/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("1100");
//            myTask.setType("services");
//            myTask.setDate("15/4/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1600");
//            myTask.setType("food");
//            myTask.setDate("19/4/2016");
//            myTask.setPlace("Kfc");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2500");
//            myTask.setType("fixed");
//            myTask.setDate("1/5/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("200");
//            myTask.setType("services");
//            myTask.setDate("15/5/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("delivery");
//            myTask.setDate("19/5/2016");
//            myTask.setPlace("hiber one");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("3500");
//            myTask.setType("fixed");
//            myTask.setDate("1/6/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("1200");
//            myTask.setType("services");
//            myTask.setDate("15/6/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("food");
//            myTask.setDate("19/6/2016");
//            myTask.setPlace("pizza hut");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2000");
//            myTask.setType("fixed");
//            myTask.setDate("1/7/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("900");
//            myTask.setType("services");
//            myTask.setDate("15/7/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("food");
//            myTask.setDate("19/7/2016");
//            myTask.setDescreption(" ");
//            myTask.setPlace("mac");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2500");
//            myTask.setType("fixed");
//            myTask.setDate("1/8/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("1900");
//            myTask.setType("services");
//            myTask.setDate("15/8/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("100");
//            myTask.setType("clothes");
//            myTask.setDate("19/8/2016");
//            myTask.setPlace("D&G");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("3000");
//            myTask.setType("fixed");
//            myTask.setDate("1/9/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("1350");
//            myTask.setType("services");
//            myTask.setDate("15/9/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("350");
//            myTask.setType("food");
//            myTask.setDate("19/9/2016");
//            myTask.setPlace("KFC");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("1550");
//            myTask.setType("fixed");
//            myTask.setDate("1/10/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("144");
//            myTask.setType("services");
//            myTask.setDate("15/10/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("339");
//            myTask.setType("food");
//            myTask.setDescreption(" ");
//            myTask.setDate("19/10/2016");
//            myTask.setPlace("pizaa hut");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2500");
//            myTask.setType("fixed");
//            myTask.setDate("1/11/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("900");
//            myTask.setType("services");
//            myTask.setDate("15/11/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("1000");
//            myTask.setType("food");
//            myTask.setDate("19/11/2016");
//            myTask.setPlace("KFC");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//        for (int i = 0; i < 1; i++) {
//            myTask.setTransaction("income");
//            myTask.setAmount("2800");
//            myTask.setType("fixed");
//            myTask.setDate("1/12/2016");
//            myTask.setPlace("company");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Planned_Expenses");
//            myTask.setAmount("800");
//            myTask.setType("services");
//            myTask.setDate("15/12/2016");
//            myTask.setPlace("home");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//
//            myTask.setTransaction("Actual_Expenses");
//            myTask.setAmount("665");
//            myTask.setType("food");
//            myTask.setDate("19/12/2016");
//            myTask.setPlace("mo'men");
//            myTask.setDescreption(" ");
//            db.Addtransaction(myTask);
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
