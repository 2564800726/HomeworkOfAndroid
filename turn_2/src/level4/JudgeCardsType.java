package level4;

import java.util.ArrayList;

public class JudgeCardsType {
    // 记录下所有的玩家
    private static ArrayList<Player> allPlayers;
    // 用于记录玩家的牌的值
    private static int[] values = new int[5];
    // 用于记录玩家的牌的花色
    private static String[] colors = new String[5];

    public static void judgeCardsType(ArrayList<Player> players) {
        allPlayers = players;
        for (Player allPlayer : allPlayers) {
            getValue(allPlayer);
            getColor(allPlayer);
            maxCard(allPlayer);
            setDigitValues(allPlayer);
            judgeAllTypes(allPlayer);
        }
    }

    // 设置玩家牌的值对应的数值
    private static void setDigitValues(Player player) {
        for (int i = 0; i < values.length; i++) {
            player.getValues()[i] = toInteger(values[i]);
        }
    }

    // 记录下玩家的牌的值
    private static void getValue(Player player) {
        for (int i = 0; i < player.getOwnCards().length; i++) {
            switch (player.getOwnCards()[i].split("-")[1]) {
                case "J":
                    values[i] = 74;
                    break;
                case "Q":
                    values[i] = 81;
                    break;
                case "K":
                    values[i] = 75;
                    break;
                case "A":
                    values[i] = 65;
                    break;
                default:
                    values[i] = Integer.parseInt(player.getOwnCards()[i].split("-")[1]);
                    break;
            }
        }
    }

    // 记录下玩家的牌的花色
    private static void getColor(Player player) {
        for (int i = 0; i < player.getOwnCards().length; i++) {
            colors[i] = player.getOwnCards()[i].split("-")[0];
        }
    }

    // 记录下玩家最大的单张
    private static void maxCard(Player player) {
        int index = 0;
        int colorStep = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[index] < values[i]) {
                index = i;
            }
        }
        switch (colors[index]) {
            case "spades":
                colorStep = 4;
                break;
            case "hearts":
                colorStep = 3;
                break;
            case "clubs":
                colorStep = 2;
                break;
            case "diamonds":
                colorStep = 1;
                break;
        }
        player.setMaxCard(colorStep + "-" + values[index]);
    }
    // 判断每位玩家的牌型
    private static void judgeAllTypes(Player player) {
            isBomb(player);
            isFiveFlowers(player);
            isFiveSmall(player);
            isFiveFlowers(player);
            whichCows(player);
    }

    // 判断是否是炸弹
    private static void isBomb(Player player) {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int value : values) {
                if (values[i] == value) {
                    count++;
                    if (count == 4) {  // 记录下该玩家是哪一个炸弹
                        player.setWhichBomb(values[i]);
                    }
                }
            }
            if (count > 4) {
                player.setRecordCardsType(0);
            }
        }
    }

    // 判断是否是五小
    private static void isFiveSmall(Player player) {
        int count = 0;
        int sum = 0;
        for (int value : values) {
            if (value < 5) {
                count++;
            }
            sum += value;
        }
        if (count == 5 && sum == 10) {
            player.setRecordCardsType(1);
            player.setTotal(count); // 记录下五小的点数
        }
    }

    //判断是否是五花
    private static void isFiveFlowers(Player player) {
        int count = 0;
        for (int value : values) {
            if (value > 10) {
                count++;
            }
        }
        if (count == 5) {
            player.setRecordCardsType(2);
        }
    }

    // 判断是否是四花
    private static void isFourFlowers(Player player) {
        int count1 = 0;
        int count2 = 0;
        for (int value : values) {
            if (value > 10) {
                count1++;
            }
            if (value == 10) {
                count2++;
            }
        }
        if (count1 == 4 && count2 == 1) {
            player.setRecordCardsType(3);
        }
    }

    // 判断是否是牛牛，有牛，无牛
    private static void whichCows(Player player) {
        for (int x = 0; x < values.length; x++) {
            for (int y = 0; y < values.length; y++) {
                for (int z = 0; z < values.length; z++) {
                    if (x != y && x != z && y != z) {
                        // partSum记录任意三个数的和，otherSum记录另两个数的和
                        int partSum = toInteger(values[x]) + toInteger(values[y]) + toInteger(values[z]);
                        int otherSum = 0;
                        for (int i = 0; i < values.length; i++) {
                            if (i != x && i != y && i != z) {
                                otherSum += toInteger(values[i]);
                            }
                        }
                        if ((partSum % 10 == 0) && (otherSum % 10 == 0)) {  // 判断是否是牛牛
                            player.setRecordCardsType(4);
                        } else if ((partSum % 10 == 0) && (otherSum % 10 != 0)) {  // 判断是否有牛
                            player.setRecordCardsType(5);
                            player.setAFewCattle(otherSum % 10);  // 如果是有牛的话就记录下是牛几
                        } else {  // 判断是否是无牛
                            player.setRecordCardsType(6);
                        }
                    }
                }
            }
        }
    }

    // 将玩家的牌中的花转换为对应的数值
    private static int toInteger(int value) {
        switch (value) {
            case 65:
                return 1;
            case 74:
                return 11;
            case 81:
                return 12;
            case 75:
                return 13;
            default:
                return value;
        }
    }
}
