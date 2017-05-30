package com.wesleymoy.contactsexample;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactStore {
    private static ContactStore mInstance;
    private List<Contact> mContacts = new ArrayList<>();

    public synchronized static ContactStore getInstance() {
        if (mInstance == null) {
            mInstance = new ContactStore();
        }
        return mInstance;
    }

    private ContactStore() {
    }

    public int size() {
        return mContacts.size();
    }

    public boolean add(Contact contact) {
        return mContacts.add(contact);
    }

    public boolean addAll(@NonNull Collection<? extends Contact> c) {
        return mContacts.addAll(c);
    }

    public void clear() {
        mContacts.clear();
    }

    public Contact get(int index) {
        return mContacts.get(index);
    }
}
