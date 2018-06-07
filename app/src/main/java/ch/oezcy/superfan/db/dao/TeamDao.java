package ch.oezcy.superfan.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ch.oezcy.superfan.db.entity.Team;

@Dao
public interface TeamDao {

    @Insert
    void insertAll(Team... teams);

    @Query("DELETE FROM team")
    void deleteAll();

}
