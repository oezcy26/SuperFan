package ch.oezcy.superfan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.oezcy.superfan.R;
import ch.oezcy.superfan.db.entity.Team;
import ch.oezcy.superfan.listener.TableOnLongClickListener;

public class TeamRankingAdapter extends ArrayAdapter<Team> {

    public TeamRankingAdapter(Context context, List<Team> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Team team = getItem(position);

        if(position == 0){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_teamranking_header, parent, false);
        }else if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_teamranking, parent, false);
        }

        TextView txtTeamId = convertView.findViewById(R.id.teamId);
        TextView txtTeamName = convertView.findViewById(R.id.teamName);
        TextView txtTeamPoints = convertView.findViewById(R.id.teamPoints);

        txtTeamId.setText(team.id);
        txtTeamName.setText(team.name);
        txtTeamPoints.setText(String.valueOf(team.teamPoints));

        return convertView;
    }
}
