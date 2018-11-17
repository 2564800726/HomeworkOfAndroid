package level4.player;

public class Reporter extends Player{
    public Reporter(int mistake) {
        this.mistake = mistake;
        this.name = "复读机";
        this.choices.add(true);
    }

    @Override
    public boolean choice(Player against, int round) {
        if (round > 1) {
            return against.choices.get(round - 1);
        }
        return true;
    }
}
