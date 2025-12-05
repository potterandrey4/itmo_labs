package itmo.soa.barsservice.model;

public enum Difficulty {
    VERY_EASY,
    EASY,
    INSANE,
    TERRIBLE;

    public Difficulty decreasedBy(int steps) {
        if (steps <= 0) {
            return this;
        }
        int target = Math.max(0, this.ordinal() - steps);
        return Difficulty.values()[target];
    }
}
