package android.example.illumismart.DAO;

import android.example.illumismart.entity.FlickerItem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlickerDao {
    @Query("SELECT * FROM flicker_item")
    LiveData<List<FlickerItem>> getAllFlickerItem();

    @Query("SELECT * FROM flicker_item where timestamp = :timeStamp")
    LiveData<FlickerItem> getFlickerItem(String timeStamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FlickerItem item);

    @Query("DELETE FROM flicker_item where timestamp = :timeStamp")
    void delete(String timeStamp);

    @Query("DELETE FROM flicker_item")
    void deleteAll();
}
