package level4;

public class Player {
    private String[] ownCards = new String[5];

    // 记录玩家牌的值对应的数值
    private int[] values = new int[5];

    private String name = null;
    // 用于记录该玩家的牌型
    private int[] recordCardsType = new int[7];

    // 用于记录玩家的牌是什么大小的炸弹
    private int whichBomb = 0;

    // 用于记录玩家是牛几
    private int aFewCattle = 0;

    // 用于记录玩家最大的单张，将花色的优先级转换为数字表示（花色-牌值）
    private String maxCard = null;

    // 用于记录如果是五小的话的点数
    private int total = 0;

    public Player(String name) {
        this.name = name;
    }
    // 抽取5张牌
    public void getCards(Poker poker) {
        for (int i = 0; i < ownCards.length; i++) {
            ownCards[i] = poker.getRandomCard();
        }
        System.out.println(this.name + "抽牌···");
    }

    // 获取玩家的所有牌
    String[] getOwnCards() {
        return ownCards;
    }

    // 展示该玩家的所有牌
    public void showAllCards() {
        for (int i = 0; i < ownCards.length; i++) {
            System.out.println(ownCards[i]);
        }
    }

    // 玩家牌型的Getter和Setter
    int[] getRecordCardsType() {
        return recordCardsType;
    }

    void setRecordCardsType(int index) {
        this.recordCardsType[index] = 1;
    }

    // 玩家是牛几的Getter和Setter
    int getAFewCattle() {
        return aFewCattle;
    }

    void setAFewCattle(int aFewCattle) {
        this.aFewCattle = aFewCattle;
    }

    // 玩家名称的Getter和Setter
    String getName() {
        return name;
    }

    int getWhichBomb() {
        return whichBomb;
    }

    void setWhichBomb(int whichBomb) {
        this.whichBomb = whichBomb;
    }

    // 最大的单张的Getter和Setter
    String getMaxCard() {
        return maxCard;
    }

    void setMaxCard(String maxCard) {
        this.maxCard = maxCard;
    }

    // 五小点数的Getter和Setter
    int getTotal() {
        return total;
    }

    void setTotal(int total) {
        this.total = total;
    }

    // 玩家的牌的值得数值的Getter和Setter
    int[] getValues() {
        return values;
    }

    void setValues(int[] values) {
        this.values = values;
    }
}
