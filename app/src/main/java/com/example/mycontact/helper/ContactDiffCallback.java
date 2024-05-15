package com.example.mycontact.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.example.mycontact.database.Contact;

import java.util.ArrayList;
import java.util.List;



public class ContactDiffCallback extends DiffUtil.Callback {
    private final List<Contact> mOldContactList;
    private final List<Contact> mNewContactList;

    public ContactDiffCallback(ArrayList<Contact> oldContactList, List<Contact> newContactList) {
        this.mOldContactList = oldContactList;
        this.mNewContactList = newContactList;
    }


    @Override
    public int getOldListSize() {
        return mOldContactList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewContactList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldContactList.get(oldItemPosition).getId() == mNewContactList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Contact oldEmployee = mOldContactList.get(oldItemPosition);
        final Contact newEmployee = mNewContactList.get(newItemPosition);
        return oldEmployee.getName().equals(newEmployee.getName())
                && oldEmployee.getNumber().equals(newEmployee.getNumber())
                && oldEmployee.getGroup().equals(newEmployee.getGroup())
                && oldEmployee.getInstagram().equals(newEmployee.getInstagram());
    }
}
