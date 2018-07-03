package ch.oezcy.superfan.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import ch.oezcy.superfan.db.entity.Team;

@Dao
public abstract class TeamDao {

    @Query("SELECT * from team ORDER BY ranking ASC")
    public abstract List<Team> getTeamsInRanking();

    @Query("SELECT name FROM team WHERE id = :id")
    public abstract String getNameById(String id);

    @Transaction
    @Insert
    public abstract void insertAll(Team... teams);

    @Transaction
    @Query("DELETE FROM team")
    public abstract void deleteAll();

    @Transaction
    public void replaceTeams(Team[] teams){
        deleteAll();
        insertAll(teams);
    }
}
