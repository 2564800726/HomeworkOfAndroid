package level4;

public class Homework {
    public static void main(String[] args) {
        // 一副全新的扑克
        Poker.getPoker();

        // 生成4名玩家
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        Player player4 = new Player("Player4");

        // 分别判断四名玩家的牌型
        new JudgeCardsType(player1, player2, player3, player4);

        // 比较四名玩家的牌型
        new Compare(player1, player2, player3, player4);
    }
}
