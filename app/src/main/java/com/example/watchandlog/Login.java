package com.example.watchandlog;

public class Login {

    private String uname;
    private String pass;
    private String test;

    public Login() {
    }

    public Login(String uname, String pass,String test) {
        this.uname = uname;
        this.pass = pass;
        this.test=test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
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
