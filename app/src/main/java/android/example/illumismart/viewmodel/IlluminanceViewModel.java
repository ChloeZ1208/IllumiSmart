package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.Illuminance;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class IlluminanceViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private final String timestamp;

    private final LiveData<List<Illuminance>> mIlluminance;

    private final LiveData<List<Illuminance>> mAllIlluminance;

    public IlluminanceViewModel(@NonNull Application application,
                                final String timeStamp) {
        super(application);
        timestamp = timeStamp;
        mRepository = new DataRepository(application);
        mIlluminance = mRepository.getIlluminance(timestamp);
        mAllIlluminance = mRepository.getAllIlluminance();
    }

    public LiveData<List<Illuminance>> getIlluminance() {
        return mIlluminance;
    }

    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mAllIlluminance;
    }

    public void insert(Illuminance illuminance) {
        mRepository.insert(illuminance);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String timestamp;

        public Factory(@NonNull Application application, String timeStamp) {
            mApplication = application;
            timestamp = timeStamp;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new IlluminanceViewModel(mApplication, timestamp);
        }
    }

}
