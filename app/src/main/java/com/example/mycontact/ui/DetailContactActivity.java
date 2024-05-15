package com.example.mycontact.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mycontact.databinding.ActivityDetailContactBinding;
import com.example.mycontact.database.Contact;


public class DetailContactActivity extends AppCompatActivity {

    ActivityDetailContactBinding binding;
    public static final String EXTRA_CONTACT = "extra_contact";
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Contact");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contact = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contact != null) {
            binding.tvName.setText(contact.getName());
            binding.tvNumber.setText(contact.getNumber());
            binding.tvGroup.setText(contact.getGroup());
            binding.tvInstagram.setText(contact.getInstagram());

            binding.btnCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:"+contact.getNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(intent);
            });

            binding.btnMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setData(Uri.parse("sms:"+contact.getNumber()));
                startActivity(intent);
            });

            binding.layoutWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone="+contact.getNumber()+"&text=";
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);
            });

            binding.btnInstagram.setOnClickListener(v -> {
                startActivity(new Intent(
                        Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+contact.getInstagram())
                ));
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}