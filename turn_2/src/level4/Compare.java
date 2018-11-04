package level4;

public class Compare {
    private Player[] allPlayers;
    private int type = 0;

    public Compare(Player ...players) {
        allPlayers = players;
        compare();
    }

    private void compare() {
        int winnerNumber = 0;
        Player winner = allPlayers[0];
        for (int i = 1; i < allPlayers.length; i++) {
            winner = compareTwoPlayer(winnerNumber, i);
        }
        showWinner(winner, type);
    }

    // 将两个玩家进行比较
    private Player compareTwoPlayer(int number1, int number2) {
        for (int j = 0; j < allPlayers[0].getRecordCardsType().length; j++) {
            if (allPlayers[number1].getRecordCardsType()[j] > allPlayers[number2].getRecordCardsType()[j]) {
                this.type = j;
                return allPlayers[number1];
            } else if (allPlayers[number1].getRecordCardsType()[j] == 1
                    || allPlayers[number2].getRecordCardsType()[j] == 1) {
                if (j == 0) {  // 如果两者的牌型是炸弹
                    if (allPlayers[number1].getWhichBomb() > allPlayers[number2].getWhichBomb()) {
                        this.type = j;
                        return allPlayers[number1];
                    } else {
                        this.type = j;
                        return allPlayers[number2];
                    }
                } else if (j == 1) {  // 如果两者的牌型是五小
                    if (allPlayers[number1].getTotal() > allPlayers[number2].getTotal()) {
                        this.type = j;
                        return allPlayers[number1];
                    } else if (allPlayers[number1].getTotal() < allPlayers[number2].getTotal()) {
                        this.type = j;
                        return allPlayers[number2];
                    } else {  // 分数相同，比较单张+花色
                        this.type = j;
                        return compareMaxCard(number1,number2);
                    }
                } else if (j == 2 || j == 3 || j == 4) {  // 如果两者的牌型是牛牛，四花，五花
                    // 比较单张+花色
                    this.type = j;
                    return compareMaxCard(number1, number2);
                } else if (j == 5) {  // 如果两者的牌型是有牛
                    if (allPlayers[number1].getAFewCattle() > allPlayers[number2].getAFewCattle()) {
                        this.type = j;
                        return allPlayers[number1];
                    } else {
                        this.type = j;
                        return allPlayers[number2];
                    }
                } else {  // 如果两者的牌型是无牛
                    //
                    sortValues(number1);
                    sortValues(number2);
                    for (int i = 0; i < allPlayers[number1].getValues().length; i++) {
                        if (allPlayers[number1].getValues()[i] >= allPlayers[number2].getValues()[i]) {
                            this.type = j;
                            return allPlayers[number1];
                        } else {
                            this.type = j;
                            return allPlayers[number2];
                        }
                    }
                }
            }
        }
        return null;
    }

    // 对玩家中的values进行排序
    private void sortValues(int number) {
        for (int i = 1; i < allPlayers[number].getValues().length; i++) {
            for (int k = 0; k < allPlayers[number].getValues().length - i; k++) {
                if (allPlayers[number].getValues()[k] < allPlayers[number].getValues()[k + 1]) {
                    int temp = allPlayers[number].getValues()[k];
                    allPlayers[number].getValues()[k] = allPlayers[number].getValues()[k + 1];
                    allPlayers[number].getValues()[k + 1] = temp;
                }
            }
        }
    }

    // 打印出胜利者
    private void showWinner(Player winner, int type) {
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
    private char toChinese(int digit) {
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
    private Player compareMaxCard(int number1, int number2) {
        if (Integer.parseInt(allPlayers[number1].getMaxCard().split("-")[1])
                > Integer.parseInt(allPlayers[number2].getMaxCard().split("-")[1])) {
            return allPlayers[number1];
        } else if (Integer.parseInt(allPlayers[number1].getMaxCard().split("-")[1])
                < Integer.parseInt(allPlayers[number2].getMaxCard().split("-")[1])) {
            return allPlayers[number2];
        } else {  // 单张数值相等，比较花色
            if (Integer.parseInt(allPlayers[number1].getMaxCard().split("-")[0])
                    > Integer.parseInt(allPlayers[number2].getMaxCard().split("-")[0])) {
                return allPlayers[number1];
            } else {
                return allPlayers[number2];
            }
        }
    }
}

