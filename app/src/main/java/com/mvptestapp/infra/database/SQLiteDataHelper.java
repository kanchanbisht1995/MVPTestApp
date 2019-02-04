package com.mvptestapp.infra.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mvptestapp.infra.database.schemas.TableHelper;
import com.mvptestapp.infra.database.schemas.tables.UserTable;

import java.util.ArrayList;

/**
 * Database Helper Class
 * Holds the Sqlite Database connections and
 * manage the database opening, closing, updating functions.
 *
 * @author jsharma
 * Database Units Standards based on US, Blips/Points distance will be in meters.
 */
public class SQLiteDataHelper {

    //Database Version, Name and Password
    public static final String DB_NAME = "matrix.db";
    public static final int DB_VERSION = 1;
    public static int NEWEST_REVISION = 1;

    private static SQLiteDataHelper sqLiteDataHelper = null;
    private static SQLiteDatabase sqlDb = null;
    private static String TAG = SQLiteDataHelper.class.getSimpleName();

    /**
     * Get Singleton instance of DataBasePersistanceManager
     *
     * @param ctx
     * @return DataBasePersistanceManager
     */
    public static synchronized SQLiteDataHelper getInstance(Context ctx) {

        if (sqLiteDataHelper == null) {
            sqLiteDataHelper = new SQLiteDataHelper();
            sqlDb = sqLiteDataHelper.new DatabaseHelper(ctx, getTablesList()).getWritableDatabase();
        }
        if (sqlDb == null)
            sqlDb = sqLiteDataHelper.new DatabaseHelper(ctx, getTablesList()).getWritableDatabase();

        return sqLiteDataHelper;
    }

    /**
     * Object Clone is not supported
     */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Get Sqlite Database instance
     *
     * @return
     */
    public SQLiteDatabase getDatabase() {
        return sqlDb;
    }

    /**
     * SQLite Helper class for creating, updating the database
     * {@link SQLiteOpenHelper}
     *
     * @author admin
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        private ArrayList<TableHelper> tables;

        DatabaseHelper(Context context, ArrayList<TableHelper> tables) {
            super(context, DB_NAME, null, DB_VERSION);
            this.tables = tables;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (TableHelper table : tables)
                table.onCreate(db);
        }

        /**
         * using {@link //http://stephenwan.net/blog/2013/02/15/managing-sqlite-databases-in-android} for
         * Referencing to apply the database changes with proper re-usable system to handle schema,
         * management and upgrade paths for independent tables in a generic way.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG + " onUpdate ", db.toString() + ": Old Version - " + String.valueOf(oldVersion) + ": New Version - " + String.valueOf(newVersion));
            for (TableHelper table : tables)
                table.onUpgrade(db, oldVersion, newVersion);
        }

        /**
         * Close any open database object.
         */
        @Override
        public synchronized void close() {
            if (null != sqlDb)
                sqlDb.close();
            super.close();
        }
    }

    /**
     * Construct ArrayList which
     * consists all the table schema's class
     *
     * @return
     */
    private static ArrayList<TableHelper> getTablesList() {

        ArrayList<TableHelper> tables = new ArrayList<TableHelper>();

        //Add Master Tables
        tables.add(new UserTable());

        return tables;
    }
}