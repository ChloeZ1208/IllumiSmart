package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.Illuminance;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IlluminanceViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    public IlluminanceViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<List<Illuminance>> getIlluminance(String timestamp) {
        return mRepository.getIlluminance(timestamp);
    }

    public LiveData<List<Illuminance>> getAllIlluminance() {
        return mRepository.getAllIlluminance();
    }

    public void insert(Illuminance illuminance) {
        mRepository.insertLux(illuminance);
    }
}
