package itmo.soa.labworkservice.model;

public enum Difficulty {
    VERY_EASY,
    EASY,
    INSANE,
    TERRIBLE;

    public boolean isGreaterThan(Difficulty other) {
        if (other == null) {
            return true;
        }
        return this.ordinal() > other.ordinal();
    }
}
