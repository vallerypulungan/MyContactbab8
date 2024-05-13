package com.example.mycontact.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.mycontact.model.Contact;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);
}
