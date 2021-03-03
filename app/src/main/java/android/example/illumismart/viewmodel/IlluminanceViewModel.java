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

    private final DataRepository mRepository;
    private final String timestamp;

    public IlluminanceViewModel(@NonNull Application application,
                                final String timeStamp) {
        super(application);
        timestamp = timeStamp;
        mRepository = new DataRepository(application);
    }

    public LiveData<List<Illuminance>> getIlluminance() {
        return mRepository.getIlluminance(timestamp);
    }

    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mRepository.getAllIlluminance();
    }

    public void insert(Illuminance illuminance) {
        mRepository.insertLux(illuminance);
    }

    /**
     * A creator is used to inject the timestamp into the ViewModel
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
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
