package com.example.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectedContactInfo extends AppCompatActivity {

    private long id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String social_network;

    private TextView nameField;
    private TextView emailField;
    private TextView phoneField;
    private TextView locationField;
    private TextView social_networkField;
    private DbAdapter helper;

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

        id = Long.parseLong(contactInfoList.get(0));
        nameField.setText(contactInfoList.get(1));
        emailField.setText(contactInfoList.get(2));
        phoneField.setText(contactInfoList.get(3));
        locationField.setText(contactInfoList.get(4));
        social_networkField.setText(contactInfoList.get(5));
        helper = new DbAdapter(this);

        nameField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CallKeyboard(nameField.getText().toString(), nameField);
                return true;
            }
        });
        emailField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExternalApp(Uri.parse(contactInfoList.get(2)), Intent.ACTION_SEND);
            }
        });
        emailField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CallKeyboard(emailField.getText().toString(), emailField);
                return true;
            }
        });
        phoneField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExternalApp(Uri.parse("tel:" + contactInfoList.get(3)), Intent.ACTION_DIAL);
            }
        });
        phoneField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CallKeyboard(phoneField.getText().toString(), phoneField);
                return true;
            }
        });
        locationField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExternalApp(Uri.parse("geo:" + contactInfoList.get(4)), Intent.ACTION_VIEW);
            }
        });
        locationField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CallKeyboard(locationField.getText().toString(), locationField);
                return true;
            }
        });
        social_networkField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExternalApp(Uri.parse(contactInfoList.get(5)), Intent.ACTION_VIEW);
            }
        });
        social_networkField.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CallKeyboard(social_networkField.getText().toString(), social_networkField);
                return true;
            }
        });

    }

    public void OpenExternalApp(Uri value, String action) {
        Intent intent = new Intent(action, value);
        if(intent != null)
        {
            startActivity(intent);
        }
    }
    private void CallKeyboard(String oldString, final TextView selectedElement) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enter new value");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(oldString);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedElement.setText(input.getText().toString());
                SetNewValues();
                if(helper.update(id, name, email, location, phone, social_network)) {
                    Toast.makeText(SelectedContactInfo.this,
                            "Data successfully changed",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SelectedContactInfo.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }
    private boolean SetNewValues() {
        try {
            name = nameField.getText().toString();
            email = emailField.getText().toString();
            phone = phoneField.getText().toString();
            location = locationField.getText().toString();
            social_network = social_networkField.getText().toString();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
