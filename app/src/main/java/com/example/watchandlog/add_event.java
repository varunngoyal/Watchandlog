package com.example.watchandlog;

import java.time.LocalDateTime;

public class add_event {

    private String amount;
    private String event;
    private String date;
    private String name;

    public add_event() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public add_event(String amount, String date, String event, String name) {
        this.amount = amount;
        this.event = event;
        this.name = name;
        this.date = date;
    }
}
