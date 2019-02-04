package com.mvptestapp.infra.database.repositories;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import com.mvptestapp.infra.database.ContentDescriptor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Base Repository Class.
 * Handles all the basic CURD operations of the Content Provider.
 * Created by hgodara on 22/03/17.
 */
public abstract class BaseRepository<T> implements IRepository<T> {

    protected WeakReference<Context> contextWk;
    protected Uri uri;

    public BaseRepository(Context context, Uri uri) {
        contextWk = new WeakReference<>(context);
        this.uri = uri;
    }

    @Override
    public long insert(T entity) throws Exception {
        Uri insertRow =
                contextWk.get().getContentResolver()
                        .insert(uri, constructContentValues(entity));

        if (null == insertRow) return -1;
        return ContentUris.parseId(insertRow);
    }

    @Override
    public int update(T entity, String whereClause, String[] whereArgs) throws Exception {
        return contextWk.get().getContentResolver()
                .update(uri, constructContentValues(entity), whereClause, whereArgs);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws Exception {
        return contextWk.get().getContentResolver()
                .applyBatch(ContentDescriptor.AUTHORITY, operations);
    }

    @Override
    public int deleteRow(String whereClause, String[] whereArgs) throws Exception {
        return contextWk.get().getContentResolver()
                .delete(uri, whereClause, whereArgs);
    }

    @Override
    public int deleteTable() throws SQLException {
        return contextWk.get().getContentResolver().delete(uri, null, null);
    }

    @Override
    public Cursor getRecordCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {
        return contextWk.get().getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public T getRecord(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {

        T entityRecords = null;
        Cursor cursor = null;

        try {

            cursor = contextWk.get().getContentResolver()
                    .query(uri, projection, selection, selectionArgs, sortOrder);

            if (cursor != null && cursor.moveToFirst())
                entityRecords = constructEntity(cursor);

        } finally {
            if (cursor != null) cursor.close();
        }

        return entityRecords;
    }

    @Override
    public ArrayList<T> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception {
        return constructEntities(contextWk.get()
                .getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder));
    }

    @Override
    public ArrayList<T> constructEntities(Cursor mCursor) throws Exception {

        ArrayList<T> entities = new ArrayList<>(0);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    entities.add(constructEntity(mCursor));
                } while (mCursor.moveToNext());
            }
        } finally {
            if (mCursor != null) mCursor.close();
        }

        return entities;
    }

    @Override
    public abstract long insertOrUpdate(T entity) throws Exception;

    @Override
    public abstract ContentValues constructContentValues(T entity) throws Exception;


    @Override
    public abstract T constructEntity(Cursor mCursor) throws Exception;

    @Override
    public abstract ArrayList<ContentProviderOperation> constructOperation(ArrayList<T> entityList) throws Exception;

    public ArrayList<Long> getTableID(String[] selectionArgs) {

        Cursor mCursor = null;

        ArrayList<Long> objects = new ArrayList<>(0);

        try {

            mCursor = contextWk.get().getContentResolver()
                    .query(uri, selectionArgs, null, null, null);

            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(selectionArgs[0]) != -1) {

                        objects.add(mCursor.getLong(mCursor.getColumnIndexOrThrow(selectionArgs[0])));

                    }
                } while (mCursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "getDataRows()", e);
        } finally {
            if (mCursor != null) mCursor.close();
        }

        return objects;
    }
}