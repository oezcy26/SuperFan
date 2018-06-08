package ch.oezcy.superfan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ch.oezcy.superfan.db.entity.Team;

public class MainViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Team>> mAllTeams;

    public MainViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllTeams = mRepository.getAllTeams();
    }

    LiveData<List<Team>> getAllTeams() { return mAllTeams; }

    public void insert(Team team) { mRepository.insert(team); }
}
