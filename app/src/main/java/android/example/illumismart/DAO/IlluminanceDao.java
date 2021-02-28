package android.example.illumismart.DAO;

import android.example.illumismart.entity.Illuminance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IlluminanceDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed.
    @Query("SELECT * FROM light_level")
    LiveData<List<Illuminance>> getAllIlluminance();

    @Query("SELECT * FROM light_level where timestamp = :timeStamp")
    LiveData<List<Illuminance>> getIlluminance(String timeStamp);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Illuminance illuminance);

    @Query("DELETE FROM light_level where timestamp = :timeStamp")
    void delete(String timeStamp);

    @Query("DELETE FROM light_level")
    void deleteAll();


}
