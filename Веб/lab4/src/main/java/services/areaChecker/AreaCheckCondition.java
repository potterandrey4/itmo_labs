package services.areaChecker;

@FunctionalInterface
public interface AreaCheckCondition {
    boolean validate(double x, double y, double r);
}