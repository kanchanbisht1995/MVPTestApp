package com.mvptestapp.infra.database.repositories;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public interface IRepository<T> {
	
	long insert(T entity) throws Exception;
	int update(T entity, String whereClause, String[] whereArgs) throws Exception;
	ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws Exception;
	int deleteRow(String whereClause, String[] whereArgs) throws Exception;
	int deleteTable() throws Exception;
	T getRecord(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception;
	ArrayList<T> getRecords(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception;
	Cursor getRecordCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) throws Exception;

	long insertOrUpdate(T entity) throws Exception;
	ContentValues constructContentValues(T entity) throws Exception;
	ArrayList<T> constructEntities(Cursor mCursor) throws Exception;
	T constructEntity(Cursor mCursor) throws Exception;
	ArrayList<ContentProviderOperation> constructOperation(ArrayList<T> entityList) throws Exception;
}