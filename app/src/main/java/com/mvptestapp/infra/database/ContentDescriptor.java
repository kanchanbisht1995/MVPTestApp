package com.mvptestapp.infra.database;

import android.content.UriMatcher;
import android.net.Uri;

import com.luckey.mvptestapplication.BuildConfig;
import com.mvptestapp.infra.database.schemas.tables.UserTable;

/**
 * Defines the Authority, Base URI and validates the other URI.
 *
 * @author hgodara
 */
public class ContentDescriptor {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //Add the URIs
        matcher.addURI(AUTHORITY, UserTable.TABLE_NAME, UserTable.PATH_TOKEN);
        return matcher;
    }
}