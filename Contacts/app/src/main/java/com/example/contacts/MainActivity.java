package com.example.contacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button add_contact_button;
    private ListView listView;
    private List<ContactClass> contactsList;
    private CustomListViewAdapter customAdapter;
    private ActionMode actionMode;
    private DbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_contact_button = findViewById(R.id.add_contact_button);
        listView = findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);
        helper = new DbAdapter(this);
        contactsList = helper.getData();

        customAdapter = new CustomListViewAdapter(this,
                R.layout.client_list,
                contactsList);
        listView.setAdapter(customAdapter);

        // method for item in list when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, SelectedContactInfo.class);
                intent.putStringArrayListExtra("selectedContact", customAdapter.getItem(position).getListOfAll());
                startActivityForResult(intent, 1);
            }
        });
        // method for long click selection
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(customAdapter.isContactSelectedForTheFirstTime(position)) {
                    view.findViewById(R.id.client_list).setBackgroundColor(Color.YELLOW);
                }
                else {
                    view.findViewById(R.id.client_list).setBackgroundColor(Color.TRANSPARENT);
                }

                if(actionMode == null) {
                    actionMode = startActionMode(actionModeCallback);
                }

                if(customAdapter.getSelectedElementsCount() > 0) {
                    actionMode.setTitle("Selected " + customAdapter.getSelectedElementsCount() + " contact(s)");
                }
                else {
                    actionMode.finish();
                }

                return true;
            }
        });
    }
    private void UpdateData() {
        contactsList = helper.getData();
        customAdapter.UpdateDataSet(contactsList);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UpdateData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_button:
                Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
                startActivityForResult(intent, 1);
        }
        return(super.onOptionsItemSelected(menuItem));
    }
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.delete_button) {
                try {
                    for (ContactClass contact : customAdapter.getSelectedContactsList()) {
                        helper.delete(contact.getId());
                        customAdapter.removeSelection();
                    }

                    UpdateData();
                    Toast.makeText(MainActivity.this,
                            "Selected contacts have been deleted",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                catch (Exception ex) {
                    Toast.makeText(MainActivity.this,
                            "An error occurred while deleting selected contacts: " +
                            ex.toString(),
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
}
