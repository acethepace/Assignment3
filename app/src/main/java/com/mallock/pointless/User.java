package com.mallock.pointless;

/**
 * Created by Mallock on 02-10-2016.
 */

public class User {
    private String username, name, hobby, phone;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getHobby() {
        return hobby;
    }

    public User(String username, String name, String hobby, String phone) {
        this.username = username;
        this.name = name;
        this.hobby = hobby;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
