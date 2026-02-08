package at.semmal.pitstopper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textCurrentTime;
    private Handler handler;
    private Runnable updateTimeRunnable;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        textCurrentTime = findViewById(R.id.textCurrentTime);

        // Initialize time format (24-hour format for racing)
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Initialize handler for time updates
        handler = new Handler(Looper.getMainLooper());

        // Create runnable for updating time
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                // Schedule next update in 1 second
                handler.postDelayed(this, 1000);
            }
        };

        // Enable fullscreen immersive mode
        hideSystemUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start updating the clock when activity becomes visible
        updateTime(); // Update immediately
        handler.postDelayed(updateTimeRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop updating the clock when activity is no longer visible
        handler.removeCallbacks(updateTimeRunnable);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void updateTime() {
        String currentTime = timeFormat.format(new Date());
        textCurrentTime.setText(currentTime);
    }

    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}

