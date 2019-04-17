package com.daothihang.mymanagercontacts.activities;

import android.app.Activity;
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
import com.daothihang.mymanagercontacts.UpdateDelete;
import com.daothihang.mymanagercontacts.adapters.AdapterUser;
import com.daothihang.mymanagercontacts.models.User;
import com.daothihang.mymanagercontacts.untils.DatabaseContacts;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadSearchView, UpdateDelete {
    RecyclerView mRecyclerView;
    AdapterUser recyclerviewAdapterUser;
    private EditText inputSearch;
    ArrayList<User> listData;
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
        imgAdd = findViewById(R.id.imgAdd);
        inputSearch = findViewById(R.id.edit_search);
        inputSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                Intent intent = new Intent(MainActivity.this, ActivityAddContacts.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    private static final int REQUEST_CODE_ADD = 101;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD && resultCode== Activity.RESULT_OK){
            //User userAdded= data.getParcelableExtra("DATA");
            User userAdded= (User) data.getSerializableExtra("DATA");
            listData.add(userAdded);
            recyclerviewAdapterUser.notifyItemInserted(listData.size()-1);
        }
    }

    private void loadData() {
        thiscontext = getApplicationContext();
        database = new DatabaseContacts(thiscontext);
        listData = database.getUsers();
        mRecyclerView = findViewById(R.id.rv_contacts);
        recyclerviewAdapterUser = new AdapterUser(thiscontext, listData, database, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(thiscontext, 1));
        mRecyclerView.setAdapter(recyclerviewAdapterUser);

    }

    @Override
    public void LoadSearchView(String key) {
        recyclerviewAdapterUser.getFilter().filter(key);
    }

    @Override
    public void update(boolean isUpdate) {
        if (isUpdate){
            loadData();
        }
    }
}
