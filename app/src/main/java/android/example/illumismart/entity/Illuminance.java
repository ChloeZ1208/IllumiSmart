package android.example.illumismart.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "light_level")
public class Illuminance {

    @PrimaryKey
    @NonNull
    private String timestamp;

    @ColumnInfo
    public String minLux;

    @ColumnInfo
    public String maxLux;

    @ColumnInfo
    public String average;

    @ColumnInfo
    public String measuredTime;


    public Illuminance (String timestamp,
                        String minLux,
                        String maxLux,
                        String average,
                        String measuredTime) {
        this.timestamp = timestamp;
        this.minLux = minLux;
        this.maxLux = maxLux;
        this.average = average;
        this.measuredTime = measuredTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMinLux() { return minLux; }

    public String getMaxLux() { return maxLux; }

    public String getAverage() { return average; }

    public String getMeasuredTime() {
        return measuredTime;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
