package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.dataItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class dataItemViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    public dataItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<List<dataItem>> getAllItems() {
        return mRepository.getAllItem();
    }

    public void insert(dataItem item) {
        mRepository.insertItem(item);
    }

    public void delete(String timestamp) { mRepository.deletedataItem(timestamp);}
}
