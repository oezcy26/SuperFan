package ch.oezcy.superfan.utility;

import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;

public class TeamSelection {

    private Team selectedTeam1;
    private Team selectedTeam2;


    //new object because otherwise binding not works
    public TeamSelection selectTeam(Team sel){
        TeamSelection newSelection = new TeamSelection();

        if(selectedTeam1 != null && selectedTeam2 != null){
            //clear selection when 2 are selected
            newSelection.selectedTeam1 = sel;
            newSelection.selectedTeam2 = null;
        }else if(selectedTeam1 != null){
            if(selectedTeam1.equals(sel)){
               //deselect when same team selected
               return newSelection;
            }
            //add second selection when one is selected
            newSelection.selectedTeam1 = selectedTeam1;
            newSelection.selectedTeam2 = sel;
        }else{
            newSelection.selectedTeam1 = sel;
            newSelection.selectedTeam2 = this.selectedTeam1;
        }


        return newSelection;
    }

    public Team getSelectedTeam1() {
        return selectedTeam1;
    }

    public void setSelectedTeam1(Team selectedTeam1) {
        this.selectedTeam1 = selectedTeam1;
    }

    public Team getSelectedTeam2() {
        return selectedTeam2;
    }

    public void setSelectedTeam2(Team selectedTeam2) {
        this.selectedTeam2 = selectedTeam2;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(selectedTeam1 != null){
            sb.append(this.selectedTeam1.name + " ");
        }
        if(selectedTeam2 != null){
            sb.append(this.selectedTeam2.name);
        }
        return sb.toString();
    }


}
