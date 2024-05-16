package duc.deptrai.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import duc.deptrai.myapplication.Adapter.AccountAdapter;
import duc.deptrai.myapplication.Model.Account1;
import duc.deptrai.myapplication.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private AccountAdapter accountAdapter;
    private List<Account1> accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcv_acc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        accountList = new ArrayList<>();
        if (isNetworkAvailable()) {
            callApiGetAccount();

        } else {
            Toast.makeText(MainActivity.this, "Không có kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }


//
//        accountList = new ArrayList<>();
//        // Thêm các phần tử Account1 vào danh sách
//        accountList.add(new Account1(1, 1, "Title 1", true));
//        accountList.add(new Account1(2, 2, "Title 2", false));
        // Thêm nhiều phần tử hơn nếu cần

//        accountAdapter = new AccountAdapter(this, accountList);
//        recyclerView.setAdapter(accountAdapter);
    }
    private  void callApiGetAccount(){
        ApiService.apiService.getListAccount(1).enqueue(new Callback<List<Account1>>() {
            @Override
            public void onResponse(Call<List<Account1>> call, Response<List<Account1>> response) {
                accountList=response.body();
                AccountAdapter accountAdapter1 = new AccountAdapter(accountList);
                recyclerView.setAdapter(accountAdapter1);
            }

            @Override
            public void onFailure(Call<List<Account1>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"onfalue",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
