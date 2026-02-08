package at.semmal.pitstopper;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonSelectTime;
    private EditText editPitWindowOpens;
    private EditText editPitWindowDuration;
    private Button buttonSave;
    private Button buttonCancel;

    private int raceStartHour = 9;
    private int raceStartMinute = 0;

    private PitWindowPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enable fullscreen immersive mode
        hideSystemUI();

        // Initialize preferences
        preferences = new PitWindowPreferences(this);

        // Initialize views
        buttonSelectTime = findViewById(R.id.buttonSelectTime);
        editPitWindowOpens = findViewById(R.id.editPitWindowOpens);
        editPitWindowDuration = findViewById(R.id.editPitWindowDuration);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Load current settings
        loadSettings();

        // Set up time picker button
        buttonSelectTime.setOnClickListener(v -> showTimePicker());

        // Set up button listeners
        buttonSave.setOnClickListener(v -> saveSettings());
        buttonCancel.setOnClickListener(v -> finish());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
            this,
            (view, hourOfDay, minute) -> {
                raceStartHour = hourOfDay;
                raceStartMinute = minute;
                updateTimeButtonText();
            },
            raceStartHour,
            raceStartMinute,
            true // 24-hour format
        );
        timePickerDialog.show();
    }

    private void updateTimeButtonText() {
        String timeText = String.format(Locale.getDefault(), "%02d:%02d", raceStartHour, raceStartMinute);
        buttonSelectTime.setText(timeText);
    }

    private void loadSettings() {
        // Load from SharedPreferences
        raceStartHour = preferences.getRaceStartHour();
        raceStartMinute = preferences.getRaceStartMinute();
        updateTimeButtonText();

        editPitWindowOpens.setText(String.valueOf(preferences.getPitWindowOpens()));
        editPitWindowDuration.setText(String.valueOf(preferences.getPitWindowDuration()));
    }

    private void saveSettings() {
        try {
            int pitWindowOpens = Integer.parseInt(editPitWindowOpens.getText().toString());
            int pitWindowDuration = Integer.parseInt(editPitWindowDuration.getText().toString());

            // Validate inputs
            if (pitWindowOpens < 0 || pitWindowOpens > 300) {
                Toast.makeText(this, "Pit window opens time must be between 0 and 300 minutes", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pitWindowDuration < 1 || pitWindowDuration > 60) {
                Toast.makeText(this, "Pit window duration must be between 1 and 60 minutes", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to SharedPreferences
            preferences.saveAll(raceStartHour, raceStartMinute, pitWindowOpens, pitWindowDuration);

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}
