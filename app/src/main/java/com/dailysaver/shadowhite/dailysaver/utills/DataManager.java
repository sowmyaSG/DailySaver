package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.category.Category;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import java.util.ArrayList;

public class DataManager {
    private Context context;
    private DatabaseHelper databaseHelper;

    public DataManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public String[] currencyData(){
        return new String[]{context.getResources().getString(R.string.select_currency),"BDT Tk.","US Dollar"};
    }

    public String[] walletTypeData(){
        return new String[]{context.getResources().getString(R.string.select_wallet_type),"Savings","Expense"};
    }


    public String[] getWalletTitle(){
        String[] walletTitleList = databaseHelper.getWalletTitle();;
        return walletTitleList;
    }

    public ArrayList<Category> setCategoryDataData() {
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Food",R.drawable.ic_food_icon));
        categoryList.add(new Category("Transport",R.drawable.ic_transport));
        categoryList.add(new Category("Electricity",R.drawable.ic_electricity));
        categoryList.add(new Category("Education",R.drawable.ic_education));
        categoryList.add(new Category("Shopping",R.drawable.ic_cshopping));
        categoryList.add(new Category("Entertainment",R.drawable.ic_entertainment));
        categoryList.add(new Category("Family",R.drawable.ic_family));
        categoryList.add(new Category("Friends",R.drawable.ic_friends));
        categoryList.add(new Category("Work",R.drawable.ic_work));
        categoryList.add(new Category("Gift",R.drawable.ic_gift));
        return categoryList;
    }
}
