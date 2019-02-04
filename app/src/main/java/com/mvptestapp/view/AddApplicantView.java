package com.mvptestapp.view;

public interface AddApplicantView {

    void showProgress();

    void hideProgress();

    void setFirstnameError();
    void setLastnameError();
    void setEmailError(int emailErrorType);
    void setLoanAmount(int emailErrorType);
    void setPanCard();
    void seVoterCard();
    void setAdhaarCard();


    void navigateToAddAppplicant();
}
