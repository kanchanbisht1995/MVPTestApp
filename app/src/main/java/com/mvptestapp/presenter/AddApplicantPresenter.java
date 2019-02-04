package com.mvptestapp.presenter;

import android.text.TextUtils;

import com.mvptestapp.core.model.UserListData;
import com.mvptestapp.infra.database.repositories.repos.UserRepo;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.ui.utils.Validator;
import com.mvptestapp.view.AddApplicantView;

import java.util.Calendar;

import static com.mvptestapp.infra.application.MyApplicationClass.mvpApp;

public class AddApplicantPresenter {

    private AddApplicantView applicantView;

    public AddApplicantPresenter(AddApplicantView applicantView) {
        this.applicantView = applicantView;
    }

    public void validateCredentials(String firstname, String lastname, String emailid,
                                    String loanamount, String pancard, String adhaar,
                                    String voterId) {
        if (applicantView != null) {
            applicantView.showProgress();
        }

        //validations as per requirement
        checkValidations(firstname, lastname, emailid, loanamount, pancard, adhaar, voterId);
    }

    public void checkValidations(String firstname, String lastname, String emailid,
                                 String loanamount, String pancard, final String adhaar,
                                 final String voterId) {


        if (TextUtils.isEmpty(firstname)) {
            if (applicantView != null) {
                applicantView.setFirstnameError(AppConstants.EMPTY);
                applicantView.hideProgress();
            }
            return;
        }

        if (firstname.length()<3) {
            if (applicantView != null) {
                applicantView.setFirstnameError(AppConstants.LESS_THAN_3);
                applicantView.hideProgress();
            }
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            if (applicantView != null) {
                applicantView.setLastnameError(AppConstants.EMPTY);
                applicantView.hideProgress();
            }
            return;
        }

        if (lastname.length()<3) {
            if (applicantView != null) {
                applicantView.setLastnameError(AppConstants.LESS_THAN_3);
                applicantView.hideProgress();
            }
            return;
        }

        if (TextUtils.isEmpty(emailid)) {


            if (applicantView != null) {
                applicantView.setEmailError(AppConstants.EMPTY);
                applicantView.hideProgress();
            }
            return;
        }
        if (!Validator.isValidEmail(emailid)) {
            if (applicantView != null) {
                applicantView.setEmailError(AppConstants.INAVLID_EMAIl);
                applicantView.hideProgress();
            }
            return;
        }
        if (TextUtils.isEmpty(loanamount)) {
            if (applicantView != null) {
                applicantView.setLoanAmount(AppConstants.EMPTY);
                applicantView.hideProgress();
            }
            return;
        }
        if (Integer.parseInt(loanamount) < 30000 && TextUtils.isEmpty(pancard) && TextUtils.isEmpty(adhaar) && TextUtils.isEmpty(voterId)) {

            if (applicantView != null) {
                applicantView.setLoanAmount(AppConstants.LOAN_ANY_REQ);
                applicantView.hideProgress();
            }
            return;

        }
        if (Integer.parseInt(loanamount) >= 30000 && Integer.parseInt(loanamount) < 50000 &&
                TextUtils.isEmpty(pancard) && TextUtils.isEmpty(adhaar)) {

            if (applicantView != null) {
                applicantView.setLoanAmount(AppConstants.LOAN_PAN_OR_ADHAAR);
                applicantView.hideProgress();

            }
            return;

        }

        if (Integer.parseInt(loanamount) >= 50000 && TextUtils.isEmpty(pancard)) {
            if (applicantView != null) {
                applicantView.setLoanAmount(AppConstants.LOAN_PAN_REQ);
                applicantView.hideProgress();
            }
            return;
        }
        if (Integer.parseInt(loanamount) > 100000) {
            if (applicantView != null) {
                applicantView.setLoanAmount(AppConstants.LOAN_GREATER);
                applicantView.hideProgress();
            }
            return;
        }

        if (!TextUtils.isEmpty(pancard) && !Validator.pancardValidatore(pancard)) {

            if (applicantView != null) {
                applicantView.setPanCard();
                applicantView.hideProgress();
            }
            return;
        }
        if (!TextUtils.isEmpty(adhaar) && adhaar.length() < 12) {
            if (applicantView != null) {
                applicantView.setAdhaarCard();
                applicantView.hideProgress();
            }
            return;
        }

        if (!TextUtils.isEmpty(voterId) && !Validator.voterIdValidatore(voterId)) {

            if (applicantView != null) {
                applicantView.seVoterCard();
                applicantView.hideProgress();
            }
            return;
        }
        saveApplicantDAta(firstname, lastname, loanamount);

    }


    private void saveApplicantDAta(String firstname, String lastname, String loanamount) {

        //saving new user details in Database
        UserListData userListData = new UserListData(Calendar.getInstance().getTimeInMillis(), firstname,
                lastname, "", Long.parseLong(loanamount));
        try {
            new UserRepo(mvpApp).insert(userListData);
            if (applicantView != null) {
                applicantView.hideProgress();
                applicantView.navigateToAddAppplicant();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
