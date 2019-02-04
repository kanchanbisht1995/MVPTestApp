package com.mvptestapp.view;

public interface AddApplicantView {

    void showProgress();

    void hideProgress();

    void setFirstnameError(int emailErrorType);
    void setLastnameError(int emailErrorType);
    void setEmailError(int emailErrorType);
    void setLoanAmount(int emailErrorType);
    void setPanCard();
    void seVoterCard();
    void setAdhaarCard();


    void navigateToAddAppplicant();
}
