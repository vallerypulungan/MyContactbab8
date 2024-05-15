package com.example.mycontact.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mycontact.databinding.ActivityMainBinding;
import com.example.mycontact.repository.ViewModelFactory;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ContactMainViewModel contactMainViewModel = obtainViewModel(MainActivity.this);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        contactMainViewModel.getAllContacts().observe(this, contacts -> {
            if (contacts != null) {
                adapter.setListContact(contacts);
            }
        });

        adapter = new ContactAdapter();
        binding.recycleContact.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleContact.setHasFixedSize(true);
        binding.recycleContact.setAdapter(adapter);

        binding.tvOption.setOnClickListener(v -> {
            Intent intent = new Intent(this, InsertUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ContactMainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactMainViewModel.class);
    }
}
