package com.example.mycontact.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.mycontact.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
