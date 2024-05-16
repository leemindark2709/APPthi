package duc.deptrai.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import duc.deptrai.myapplication.Model.Account1;
import duc.deptrai.myapplication.R;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private Context context;
    private List<Account1> accountList;

    public AccountAdapter(Context context, List<Account1> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account1 account = accountList.get(position);
        holder.userIdTextView.setText(String.valueOf(account.getUserId()));
        holder.completedTextView.setText(account.getCompleted() ? "Completed" : "Not Completed");
        holder.textView1.setText("Completed:");
        holder.titleTextView.setText(account.getTitle());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView userIdTextView;
        TextView completedTextView;
        TextView textView1;
        TextView titleTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTextView = itemView.findViewById(R.id.userId);
            completedTextView = itemView.findViewById(R.id.completed);
            textView1 = itemView.findViewById(R.id.textView1);
            titleTextView = itemView.findViewById(R.id.title);
        }
    }
}
