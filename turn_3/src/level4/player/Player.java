package level4.player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Player implements Cloneable {
    protected int score = 0;
    protected ArrayList<Boolean> choices = new ArrayList<>();
    protected String name = null;
    protected int mistake = 0;
    private Scanner scan = new Scanner(System.in);

    protected abstract boolean choice(Player against, int round);  // 做出选择

    public boolean recordChoice(Player against, int round) {
        choices.add(makeMistake(choice(against, round)));  // 记录选择
        return makeMistake(choice(against, round));  // 返回选择
    }

    public void changeScore(int det) {
        this.score += det;
    }

    public int getScore() {
        return this.score;
    }

    // 一定几率犯错
    private boolean makeMistake(boolean bool) {
        int random = new Random().nextInt(51) - 1;
        if (random <= this.mistake) {
            return !bool;
        }
        return bool;
    }

    // 克隆
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            System.out.println("克隆玩家失败");
            return null;
        }
    }

    public String getName() {
        return name;
    }
}
