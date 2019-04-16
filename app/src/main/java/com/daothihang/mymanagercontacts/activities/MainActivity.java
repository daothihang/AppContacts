package com.daothihang.mymanagercontacts.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.daothihang.mymanagercontacts.LoadSearchView;
import com.daothihang.mymanagercontacts.R;
import com.daothihang.mymanagercontacts.adapters.AdapterUser;
import com.daothihang.mymanagercontacts.models.User;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadSearchView {
    RecyclerView mRecyclerView;
    AdapterUser recyclerviewAdapterUser;
    private EditText inputSearch;
    ArrayList<User> data;
    DatabaseContacts database;
    //ImageView imgBack;
    Context thiscontext;
    private ImageView imgAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        loadData();
    }

    private void findId() {
        imgAdd=findViewById(R.id.imgAdd);
        inputSearch=findViewById(R.id.edit_search);
        inputSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(inputSearch, InputMethodManager.SHOW_IMPLICIT);
                }
                return true;
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerviewAdapterUser.getFilter().filter(inputSearch.getText().toString());

            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityAddContacts.class);
                startActivity(intent);
            }
        });
    }


    private void loadData() {
        thiscontext = getApplicationContext();
        database = new DatabaseContacts(thiscontext);
        data = database.getUsers();
        mRecyclerView = findViewById(R.id.rv_contacts);
        recyclerviewAdapterUser = new AdapterUser(thiscontext, data,database);
        mRecyclerView.setLayoutManager(new GridLayoutManager(thiscontext, 1));
        mRecyclerView.setAdapter(recyclerviewAdapterUser);

    }

    @Override
    public void LoadSearchView(String key) {
        recyclerviewAdapterUser.getFilter().filter(key);
    }
}
