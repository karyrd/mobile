package com.example.contacts;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelectedContactsInfo extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;
    List<ArrayList<String>> listOfSelectedContactsParameters;
    ClipboardManager clipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_selected_contacts);
        listView = findViewById(R.id.list);
        listOfSelectedContactsParameters = new ArrayList<ArrayList<String>>();
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>()
        );
        listView.setAdapter(adapter);

        ImportSelectedContactsInfo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowPopupMenu(view);
            }
        });
    }

    private boolean ImportSelectedContactsInfo() {
        try {
            ArrayList<String> pileOfContactsParameters = new ArrayList<String>();

            for (int i = 0;
                 i < getIntent().getIntExtra("amountOfSelectedContacts", 0);
                 i++) {
                ArrayList<String> contactInfo = getIntent()
                        .getStringArrayListExtra("selectedContact" + i);
                listOfSelectedContactsParameters.add(contactInfo);
                pileOfContactsParameters.add(contactInfo.get(1));
                pileOfContactsParameters.add(contactInfo.get(2));
                pileOfContactsParameters.add(contactInfo.get(3));
                pileOfContactsParameters.add(contactInfo.get(4));
                pileOfContactsParameters.add(contactInfo.get(5));
            }

            adapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    pileOfContactsParameters
            );
            listView.setAdapter(adapter);

            return true;
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "An error occurred while trying to access selected contacts all at once: " +
                            ex.toString(),
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean CopyToClipboard(String copiedString) {
        try {
            clipboard.setPrimaryClip(
                    ClipData.newPlainText(
                            "info", copiedString)
            );
            Toast.makeText(MultipleSelectedContactsInfo.this,
                    "Copied to clipboard",
                    Toast.LENGTH_SHORT)
                    .show();

            return true;
        }
        catch (Exception ex) {
            Toast.makeText(MultipleSelectedContactsInfo.this,
                    "An error occurred while trying to copy to clipboard: " +
                            ex.toString(),
                    Toast.LENGTH_LONG).show();

            return false;
        }
    }
    private void ShowPopupMenu(View view) {
        final View finalView = view;
        PopupMenu popupMenu = new PopupMenu(this, finalView);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.copyToClipboardOption:
                        CopyToClipboard(((TextView) finalView).getText().toString());
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
}
