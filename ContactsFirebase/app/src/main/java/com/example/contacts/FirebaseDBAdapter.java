package com.example.contacts;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class FirebaseDBAdapter {

    private FirebaseDatabase fbdatabase;
    private DatabaseReference dbreference;
    public ArrayList<ContactClass> contactsArray;

    public FirebaseDBAdapter() {
        fbdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getUid();
        dbreference = fbdatabase.getReference("contacts/" + userId);
        contactsArray = new ArrayList<>();

        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactsArray = GetData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase onCancelled method error",
                        "Error: " + databaseError.toString());
            }
        });
    }

    public void Save(ContactClass data) {
        try {
            Log.d("##SAVE##", data.toString());
            String id = dbreference.push().getKey();
            dbreference.child(id).setValue(data);
        } catch (Exception ex) {
            Log.d("Firebase save method error",
                    "Error: " + ex.getMessage());
        }
    }

    public void Update(ContactClass data, String id) {
        try {
            dbreference.child(id).setValue(data);
        } catch (Exception ex) {
            Log.d("Firebase update method error",
                    "Error: " + ex.getMessage());
        }
    }

    private ArrayList<ContactClass> GetData(DataSnapshot dataSnapshot) {
        ArrayList<ContactClass> db_contacts_info = new ArrayList<>();
        for(DataSnapshot db_contact : dataSnapshot.getChildren()) {
            db_contacts_info.add(new ContactClass(
                    db_contact.getKey(),
                    db_contact.child("name").getValue().toString(),
                    db_contact.child("email").getValue().toString(),
                    db_contact.child("location").getValue().toString(),
                    db_contact.child("phone").getValue().toString(),
                    db_contact.child("socialNetwork").getValue().toString()
            ));
        }

        return db_contacts_info;
    }

    public boolean DeleteContact(String id) {
        try {
            dbreference.child(id).removeValue();

            return true;
        } catch (Exception ex) {


            return false;
        }
    }
}
