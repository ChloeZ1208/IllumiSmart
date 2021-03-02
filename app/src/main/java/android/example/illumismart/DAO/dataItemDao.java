package android.example.illumismart.DAO;

import android.example.illumismart.entity.dataItem;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface dataItemDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed.
    @Query("SELECT * FROM data_item ORDER BY timestamp DESC")
    LiveData<List<dataItem>> getAllItem();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(dataItem item);

    @Query("DELETE FROM data_item where timestamp = :timeStamp")
    void delete(String timeStamp);

    @Query("DELETE FROM data_item")
    void deleteAll();


}

