import java.util.*;

public class gcpc {
    
    // team info
    class Team implements Comparable<Team>{
        int  num;
        long score   = 0;
        long penalty = 0;
		
	   // CONSTRUCTOR        
	   Team(int num) {this.num = num;}

        @Override
        public int compareTo(Team team) {
            if (score != team.score) {
                if (score > team.score) {return 1;}
                else { return -1;}
            }
            if (penalty != team.penalty) {
                if (penalty > team.penalty) { return -1; }
                else { return 1; }
            }

            return team.num - num;
        }

        public void add_point(long penalty) {
            score++;
            this.penalty += penalty;
        }
    }
    
    // FIELDS
    TreeSet<Team> better;
    Team[] teams;
    int counter;

    // CONSTRUCTOR
    public gcpc(int numTeams) {
        better = new TreeSet<>(); // Tree containing teams better than team 1
        counter = 0; // no. of teams better than team 1 (reduce time)

        teams = new Team[numTeams];
        for (int i = 1; i <= numTeams; i++) {
            teams[i-1] = new Team(i);
        }
    }

    public int update(int num, long newPenalty){
        Team team = teams[num - 1];

        if (num == 1) { // our team scored
            team.add_point(newPenalty); // update team score

            // remove all teams that became worse
            while (!better.isEmpty() && team.compareTo(better.first()) > 0) {
                better.remove(better.first());
                counter--;
            }

        } else { // other teams scored
            
		 // assume this team isn't better
            if (better.contains(team)) {
                better.remove(team);
                counter--;
            }

            team.add_point(newPenalty); // update team score

            if (teams[0].compareTo(team) < 0) { // better than our team
                better.add(team);
                counter++;
            }
        }

        return counter + 1; // rank of our team
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        gcpc tree = new gcpc(sc.nextInt());
        int count = sc.nextInt();
        for (int i = 0; i < count; i++) {
            int rank = tree.update(sc.nextInt(), sc.nextInt());
            System.out.println(rank);
        }
    }

}