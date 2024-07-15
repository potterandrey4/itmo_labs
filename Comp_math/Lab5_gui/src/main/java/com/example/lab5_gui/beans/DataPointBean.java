package com.example.lab5_gui.beans;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class DataPointBean {
    private final String x;
    private final String y;
    private final String yBest;
    private final String eBest;

    DecimalFormat df = new DecimalFormat("###.#######");

    public DataPointBean(double x, double y, double yBest, double eBest) {
        this.x = df.format(x);
        this.y = df.format(y);
        this.yBest = df.format(yBest);
        this.eBest = df.format(eBest);
    }
}