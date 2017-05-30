package com.wesleymoy.contactsexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactDetailActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "CONTACT_ID";
    private TextView nameView;
    private TextView phoneNumberView;
    private ContactStore contactStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        contactStore = ContactStore.getInstance();

        nameView = (TextView) findViewById(R.id.name);
        phoneNumberView = (TextView) findViewById(R.id.phoneNumber);

        final Contact contact = contactStore.get(getIntent().getIntExtra(CONTACT_ID, -1));
        nameView.setText(contact.mName);
        phoneNumberView.setText(contact.mPhoneNumber);
    }
}
