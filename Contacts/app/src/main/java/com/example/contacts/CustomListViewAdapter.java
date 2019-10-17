package com.example.contacts;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<ContactClass> contactList;
    private List<ContactClass> selectedContactsList;
    private View rowView;

    public CustomListViewAdapter(Context context, int resource, List<ContactClass> contactList)
    {
        //super(context, resource, contactList);

        this.context = context;
        this.resource = resource;
        this.contactList = contactList;
        selectedContactsList = new ArrayList<ContactClass>();
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        rowView = view;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(resource, null);
        }

        TextView id = rowView.findViewById(R.id.client_id);
        TextView name = rowView.findViewById(R.id.client_name);
        TextView phone = rowView.findViewById(R.id.client_phone);

        ContactClass contact = getItem(position);
        id.setText(contact.getId().toString());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        return rowView;
    }

    public boolean UpdateDataSet(List<ContactClass> newDataSet)
    {
        try
        {
            contactList.clear();
            contactList.addAll(newDataSet);
            this.notifyDataSetChanged();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public int getCount()
    {
        return contactList.size();
    }
    @Override
    public ContactClass getItem(int position) {
        return contactList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public boolean isContactSelectedForTheFirstTime(int position)
    {
        ContactClass contact = getItem(position);
        if(!selectedContactsList.contains(contact)) {
            selectedContactsList.add(contact);
            return true;
        }
        else {
            selectedContactsList.remove(contact);
            return false;
        }
    }
    public int getSelectedElementsCount()
    {
        return selectedContactsList.size();
    }
    public void removeSelection()
    {
        selectedContactsList = new ArrayList<ContactClass>();
    }
    public List<ContactClass> getSelectedContactsList()
    {
        return selectedContactsList;
    }
}
