package com.example.islam.tawasoltaskaccountingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, password, Email, phone;
    private Button save;
    public MyTask myTask = new MyTask();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("name",MODE_PRIVATE);
        editor = preferences.edit();

        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        name = (EditText) findViewById(R.id.name_register);
        password = (EditText) findViewById(R.id.password_register);
        Email = (EditText) findViewById(R.id.email_register);
        phone = (EditText) findViewById(R.id.phone_register);

        save = (Button) findViewById(R.id.register_seve);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);


                myTask.setName(name.getText().toString().trim());
                myTask.setPassword(password.getText().toString().trim());
                myTask.setEmail(Email.getText().toString().trim());
                myTask.setPhone(phone.getText().toString().trim());

                db.AddCostumer(myTask);
                Intent intent = new Intent(RegisterActivity.this, Signin.class);
                editor.putString("costumerName", name.getText().toString().trim());
                editor.putString("costumerPassword", password.getText().toString().trim());
                editor.commit();
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
