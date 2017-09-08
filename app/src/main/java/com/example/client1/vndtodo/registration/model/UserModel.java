package com.example.client1.vndtodo.registration.model;

/**
 * Created by client1 on 9/8/2017.
 */

public class UserModel
{
    private String id;
    private String mobile;
    private String password;
    private String fullname;
    private String email;

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
