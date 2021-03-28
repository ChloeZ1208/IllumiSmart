package android.example.illumismart.viewmodel;

import android.app.Application;
import android.example.illumismart.DataRepository;
import android.example.illumismart.entity.SelfAssessmentItem;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SelfAssessmentItemViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public SelfAssessmentItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<SelfAssessmentItem> getSelfAssessmentItem(String timestamp) {
        return mRepository.getSelfAssessmentItem(timestamp);
    }

    public LiveData<List<SelfAssessmentItem>> getAllSelfAssessmentItem() {
        return mRepository.getAllSelfAssessmentItem();
    }

    public void insert(SelfAssessmentItem item) {
        mRepository.insertSelfAssessmentItem(item);
    }

    public void delete(String timestamp) { mRepository.deleteSelfAssessmentItem(timestamp); }
}
