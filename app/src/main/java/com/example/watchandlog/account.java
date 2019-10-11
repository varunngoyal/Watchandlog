package com.example.watchandlog;

import java.util.HashMap;
import java.util.Map;

public class account {

   private String account_holder;
   private Map<String, Double> user_contribution = new HashMap<>() ;

    public account() {
    }

    public account(String account_holder, Map<String, Double> user_contribution) {
        this.account_holder = account_holder;
        this.user_contribution = user_contribution;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public Map<String, Double> getUser_contribution() {
        return user_contribution;
    }

    public void setUser_contribution(Map<String, Double> user_contribution) {
        this.user_contribution = user_contribution;
    }
}
