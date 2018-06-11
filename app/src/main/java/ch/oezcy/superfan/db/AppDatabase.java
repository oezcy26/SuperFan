package ch.oezcy.superfan.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ch.oezcy.superfan.db.dao.GameDao;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.db.dao.TeamDao;


@Database(entities = {Team.class, Game.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TeamDao teamDao();
    public abstract GameDao gameDao();


    // code to make it SINGLETON
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "superfan")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
