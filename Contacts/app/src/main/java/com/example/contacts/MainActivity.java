package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button add_contact_button;
    private ListView listView;
    private List<ContactClass> contactClassList;
    private ArrayAdapter<String> adapter;
    private CustomListView customAdapter;
    DbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_contact_button = findViewById(R.id.add_contact_button);
        listView = findViewById(R.id.list);
        helper = new DbAdapter(this);
        contactClassList = helper.getData();

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getContactNames());
//        listView.setAdapter(adapter);

        customAdapter = new CustomListView(this,
                R.layout.client_list,
                contactClassList);
        listView.setAdapter(customAdapter);

        add_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, SelectedContactInfo.class);
                intent.putStringArrayListExtra("selectedContact", contactClassList.get(position).getListOfAll());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            contactClassList = helper.getData();
            contactClassList.clear();
            customAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "requestCode = " + requestCode +
                        "\nresultCode = " + resultCode,
                Toast.LENGTH_LONG).show();
    }

    private ArrayList<Integer> getContactIDs()
    {
        ArrayList<Integer> contactIDsList = new ArrayList<>();
        for(ContactClass contact : contactClassList)
        {
            contactIDsList.add(contact.getId());
        }
        return contactIDsList;
    }
    private ArrayList<String> getContactNames()
    {
        ArrayList<String> contactNamesList = new ArrayList<>();
        for(ContactClass contact : contactClassList)
        {
            contactNamesList.add(contact.getName());
        }
        return contactNamesList;
    }
    private ArrayList<String> getContactPhones()
    {
        ArrayList<String> contactPhonesList = new ArrayList<>();
        for(ContactClass contact : contactClassList)
        {
            contactPhonesList.add(contact.getPhone());
        }
        return contactPhonesList;
    }


}
