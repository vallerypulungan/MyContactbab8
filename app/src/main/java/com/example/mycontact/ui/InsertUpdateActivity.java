package com.example.mycontact.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycontact.R;
import com.example.mycontact.databinding.ActivityInsertUpdateBinding;
import com.example.mycontact.database.Contact;
import com.example.mycontact.repository.ViewModelFactory;

public class InsertUpdateActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "extra_contact";

    private boolean isEdit = false;
    private Contact contact;

    private ContactInsertUpdateViewModel contactInsertUpdateViewModel;
    private ActivityInsertUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactInsertUpdateViewModel = obtainViewModel(InsertUpdateActivity.this);

        contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contact != null) {
            isEdit = true;
        } else {
            contact = new Contact();
        }

        String actionBarTitle, btnTitle;
        if (isEdit) {
            actionBarTitle = "Ubah";
            btnTitle = "Perbarui";

            binding.etName.setText(contact.getName());
            binding.etNumber.setText(contact.getNumber());
            binding.etGroup.setText(contact.getGroup());
            binding.etInstagram.setText(contact.getInstagram());
        }else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnSubmit.setText(btnTitle);
        binding.btnSubmit.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String number = binding.etNumber.getText().toString().trim();
            String group = binding.etGroup.getText().toString().trim();
            String instagram = binding.etInstagram.getText().toString().trim();

            if (name.isEmpty()) {
                binding.etName.setError(getString(R.string.empty));
            } else if (number.isEmpty()) {
                binding.etNumber.setError(getString(R.string.empty));
            } else if (group.isEmpty()) {
                binding.etGroup.setError(getString(R.string.empty));
            } else if (instagram.isEmpty()) {
                binding.etInstagram.setError(getString(R.string.empty));
            } else {
                contact.setName(name);
                contact.setNumber(number);
                contact.setGroup(group);
                contact.setInstagram(instagram);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONTACT, contact);

                if (isEdit) {
                    contactInsertUpdateViewModel.update(contact);
                    showToast(getString(R.string.changed));
                } else {
                    contactInsertUpdateViewModel.insert(contact);
                    showToast(getString(R.string.added));
                }
                finish();
            }
        });

        binding.btnClear.setOnClickListener(v -> clearData());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showAlertDialogClose();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialogClose();
    }

    private void showAlertDialogClose() {
        String dialogTitle = getString(R.string.cancel);
        String dialogMessage = getString(R.string.message_cancel);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        (dialog, which) -> finish())
                .setNegativeButton(getString(R.string.no),
                        (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ContactInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactInsertUpdateViewModel.class);
    }

    public void clearData(){
        binding.etName.setText("");
        binding.etNumber.setText("");
        binding.etInstagram.setText("");
        binding.etGroup.setText("");
    }
}