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
    public String fluctuationRate;

    @ColumnInfo
    public String flickerCounts;

    @ColumnInfo
    public String relativeChange;

    @ColumnInfo
    public String maxLux;

    @ColumnInfo
    public String minLux;

    @ColumnInfo
    public String avgLux;

    public FlickerItem (String timestamp,
                        String fluctuationRate,
                        String flickerCounts,
                        String relativeChange,
                        String maxLux,
                        String minLux,
                        String avgLux) {
        this.timestamp = timestamp;
        this.fluctuationRate = fluctuationRate;
        this.flickerCounts = flickerCounts;
        this.relativeChange =  relativeChange;
        this.maxLux = maxLux;
        this.minLux = minLux;
        this.avgLux = avgLux;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMinLux() { return minLux; }

    public String getMaxLux() { return maxLux; }

    public String getAvgLux() {return avgLux;}

    public String getFluctuationRate() { return fluctuationRate; }

    public String getRelativeChange() {
        return relativeChange;
    }

    public String getFlickerCounts() {
        return flickerCounts;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
