package com.example.contacts;
import com.google.firebase.database.Exclude;
import java.util.ArrayList;

public class ContactClass {

    private String id;
    private String name;
    private String email;
    private String location;
    private String phone;
    private String social_network;

    public ContactClass() {

    }
    public ContactClass(String id, String name, String email, String location,
                        String phone, String social_network)    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.social_network = social_network;
    }
    public ContactClass(String name, String email, String location,
                        String phone, String social_network) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.social_network = social_network;
    }

    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    public String getLocation()
    {
        return location;
    }
    public String getPhone()
    {
        return phone;
    }
    public String getSocialNetwork()
    {
        return social_network;
    }
    @Exclude
    public ArrayList<String> getListOfAll()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(getId());
        list.add(getName());
        list.add(getEmail());
        list.add(getPhone());
        list.add(getLocation());
        list.add(getSocialNetwork());
        return list;
    }
}
