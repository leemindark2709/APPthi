package duc.deptrai.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import duc.deptrai.myapplication.Adapter.AccountAdapter;
import duc.deptrai.myapplication.Click.RecyclerItemClickListener;
import duc.deptrai.myapplication.Model.Account1;
import duc.deptrai.myapplication.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Account1> accountList;
    private AccountAdapter accountAdapter;
    private BroadcastReceiver airplaneModeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcv_acc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Thêm ngăn cách giữa các mục
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);

        accountList = new ArrayList<>();
        if (isNetworkAvailable()) {
            callApiGetAccount();
        } else {
            Toast.makeText(MainActivity.this, "Không có kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Account1 account = accountList.get(position);
                if (account != null) {
                    Toast.makeText(MainActivity.this, "Bạn đã chọn UserID: " + account.getUserId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showDeleteConfirmationDialog(position);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerAirplaneModeReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterAirplaneModeReceiver();
    }

    private void callApiGetAccount() {
        ApiService.apiService.getListAccount().enqueue(new Callback<List<Account1>>() {
            @Override
            public void onResponse(Call<List<Account1>> call, Response<List<Account1>> response) {
                if (response.isSuccessful()) {
                    List<Account1> accounts = response.body();
                    if (accounts != null) {
                        accountList.addAll(accounts);
                        accountAdapter = new AccountAdapter(accountList);
                        recyclerView.setAdapter(accountAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Danh sách account rỗng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Account1>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Có thực sự muốn xóa todo này không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    if (accountAdapter != null) {
                        accountList.remove(position);
                        accountAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void registerAirplaneModeReceiver() {
        if (airplaneModeReceiver == null) {
            airplaneModeReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
                    if (isAirplaneModeOn) {
                        Toast.makeText(MainActivity.this, "Airplane mode vừa Bật", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Airplane mode vừa Tắt", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }
        registerReceiver(airplaneModeReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }

    private void unregisterAirplaneModeReceiver() {
        if (airplaneModeReceiver != null) {
            unregisterReceiver(airplaneModeReceiver);
            airplaneModeReceiver = null;
        }
    }
}
