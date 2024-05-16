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
    private final List<Account1> accountList;

    public AccountAdapter(List<Account1> accountList) {
        this.accountList = accountList;
    }

    public AccountAdapter(Context context, List<Account1> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        final Account1 account = accountList.get(position);
        if (account == null) {
            return;
        }

        holder.IdTextView.setText(String.valueOf(account.getId()));
        holder.completedTextView.setText(account.getCompleted() ? "Completed" : "Not Completed");
        holder.textView1.setText("Completed:");
        holder.titleTextView.setText(account.getTitle());

        // Xử lý sự kiện khi người dùng chạm vào mỗi todo item


    }


    @Override
    public int getItemCount() {
        if (accountList!=null){
        return accountList.size();}
        return 0 ;
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView IdTextView;
        TextView completedTextView;
        TextView textView1;
        TextView titleTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            IdTextView = itemView.findViewById(R.id.id);
            completedTextView = itemView.findViewById(R.id.completed);
            textView1 = itemView.findViewById(R.id.textView1);
            titleTextView = itemView.findViewById(R.id.title);

        }
    }
}
