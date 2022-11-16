package com.nvcorp.nsmoneytracker.transaction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey
    @NotNull
    private String id;
    private String contactId;
    private String categoryId;
    private String paymentId;
    private String paymentType;
    private String remarks;
    private double amount;
    private double balance;
    private String date;
    private String Time;

    public Transaction() {}

    public Transaction(@NotNull String id, String contactId, String categoryId, String paymentId, String paymentType, String remarks, double amount, double balance, String date, String time) {
        this.id = id;
        this.contactId = contactId;
        this.categoryId = categoryId;
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.remarks = remarks;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
        Time = time;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
