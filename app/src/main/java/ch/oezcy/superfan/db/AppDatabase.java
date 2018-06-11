package ch.oezcy.superfan.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import ch.oezcy.superfan.db.dao.GameDao;
import ch.oezcy.superfan.db.dao.GamedayDao;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Gameday;

import static ch.oezcy.superfan.ConfigConstants.GAMEDAY_AMOUNT;


@Database(entities = {Game.class, Gameday.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {





   // public abstract TeamDao teamDao();
    public abstract GameDao gameDao();
    public abstract GamedayDao gamedayDao();


    // code to make it SINGLETON
    private static AppDatabase INSTANCE;

    /*
    static RoomDatabase.Callback initialData = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Gameday[] initialGamedays = new Gameday[GAMEDAY_AMOUNT];
            Gameday newGd;
            for (int i = 0; i < GAMEDAY_AMOUNT; i++) {
                newGd = new Gameday((short)(i + 1));
                initialGamedays[i] = newGd;
            }

            INSTANCE.gamedayDao().insertAll(initialGamedays);

        }
    };
    */

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "superfan").build();
                }
            }
        }
        return INSTANCE;
    }


}
