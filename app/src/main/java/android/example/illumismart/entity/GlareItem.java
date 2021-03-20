package android.example.illumismart.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "glare_item")
public class GlareItem {
    @PrimaryKey
    @NonNull
    private String timestamp;

    @ColumnInfo
    public String maxPixelVal;

    @ColumnInfo
    public String imgPath;

    @ColumnInfo
    public String glareEvent;

    public GlareItem (String timestamp,
                      String maxPixelVal,
                      String imgPath,
                      String glareEvent) {
        this.timestamp = timestamp;
        this.maxPixelVal = maxPixelVal;
        this.imgPath = imgPath;
        this.glareEvent = glareEvent;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getMaxPixelVal() {
        return maxPixelVal;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getGlareEvent() {
        return glareEvent;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMaxPixelVal(String maxPixelVal) {
        this.maxPixelVal = maxPixelVal;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setGlareEvent(String glareEvent) {
        this.glareEvent = glareEvent;
    }
}
