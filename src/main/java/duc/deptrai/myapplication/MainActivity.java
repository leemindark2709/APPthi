package duc.deptrai.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import duc.deptrai.myapplication.Adapter.AccountAdapter;
import duc.deptrai.myapplication.Model.Account1;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private List<Account1> accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcv_acc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accountList = new ArrayList<>();
        // Thêm các phần tử Account1 vào danh sách
        accountList.add(new Account1(1, 1, "Title 1", true));
        accountList.add(new Account1(2, 2, "Title 2", false));
        // Thêm nhiều phần tử hơn nếu cần

        accountAdapter = new AccountAdapter(this, accountList);
        recyclerView.setAdapter(accountAdapter);
    }
}
