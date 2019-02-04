package com.mvptestapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.luckey.mvptestapplication.R;
import com.mvptestapp.core.adapter.UserListAdapter;
import com.mvptestapp.core.model.UserListData;
import com.mvptestapp.presenter.UserListPresenter;
import com.mvptestapp.view.UserListView;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity implements UserListView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UserListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        findViewById(R.id.tvAddApplicant).setOnClickListener(v -> addApplicant());
        presenter = new UserListPresenter(this);
    }

    private void addApplicant() {
        startActivity(new Intent(this, AddApplicantActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setItems(ArrayList<UserListData> items) {
        UserListAdapter userListAdapter = new UserListAdapter(items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userListAdapter);

    }
}
