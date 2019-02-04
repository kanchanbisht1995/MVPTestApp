package com.mvptestapp.infra.database.schemas.tables;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mvptestapp.infra.database.ContentDescriptor;
import com.mvptestapp.infra.database.schemas.TableHelper;
import com.mvptestapp.infra.database.schemas.TableRevision;

public class UserTable extends TableHelper {

    public static final String TABLE_NAME = "user";
    public static final int PATH_TOKEN = 1;

    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(TABLE_NAME).build();


    public static class Cols {
        public static final String USER_ID = "user_id";
        public static final String LAST_NAME = "lastname";
        public static final String FIRST_NAME = "firstname";
        public static final String PROFILE_PIC_URL = "profile_pic_url";
        public static final String LOAN = "loan";
    }

    public UserTable() {

        registerRevision(new TableRevision() {
            private final String TABLE_CREATE =
                    "create table if not exists "
                            + TABLE_NAME + "("
                            + Cols.USER_ID + " LONG PRIMARY KEY NOT NULL, "
                            + Cols.FIRST_NAME + "  TEXT, "
                            + Cols.LAST_NAME + " TEXT, "
                            + Cols.LOAN + " LONG, "
                            + Cols.PROFILE_PIC_URL + " TEXT "
                            + ");";

            @Override
            public void applyRevision(SQLiteDatabase database) {
                database.execSQL(TABLE_CREATE);
            }

            @Override
            public int getRevisionNumber() {
                return 1;
            }
        });
    }
}
