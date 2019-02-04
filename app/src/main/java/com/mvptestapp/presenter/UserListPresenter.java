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

import android.widget.Toast;

import com.mvptestapp.core.callbacks.RestApiCallback;
import com.mvptestapp.core.model.UserListData;
import com.mvptestapp.core.model.UserListResponseModel;
import com.mvptestapp.infra.database.repositories.repos.UserRepo;
import com.mvptestapp.infra.webservices.apihelperinitiator.APIHelperInitiator;
import com.mvptestapp.view.UserListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.mvptestapp.infra.application.MyApplicationClass.mvpApp;

public class UserListPresenter {

    private UserListView mainView;

    public UserListPresenter(UserListView mainView) {
        this.mainView = mainView;
    }

    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }
        userListData();
    }


    public void onDestroy() {
        mainView = null;
    }


    private void userListData() {
        String[] params = new String[]{};
        APIHelperInitiator.getUserList(mvpApp, 2, new RestApiCallback<UserListResponseModel>() {
            @Override
            public void onResponse(UserListResponseModel response) {
                if (response != null) {
                    if (mainView != null) {
                        if (response.getData().size() > 0) {

                            ArrayList<UserListData> userListData = response.getData();
                            for (int i = 0; i < userListData.size(); i++) {
                                try {

                                    new UserRepo(mvpApp).insert(response.getData().get(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        try {
                            ArrayList<UserListData> userListData = new UserRepo(mvpApp)
                                    .getRecords(null, null, null, null);
                            if (userListData != null) {
                                if (userListData.size() > 0) {

                                    //array list sorted in descending order/
                                    Collections.sort(userListData, new Comparator<UserListData>() {
                                        @Override
                                        public int compare(UserListData lhs, UserListData rhs) {
                                            return lhs.getLoanAmount().compareTo(rhs.getLoanAmount());
                                        }
                                    });
                                    Collections.reverse(userListData);
                                    mainView.setItems(userListData);
                                    mainView.hideProgress();

                                }
                            } else {
                                userListData = response.getData();
                                //array list sorted in descending order/
                                Collections.sort(userListData, new Comparator<UserListData>() {
                                    @Override
                                    public int compare(UserListData lhs, UserListData rhs) {
                                        return lhs.getLoanAmount().compareTo(rhs.getLoanAmount());
                                    }
                                });

                                // Collections.reverse(userListData);
                                mainView.setItems(userListData);
                                mainView.hideProgress();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mainView != null) {
                    mainView.hideProgress();
                }
                Toast.makeText(mvpApp, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, params);
    }

}
