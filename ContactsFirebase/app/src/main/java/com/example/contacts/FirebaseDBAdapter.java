package com.example.contacts;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDBAdapter {

    private FirebaseDatabase fbdatabase;
    private DatabaseReference dbreference;

    public FirebaseDBAdapter() {
        fbdatabase = FirebaseDatabase.getInstance();
        fbdatabase.setPersistenceEnabled(true);
        dbreference = fbdatabase.getReference("contacts");

        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Save(ArrayList<String> data) {
        try {
            String id = dbreference.push().getKey();
            dbreference.child(id).setValue(data);
        } catch (Exception ex) {
            Log.d("asd", "Error: " + ex.getMessage());
        }
    }
    public void Read() {
        try {
            Log.d("asd", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                    dbreference.);
        } catch(Exception ex) {
            Log.d("asd", "Error: " + ex.getMessage());
        }
    }

}
