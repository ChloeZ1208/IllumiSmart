package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.dataItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class dataItemViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private final LiveData<List<dataItem>> mAllItem;

    public dataItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllItem = mRepository.getAllItem();
    }

    LiveData<List<dataItem>> getAllItems() {
        return mAllItem;
    }

    public void insert(dataItem item) {
        mRepository.insertItem(item);
    }
}
