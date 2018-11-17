package level4.player;

public class OneTrackMind extends Player {
    public OneTrackMind(int mistake) {
        this.mistake = mistake;
        this.name = "一根筋";
        this.choices.add(true);
    }

    @Override
    public boolean choice(Player against, int round) {
        if ((round > 1) && against.choices.get(round - 1)) {
            return this.choices.get(round - 1);
        }
        return !this.choices.get(round - 1);
    }
}
