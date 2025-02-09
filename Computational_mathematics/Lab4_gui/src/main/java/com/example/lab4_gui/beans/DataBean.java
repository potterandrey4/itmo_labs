package com.example.lab4_gui.beans;
import lombok.Data;
@Data
public class DataBean {
    double[] coeffsLinear;
    double mseLinear;
    double rPearsonLinear;
    double r2Linear;
    double[] yLinear;
    double[] eLinear;
    String textR2;

    double[] quadCoeffs;
    double mseQuad;
    double[] yQuad;
    double[] eQuad;

    double[] cubicCoeffs;
    double mseCubic;
    double[] yCubic;
    double[] eCubic;

    double[] expCoeffs;
    double mseExp;
    double[] yExp;
    double[] eExp;

    double[] logCoeffs;
    double mseLog;
    double[] yLog;
    double[] eLog;

    double[] powerCoeffs;
    double msePower;
    double[] yPower;
    double[] ePower;

    String functionBest;
    double[] x;
    double[] yBest;
    double[] eBest;

}