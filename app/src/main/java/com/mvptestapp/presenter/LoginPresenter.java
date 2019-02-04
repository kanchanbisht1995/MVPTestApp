/*
 *
 *  * Copyright (C) 2018 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.mvptestapp.presenter;

import android.text.TextUtils;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.model.LoginResponseModel;
import com.mvptestapp.infra.application.MyApplicationClass;
import com.mvptestapp.infra.database.repositories.repos.UserRepo;
import com.mvptestapp.infra.utils.AppConstants;
import com.mvptestapp.infra.webservices.apihelperinitiator.APIHelperInitiator;
import com.mvptestapp.ui.utils.Validator;
import com.mvptestapp.view.LoginView;

import static com.mvptestapp.infra.application.MyApplicationClass.mvpApp;

public class LoginPresenter {

    private LoginView loginView;
    private String[] params;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        login(username, password);
    }

    public void onDestroy() {
        loginView = null;
    }

    public void login(final String username, final String password) {
        if (TextUtils.isEmpty(username)) {
            if (loginView != null) {
                loginView.setUsernameError(AppConstants.EMPTY_EMAIl);
                loginView.hideProgress();
            }
            return;
        }

        if (!Validator.isValidEmail(username)) {
            if (loginView != null) {
                loginView.setUsernameError(AppConstants.INAVLID_EMAIl);
                loginView.hideProgress();
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (loginView != null) {
                loginView.setPasswordError();
                loginView.hideProgress();
            }
            return;
        }
        params = new String[]{username, password};
        if (loginView != null) {
            loginApi();
        }
    }

    private void loginApi() {
        APIHelperInitiator.initiateLogin(MyApplicationClass.mvpApp, 2, new RestApiCallback<LoginResponseModel>() {
            @Override
            public void onResponse(LoginResponseModel response) {
                if (response != null) {
                    if (loginView != null) {

                        //deleting table for new login
                        new UserRepo(mvpApp).deleteTable();

                        loginView.hideProgress();
                        loginView.navigateToHome();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (loginView != null) {
                    loginView.hideProgress();
                }
                //Toast.makeText(HomeNewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, params);
    }
}
