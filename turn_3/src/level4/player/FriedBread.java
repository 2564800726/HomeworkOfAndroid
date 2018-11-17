package level4.player;

public class FriedBread extends Player {
    public FriedBread(int mistake) {
        this.mistake = mistake;
        this.name = "千年油条";
        this.choices.add(false);
    }

    @Override
    public boolean choice(Player against, int round) {
        return false;
    }
}
