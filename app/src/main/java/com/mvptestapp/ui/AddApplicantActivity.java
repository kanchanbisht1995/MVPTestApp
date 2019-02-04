package com.mvptestapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.luckey.mvptestapplication.R;
import com.mvptestapp.core.model.UserListData;
import com.mvptestapp.infra.database.repositories.repos.UserRepo;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.presenter.AddApplicantPresenter;
import com.mvptestapp.view.AddApplicantView;

import java.util.ArrayList;

public class AddApplicantActivity extends AppCompatActivity implements AddApplicantView {

    private ProgressBar progressBar;
    private EditText etFirstname, etEmailId, etLastName;
    private EditText etLoanValue, etPanNo, etAdhar, etVoterId;
    private AddApplicantPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_applicant);

        etFirstname = findViewById(R.id.etFirstname);
        etEmailId = findViewById(R.id.etEmailId);
        etLastName = findViewById(R.id.etLastName);
        etLoanValue = findViewById(R.id.etLoanValue);
        etAdhar = findViewById(R.id.etAdhar);
        etVoterId = findViewById(R.id.etVoterId);
        etPanNo = findViewById(R.id.etPanNo);
        progressBar = findViewById(R.id.progress);
        findViewById(R.id.tvAdd).setOnClickListener(v -> validateCredentials());
        presenter = new AddApplicantPresenter(this);
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
    public void setFirstnameError() {
        etFirstname.setError(getString(R.string.cant_empty));
    }

    @Override
    public void setLastnameError() {
        etLastName.setError(getString(R.string.cant_empty));
    }

    @Override
    public void setEmailError(int emailErrorType) {
        if (emailErrorType == AppConstants.INAVLID_EMAIl) {
            etEmailId.setError(getString(R.string.valid_email));
        } else {
            etEmailId.setError(getString(R.string.cant_empty));
        }
    }

    @Override
    public void setLoanAmount(int emailErrorType) {
        if (emailErrorType == AppConstants.LOAN_GREATER) {
            etLoanValue.setError(getString(R.string.loan_cant_greater));
        } else if (emailErrorType == AppConstants.LOAN_ANY_REQ) {
            Toast.makeText(this, getString(R.string.anyoftheREquired), Toast.LENGTH_SHORT).show();
            //etLoanValue.setError(getString(R.string.loan_cant_greater));
        } else if (emailErrorType == AppConstants.LOAN_PAN_OR_ADHAAR) {
            Toast.makeText(this, getString(R.string.panoradhar), Toast.LENGTH_SHORT).show();
        } else if (emailErrorType == AppConstants.LOAN_PAN_REQ) {
            Toast.makeText(this, getString(R.string.pan_required), Toast.LENGTH_SHORT).show();
        } else {
            etLoanValue.setError(getString(R.string.cant_empty));
        }
    }

    @Override
    public void setPanCard() {
        etPanNo.setError(getString(R.string.invalid_pan));
    }

    @Override
    public void seVoterCard() {
        etVoterId.setError(getString(R.string.invalid_voter_id));
    }

    @Override
    public void setAdhaarCard() {
        etAdhar.setError(getString(R.string.invalid_adhaar));
    }

    @Override
    public void navigateToAddAppplicant() {
        try {
            ArrayList<UserListData> userListData = new UserRepo(this).getRecords(null, null, null, null);
            if (userListData != null) {
                Toast.makeText(this, getString(R.string.user_added), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserListActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void validateCredentials() {
        presenter.validateCredentials(etFirstname.getText().toString(), etLastName.getText().toString(),
                etEmailId.getText().toString(), etLoanValue.getText().toString(),
                etPanNo.getText().toString(), etAdhar.getText().toString(), etVoterId.getText().toString());
    }

}
