package com.example.islam.tawasoltaskaccountingapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

public class AddTransaction extends AppCompatActivity {

    private EditText amount, Place, Description;
    private TextView EnterType;
    private Spinner type, days, monthes, years;
    private Button Add;
    public MyTask myTask = new MyTask();
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        preferences = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amount = (EditText) findViewById(R.id.addamount);
        type = (Spinner) findViewById(R.id.addType);
        Place = (EditText) findViewById(R.id.AddPlace);
        Description = (EditText) findViewById(R.id.addDescription);
        EnterType = (TextView) findViewById(R.id.textTypeTransaction);
        days = (Spinner) findViewById(R.id.spinnerOfDay);
        monthes = (Spinner) findViewById(R.id.spinnerOfMonth);
        years = (Spinner) findViewById(R.id.spinnerOfYear);


        Bundle extras = getIntent().getExtras();
        final int data = extras.getInt("income");
        if (data == 0) {

            EnterType.setText("income");
            myTask.setTransaction("income");
        } else if (data == 1) {

            EnterType.setText("Actual Expenses");
            myTask.setTransaction("Actual_Expenses");
        } else {

            EnterType.setText("Planned Expenses");
            myTask.setTransaction("Planned_Expenses");
        }

        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    myTask.setDay(0);
                } else {
                    String Text = years.getSelectedItem().toString();
                    myTask.setDay(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    myTask.setMonth(0);
                } else {
                    myTask.setMonth(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        years.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    myTask.setYear(0);
                } else {
                    String Text = years.getSelectedItem().toString();
                    myTask.setYear(position + 2014);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    myTask.setType(null);
                } else {
                    String Text = type.getSelectedItem().toString();
                    myTask.setType(Text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Add = (Button) findViewById(R.id.addTransaction);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((amount.getText().length() == 0) || (Place.getText().length() == 0) || (myTask.getType() == null) ||
                        (myTask.getDay() == null) || (myTask.getMonth() == null) || (myTask.getYear() == null)) {

                    Toast.makeText(getApplicationContext(), "Please Enter All Data !", Toast.LENGTH_LONG).show();

                } else {

                    String name = preferences.getString("costumerName", "");
                    Log.v("data13", name);
                    DatabaseHelper db = new DatabaseHelper(AddTransaction.this);
                    myTask.setName(name);
                    myTask.setAmount(amount.getText().toString());
                    myTask.setPlace(Place.getText().toString());
                    myTask.setDescreption(Description.getText().toString());

                    db.Addtransaction(myTask);
//                amount.setText("");
//                type.setText("");
//                Place.setText("");
//                Description.setText("");
//                date.setText("");
//                amount.setFocusable(true);
                    //finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
