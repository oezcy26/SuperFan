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
    public short ranking;

    public Team(String id, String name, short teamPoints, short ranking) {
        this.id = id;
        this.name = name;
        this.teamPoints = teamPoints;
        this.ranking = ranking;
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

    @Override
    public String toString() {
        return "" + ranking + "\t" + name + "\t" + teamPoints;
    }
}
