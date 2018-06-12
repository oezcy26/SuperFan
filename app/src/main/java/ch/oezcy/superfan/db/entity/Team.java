package ch.oezcy.superfan.db.entity;

// represents a Soccer-Team in app

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Team {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;
    public short teamPoints;

    public Team(String id, String name, short teamPoints) {
        this.id = id;
        this.name = name;
        this.teamPoints = teamPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Team){
            Team t = (Team) obj;
            return this.id == t.id;
        }else{
            return false;
        }
    }
}
