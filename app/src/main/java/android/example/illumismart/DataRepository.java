package android.example.illumismart;

import android.app.Application;
import android.example.illumismart.DAO.IlluminanceDao;
import android.example.illumismart.DAO.dataItemDao;
import android.example.illumismart.DAO.FlickerDao;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.entity.dataItem;
import android.example.illumismart.entity.FlickerItem;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private final AppDatabase db;
    private static DataRepository sInstance;

    private final IlluminanceDao mIlluminanceDao;
    private final dataItemDao mdataItemDao;
    private final FlickerDao mFlickerDao;

    public DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mIlluminanceDao = db.illuminanceDao();
        mdataItemDao = db.itemDao();
        mFlickerDao = db.flickerDao();
    }


    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mIlluminanceDao.getAllIlluminance();
    }

    public LiveData<List<dataItem>> getAllItem() { return mdataItemDao.getAllItem(); }

    public LiveData<List<Illuminance>> getIlluminance(final String timeStamp) {
        return db.illuminanceDao().getIlluminance(timeStamp);
    }

    public LiveData<List<FlickerItem>> getAllFlickerItem() {
        return mFlickerDao.getAllFlickerItem();
    }

    public LiveData<List<FlickerItem>> getFlickerItem(final String timeStamp) {
        return mFlickerDao.getFlickerItem(timeStamp);
    }

    public void insertLux(Illuminance illuminance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIlluminanceDao.insert(illuminance);
        });
    }

    public void insertItem(dataItem item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mdataItemDao.insert(item);
        });
    }

    public void insertFlickerItem(FlickerItem item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mFlickerDao.insert(item);
        });
    }

}
