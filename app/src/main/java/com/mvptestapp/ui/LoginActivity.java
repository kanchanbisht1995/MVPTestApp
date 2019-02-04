package com.mvptestapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.luckey.mvptestapplication.R;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.presenter.LoginPresenter;
import com.mvptestapp.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {


    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        findViewById(R.id.tvLogin).setOnClickListener(v -> validateCredentials());
        presenter = new LoginPresenter(this);
    }

    private void validateCredentials() {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());

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
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void setUsernameError(int emailErrorType) {
        if (emailErrorType == AppConstants.INAVLID_EMAIl) {
            username.setError(getString(R.string.valid_email));
        } else {
            username.setError(getString(R.string.cant_empty));
        }


    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.cant_empty));
    }

    @Override
    public void navigateToHome() {
        Toast.makeText(this, getString(R.string.logged_in), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, UserListActivity.class));
    }
}
