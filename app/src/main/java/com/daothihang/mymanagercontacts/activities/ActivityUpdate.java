package com.daothihang.mymanagercontacts.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daothihang.mymanagercontacts.R;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

public class ActivityUpdate extends AppCompatActivity {
    private EditText editName, editPhone, editAddress, editAvartar;
    private Button btnUpdate, btnHuy;
    private DatabaseContacts databaseContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findId();
        loadData();
    }


    private void loadData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("iduser");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String avartar = intent.getStringExtra("avartar");
        editName.setText(name);
        editPhone.setText(phone);
        editAddress.setText(address);
        editAvartar.setText(avartar);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseContacts = new DatabaseContacts(getApplicationContext());
                databaseContacts.updateUsers(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString(), editAvartar.getText().toString(), id);
                finish();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void findId() {
        editName = findViewById(R.id.edit_suaName);
        editPhone = findViewById(R.id.edit_suaPhone);
        editAddress = findViewById(R.id.edit_suaAddress);
        editAvartar = findViewById(R.id.edit_suaAvartar);
        btnUpdate = findViewById(R.id.btn_update);
        btnHuy = findViewById(R.id.btn_huybosua);

    }
}
