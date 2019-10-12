package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectedContactInfo extends AppCompatActivity {

    TextView nameField;
    private TextView emailField;
    private TextView phoneField;
    private TextView locationField;
    private TextView social_networkField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_contact_info);

        nameField = findViewById(R.id.name_value);
        emailField = findViewById(R.id.email_value);
        phoneField = findViewById(R.id.phone_value);
        locationField = findViewById(R.id.location_value);
        social_networkField = findViewById(R.id.social_network_value);

        final ArrayList<String> contactInfoList = getIntent().getStringArrayListExtra("selectedContact");

        nameField.setText(contactInfoList.get(1));
        emailField.setText(contactInfoList.get(2));
        phoneField.setText(contactInfoList.get(3));
        locationField.setText(contactInfoList.get(4));
        social_networkField.setText(contactInfoList.get(5));

        emailField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "email clicked", Toast.LENGTH_SHORT).show();
                OpenExternalApp(Uri.parse(contactInfoList.get(2)), Intent.ACTION_SEND);
            }
        });
        phoneField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "phone clicked", Toast.LENGTH_SHORT).show();
                OpenExternalApp(Uri.parse("tel:" + contactInfoList.get(3)), Intent.ACTION_DIAL);
            }
        });
        locationField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "location clicked", Toast.LENGTH_SHORT).show();
                OpenExternalApp(Uri.parse("geo:" + contactInfoList.get(4)), Intent.ACTION_VIEW);
            }
        });
        social_networkField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "link clicked", Toast.LENGTH_SHORT).show();
                OpenExternalApp(Uri.parse(contactInfoList.get(5)), Intent.ACTION_VIEW);
            }
        });

    }

    public void OpenExternalApp(Uri value, String action)
    {
        Intent intent = new Intent(action, value);
        if(intent != null)
        {
            startActivity(intent);
        }
    }
}
