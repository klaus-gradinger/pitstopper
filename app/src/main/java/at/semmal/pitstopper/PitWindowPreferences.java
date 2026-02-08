package at.semmal.pitstopper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Locale;

public class PitWindowPreferences {

    private static final String PREFS_NAME = "PitWindowPrefs";
    private static final String KEY_RACE_START_HOUR = "race_start_hour";
    private static final String KEY_RACE_START_MINUTE = "race_start_minute";
    private static final String KEY_PIT_WINDOW_OPENS = "pit_window_opens";
    private static final String KEY_PIT_WINDOW_DURATION = "pit_window_duration";

    // Default values
    private static final int DEFAULT_RACE_START_HOUR = 9;
    private static final int DEFAULT_RACE_START_MINUTE = 0;
    private static final int DEFAULT_PIT_WINDOW_OPENS = 17;
    private static final int DEFAULT_PIT_WINDOW_DURATION = 6;

    private final SharedPreferences prefs;

    public PitWindowPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Save methods
    public void saveRaceStartTime(int hour, int minute) {
        prefs.edit()
            .putInt(KEY_RACE_START_HOUR, hour)
            .putInt(KEY_RACE_START_MINUTE, minute)
            .apply();
    }

    public void savePitWindowOpens(int minutes) {
        prefs.edit()
            .putInt(KEY_PIT_WINDOW_OPENS, minutes)
            .apply();
    }

    public void savePitWindowDuration(int minutes) {
        prefs.edit()
            .putInt(KEY_PIT_WINDOW_DURATION, minutes)
            .apply();
    }

    public void saveAll(int raceStartHour, int raceStartMinute, int pitWindowOpens, int pitWindowDuration) {
        prefs.edit()
            .putInt(KEY_RACE_START_HOUR, raceStartHour)
            .putInt(KEY_RACE_START_MINUTE, raceStartMinute)
            .putInt(KEY_PIT_WINDOW_OPENS, pitWindowOpens)
            .putInt(KEY_PIT_WINDOW_DURATION, pitWindowDuration)
            .apply();
    }

    // Load methods
    public int getRaceStartHour() {
        return prefs.getInt(KEY_RACE_START_HOUR, DEFAULT_RACE_START_HOUR);
    }

    public int getRaceStartMinute() {
        return prefs.getInt(KEY_RACE_START_MINUTE, DEFAULT_RACE_START_MINUTE);
    }

    public int getPitWindowOpens() {
        return prefs.getInt(KEY_PIT_WINDOW_OPENS, DEFAULT_PIT_WINDOW_OPENS);
    }

    public int getPitWindowDuration() {
        return prefs.getInt(KEY_PIT_WINDOW_DURATION, DEFAULT_PIT_WINDOW_DURATION);
    }

    // Convenience method to get race start time as formatted string
    public String getRaceStartTimeFormatted() {
        return String.format(Locale.getDefault(), "%02d:%02d", getRaceStartHour(), getRaceStartMinute());
    }

    // Check if settings have been configured (not using defaults)
    public boolean hasSettings() {
        return prefs.contains(KEY_RACE_START_HOUR);
    }

    // Clear all settings (for testing or reset)
    public void clear() {
        prefs.edit().clear().apply();
    }
}



