package ch.oezcy.superfan.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.oezcy.superfan.db.entity.Team;

@Dao
public interface TeamDao {

    @Query("SELECT * from team ORDER BY teamPoints DESC")
    List<Team> getAllWords();

    @Insert
    void insertAll(Team... teams);

    @Query("DELETE FROM team")
    void deleteAll();

}
