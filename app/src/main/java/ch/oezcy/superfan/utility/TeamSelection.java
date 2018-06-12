package ch.oezcy.superfan.utility;

import ch.oezcy.superfan.db.entity.Game;
import ch.oezcy.superfan.db.entity.Team;

public class TeamSelection {

    private Team selectedTeam1;
    private Team selectedTeam2;

    private Game foundGame1;
    private Game foundGame2;



    //new object because otherwise binding not works
    public TeamSelection selectTeam(Team sel){
        TeamSelection newSelection = new TeamSelection();

        newSelection.selectedTeam1 = sel;
        newSelection.selectedTeam2 = this.selectedTeam1;

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

    public Game getFoundGame1() {
        return foundGame1;
    }

    public void setFoundGame1(Game foundGame1) {
        this.foundGame1 = foundGame1;
    }

    public Game getFoundGame2() {
        return foundGame2;
    }

    public void setFoundGame2(Game foundGame2) {
        this.foundGame2 = foundGame2;
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
