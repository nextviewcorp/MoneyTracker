package com.nvcorp.nsmoneytracker.payment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PaymentDao {

    @Insert
    void insert(Payment payment);

    @Query("SELECT * FROM payments")
    LiveData<List<Payment>> getAllPayments();
}
