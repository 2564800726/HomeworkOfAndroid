package level4;

import level4.player.Player;

public class Machine {
    private int drawScore = 2;
    private int allFalse = 0;
    private int loseScore = -1;
    private int winScore = 3;
    private int round = 5;

    public void setDrawScore(int drawScore) {
        this.drawScore = drawScore;
    }

    public void setLoseScore(int loseScore) {
        this.loseScore = loseScore;
    }

    public void setWinScore(int winScore) {
        this.winScore = winScore;
    }

    public void setRound(int round) { this.round = round; }

    public int getDrawScore() {
        return drawScore;
    }

    public int getLoseScore() {
        return loseScore;
    }

    public int getWinScore() {
        return winScore;
    }

    public int getRound() {
        return round;
    }

    public int getAllFalse() {
        return allFalse;
    }

    public void setAllFalse(int allFalse) {
        this.allFalse = allFalse;
    }

    // 两个玩家进行游戏
    void compete(Player player1, Player player2) {
        for (int i = 1; i <= this.round; i++) {
            boolean player1Choice = player1.recordChoice(player2, i);
            boolean player2Choice = player2.recordChoice(player1, i);
            if (player1Choice && player2Choice) {  // player1和player2都信任
                player1.changeScore(this.drawScore);
                player2.changeScore(this.drawScore);
            } else if (player1Choice && !player2Choice) {  // player1信任，player2欺骗
                player1.changeScore(this.loseScore);
                player2.changeScore(this.winScore);
            } else if (!player1Choice && player2Choice) {  // player1欺骗，player2信任
                player1.changeScore(this.winScore);
                player2.changeScore(this.loseScore);
            } else if (!player1Choice && !player2Choice) {
                player1.changeScore(this.allFalse);
                player2.changeScore(this.allFalse);
            }
        }
    }
}
