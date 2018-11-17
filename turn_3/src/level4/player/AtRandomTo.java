package level4.player;

import java.util.Random;

public class AtRandomTo extends Player {
    public AtRandomTo(int mistake) {
        this.name = "胡乱来";
        this.mistake = mistake;
        if (Math.random() * 10 == 1) {
            this.choices.add(true);
        } else {
            this.choices.add(false);
        }
    }

    @Override
    public boolean choice(Player against, int round) {
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            return true;
        }
        return false;
    }
}
