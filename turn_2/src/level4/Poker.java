package level4;
import java.util.Random;

public class Poker {

    // spades(黑桃),hearts(红桃),clubs(梅花),diamonds(方块)
    private String[] pokerColor = {"黑桃", "红桃", "梅花", "方块"};
    private int[] pokerValue = {65, 2, 3, 4, 5, 6, 7, 8, 9, 10, 74, 81, 75};
    private String[] joker = {"REDJOKER", "BLACKJOKER"};
    private String[] poker = new String[54];

    public Poker() {
        getPoker();
        System.out.println("洗牌完毕！");
    }
    // 得到一副全新的扑克牌
    private void getPoker() {
        for (int i = 0; i < pokerColor.length; i++) {
            for (int j = 0; j < pokerValue.length; j++) {
                if (pokerValue[j] < 11) {
                    poker[j + i * pokerValue.length] = pokerColor[i] + "-" + pokerValue[j];
                } else {
                    poker[j + i * pokerValue.length] = pokerColor[i] + "-" + (char) pokerValue[j];
                }
            }
            poker[poker.length - 2] = joker[joker.length - 2];
            poker[poker.length - 1] = joker[joker.length - 1];
        }
    }
    // 随机抽取一张扑克牌，并从扑克牌中剔除这张牌
    public String getRandomCard() {
        Random rand = new Random();
        String randomCard = null;
        do {
            int index = rand.nextInt(poker.length - 2);
            randomCard = poker[index];
            poker[index] = null;
        } while (randomCard == null);
        return randomCard;
    }
}
