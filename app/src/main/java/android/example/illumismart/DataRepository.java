package android.example.illumismart;

import android.app.Application;
import android.example.illumismart.DAO.IlluminanceDao;
import android.example.illumismart.entity.Illuminance;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private AppDatabase db;
    private static DataRepository sInstance;
    private IlluminanceDao mIlluminanceDao;
    private LiveData<List<Illuminance>> mIlluminance;

    public DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mIlluminanceDao = db.illuminanceDao();
        mIlluminance = mIlluminanceDao.getAllIlluminance();
    }


    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mIlluminance;
    }

    public LiveData<List<Illuminance>> getIlluminance(final String timeStamp) {
        return db.illuminanceDao().getIlluminance(timeStamp);
    }

    public void insert(Illuminance illuminance) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIlluminanceDao.insert(illuminance);
        });
    }
}
