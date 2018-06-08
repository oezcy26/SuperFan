package ch.oezcy.superfan;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.List;


import ch.oezcy.superfan.background.TableLoader;
import ch.oezcy.superfan.databinding.ActivityMainBinding;
import ch.oezcy.superfan.db.AppDatabase;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.utility.TeamSelection;

public class MainActivity extends AppCompatActivity {

    private TeamSelection selection = new TeamSelection();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TeamListAdapter adapter = new TeamListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        //for test
        selection = selection.selectTeam(new Team("111", "Galatasaray", (short)1));
        selection = selection.selectTeam(new Team("222", "Kasimpasa", (short)2));

        binding.setSelection(selection);

        //to change the value, the whole object must be replaced in binding. it is not sufficient to change only an atttribut

        /*
        final TableLayout table = findViewById(R.id.table);

        MainViewModel model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                for(Team t : teams){

                    TableRow tableRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablerow, null);
                    ((TextView)tableRow.findViewById(R.id.teamName)).setText(t.name);
                    ((TextView)tableRow.findViewById(R.id.teamPoints)).setText(String.valueOf(t.teamPoints));
                    ((TextView)tableRow.findViewById(R.id.teamId)).setText(t.id);

                    tableRow.setOnLongClickListener(new TableSelectLongclickListener(binding));

                    table.addView(tableRow);
                }
            }
        });

        new TableLoader(db).execute();
        */



/*
        try {
            Elements tablerows = new TableLoader(db).execute().get();

            if(tablerows != null){
                for(Element row : tablerows){
                    String teamId = ParseHelper.getTeamIdFromRow(row);
                    String teamName = ParseHelper.getTeamNameFromRow(row);
                    int teamPoints = ParseHelper.getTeamPointsFromRow(row);




                    TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.tablerow, null);
                    ((TextView)tableRow.findViewById(R.id.teamName)).setText(teamName);
                    ((TextView)tableRow.findViewById(R.id.teamPoints)).setText(String.valueOf(teamPoints));
                    ((TextView)tableRow.findViewById(R.id.teamId)).setText(teamId);


                    tableRow.setOnLongClickListener(new TableSelectLongclickListener(binding));

                    table.addView(tableRow);

                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
            */
        }

    private class TableSelectLongclickListener implements View.OnLongClickListener{
        final ActivityMainBinding binding;
        TableSelectLongclickListener(ActivityMainBinding binding) {
            this.binding = binding;
        }

        @Override
        public boolean onLongClick(View v) {
            TableRow row = (TableRow)v;
            TextView textTeam = (TextView)row.getChildAt(1);

            Team newTeam = new Team("xxx", textTeam.getText().toString(), (short)1);

            selection = selection.selectTeam(newTeam);
            binding.setSelection(selection);
            System.out.println(selection.toString());
            return false;
        }
    }





}
