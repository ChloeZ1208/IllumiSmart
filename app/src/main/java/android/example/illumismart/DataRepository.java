package android.example.illumismart;

import android.app.Application;
import android.example.illumismart.DAO.IlluminanceDao;
import android.example.illumismart.DAO.dataItemDao;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.entity.dataItem;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private AppDatabase db;
    private static DataRepository sInstance;

    private IlluminanceDao mIlluminanceDao;
    private dataItemDao mdataItemDao;

    private LiveData<List<Illuminance>> mIlluminance;
    private LiveData<List<dataItem>> mdataItem;

    public DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mIlluminanceDao = db.illuminanceDao();
        mIlluminance = mIlluminanceDao.getAllIlluminance();

        mdataItemDao = db.itemDao();
        mdataItem = mdataItemDao.getAllItem();
    }


    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mIlluminance;
    }

    public LiveData<List<dataItem>> getAllItem() { return mdataItem; }

    public LiveData<List<Illuminance>> getIlluminance(final String timeStamp) {
        return db.illuminanceDao().getIlluminance(timeStamp);
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

}
