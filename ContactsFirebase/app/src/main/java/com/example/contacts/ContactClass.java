package com.example.contacts;

import java.util.ArrayList;

public class ContactClass {

    private Integer id;
    private String name;
    private String email;
    private String location;
    private String phone;
    private String social_network;

    public ContactClass(Integer id, String name, String email, String location,
                        String phone, String social_network)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.social_network = social_network;
    }

    public Integer getId()
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
    public ArrayList<String> getListOfAll()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add(getId().toString());
        list.add(getName());
        list.add(getEmail());
        list.add(getPhone());
        list.add(getLocation());
        list.add(getSocialNetwork());
        return list;
    }
}
