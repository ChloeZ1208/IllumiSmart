package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DataActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private RecyclerView recyclerData;
    private dataItemViewModel mdataItemViewModel;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        bottomNav = findViewById(R.id.bottom_navigation_data);
        bottomNav.getMenu().findItem(R.id.data_page).setChecked(true);

        // TODO: get all dataItem
        recyclerData = findViewById(R.id.recycler_data);
        adapter = new DataAdapter(new DataAdapter.DataDiff());
        recyclerData.setAdapter(adapter);
        recyclerData.setLayoutManager(new LinearLayoutManager(this));

        mdataItemViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(dataItemViewModel.class);
        // Add an observer on the LiveData returned by getAllItems.
        mdataItemViewModel.getAllItems().observe(this, mAllItem -> {
            adapter.submitList(mAllItem);
        });

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(DataActivity.this,
                                HomeActivity.class));
                        break;
                    case R.id.info_page:
                        // TODO: info
                        break;
                    case R.id.setting_page:
                        // TODO: profile
                        break;
                }
                return false;
            }
        });

    }
}