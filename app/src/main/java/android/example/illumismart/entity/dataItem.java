package android.example.illumismart.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_item")
public class dataItem {
    @PrimaryKey
    @NonNull
    private String timestamp;

    @ColumnInfo
    public String item_name;

    public dataItem(String timestamp, String item_name) {
        this.timestamp = timestamp;
        this.item_name = item_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
