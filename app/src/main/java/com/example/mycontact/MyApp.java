package com.example.mycontact;


import android.app.Application;
import androidx.room.Room;
import com.example.mycontact.database.ContactDatabase ;

public class MyApp extends Application {
    private static MyApp instance;
    private ContactDatabase contactDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        contactDatabase = Room.databaseBuilder(getApplicationContext(),
                ContactDatabase.class, "contact-database").build();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public ContactDatabase getContactDatabase() {
        return contactDatabase;
    }
}
