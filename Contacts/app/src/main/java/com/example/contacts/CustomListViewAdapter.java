package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ContactClass> contactList;
    private int resource;

    public CustomListViewAdapter(Context context, int resource, List<ContactClass> contactList)
    {
        //super(context, resource, contactList);

        this.context = context;
        this.resource = resource;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View rowView = view;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.client_list, null);
        }

        TextView id = rowView.findViewById(R.id.client_id);
        TextView name = rowView.findViewById(R.id.client_name);
        TextView phone = rowView.findViewById(R.id.client_phone);

        ContactClass contact = contactList.get(position);
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
    public Object getItem(int pos) {
        return contactList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder
    {
        LinearLayout linearLayout;
        CheckedTextView checkedTextView;
        ImageView checkedImage;
    }
}
