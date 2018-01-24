package com.kanchan.groceryapp.groceryapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kanchan.groceryapp.groceryapp.Model.Grocery;
import com.kanchan.groceryapp.groceryapp.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ganesh-Salvi on 12/22/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private Context ctx;

    public DataBaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx  = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GROCERY_TBL = "CREATE TABLE " + Constants.TABLE_NAME + "( "
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_GROCERY_ITEM + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG);" ; /*LONG*/

        //Log.d("TABLE : , ", "Creating table ...");
          db.execSQL(CREATE_GROCERY_TBL);
        //Log.d("TABLE : , ", "table created ...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);

    }

    /******************************************************************************/

    public  void addGrocery(Grocery grocery){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert a row;

        db.insert(Constants.TABLE_NAME, null,values);

        Log.d("Saved !", "Saved to DB");

    }

    public  Grocery getGrocery(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[] {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME},
                Constants.KEY_ID + " =?",
                new String[] {String.valueOf(id)}, null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getColumnName(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //Converting timestamp;
            java.text.DateFormat dateFormat= java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format((new Date(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

            grocery.setDateItemAdded(formatedDate);

            return  grocery;

    }

    public List<Grocery> getAllGroceries(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery>  groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[] {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME},
                null, null, null, null, Constants.KEY_DATE_NAME  +  " DESC");

        if (cursor.moveToFirst()){
            do{
                Grocery grocery = new Grocery();

                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity((cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))));

                //Converting TimeStamp;

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))));

                grocery.setDateItemAdded(formatedDate);

                groceryList.add(grocery);

            }while(cursor.moveToNext());

            Log.d("Added to Grocery", "Items added");
        }

        return  groceryList;
    }

    public  int updateGroceries(Grocery grocery){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());


        return db.update(Constants.TABLE_NAME, values,
                Constants.KEY_ID + "=?",  new String[] {String.valueOf(grocery.getId())});
    }

    public  void deleteGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
                new String[] { String.valueOf(id) }  );
        db.close();
    }

    public int getGroceriesCount(){

        String countQuery = "SELECT * FROM  " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery , null);

        return cursor.getCount();
    }



}
