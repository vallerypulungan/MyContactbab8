package com.example.mycontact.UI.contact;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.mycontact.MyApp ;
import com.example.mycontact.R;
import com.example.mycontact.database.ContactDao ;
import com.example.mycontact.model.Contact ;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView option;
    private LinearLayout layAddContact;
    private EditText etName, etNumber, etInstagram, etGroup;
    private Button btnClear, btnSubmit;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // disini berfungsi buat inisiasi database
        contactDao = MyApp.getInstance().getContactDatabase().contactDao();

        recyclerView = findViewById(R.id.recycle_contact);
        recyclerView.setHasFixedSize(true);
        layAddContact = findViewById(R.id.layout_add);
        option = findViewById(R.id.tv_option);
        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        etInstagram = findViewById(R.id.et_instagram);
        etGroup = findViewById(R.id.et_group);
        btnClear = findViewById(R.id.btn_clear);
        btnSubmit = findViewById(R.id.btn_submit);

        // Setup RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ContactActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        option.setOnClickListener(v -> {
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
                layAddContact.setVisibility(View.VISIBLE);
                clearData();
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);
            }
        });

        btnClear.setOnClickListener(v -> clearData());

        btnSubmit.setOnClickListener(v -> {
            if (etName.getText().toString().equals("") || etNumber.getText().toString().equals("") ||
                    etInstagram.getText().toString().equals("") || etGroup.getText().toString().equals("")) {
                Toast.makeText(this, "Please fill in the entire form", Toast.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact(etName.getText().toString(), etNumber.getText().toString(),
                        etGroup.getText().toString(), etInstagram.getText().toString());
                contactDao.insert(contact);
                loadContacts();
                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);
            }
        });

        loadContacts();
    }

    private void loadContacts() {
        contactList.clear();
        contactList.addAll(contactDao.getAll());
        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(contactAdapter);
    }

    public void clearData() {
        etName.setText("");
        etNumber.setText("");
        etInstagram.setText("");
        etGroup.setText("");
    }
}
