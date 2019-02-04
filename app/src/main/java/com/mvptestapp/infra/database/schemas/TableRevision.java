package com.mvptestapp.infra.database.schemas;

import android.database.sqlite.SQLiteDatabase;

/**
 * Interface for holding the
 * Table single version, or revision, of the table at a given time.
 *
 */
public interface TableRevision {
	void applyRevision(SQLiteDatabase database);
	int getRevisionNumber();
}