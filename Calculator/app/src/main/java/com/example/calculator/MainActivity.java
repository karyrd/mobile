package com.example.calculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Vars.age = GetSpinner(18, 100, R.id.spinner);
        Vars.height = GetSpinner(155, 210, R.id.spinner2);
        Vars.weight = GetSpinner(45, 200, R.id.spinner3);
        GetSpinnerActivity();
        Vars.adb = new android.app.AlertDialog.Builder(this);
        Vars.AMR = Vars.AMRlist.get(0);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RadioGroup rg = findViewById(R.id.radiogroup);
                if(rg.getCheckedRadioButtonId() == R.id.male)
                {
                    Vars.BMR = Vars.GetBMR(Integer.parseInt(Vars.age.getSelectedItem().toString()),
                            Integer.parseInt(Vars.height.getSelectedItem().toString()),
                            Integer.parseInt(Vars.weight.getSelectedItem().toString()),
                            'M');
                    Vars.GetPopupValid(Vars.adb,
                            String.valueOf(round(Vars.BMR * Vars.AMR, 1))).show();
                }
                else if(rg.getCheckedRadioButtonId() == R.id.female)
                {
                    Vars.BMR = Vars.GetBMR(Integer.parseInt(Vars.age.getSelectedItem().toString()),
                            Integer.parseInt(Vars.height.getSelectedItem().toString()),
                            Integer.parseInt(Vars.weight.getSelectedItem().toString()),
                            'F');
                    Vars.GetPopupValid(Vars.adb,
                            String.valueOf(round(Vars.BMR * Vars.AMR, 1))).show();
                }
                else
                {
                    Vars.GetPopupInvalid().show();
                }
            }
        });

        Spinner lifestyle = findViewById(R.id.spinner4);
        lifestyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer selection = adapterView.getSelectedItemPosition();
                Vars.AMR = Vars.AMRlist.get(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private double round(double value, int after) {
        if(after < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(after, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Spinner GetSpinner(int from, int to, int itemid)
    {
        Spinner dropdown = findViewById(itemid);
        ArrayList<String> items = new ArrayList<String>();
        for (int i = from; i <= to; i++) {
            items.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        return dropdown;
    }

    public Spinner GetSpinnerActivity()
    {
        Spinner dropdown = findViewById(R.id.spinner4);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Not active at all");
        items.add("Stretch sometimes");
        items.add("Morning push-ups every other day");
        items.add("Physical activities 6-7 times a week");
        items.add("Doing P.E. activities at least once a day");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        return dropdown;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
