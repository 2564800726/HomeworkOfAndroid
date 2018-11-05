package level4;

import java.util.List;

public class Compare {
    private static List<Player> allPlayers;
    private static int type = 0;

    public static void comparePlayers(List<Player> players) {
        allPlayers = players;
        compare();
    }

    private static void compare() {
        int winnerNumber = 0;
        Player winner = allPlayers.get(0);
        for (int i = 1; i < allPlayers.size(); i++) {
            winner = compareTwoPlayer(winnerNumber, i);
        }
        showWinner(winner, type);
    }

    // 将两个玩家进行比较
    private static Player compareTwoPlayer(int number1, int number2) {
        for (int j = 0; j < allPlayers.get(0).getRecordCardsType().length; j++) {
            if (allPlayers.get(number1).getRecordCardsType()[j] > allPlayers.get(number2).getRecordCardsType()[j]) {
                type = j;
                return allPlayers.get(number1);
            } else if (allPlayers.get(number1).getRecordCardsType()[j] == 1
                    || allPlayers.get(number2).getRecordCardsType()[j] == 1) {
                if (j == 0) {  // 如果两者的牌型是炸弹
                    if (allPlayers.get(number1).getWhichBomb() > allPlayers.get(number2).getWhichBomb()) {
                        type = j;
                        return allPlayers.get(number1);
                    } else {
                        type = j;
                        return allPlayers.get(number2);
                    }
                } else if (j == 1) {  // 如果两者的牌型是五小
                    if (allPlayers.get(number1).getTotal() > allPlayers.get(number2).getTotal()) {
                        type = j;
                        return allPlayers.get(number1);
                    } else if (allPlayers.get(number1).getTotal() < allPlayers.get(number2).getTotal()) {
                        type = j;
                        return allPlayers.get(number2);
                    } else {  // 分数相同，比较单张+花色
                        type = j;
                        return compareMaxCard(number1,number2);
                    }
                } else if (j == 2 || j == 3 || j == 4) {  // 如果两者的牌型是牛牛，四花，五花
                    // 比较单张+花色
                    type = j;
                    return compareMaxCard(number1, number2);
                } else if (j == 5) {  // 如果两者的牌型是有牛
                    if (allPlayers.get(number1).getAFewCattle() > allPlayers.get(number2).getAFewCattle()) {
                        type = j;
                        return allPlayers.get(number1);
                    } else {
                        type = j;
                        return allPlayers.get(number2);
                    }
                } else {  // 如果两者的牌型是无牛
                    //
                    sortValues(number1);
                    sortValues(number2);
                    for (int i = 0; i < allPlayers.get(number1).getValues().length; i++) {
                        if (allPlayers.get(number1).getValues()[i] >= allPlayers.get(number2).getValues()[i]) {
                            type = j;
                            return allPlayers.get(number1);
                        } else {
                            type = j;
                            return allPlayers.get(number2);
                        }
                    }
                }
            }
        }
        return null;
    }

    // 对玩家中的values进行排序
    private static void sortValues(int number) {
        for (int i = 1; i < allPlayers.get(number).getValues().length; i++) {
            for (int k = 0; k < allPlayers.get(number).getValues().length - i; k++) {
                if (allPlayers.get(number).getValues()[k] < allPlayers.get(number).getValues()[k + 1]) {
                    int temp = allPlayers.get(number).getValues()[k];
                    allPlayers.get(number).getValues()[k] = allPlayers.get(number).getValues()[k + 1];
                    allPlayers.get(number).getValues()[k + 1] = temp;
                }
            }
        }
    }

    // 打印出胜利者
    private static void showWinner(Player winner, int type) {
        System.out.println(winner.getName() + "获胜");
        winner.showAllCards();
        switch (type) {
            case 0:
                System.out.println("炸弹");
                break;
            case 1:
                System.out.println("五小");
                break;
            case 2:
                System.out.println("五花");
                break;
            case 3:
                System.out.println("四花");
                break;
            case 4:
                System.out.println("牛牛");
                break;
            case 5:
                System.out.println("牛" + toChinese(winner.getAFewCattle()));
                break;
            case 6:
                System.out.println("无牛");
                break;
        }
    }

    // 把有分时的分数转为中文的数字
    private static char toChinese(int digit) {
        switch (digit) {
            case 1:
                return '一';
            case 2:
                return '二';
            case 3:
                return '三';
            case 4:
                return '四';
            case 5:
                return '五';
            case 6:
                return '六';
            case 7:
                return '七';
            case 8:
                return '八';
            case 9:
                return '九';
        }
        return '0';
    }

    // 比较单张+花色
    private static Player compareMaxCard(int number1, int number2) {
        if (Integer.parseInt(allPlayers.get(number1).getMaxCard().split("-")[1])
                > Integer.parseInt(allPlayers.get(number2).getMaxCard().split("-")[1])) {
            return allPlayers.get(number1);
        } else if (Integer.parseInt(allPlayers.get(number1).getMaxCard().split("-")[1])
                < Integer.parseInt(allPlayers.get(number2).getMaxCard().split("-")[1])) {
            return allPlayers.get(number2);
        } else {  // 单张数值相等，比较花色
            if (Integer.parseInt(allPlayers.get(number1).getMaxCard().split("-")[0])
                    > Integer.parseInt(allPlayers.get(number2).getMaxCard().split("-")[0])) {
                return allPlayers.get(number1);
            } else {
                return allPlayers.get(number2);
            }
        }
    }
}

