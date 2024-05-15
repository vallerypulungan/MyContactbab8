package com.example.mycontact.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontact.database.Contact;
import com.example.mycontact.helper.ContactDiffCallback;
import com.example.mycontact.repository.ViewModelFactory;
import com.example.mycontact.R;
import com.example.mycontact.databinding.ItemContactBinding;

import java.util.ArrayList;
import java.util.List;



public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final ArrayList<Contact> contactList = new ArrayList<>();
    private ContactInsertUpdateViewModel contactInsertUpdateViewModel;

    void setListContact(List<Contact> listContact){
        final ContactDiffCallback diffCallback = new ContactDiffCallback(this.contactList, listContact);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.contactList.clear();
        this.contactList.addAll(listContact);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        final ItemContactBinding binding;
        public ContactViewHolder(ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Contact contact) {
            binding.tvName.setText(contact.getName());
            binding.tvNumber.setText(contact.getNumber());
            binding.tvEdit.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), InsertUpdateActivity.class);

                intent.putExtra(InsertUpdateActivity.EXTRA_CONTACT, contact);
                v.getContext().startActivity(intent);
            });

            binding.tvDelete.setOnClickListener(v -> {
                contactInsertUpdateViewModel = obtainViewModel((AppCompatActivity) v.getContext());

                String dialogTitle = (v.getContext()).getString(R.string.delete);
                String dialogMessage = (v.getContext()).getString(R.string.message_delete);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setTitle(dialogTitle)
                        .setMessage(dialogMessage)
                        .setCancelable(false)
                        .setPositiveButton((v.getContext()).getString(R.string.yes),
                                (dialog, which) -> {
                                    contactInsertUpdateViewModel.delete(contact);
                                    Toast.makeText(v.getContext(), (v.getContext()).getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                                })
                        .setNegativeButton((v.getContext()).getString(R.string.no),
                                (dialog, which) -> dialog.cancel());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });

            binding.tvCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:"+contact.getNumber()));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                v.getContext().startActivity(intent);
            });

            binding.tvMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setData(Uri.parse("sms:"+contact.getNumber()));
                v.getContext().startActivity(intent);
            });

            binding.tvWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone="+contact.getNumber()+"&text=";
                sendIntent.setData(Uri.parse(url));
                v.getContext().startActivity(sendIntent);
            });

            binding.contactLayout.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetailContactActivity.class);

                intent.putExtra(DetailContactActivity.EXTRA_CONTACT, contact);
                v.getContext().startActivity(intent);
            });
        }
    }

    @NonNull
    private static ContactInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactInsertUpdateViewModel.class);
    }
}