package com.example.krupa.inventoryapp.data;



import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ItemContract {

    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract() {
    }


    /**
     * Building URI
     */
    // authority
    public static final String CONTENT_AUTHORITY = "com.example.android.product";
    // base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // path to table name
    public static final String PATH_ITEMS = "items";

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */

    public static abstract class ItemEntry implements BaseColumns {

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;


        /**
         * The content URI to access the item data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /**
         * Name of database table for items
         */
        public final static String TABLE_NAME = "items";

        /**
         * Unique ID number for the items (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_NAME = "name";

        /**
         * Model of the item.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ITEM_DESCR = "descr";

        /**
         * Status of the item.
         * <p>
         * The only possible values are {@link #STATUS_UNKNOWN}, {@link #STATUS_NEW},
         * or {@link #STATUS_USED}.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_STATE = "status";

        /**
         * Quantity of the item.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        /**
         * Quantity of the item.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_ITEM_PICTURE = "picture";

        /**
         * Price of the item.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ITEM_PRICE = "price";

        /**
         * Name of the supplier.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplierName";

        /**
         * Name of the supplier.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_EMAIL = "supplierEmail";

        /**
         * Possible values for the status of the item.
         */
        public static final int STATUS_UNKNOWN = 0;
        public static final int STATUS_NEW = 1;
        public static final int STATUS_USED = 2;

        /**
         * Returns whether or not the given status is {@link #STATUS_UNKNOWN}, {@link #STATUS_NEW},
         * or {@link #STATUS_USED}.
         */
        public static boolean isValidState(int state) {
            if (state == STATUS_UNKNOWN || state == STATUS_NEW || state == STATUS_USED) {
                return true;
            }
            return false;
        }
    }

}
