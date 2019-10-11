package com.example.watchandlog;

public class transactions {

    private String from;
    private String to;
    private String amount;
    private String date;
    private String status;

    public transactions() {
    }

    public transactions(String from, String to, String amount, String date, String status) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
