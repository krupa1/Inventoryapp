package com.example.krupa.inventoryapp.data;

/**
 * Created by krupa on 3/9/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public final class ItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "storehouse.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ItemDbHelper}
     *
     * @param context of the app
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " +
                ItemContract.ItemEntry.TABLE_NAME + "(" +
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_ITEM_DESCR + " TEXT, " +
                ItemContract.ItemEntry.COLUMN_ITEM_STATE + " INTEGER NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_ITEM_PRICE + " REAL NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_SUPPLIER_NAME + " TEXT, " +
                ItemContract.ItemEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_ITEM_PICTURE + " TEXT NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER DEFAULT 0);";
        Log.v(LOG_TAG, SQL_CREATE_ITEMS_TABLE);

        /**
         * Create table
         */
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
