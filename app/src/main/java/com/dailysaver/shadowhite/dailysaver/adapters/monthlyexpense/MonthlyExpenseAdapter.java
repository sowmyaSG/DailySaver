package com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class MonthlyExpenseAdapter extends RecyclerView.Adapter<MonthlyExpenseAdapter.ViewHolder>{
    private ArrayList<Expense> expenseList;
    private onItemClick onItemClick;

    public MonthlyExpenseAdapter(ArrayList<Expense> expenseList, onItemClick onItemClick) {
        this.expenseList = expenseList;
        this.onItemClick = onItemClick;
    }

    public interface onItemClick{
        void itemClick(Expense expense);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_generic_expense,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Expense expense = expenseList.get(position);
        holder.Category.setText(expense.getCategory());
        holder.Note.setText(expense.getNote());
        holder.Amount.setText(""+ expense.getAmount());
        holder.ExpenseDate.setText(expense.getExpenseDate());
        setTypeIcon(holder.Icon, expense.getCategory());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemClick(expense);
            }
        });
    }

    private void setTypeIcon(CircleImageView icon, String type) {
        if (type.equals("Food")) icon.setImageResource(R.drawable.ic_food_icon);
        else if (type.equals("Transport")) icon.setImageResource(R.drawable.ic_transport);
        else if (type.equals("Electricity")) icon.setImageResource(R.drawable.ic_electricity);
        else if (type.equals("Education")) icon.setImageResource(R.drawable.ic_education);
        else if (type.equals("Shopping")) icon.setImageResource(R.drawable.ic_cshopping);
        else if (type.equals("Entertainment")) icon.setImageResource(R.drawable.ic_entertainment);
        else if (type.equals("Family")) icon.setImageResource(R.drawable.ic_family);
        else if (type.equals("Friends")) icon.setImageResource(R.drawable.ic_friends);
        else if (type.equals("Work")) icon.setImageResource(R.drawable.ic_work);
        else if (type.equals("Gift")) icon.setImageResource(R.drawable.ic_gift);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Category,Note,Amount,ExpenseDate;
        CircleImageView Icon;
        CardView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Category = itemView.findViewById(R.id.Category);
            Note = itemView.findViewById(R.id.Note);
            Amount = itemView.findViewById(R.id.Amount);
            Icon = itemView.findViewById(R.id.Icon);
            ExpenseDate = itemView.findViewById(R.id.ExpenseDate);
            view = itemView.findViewById(R.id.cardItemLayout);
        }
    }
}