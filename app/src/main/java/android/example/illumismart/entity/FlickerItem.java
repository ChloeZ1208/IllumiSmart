package android.example.illumismart.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "flicker_item")
public class FlickerItem {
    @PrimaryKey
    @NonNull
    private String timestamp;

    @ColumnInfo
    public String fluctuation_freq;

    @ColumnInfo
    public String flicker_counts;

    @ColumnInfo
    public String maxLux;

    @ColumnInfo
    public String minLux;

    public FlickerItem (String timestamp,
                        String fluctuation_freq,
                        String flicker_counts,
                        String maxLux,
                        String minLux) {
        this.timestamp = timestamp;
        this.fluctuation_freq = fluctuation_freq;
        this.flicker_counts = flicker_counts;
        this.maxLux = maxLux;
        this.minLux = minLux;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMinLux() { return minLux; }

    public String getMaxLux() { return maxLux; }

    public String getFluctuationFreq() { return fluctuation_freq; }

    public String getFlickerCounts() {
        return flicker_counts;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
