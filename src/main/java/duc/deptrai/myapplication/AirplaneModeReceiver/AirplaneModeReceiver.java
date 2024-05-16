package duc.deptrai.myapplication.AirplaneModeReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isAirplaneModeEnabled = intent.getBooleanExtra("state", false);
            String message = isAirplaneModeEnabled ? "Airplane mode vừa Bật" : "Airplane mode vừa Tắt";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
