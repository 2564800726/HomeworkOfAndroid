package level4.player;

public class Duck extends Player {
    public Duck(int mistake) {
        this.mistake = mistake;
        this.name = "复读鸭";
        this.choices.add(true);
    }

    @Override
    public boolean choice(Player against, int round) {
        if ((round > 2) && !(against.choices.get(round - 1)) && !(against.choices.get(round - 2))) {
            return against.choices.get(round - 1);
        }
        return true;
    }
}
