package com.example.watchandlog;

public class login_details {
    String uname;
    String pass;

    public login_details() {
    }

    public login_details(String uname, String pass) {
        this.uname = uname;
        this.pass = pass;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
