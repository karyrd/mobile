package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.List;

public class CustomListView extends ArrayAdapter<ContactClass> {

    Context context;
    List<ContactClass> contactList;
    int resource;

    public CustomListView(Context context, int resource, List<ContactClass> contactList)
    {
        super(context, resource, contactList);

        this.context = context;
        this.resource = resource;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(resource, null, false);

        TextView id = rowView.findViewById(R.id.client_id);
        TextView name = rowView.findViewById(R.id.client_name);
        TextView phone = rowView.findViewById(R.id.client_phone);

        ContactClass contact = contactList.get(position);
        id.setText(contact.getId().toString());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        return rowView;
    }

}
