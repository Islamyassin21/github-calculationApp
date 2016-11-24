package com.example.islam.tawasoltaskaccountingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.islam.tawasoltaskaccountingapplication.Data.Conestance;
import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;

import java.util.ArrayList;

/**
 * Created by islam on 18/10/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public String CREATE_COSTUMER_TABLE;
    public String CREATE_TRANSACTION_TABLE;
    private final ArrayList<MyTask> datalList = new ArrayList<>();
    MyTask myTask = new MyTask();

    public DatabaseHelper(Context context) {
        super(context, Conestance.DATABASE_NAME, null, Conestance.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.v("data000", "islam yassin");
        CREATE_COSTUMER_TABLE = "CREATE TABLE " + Conestance.TABLE_Costumer +
                "(" +
                Conestance.KEY_ID + " INTEGER PRIMARY KEY," +
                Conestance.COSTUMER_NAME + " TEXT," +
                Conestance.COSTUMER_PASSWORD + " TEXT," +
                Conestance.COSTUMER_EMAIL + " TEXT," +
                Conestance.COSTUMER_PHONE + " TEXT);";
        db.execSQL(CREATE_COSTUMER_TABLE);

        Log.v("data000", "islam yassin");
        CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Conestance.TABLE_Transaction +
                " (" +
                Conestance.KEY_ID + " INTEGER PRIMARY KEY," +
                Conestance.COSTUMER_NAME + " TEXT," +
                Conestance.TRANSACTION + " TEXT," +
                Conestance.TRANSACTION_TYPE + " TEXT," +
                Conestance.TRANSACTION_AMOUNT + " TEXT," +
                Conestance.TRANSACTION_PLACE + " TEXT," +
                Conestance.TRANSACTION_DESCRIPTION + " TEXT," +
                Conestance.TRANSACTION_DAY + " INTEGER," +
                Conestance.TRANSACTION_YEAR + " INTEGER," +
                Conestance.TRANSACTION_MONTH + " INTEGER);";
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_COSTUMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TRANSACTION_TABLE);

        // create new tables
        onCreate(db);

    }

    public void deleteMyAccount(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestance.TABLE_Costumer, "Name=?", new String[]{name});
        db.delete(Conestance.TABLE_Transaction, "Name=?", new String[]{name});
    }

    public void deleteAllTransaction() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Conestance.TABLE_Transaction);
    }

    public void deleteAllCostumer() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Conestance.TABLE_Costumer);
    }

    public void AddCostumer(MyTask myTask) {
        Log.v("data000", "islam yassin");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Conestance.COSTUMER_NAME, myTask.getName());
        values.put(Conestance.COSTUMER_PASSWORD, myTask.getPassword());
        values.put(Conestance.COSTUMER_EMAIL, myTask.getEmail());
        values.put(Conestance.COSTUMER_PHONE, myTask.getPhone());

        db.insert(Conestance.TABLE_Costumer, null, values);
        db.close();
    }

    public void Addtransaction(MyTask myTask) {
        Log.v("data000", "islam yassin");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Conestance.COSTUMER_NAME, myTask.getName());
        values.put(Conestance.TRANSACTION, myTask.getTransaction());
        values.put(Conestance.TRANSACTION_AMOUNT, myTask.getAmount());
        values.put(Conestance.TRANSACTION_TYPE, myTask.getType());
        values.put(Conestance.TRANSACTION_PLACE, myTask.getPlace());
        values.put(Conestance.TRANSACTION_DESCRIPTION, myTask.getDescreption());
        values.put(Conestance.TRANSACTION_DAY, myTask.getDay());
        values.put(Conestance.TRANSACTION_MONTH, myTask.getMonth());
        values.put(Conestance.TRANSACTION_YEAR, myTask.getYear());

        db.insert(Conestance.TABLE_Transaction, null, values);
        db.close();
    }

    public void EditCostumer(MyTask myTask) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Conestance.COSTUMER_NAME, myTask.getName());
        values.put(Conestance.COSTUMER_PASSWORD, myTask.getPassword());
        values.put(Conestance.COSTUMER_EMAIL, myTask.getPassword());
        values.put(Conestance.COSTUMER_PHONE, myTask.getPhone());

        db.update(Conestance.TABLE_Costumer, values, "_id=" + myTask.getItemId(), null);

    }

    public void DeleteCostumer(MyTask myTask) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Conestance.TABLE_Costumer, "_id=" + myTask.getItemId(), null);
    }

    public ArrayList<MyTask> getOneCostumer(String name, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Costumer, null, "Name=? AND Password=? ", new String[]{name, password}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setEmail(cursor.getString(cursor.getColumnIndex(Conestance.COSTUMER_EMAIL)));
                task.setPhone(cursor.getString(cursor.getColumnIndex(Conestance.COSTUMER_PHONE)));
                datalList.add(task);

            } while ((cursor.moveToNext()));
        }

        return datalList;
    }

    public ArrayList<MyTask> getAllTransaction() {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=?", new String[]{myTask.getName()}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDate(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }

        return datalList;
    }

    public ArrayList<MyTask> getAllTransactionOrderByMonth(String userName) {

        Log.v("data22250", String.valueOf(myTask.getName()));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, Conestance.COSTUMER_NAME + "=?", new String[]{userName}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        Log.v("data222", String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> getAllTransactionOrderByType(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=?", new String[]{userName}, null, null,
                Conestance.TRANSACTION_TYPE + " ASC," + Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");

        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByIncomeTransaction(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type_Transaction=?", new String[]{userName, "income"},
                null, null, Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByExpensessTransaction(int name, String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type_Transaction=? AND Month=?",
                new String[]{userName, "Actual_Expenses", String.valueOf(name)}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByPlannedTransaction(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type_Transaction=?",
                new String[]{userName, "Planned_Expenses"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByservicesOrLease(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "services"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByClothes(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "clothes"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByDelivery(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "delevary"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByFix(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "fix"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByFood(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "food"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> FilterByOther(String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=?", new String[]{userName, "other"}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> Search(String name, String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Type=? OR Amount=? OR Place=? OR Type_Transaction=?",
                new String[]{userName, name, name, name, name}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public ArrayList<MyTask> SearchByDate(int month, String userName) {
        MyTask myTask = new MyTask();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Conestance.TABLE_Transaction, null, "Name=? AND Month=?",
                new String[]{userName, String.valueOf(month)}, null, null,
                Conestance.TRANSACTION_YEAR + " ASC," + Conestance.TRANSACTION_MONTH + " ASC," + Conestance.TRANSACTION_DAY + " ASC");
        if (cursor.moveToFirst()) {
            do {
                MyTask task = new MyTask();
                task.setTransaction(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION)));
                task.setAmount(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_AMOUNT)));
                task.setType(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_TYPE)));
                task.setPlace(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_PLACE)));
                task.setDescreption(cursor.getString(cursor.getColumnIndex(Conestance.TRANSACTION_DESCRIPTION)));
                task.setDay(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_DAY)));
                task.setMonth(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_MONTH)));
                task.setYear(cursor.getInt(cursor.getColumnIndex(Conestance.TRANSACTION_YEAR)));
                task.setItemId(cursor.getInt(cursor.getColumnIndex(Conestance.KEY_ID)));

                datalList.add(task);

            } while ((cursor.moveToNext()));
        }
        return datalList;
    }

    public boolean verificationUserAndPAss(String userName, String Password) {

        Log.v("data888", "1234");
        SQLiteDatabase db = this.getReadableDatabase();
        Log.v("data888", "1235");
        Cursor cursor = null;
        Log.v("data888", "123456");
        cursor = db.query(Conestance.TABLE_Costumer, null,
                "Name=? AND Password=?", new String[]{userName.trim(), Password.trim()}, null, null, null);
        Log.v("data888", "1234567");
        cursor.moveToFirst();
        cursor.close();
        Log.v("data888", String.valueOf(cursor.getCount()));
        if (cursor.getCount() <= 0) {
            return false;
        }
        return true;
    }
}
