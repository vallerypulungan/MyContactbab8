package com.example.mycontact.ui;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycontact.database.Contact;
import com.example.mycontact.repository.ContactRepository;

import java.util.List;



public class ContactMainViewModel extends ViewModel {
    private final ContactRepository mContactRepository;

    public ContactMainViewModel(Application application) {
        mContactRepository = new ContactRepository(application);
    }

    LiveData<List<Contact>> getAllContacts(){
        return mContactRepository.getAllContacts();
    }
}