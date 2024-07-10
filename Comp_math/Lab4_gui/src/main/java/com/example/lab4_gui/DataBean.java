package com.example.lab4_gui;
import lombok.Data;
@Data
public class DataBean {
    double[] coeffsLinear;
    double mseLinear;
    double rPearsonLinear;
    double r2Linear;
    String textR2;

    double[] quadCoeffs;
    double mseQuad;

    double[] cubicCoeffs;
    double mseCubic;

    double[] expCoeffs;
    double mseExp;

    double[] logCoeffs;
    double mseLog;

    double[] powerCoeffs;
    double msePower;

    String functionBest;
    double[] x;
    double[] yBest;
    double[] eBest;

}