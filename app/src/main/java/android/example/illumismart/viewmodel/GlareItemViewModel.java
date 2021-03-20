package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.GlareItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GlareItemViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public GlareItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<GlareItem> getGlareItem(String timestamp) {
        return mRepository.getGlareItem(timestamp);
    }

    public LiveData<List<GlareItem>> getAllGlareItem() {
        return mRepository.getAllGlareItem();
    }

    public void insert(GlareItem item) {
        mRepository.insertGlareItem(item);
    }

    public void delete(String timestamp) { mRepository.deleteGlareItem(timestamp); }
}
