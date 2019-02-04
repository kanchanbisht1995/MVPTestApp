package com.mvptestapp.infra.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mvptestapp.infra.database.schemas.tables.UserTable;

/**
 * Content Provider Class.
 * Manages all the Database basic operations.
 * Created by hgodara on 22/03/17.
 */
public class AppContentProvider extends ContentProvider {

    //Defines the URI which is not supported by us.
    private static final String UNKNOWN_URI = "Unknown URI";

    private SQLiteDatabase sqlDb;

    @Override
    public boolean onCreate() {

        SQLiteDataHelper dbHelper = SQLiteDataHelper.getInstance(getContext());
        sqlDb = dbHelper.getDatabase();

        sqlDb.execSQL("PRAGMA foreign_keys=ON;");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {

        return doQuery(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                projection, selection, selectionArgs,
                sortOrder);

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return doInsert(sqlDb,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                uri, values);

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        sqlDb.beginTransaction();

        for (ContentValues cv : values) {
            sqlDb.insert(
                    getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                    null, cv);
        }

        sqlDb.setTransactionSuccessful();
        sqlDb.endTransaction();

        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return doDelete(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                selection, selectionArgs);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return doUpdate(sqlDb, uri,
                getTableName(ContentDescriptor.URI_MATCHER.match(uri), uri),
                selection, selectionArgs, values);

    }

    //
    private Cursor doQuery(SQLiteDatabase db, Uri uri, String tableName, String[] projection,
                           String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor result = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    private int doUpdate(SQLiteDatabase db, Uri uri, String tableName, String selection,
                         String[] selectionArgs, ContentValues values) {
        int result = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private int doDelete(SQLiteDatabase db, Uri uri, String tableName, String selection,
                         String[] selectionArgs) {
        int result = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private Uri doInsert(SQLiteDatabase db, String tableName, Uri contentUri, ContentValues values) {
        long id = db.insert(tableName, null, values);
        Uri result = contentUri.buildUpon().appendPath(String.valueOf(id)).build();
        getContext().getContentResolver().notifyChange(contentUri, null);
        return result;
    }

    private String getTableName(int token, Uri uri) {

        switch (token) {
            case UserTable.PATH_TOKEN:
                return UserTable.TABLE_NAME;
            default:
                throw new IllegalArgumentException(UNKNOWN_URI + uri);
        }

    }
}
