package com.example.watchandlog;

public class Login {

    private String uname;
    private String pass;


    public Login() {
    }

    public Login(String uname, String pass,String test, String emailid) {
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
