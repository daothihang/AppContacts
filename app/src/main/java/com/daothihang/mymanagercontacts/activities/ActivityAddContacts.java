package com.daothihang.mymanagercontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daothihang.mymanagercontacts.R;
import com.daothihang.mymanagercontacts.models.User;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

public class ActivityAddContacts extends AppCompatActivity {
    private EditText editName, editPhone, editAddress, editAvartar;
    private DatabaseContacts database;
    private Button btnThem, btnhuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        findId();
        initView();

    }

    private void findId() {
        editName = findViewById(R.id.edit_addName);
        editPhone = findViewById(R.id.edit_addPhone);
        editAddress = findViewById(R.id.edit_addAddress);
        editAvartar = findViewById(R.id.edit_addAvartar);
        btnThem = findViewById(R.id.btn_them);
        btnhuy = findViewById(R.id.btn_huybo);

    }

    private void initView() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().trim().length() == 0 ||
                        editPhone.getText().toString().trim().length() == 0 ||
                        editAddress.getText().toString().trim().length() == 0 ||
                        editAddress.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String address = editAddress.getText().toString();
                String avartar = editAvartar.getText().toString();
                database.insertUsers(name, phone, address, avartar);

                Intent intent=new Intent();
                intent.putExtra("DATA",new User(""+ -1,name,phone,address,avartar));
                setResult(Activity.RESULT_OK,intent);
                finish();
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = new DatabaseContacts(getApplicationContext());

    }


}
