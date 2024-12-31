package com.hajera.uasmobile;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String fullName;
    private String address;
    private String phone;

    // Constructor
    public User(String fullName, String address, String phone) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    // Getters and setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Method to convert User object to a Map for Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("fullName", fullName);
        userMap.put("address", address);
        userMap.put("phone", phone);
        return userMap;
    }
}
