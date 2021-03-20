package android.example.illumismart.DAO;

import android.example.illumismart.entity.GlareItem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GlareDao {
    @Query("SELECT * FROM glare_item")
    LiveData<List<GlareItem>> getAllGlareItem();

    @Query("SELECT * FROM glare_item where timestamp = :timeStamp")
    LiveData<GlareItem> getGlareItem(String timeStamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GlareItem item);

    @Query("DELETE FROM glare_item where timestamp = :timeStamp")
    void delete(String timeStamp);

    @Query("DELETE FROM glare_item")
    void deleteAll();
}
