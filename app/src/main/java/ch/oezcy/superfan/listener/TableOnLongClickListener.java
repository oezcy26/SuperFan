package ch.oezcy.superfan.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.oezcy.superfan.db.accessor.GameForTeamsSelector;
import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;

import static ch.oezcy.superfan.MainActivity.binding;
import static ch.oezcy.superfan.MainActivity.selection;

public class TableOnLongClickListener implements AdapterView.OnItemLongClickListener {


    private Context appContext;
    private AppDatabase db;

    public TableOnLongClickListener(Context ctx, AppDatabase db) {
        this.db = db;
        this.appContext = ctx;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // extract team-infos from row
        Team team = (Team) parent.getAdapter().getItem(position);


        // make new Team and set selection
        Team newTeam = new Team(team.id, team.name, team.teamPoints, (short)0);
        selection = selection.selectTeam(newTeam);
        binding.setSelection(selection);

        // load data for games
        if (selection.getSelectedTeam1() != null && selection.getSelectedTeam2() != null) {
            try {
                List<Game> games = new GameForTeamsSelector(db).execute(selection).get();
                if(games.size() != 2){
                    CharSequence text = "Only one game ?!??";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(appContext, text, duration);
                    toast.show();
                    return false;

                }
                binding.setGame1(games.get(0));
                binding.setGame2(games.get(1));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            binding.setGame1(null);
            binding.setGame2(null);
        }
        return false;
    }
}
