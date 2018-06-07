package ch.oezcy.superfan.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.db.dao.TeamDao;


@Database(entities = {Team.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();
}
