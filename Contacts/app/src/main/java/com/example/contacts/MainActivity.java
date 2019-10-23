package com.example.contacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<ContactClass> contactsList;
    private List<View> highlightedContactsViews;
    private CustomListViewAdapter customAdapter;
    private ActionMode actionMode;
    private DbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);
        helper = new DbAdapter(this);
        contactsList = helper.getData();
        highlightedContactsViews = new ArrayList<>();

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
                View selectedView = view.findViewById(R.id.client_list);
                if(customAdapter.isContactSelectedForTheFirstTime(position)) {
                    selectedView.setBackgroundColor(Color.YELLOW);
                    highlightedContactsViews.add(selectedView);
                }
                else {
                    selectedView.setBackgroundColor(Color.TRANSPARENT);
                    highlightedContactsViews.remove(selectedView);
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
                    DeleteHighlightedContacts();
                    ClearHighlightedContacts();
                    UpdateData();

                    Toast.makeText(MainActivity.this,
                            "Selected contacts have been deleted",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                catch (Exception ex) {
                    ClearHighlightedContacts();
                    Toast.makeText(MainActivity.this,
                            "An error occurred while deleting selected contacts: " +
                            ex.toString(),
                            Toast.LENGTH_LONG).show();

                    return false;
                }
            }
            if(item.getItemId() == R.id.forward_button) {
                try {
                    Intent intent = new Intent(MainActivity.this, MultipleSelectedContactsInfo.class);
                    PassHighlightedContacts(intent);
                    startActivityForResult(intent, 1);
                }
                catch (Exception ex) {
                    ClearHighlightedContacts();
                    Toast.makeText(MainActivity.this,
                            "An error occurred while trying to access selected contacts all at once: " +
                                    ex.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
            if(actionMode != null) {
                actionMode.finish();
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    private boolean ClearHighlightedContacts() {
        try {
            for (View highlightedView : highlightedContactsViews) {
                highlightedView.setBackgroundColor(Color.TRANSPARENT);
                customAdapter.removeSelection();
            }

            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    private boolean DeleteHighlightedContacts() {
        try {
            for (ContactClass contact : customAdapter.getSelectedContactsList()) {
                helper.delete(contact.getId());
            }
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    private boolean PassHighlightedContacts(Intent intent) {
        try {
            int i = 0;
            for (ContactClass contact : customAdapter.getSelectedContactsList()) {
                intent.putStringArrayListExtra("selectedContact" + i, contact.getListOfAll());
                i++;
            }
            intent.putExtra("amountOfSelectedContacts", i);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
