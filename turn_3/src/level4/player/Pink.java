package level4.player;

public class Pink extends Player {
    public Pink(int mistake) {
        this.mistake = mistake;
        this.name = "万年小粉";
        this.choices.add(true);
    }

    @Override
    public boolean choice(Player against, int round) {
        return true;
    }
}
