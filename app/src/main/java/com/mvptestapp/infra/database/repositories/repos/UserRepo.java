package com.mvptestapp.infra.database.repositories.repos;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.mvptestapp.core.localexception.RequestFailureException;
import com.mvptestapp.core.model.UserListData;
import com.mvptestapp.infra.database.repositories.BaseRepository;
import com.mvptestapp.infra.database.schemas.tables.UserTable;

import java.util.ArrayList;

public class UserRepo extends BaseRepository<UserListData> {

    public UserRepo(Context context) {
        super(context, UserTable.CONTENT_URI);
    }

    @Override
    public long insertOrUpdate(UserListData entity) throws Exception {
        ArrayList<Integer> objects = getUserIDs(new String[]{UserTable.Cols.USER_ID});
        if (objects.contains(entity.getId())) return insert(entity);
        else return update(entity, UserTable.Cols.USER_ID + "=?",
                new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public ContentValues constructContentValues(UserListData entity) throws Exception {
        ContentValues values = new ContentValues();
        if (null == entity || entity.getId() <= 0)
            throw new RequestFailureException("Invalid data model.");
        values.put(UserTable.Cols.USER_ID, entity.getId());
        values.put(UserTable.Cols.LAST_NAME, entity.getLastName());
        values.put(UserTable.Cols.FIRST_NAME, entity.getFirstName());
        values.put(UserTable.Cols.PROFILE_PIC_URL, entity.getAvatar());
        if (entity.getLoanAmount() != null) {
            values.put(UserTable.Cols.LOAN, entity.getLoanAmount());
        }

        return values;
    }

    @Override
    public UserListData constructEntity(Cursor mCursor) throws Exception {
        return new UserListData(mCursor.getLong(mCursor.getColumnIndexOrThrow(UserTable.Cols.USER_ID)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(UserTable.Cols.FIRST_NAME)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(UserTable.Cols.LAST_NAME)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(UserTable.Cols.PROFILE_PIC_URL)),
                mCursor.getLong(mCursor.getColumnIndexOrThrow(UserTable.Cols.LOAN)));
    }

    @Override
    public ArrayList<ContentProviderOperation> constructOperation(ArrayList<UserListData> entityList) throws Exception {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>(0);

        ArrayList<Integer> objects = getUserIDs(new String[]{UserTable.Cols.USER_ID});
        for (UserListData entity : entityList) {

            ContentProviderOperation.Builder contentProviderOperation;

            if (objects.contains(entity.getId())) {
                contentProviderOperation = ContentProviderOperation.newUpdate(uri)
                        .withSelection(UserTable.Cols.USER_ID + "=?",
                                new String[]{String.valueOf(entity.getId())});
            } else {
                contentProviderOperation = ContentProviderOperation.newInsert(uri);
            }

            contentProviderOperation
                    .withValue(UserTable.Cols.USER_ID, entity.getId())
                    .withValue(UserTable.Cols.FIRST_NAME, entity.getFirstName())
                    .withValue(UserTable.Cols.LAST_NAME, entity.getLastName())
                    .withValue(UserTable.Cols.PROFILE_PIC_URL, entity.getAvatar())
                    .withValue(UserTable.Cols.LOAN, entity.getLoanAmount());

            operations.add(contentProviderOperation.build());
        }

        return operations;
    }

    public ArrayList<Integer> getUserIDs(String[] selectionArgs) {

        Cursor mCursor = null;

        ArrayList<Integer> objects = new ArrayList<>(0);

        try {

            mCursor = contextWk.get().getContentResolver()
                    .query(uri, selectionArgs, null, null, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    if (mCursor.getColumnIndex(selectionArgs[0]) != -1) {
                        objects.add(mCursor.getInt(mCursor.getColumnIndexOrThrow(selectionArgs[0])));
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
