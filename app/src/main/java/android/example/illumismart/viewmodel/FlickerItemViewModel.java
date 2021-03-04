package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.FlickerItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FlickerItemViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public FlickerItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<FlickerItem> getFlickerItem(String timestamp) {
        return mRepository.getFlickerItem(timestamp);
    }

    public LiveData<List<FlickerItem>> getAllFlickerItem() {
        return mRepository.getAllFlickerItem();
    }

    public void insert(FlickerItem item) {
        mRepository.insertFlickerItem(item);
    }

    public void delete(String timestamp) { mRepository.deleteFlickerItem(timestamp); }
}
