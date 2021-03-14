package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        View v = findViewById(R.id.profile_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#E5E5E5"));

        topAppBar = findViewById(R.id.lux_sugg_top_app_bar);
        setNavigationBar();

    }

    private void setNavigationBar() {
        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*
         * TODO：if user profile is finished, display delete menu
         */
        /*
        * if () {
            topAppBar.inflateMenu(R.menu.save_menu);
            topAppBar.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.save_profile) {
                    // TODO: clear SharedPreference "Profile"
                }
                return false;
            });
        }
         */
    }
}