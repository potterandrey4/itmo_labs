package beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class DataBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int choiceEquation;
    private int choiceSystemEquations;
    private double a;
    private double b;
    private double eps;
    private BigDecimal[] bisectionMethodRoot;
    private BigDecimal[] chordMethod;
    private BigDecimal[] newtonMethod;
    private BigDecimal[] simmpleIterMethodRoot;


    public int getChoiceEquation() {
        return choiceEquation;
    }

    public int getChoiceSystemEquations() {
        return choiceSystemEquations;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getEps() {
        return eps;
    }

    public BigDecimal[] getBisectionMethodRoot() {
        return bisectionMethodRoot;
    }

    public BigDecimal[] getChordMethod() {
        return chordMethod;
    }

    public BigDecimal[] getNewtonMethod() {
        return newtonMethod;
    }

    public void setChoiceEquation(int choiceEquation) {
        this.choiceEquation = choiceEquation;
    }

    public void setChoiceSystemEquations(int choiceSystemEquations) {
        this.choiceSystemEquations = choiceSystemEquations;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public void setBisectionMethodRoot(BigDecimal[] bisectionMethodRoot) {
        this.bisectionMethodRoot = bisectionMethodRoot;
    }

    public void setChordMethod(BigDecimal[] chordMethod) {
        this.chordMethod = chordMethod;
    }

    public void setNewtonMethod(BigDecimal[] newtonMethod) {
        this.newtonMethod = newtonMethod;
    }

    public BigDecimal[] getSimmpleIterMethodRoot() {
        return simmpleIterMethodRoot;
    }

    public void setSimmpleIterMethodRoot(BigDecimal[] simmpleIterMethodRoot) {
        this.simmpleIterMethodRoot = simmpleIterMethodRoot;
    }

    public DataBean(int choiceEquation, int choiceSystemEquations, double a, double b, double eps) {
        this.choiceEquation = choiceEquation;
        this.choiceSystemEquations = choiceSystemEquations;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }

    public DataBean() {}
}
