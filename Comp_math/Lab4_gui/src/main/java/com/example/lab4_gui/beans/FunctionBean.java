package com.example.lab4_gui.beans;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class FunctionBean {

    private String functionName;
    private String a;
    private String b;
    private String c;
    private String d;
    private String mse;

    DecimalFormat df = new DecimalFormat("###.##########");

    public FunctionBean(String functionName, double[] coeffs, double mse) {
        this.functionName = functionName;
        this.a = df.format(coeffs[0]);
        this.b = df.format(coeffs[1]);
        if (coeffs.length == 3) {
            this.c = df.format(coeffs[2]);
        } else if (coeffs.length == 4) {
            this.c = df.format(coeffs[2]);
            this.d = df.format(coeffs[3]);
        }
        this.mse = df.format(mse);;
    }
}
