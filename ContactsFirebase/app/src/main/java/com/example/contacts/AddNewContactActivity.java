package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddNewContactActivity extends AppCompatActivity {

    private EditText name, email, phone, location, social_network;
    private DbAdapter helper;
    private Intent intent;

    static final String NEW_CONTACT_INFO = "newContactInfoForSaveInstance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        intent = getIntent();

        name = findViewById(R.id.name_value);
        email = findViewById(R.id.email_value);
        phone = findViewById(R.id.phone_value);
        location = findViewById(R.id.location_value);
        social_network = findViewById(R.id.social_network_value);

        helper = new DbAdapter(this);
    }

    public void addContact(View view)
    {
        String new_name = name.getText().toString();
        String new_email = email.getText().toString();
        String new_location = location.getText().toString();
        String new_phone = phone.getText().toString();
        String new_social_network = social_network.getText().toString();

        try
        {
            boolean isInserted = helper.insertData(new_name, new_email, new_location,
                                new_phone, new_social_network);
            if(!isInserted)
            {
                Toast.makeText(this, "An error occurred! Couldn't add a new contact",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "New contact has been added",
                        Toast.LENGTH_LONG).show();
            }
            setResult(RESULT_OK, intent);
            finish();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putStringArrayList(NEW_CONTACT_INFO, new ArrayList<String>(
                Arrays.asList(name.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        location.getText().toString(),
                        social_network.getText().toString())
        ));

        super.onSaveInstanceState(bundle);
    }
    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        ArrayList<String> savedArray = bundle.getStringArrayList(NEW_CONTACT_INFO);
        name.setText(savedArray.get(0));
        email.setText(savedArray.get(1));
        phone.setText(savedArray.get(2));
        location.setText(savedArray.get(3));
        social_network.setText(savedArray.get(4));
    }
}
