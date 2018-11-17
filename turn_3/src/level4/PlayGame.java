package level4;

import level4.player.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlayGame {
    private int[] eachTypePlayers = { 0, 0, 0, 0, 0, 0, 0, 0 };  // 8个类型的玩家分别有多少人
    private String[] eachTypes = { "胡乱来", "复读鸭", "千年油条", "一根筋", "万年小粉", "复读机", "黑帮老铁", "福尔摩星儿" };  // 所有类型的玩家
    private Player[] players = new Player[25];  // 25名玩家
    private int currentPlayers = 0;  // 当前玩家人数
    private int weedOutPlayers = 5;
    private int round = 10;
    private int mistake = 5;
    
    // 记录挑选出的最差的5名玩家和最好的5名玩家的索引
    private int[] lowestPlayersIndex = new int[weedOutPlayers];
    private int[] highestPlayersIndex = new int[weedOutPlayers];

    private ArrayList<Integer> peoples = new ArrayList<>();

    private Scanner scan = new Scanner(System.in);

    private Random rand = new Random();

    // 开始游戏
    public void startGame() {
        setPlayer();
        while (true) {
            for (int i = 0; i < 8; i++) {
                System.out.printf("%s:\t%d\t", this.eachTypes[i], this.eachTypePlayers[i]);
                if (i == 3 || i == 7) {
                    System.out.println();
                }
            }
            System.out.printf("输入“1”设置盈利(当前：合作-合作：%d 合作-欺骗：%d 欺骗-合作：%d 欺骗-欺骗：%d\n",
                    this.machine.getDrawScore(), this.machine.getLoseScore(), this.machine.getWinScore(), this.machine.getAllFalse());
            System.out.print("输入“2”设置玩家之间进行的轮数(当前：" + this.machine.getRound() + ")\n" +
                    "输入“3”设置玩家犯错的概率(当前：" + this.mistake + "%)\n" +
                    "输入“4”设置整个游戏进行的轮数(当前：" + this.round + ")\n" +
                    "输入“5”开始游戏\n" +
                    "输入其余任意字符结束游戏\n");
            String select = this.scan.next();
            if ("1".equals(select)) {
                setGain();
            } else if ("2".equals(select)) {
                setRound();
            } else if ("3".equals(select)) {
                setMistake();
            } else if ("4".equals(select)) {
                System.out.print("请输入整个游戏进行的轮数1~*:");
                this.round = this.scan.nextInt();
            } else if ("5".equals(select)) {
                for (int i = 0; i < this.round; i++) {
                    startCompete();
                    sortPlayers();
                    weedOutPlayers();
                }
                showAllPlayers();
                break;
            } else {
                break;
            }
        }

}

    // 显示所有玩家的信息
    private void showAllPlayers() {
        for (int i = 0; i < 25; i++) {
            if (i == 24) {
                System.out.println(this.players[i].getName() + this.players[i].getScore());
            } else if (i == 11) {
                System.out.println();
            } else {
                System.out.print(this.players[i].getName() + this.players[i].getScore() + ",");
            }
        }
    }

    // 设置各个类型的玩家的人数
    private void setPlayer() {
        for (int i = 0; i < 8; i++) {
            if (this.currentPlayers != this.players.length) {
                System.out.print("输入" + this.eachTypes[i] + "的人数(0~" + (players.length - this.currentPlayers) + "):");
                this.eachTypePlayers[i] = scan.nextInt();
                for (int j = 0; j < this.eachTypePlayers[i]; j++) {
                    this.players[this.currentPlayers + j] = getPlayer(i);
                }
                this.currentPlayers += this.eachTypePlayers[i];
            }
        }
    }

    private Machine machine = new Machine();

    // 让玩家之间开始比赛
    private void startCompete() {
        for (int i = 0; i < this.players.length; i++) {
            for (int j = 0; j < this.players.length; j++) {
                if (i != j) {
                    this.machine.compete(this.players[i], this.players[j]);
                }
            }
        }
    }

    // 设置报酬
    private void setGain() {
        System.out.print("请输入平局时的收入(0~*):");
        this.machine.setDrawScore(this.scan.nextInt());
        System.out.print("请输入赢得比赛的收入(0~*):");
        this.machine.setWinScore(this.scan.nextInt());
        System.out.print("请输入输掉比赛的收入(*~0):");
        this.machine.setLoseScore(this.scan.nextInt());
        System.out.print("请输入玩家都选择欺骗时的收入(*~0):");
        this.machine.setAllFalse(this.scan.nextInt());
    }

    // 设置每一局比赛进行多少轮
    private void setRound() {
        System.out.print("请输入每局比赛进行的轮数(1~50):");
        this.machine.setRound(this.scan.nextInt());
    }

    // 按照比赛完成后的分数对玩家进行排序
    private void sortPlayers() {
        for (int i = 1; i < this.players.length; i++) {
            for (int j = 0; j < this.players.length - i; j++) {
                if (this.players[j].getScore() > this.players[j + 1].getScore()) {
                    Player temp = this.players[j];
                    this.players[j] = this.players[j + 1];
                    this.players[j + 1] = temp;
                }
            }
        }
        // 将各个分段的分值以及人数记录下来
        int score = this.players[0].getScore();
        int people = 1;
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i].getScore() == score) {
                people++;
            } else {
                this.peoples.add(people);
                score = this.players[i].getScore();
                people = 1;
            }
        }
    }

    // 根据设定的淘汰玩家数进行淘汰玩家和生成新的玩家
    private void weedOutPlayers() {
        // 最低分的5个玩家包含于哪几个分段以及各个分段的人数总和
        int lowest = 0;
        int lowestPeople = 0;
        // 最高分的5个玩家包含于哪几个分段以及各个分段的人数总和
        int highest = this.peoples.size() - 1;
        int highestPeople = 0;

        for (int i = 0; i < this.peoples.size(); i++) {
            for (int j = 0; j <= lowest; j++) {
                lowestPeople += this.peoples.get(j);
            }
            if (lowestPeople < 5) {
                lowest++;
                lowestPeople = 0;
            }

            for (int j = this.peoples.size() - 1; j >= highest; j--) {
                highestPeople += this.peoples.get(j);
            }
            if (highestPeople < 5) {
                highest--;
                highestPeople = 0;
            }
        }

//        for (int i = 0; i < this.peoples.size(); i++) {
//            System.out.print(this.peoples.get(i) + "  ");
//        }
//        System.out.println();
//
//        System.out.println(lowest + " " + highest);

        findLowestPlayers(lowest);
        findHighestPlayers(highest);

//        for (int i = 0; i < 25; i++) {
//            System.out.print(this.players[i].getScore() + "," + i + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < 5; i++) {
//            System.out.print(this.lowestPlayersIndex[i] + "," + this.highestPlayersIndex[i] + " ");
//        }
//        System.out.println();
        clonePlayers();
    }

    // 克隆玩家
    private void clonePlayers() {
        for (int i = 0; i < this.weedOutPlayers; i++) {
            this.players[this.lowestPlayersIndex[i]] = (Player) this.players[this.highestPlayersIndex[i]].clone();
        }
    }

    // 获取最差的5名玩家的索引
    private void findLowestPlayers(int lowest) {  // [0,*]
        if (lowest > 0) {
            int currentIndex = 0;
            for (int i = 0; i <= lowest; i++) {
                if (i != lowest) {
                    for (int j = 0; j < this.peoples.get(i); j++) {
                        this.lowestPlayersIndex[currentIndex] = currentIndex;
                        currentIndex++;
                    }
                } else {
                    int bound = weedOutPlayers - currentIndex;
                    for (int j = 0; j < bound; j++) {
                        while (true) {
                            int index = rand.nextInt(this.peoples.get(lowest)) + 1;  // [1,*]
                            for (int t = 0; t < currentIndex; t++) {
                                if ((index + this.lowestPlayersIndex[currentIndex - 1]) == this.lowestPlayersIndex[t]) {
                                    break;
                                } else if (t == currentIndex - 1) {
                                    this.lowestPlayersIndex[currentIndex] = index + this.lowestPlayersIndex[currentIndex - 1];
                                    currentIndex++;
                                    if (currentIndex == 5) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {  // lowest为0时
            int currentIndex = 0;
            for (int j = 0; j < this.weedOutPlayers; j++) {
                while (true) {
                    int index = rand.nextInt(this.peoples.get(lowest)) + 1;  // [1,*]
                    if (currentIndex > 0) {
                        for (int t = 0; t < currentIndex; t++) {
                            if ((index + this.lowestPlayersIndex[currentIndex - 1]) == this.lowestPlayersIndex[t]) {
                                break;
                            } else if (t == currentIndex - 1) {
                                this.lowestPlayersIndex[currentIndex] = index + this.lowestPlayersIndex[currentIndex - 1];
                                currentIndex++;
                                if (currentIndex == 5) {
                                    return;
                                }
                            }
                        }
                    } else {
                        this.lowestPlayersIndex[currentIndex] = index + this.lowestPlayersIndex[currentIndex - 1];
                        currentIndex++;
                    }
                }
            }
        }
    }

    // 获取最强的5名玩家的索引
    private void findHighestPlayers(int highest) {
        if (highest > 0) {
            int currentIndex = 0;
            for (int i = this.peoples.size() - 1; i >= highest; i--) {
                if (i != highest) {
                    for (int j = 0; j < this.peoples.get(i); j++) {
                        this.highestPlayersIndex[currentIndex] = this.players.length - 1 - currentIndex;
                        currentIndex++;
                    }
                } else {
                    int bound = weedOutPlayers - currentIndex;
                    for (int j = 0; j < bound; j++) {
                        while (true) {
                            int index = rand.nextInt(this.peoples.get(highest)) + 1;  // [1,*]
                            for (int t = 0; t < currentIndex; t++) {
                                if ((this.highestPlayersIndex[currentIndex - 1] - index) == this.highestPlayersIndex[t]) {
                                    break;
                                } else if (t == currentIndex - 1) {
                                    this.highestPlayersIndex[currentIndex] = this.highestPlayersIndex[currentIndex - 1] - index;
                                    currentIndex++;
                                    if (currentIndex == 5) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {  // highest为0时
            int currentIndex = 0;
            for (int j = 0; j < this.weedOutPlayers; j++) {
                while (true) {
                    int index = rand.nextInt(this.peoples.get(highest)) + 1;  // [1,*]
                    if (currentIndex > 0) {
                        for (int t = 0; t < currentIndex; t++) {
                            if ((this.highestPlayersIndex[currentIndex - 1] - index) == this.highestPlayersIndex[t]) {
                                break;
                            } else if (t == currentIndex - 1) {
                                this.highestPlayersIndex[currentIndex] = this.highestPlayersIndex[currentIndex - 1] - index;
                                currentIndex++;
                                if (currentIndex == 5) {
                                    return;
                                }
                            }
                        }
                    } else {
                        this.highestPlayersIndex[currentIndex] = this.highestPlayersIndex[currentIndex - 1] - index;
                        currentIndex++;
                    }
                }
            }
        }
    }
    
    // 创建对应类型的玩家的对象
    private Player getPlayer(int index) {
        switch (index) {
            case 0:
                return new AtRandomTo(this.mistake);
            case 1:
                return new Duck(this.mistake);
            case 2:
                return new FriedBread(this.mistake);
            case 3:
                return new OneTrackMind(this.mistake);
            case 4:
                return new Pink(this.mistake);
            case 5:
                return new Reporter(this.mistake);
            case 6:
                return new SinisterGang(this.mistake);
            case 7:
                return new Star(this.mistake);
        }
        return null;
    }

    public void setMistake() {
        System.out.println("输入每位玩家犯错的概率0~50");
        this.mistake = this.scan.nextInt();
    }
}
