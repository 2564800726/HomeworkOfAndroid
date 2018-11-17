package level4.player;

public class Star extends Player {
    public Star(int mistake) {
        this.mistake = mistake;
        this.name = "福尔摩星儿";
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                this.choices.add(false);
            } else {
                this.choices.add(true);
            }
        }
    }

    @Override
    public boolean choice(Player against, int round) {
        boolean isReporter = false;
        if (round == 5) {
            for (int i = 0; i < 4; i++) {
                if (!against.choices.get(i)) {
                    isReporter = true;
                }
            }
        }

        if (round < 5) {
            return this.choices.get(round - 1);
        } else {
            if (isReporter) {
                return against.choices.get(round - 2);
            } else {
                return false;
            }
        }
    }
}
