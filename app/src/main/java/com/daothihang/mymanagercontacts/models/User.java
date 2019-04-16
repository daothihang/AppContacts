package com.daothihang.mymanagercontacts.models;

import java.io.Serializable;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
public class User implements Serializable {
    private String id_user;
    private String name;
    private String phone;
    private String address;
    private String avartar;

    public User(String id_user, String name, String phone, String address, String avartar) {
        this.id_user = id_user;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.avartar = avartar;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }
}
