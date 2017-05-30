package com.wesleymoy.contactsexample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity {

    private RecyclerView mContactList;
    private ContactStore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStore = ContactStore.getInstance();

        mContactList = (RecyclerView) findViewById(R.id.contactList);
        mContactList.setLayoutManager(new LinearLayoutManager(this));
        mContactList.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ContactViewHolderAdapter adapter = new ContactViewHolderAdapter();
        adapter.setHasStableIds(true);
        mContactList.setAdapter(adapter);

        new AsyncTask<Void, Void, List<Contact>>() {
            @Override
            protected List<Contact> doInBackground(Void... params) {
                ArrayList<Contact> contacts = new ArrayList<>();
                Contact contact = new Contact();
                contact.mId = 0;
                contact.mName = "Jacqueline Trombley";
                contact.mPhoneNumber = "+1 618-555-5257";
                contacts.add(contact);
                contact = new Contact();
                contact.mId = 1;
                contact.mName = "Annie Feeney";
                contact.mPhoneNumber = "+1 732-555-1476";
                contacts.add(contact);
                contact = new Contact();
                contact.mId = 2;
                contact.mName = "Donna Brady";
                contact.mPhoneNumber = "+1 213-555-8586";
                contacts.add(contact);
                return contacts;
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                mStore.clear();
                mStore.addAll(contacts);
                mContactList.getAdapter().notifyDataSetChanged();
            }
        }.execute();
    }

    private static class ContactOnClickListener implements View.OnClickListener {
        private View mItemView;
        private int mContactId = -1;

        public ContactOnClickListener(View itemView) {
            mItemView = itemView;
        }

        @Override
        public void onClick(View v) {
            if (mContactId < 0) {
                return;
            }
            Context context = mItemView.getContext();
            Intent intent = new Intent(context, ContactDetailActivity.class)
                    .putExtra(ContactDetailActivity.CONTACT_ID, mContactId);
            context.startActivity(intent);
        }
    }

    private class ContactViewHolderAdapter extends RecyclerView.Adapter<ContactViewHolder> {

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ContactsListActivity.this)
                    .inflate(R.layout.contacts_item, parent, false);
            return new ContactViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            Contact contact = mStore.get(position);
            holder.mNameView.setText(contact.mName);
            holder.mPhoneNumberView.setText(contact.mPhoneNumber);
            holder.mClickListener.mContactId = contact.mId;
        }

        @Override
        public int getItemCount() {
            return mStore.size();
        }

        @Override
        public long getItemId(int position) {
            return mStore.get(position).mId;
        }
    }

    private static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameView;
        private final TextView mPhoneNumberView;
        private final ContactOnClickListener mClickListener;

        public ContactViewHolder(final View itemView) {
            super(itemView);

            mNameView = (TextView) itemView.findViewById(R.id.name);
            mPhoneNumberView = (TextView) itemView.findViewById(R.id.phoneNumber);

            mClickListener = new ContactOnClickListener(itemView);
            itemView.setOnClickListener(mClickListener);
        }

    }
}
