package android.example.illumismart.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "self_assessment_item")
public class SelfAssessmentItem {
    @PrimaryKey
    @NonNull
    private String timestamp;

    @ColumnInfo
    public String issues;

    public SelfAssessmentItem (String timestamp, String issues) {
        this.timestamp = timestamp;
        this.issues = issues;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getIssues() {
        return issues;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }
}
