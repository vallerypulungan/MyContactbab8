package com.example.mycontact.ui;


import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.mycontact.database.Contact;
import com.example.mycontact.repository.ContactRepository;


public class ContactInsertUpdateViewModel extends ViewModel {
    private final ContactRepository mContactRepository;

    public ContactInsertUpdateViewModel(Application application) {
        mContactRepository = new ContactRepository(application);
    }

    public void insert(Contact contact) {
        mContactRepository.insert(contact);
    }

    public void update(Contact contact) {
        mContactRepository.update(contact);
    }

    public void delete(Contact contact) {
        mContactRepository.delete(contact);
    }
}