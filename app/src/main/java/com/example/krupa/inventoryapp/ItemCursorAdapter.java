package com.example.krupa.inventoryapp;

/**
 * Created by krupa on 3/9/17.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

import com.example.krupa.inventoryapp.data.ItemContract;




/**
 * {@link ItemCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of item data as its data source. This adapter knows
 * how to create list items for each row of item data in the {@link Cursor}.
 */
public class ItemCursorAdapter extends CursorAdapter {


    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.item_name);
        TextView DescrTextView = (TextView) view.findViewById(R.id.item_descr);
        TextView priceTextView = (TextView) view.findViewById(R.id.item_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.item_current_quantity);
        ImageView photoImageView = (ImageView) view.findViewById(R.id.item_image);
        ImageButton buyImageButton = (ImageButton) view.findViewById(R.id.item_buy_button);

        // Find the columns of item attributes that we're interested in
        final int itemIdColumnIndex = cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        int DescrColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_DESCR);
        int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        int photoColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PICTURE);

        // Read the item attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        String itemDescr = cursor.getString(DescrColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        final int quantityItem = cursor.getInt(quantityColumnIndex);
        String imageUriString = cursor.getString(photoColumnIndex);
        Uri itemImageUri = Uri.parse(imageUriString);

        // If the item descr is empty string or null, then hide TextView
        if (TextUtils.isEmpty(itemDescr)) {
            DescrTextView.setVisibility(View.GONE);
        }

        // Update the TextViews with the attributes for the current item
        nameTextView.setText(itemName);
        DescrTextView.setText(itemDescr);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(String.valueOf(quantityItem));
        photoImageView.setImageURI(itemImageUri);

        buyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri itemUri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI, itemIdColumnIndex);
                adjustProductQuantity(context, itemUri, quantityItem);
            }
        });
    }

    /**
     * This method reduced item stock by 1
     *
     * @param context                - Activity context
     * @param itemUri             - Uri used to update the stock of a specific item in the ListView
     * @param currentQuantityInStock - current stock of that specific item
     */
    private void adjustProductQuantity(Context context, Uri itemUri, int currentQuantityInStock) {

        // Subtract 1 from current value if quantity of item >= 1
        int newQuantityValue = (currentQuantityInStock >= 1) ? currentQuantityInStock - 1 : 0;

        if (currentQuantityInStock == 0) {
            Toast.makeText(context.getApplicationContext(), R.string.toast_out_of_stock_msg, Toast.LENGTH_SHORT).show();
        }

        // Update table by using new value of quantity
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, newQuantityValue);
        int numRowsUpdated = context.getContentResolver().update(itemUri, contentValues, null, null);
        if (numRowsUpdated > 0) {
            // Show error message in Logs with info about pass update.
            Log.i(TAG, context.getString(R.string.buy_msg_confirm));
        } else {
            Toast.makeText(context.getApplicationContext(), R.string.no_item_in_stock, Toast.LENGTH_SHORT).show();
            // Show error message in Logs with info about fail update.
            Log.e(TAG, context.getString(R.string.error_msg_stock_update));
        }


    }
}
