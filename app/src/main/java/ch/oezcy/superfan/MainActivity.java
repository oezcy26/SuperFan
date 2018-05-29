package ch.oezcy.superfan;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ch.oezcy.superfan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TableSelection<String> selection = new TableSelection<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        final User user = new User("Hallo", "Friend");
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setUser(user);

        Button testButton = (Button)findViewById(R.id.testbtn);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newuser = new User("Pedro", "Excobar");
                binding.setUser(newuser);
                System.out.println();

            }
        });



        TableLayout table = (TableLayout) findViewById(R.id.table);

        try {
            Elements tablerows = new MainLoader().execute().get();

            if(tablerows != null){
                for(Element row : tablerows){
                    String teamName = getTeamNameFromRow(row);
                    int teamPoints = getTeamPointsFromRow(row);

                    TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.tablerow, null);
                    ((TextView)tableRow.findViewById(R.id.teamName)).setText(teamName);
                    ((TextView)tableRow.findViewById(R.id.teamPoints)).setText(String.valueOf(teamPoints));

                    tableRow.setOnLongClickListener(new TableSelectLongclickListener());

                    table.addView(tableRow);

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }





    }

    private String getTeamNameFromRow(Element row){
        String teamName = row.select("td[data-col-seq=2] a").text();
        return teamName;
    }

    private int getTeamPointsFromRow(Element row){
        String teamPoints = row.select("td[data-col-seq=6]").text();
        return Integer.valueOf(teamPoints);
    }

    private class TableSelectLongclickListener implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View v) {
            TableRow row = (TableRow)v;
            TextView textTeam = (TextView)row.getChildAt(0);
            selection.putElement(textTeam.getText().toString());
            System.out.println(selection.toString());
            return false;
        }
    }



    private class MainLoader extends AsyncTask<Void, Void, Elements>{

        @Override
        protected Elements doInBackground(Void... strings) {
            Elements tablerows = null;
            try {
                Document doc = Jsoup.connect("https://www.fussballdaten.de/tuerkei/").get();

                tablerows = doc.select("div#spieleWidgetTabelle233-container tbody tr");


            } catch (IOException e) {
                // TODO Fehlermeldung ausgeben, keine Verbindung zu livescores.
                e.printStackTrace();
            }

            return tablerows;
        }
    }
}
