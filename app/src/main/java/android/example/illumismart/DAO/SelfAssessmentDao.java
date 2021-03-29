package android.example.illumismart.DAO;

import android.example.illumismart.entity.SelfAssessmentItem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SelfAssessmentDao {
    @Query("SELECT * FROM self_assessment_item")
    LiveData<List<SelfAssessmentItem>> getAllSelfAssessmentItem();

    @Query("SELECT * FROM self_assessment_item where timestamp = :timeStamp")
    LiveData<SelfAssessmentItem> getSelfAssessmentItem(String timeStamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SelfAssessmentItem item);

    @Query("DELETE FROM self_assessment_item where timestamp = :timeStamp")
    void delete(String timeStamp);

    @Query("DELETE FROM self_assessment_item")
    void deleteAll();
}
