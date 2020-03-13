package com.dailysaver.shadowhite.dailysaver.utills.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import java.util.ArrayList;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_AMOUNT;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_CATEGORY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_CURRENCY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_EXPENSE_DATE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_ID;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_NOTE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_WALLET;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_AMOUNT;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_CURRENCY;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_EXPIRES_ON;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_ID;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_NOTE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_TITLE;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_WALLET_TYPE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dailySaver.db";
    private static final int DB_VERSION = 1;

    private static final String WALLET_TABLE = "Wallet";
    private static final String EXPENSE_TABLE = "Expense";

    private static String CREATE_EXPENSE_TABLE = "CREATE TABLE " + EXPENSE_TABLE + "("
            + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EXPENSE_AMOUNT + " INTEGER,"
            + COLUMN_EXPENSE_CURRENCY + " INTEGER," + COLUMN_EXPENSE_CATEGORY + " TEXT," + COLUMN_EXPENSE_NOTE + " TEXT,"
            + COLUMN_EXPENSE_WALLET + " INTEGER," + COLUMN_EXPENSE_EXPENSE_DATE + " TEXT" + ")";

    private static String CREATE_WALLET_TABLE = "CREATE TABLE " + WALLET_TABLE + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WALLET_AMOUNT + " REAL,"
            + COLUMN_WALLET_CURRENCY + " INTEGER," + COLUMN_WALLET_TITLE + " TEXT," + COLUMN_WALLET_NOTE + " TEXT,"
            + COLUMN_WALLET_TYPE + " INTEGER," + COLUMN_WALLET_EXPIRES_ON + " TEXT" + ")";

    private static String DROP_WALLET_TABLE = "DROP TABLE IF EXISTS "+WALLET_TABLE;
    private static String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS "+EXPENSE_TABLE;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_WALLET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EXPENSE_TABLE);
        db.execSQL(DROP_WALLET_TABLE);
    }

    /**
     * This method is to create EXPENSE record
     *
     * @param expense
     */
    public void addNewExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(COLUMN_EXPENSE_CURRENCY, expense.getCurrency());
        values.put(COLUMN_EXPENSE_CATEGORY, expense.getCategory());
        values.put(COLUMN_EXPENSE_WALLET, expense.getWallet());
        values.put(COLUMN_EXPENSE_NOTE, expense.getNote());
        values.put(COLUMN_EXPENSE_EXPENSE_DATE, expense.getExpenseDate());

        // Inserting Row
        db.insert(EXPENSE_TABLE, null, values);
        db.close();
    }

    public ArrayList<Expense> getAllExpenseItems() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_EXPENSE_ID,
                COLUMN_EXPENSE_AMOUNT,
                COLUMN_EXPENSE_CATEGORY,
                COLUMN_EXPENSE_EXPENSE_DATE,
                COLUMN_EXPENSE_CURRENCY,
                COLUMN_EXPENSE_NOTE,
                COLUMN_EXPENSE_WALLET,
        };
        // sorting orders
        String sortOrder =
                COLUMN_EXPENSE_ID + " DESC";
        ArrayList<Expense> expenseList = new ArrayList<Expense>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EXPENSE_TABLE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

         if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();

                    expense.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_ID)));
                    expense.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT)));
                    expense.setCategory((cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_CATEGORY))));
                    expense.setExpenseDate((cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_EXPENSE_DATE))));
                    expense.setCurrency(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_CURRENCY)));
                    expense.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_NOTE)));
                    expense.setWalletId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_WALLET)));
                    expenseList.add(expense);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return expenseList;
    }

    /**
     * This method is to create wallet record
     *
     * @param wallet
     */
    public void addNewWallet(Wallet wallet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WALLET_AMOUNT, wallet.getAmount());
        values.put(COLUMN_WALLET_TITLE, wallet.getTitle());
        values.put(COLUMN_WALLET_CURRENCY, wallet.getCurrency());
        values.put(COLUMN_WALLET_NOTE, wallet.getNote());
        values.put(COLUMN_WALLET_TYPE,wallet.getWalletType());
        values.put(COLUMN_WALLET_EXPIRES_ON,wallet.getExpiresOn());

        // Inserting Row
        db.insert(WALLET_TABLE, null, values);
        db.close();
    }

    public ArrayList<Wallet> getAllWalletItems() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_WALLET_ID,
                COLUMN_WALLET_AMOUNT,
                COLUMN_WALLET_TITLE,
                COLUMN_WALLET_CURRENCY,
                COLUMN_WALLET_NOTE,
                COLUMN_WALLET_EXPIRES_ON,
                COLUMN_WALLET_TYPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_WALLET_ID + " DESC";
        ArrayList<Wallet> walletList = new ArrayList<Wallet>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WALLET_TABLE, columns, null, null, null, null, sortOrder); //The sort order

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    Wallet wallet = new Wallet();

                    wallet.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_ID)));
                    wallet.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_AMOUNT)));
                    wallet.setTitle((cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_TITLE))));
                    wallet.setNote((cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_NOTE))));
                    wallet.setCurrency(cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_CURRENCY)));
                    wallet.setExpiresOn(cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_EXPIRES_ON)));
                    wallet.setWalletType(cursor.getString(cursor.getColumnIndex(COLUMN_WALLET_TYPE)));

                    walletList.add(wallet);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return walletList;
    }

    public String[] getWalletTitle() {
        String[] walletTitle = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {
                COLUMN_WALLET_TITLE
        };

        Cursor cursor = sqLiteDatabase.query(WALLET_TABLE, columns ,null , null , null , null , null);
        int counter = 0;
        if (cursor != null){
            walletTitle = new String[cursor.getCount()];
            if (cursor.moveToFirst()){
                do{
                    walletTitle[counter] = cursor.getString(0);
                    counter++;
                }while (cursor.moveToNext());
            }
        }

        return walletTitle;
    }
}
