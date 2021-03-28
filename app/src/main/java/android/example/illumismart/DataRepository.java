package android.example.illumismart;

import android.app.Application;
import android.example.illumismart.DAO.GlareDao;
import android.example.illumismart.DAO.IlluminanceDao;
import android.example.illumismart.DAO.dataItemDao;
import android.example.illumismart.DAO.FlickerDao;
import android.example.illumismart.DAO.SelfAssessmentDao;
import android.example.illumismart.entity.GlareItem;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.entity.dataItem;
import android.example.illumismart.entity.FlickerItem;
import android.example.illumismart.entity.SelfAssessmentItem;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private final AppDatabase db;
    private static DataRepository sInstance;

    private final IlluminanceDao mIlluminanceDao;
    private final dataItemDao mdataItemDao;
    private final FlickerDao mFlickerDao;
    private final GlareDao mGlareDao;
    private final SelfAssessmentDao mSelfAssessmentDao;

    public DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mIlluminanceDao = db.illuminanceDao();
        mdataItemDao = db.itemDao();
        mFlickerDao = db.flickerDao();
        mGlareDao = db.glareDao();
        mSelfAssessmentDao = db.selfAssessmentDao();
    }


    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mIlluminanceDao.getAllIlluminance();
    }

    public LiveData<List<dataItem>> getAllItem() { return mdataItemDao.getAllItem(); }

    public LiveData<Illuminance> getIlluminance(final String timeStamp) {
        return db.illuminanceDao().getIlluminance(timeStamp);
    }

    public LiveData<List<FlickerItem>> getAllFlickerItem() {
        return mFlickerDao.getAllFlickerItem();
    }

    public LiveData<FlickerItem> getFlickerItem(final String timeStamp) {
        return mFlickerDao.getFlickerItem(timeStamp);
    }

    public LiveData<List<GlareItem>> getAllGlareItem() {
        return mGlareDao.getAllGlareItem();
    }

    public LiveData<GlareItem> getGlareItem(final String timeStamp) {
        return mGlareDao.getGlareItem(timeStamp);
    }

    public LiveData<List<SelfAssessmentItem>> getAllSelfAssessmentItem() {
        return mSelfAssessmentDao.getAllSelfAssessmentItem();
    }

    public LiveData<SelfAssessmentItem> getSelfAssessmentItem(final String timeStamp) {
        return mSelfAssessmentDao.getSelfAssessmentItem(timeStamp);
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

    public void insertGlareItem(GlareItem item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mGlareDao.insert(item);
        });
    }

    public void insertSelfAssessmentItem(SelfAssessmentItem item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSelfAssessmentDao.insert(item);
        });
    }

    public void deleteLuxItem(final String timeStamp) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIlluminanceDao.delete(timeStamp);
        });
    }

    public void deleteFlickerItem(final String timeStamp) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mFlickerDao.delete(timeStamp);
        });
    }

    public void deleteGlareItem(final String timeStamp) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mGlareDao.delete(timeStamp);
        });
    }

    public void deleteSelfAssessmentItem(final String timeStamp) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSelfAssessmentDao.delete(timeStamp);
        });
    }

    public void deletedataItem(final String timeStamp) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mdataItemDao.delete(timeStamp);
        });
    }
}
