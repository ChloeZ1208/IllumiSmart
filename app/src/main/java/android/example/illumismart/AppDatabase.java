package android.example.illumismart;

import android.content.Context;
import android.example.illumismart.DAO.IlluminanceDao;
import android.example.illumismart.DAO.dataItemDao;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.entity.dataItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Illuminance.class, dataItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract IlluminanceDao illuminanceDao();

    public abstract dataItemDao itemDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static final int NUMBER_OF_THREADS = 4;

    private static final String DATABASE_NAME = "app_database";

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


    /**
     * Override the onCreate method to populate the database.
     * Here, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                IlluminanceDao illuminance_dao = INSTANCE.illuminanceDao();
                illuminance_dao.deleteAll();

                dataItemDao item_dao = INSTANCE.itemDao();
                item_dao.deleteAll();
                INSTANCE.setDatabaseCreated();
            });
        }
    };
}
