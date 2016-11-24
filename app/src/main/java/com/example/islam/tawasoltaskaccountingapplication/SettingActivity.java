package com.example.islam.tawasoltaskaccountingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

public class SettingActivity extends AppCompatActivity {


    private RadioGroup language, Curruncy;
    private RadioButton languageRadioButton, currencuRadioButton;
    private Button setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences preferences = getSharedPreferences("name", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        language = (RadioGroup) findViewById(R.id.language);
        setting = (Button) findViewById(R.id.save_setting);
        Curruncy = (RadioGroup) findViewById(R.id.currencyGroup);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioId = language.getCheckedRadioButtonId();
                languageRadioButton = (RadioButton) findViewById(radioId);

                if (languageRadioButton.getText().equals("Arabic Language")) {

                    editor.putString("locale", "ar").commit();

                } else {

                    editor.putString("locale", "en").commit();

                }
            }
        });

        Curruncy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int radioId = Curruncy.getCheckedRadioButtonId();
                currencuRadioButton = (RadioButton) findViewById(radioId);

                if (currencuRadioButton.getText().equals("Egyption_Pound")) {
                    editor.putString("currency", "EGB").commit();

                } else if (currencuRadioButton.getText().equals("U.S._dollar")) {
                    editor.putString("currency", "$").commit();
                } else if (currencuRadioButton.getText().equals("Kuwaiti_dinar")) {
                    editor.putString("currency", "KWD").commit();
                } else if (currencuRadioButton.getText().equals("Chinese_yuan")) {
                    editor.putString("currency", "CNY").commit();
                } else if (currencuRadioButton.getText().equals("Japanese_Yen")) {
                    editor.putString("currency", "JPY").commit();
                } else {
                    editor.putString("currency", "SAR").commit();
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
