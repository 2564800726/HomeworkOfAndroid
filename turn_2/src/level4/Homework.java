package level4;

import java.util.ArrayList;
import java.util.Scanner;

public class Homework {
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        System.out.println("输入玩家昵称，至少两名玩家，最多十名玩家（player1,player2,player3···）");
        Scanner scan = new Scanner(System.in);
        String allNames = scan.next();
        for (int i = 0; i < allNames.split(",").length; i++) {
            players.add(new Player(allNames.split(",")[i]));
        }
        Poker poker = new Poker();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getCards(poker);
        }
        System.out.println("================================");
        JudgeCardsType.judgeCardsType(players);
        Compare.comparePlayers(players);
    }
}
