package level4.player;

public class SinisterGang extends Player {
    public SinisterGang(int mistake) {
        this.mistake = mistake;
        this.name = "黑帮老铁";
        this.choices.add(true);
    }

    @Override
    public boolean choice(Player against, int round) {
        if ((round > 1) && (!against.choices.get(round - 1))) {
            return false;
        }
        return true;
    }
}
