package com.example.lab6_gui.beans;

import lombok.Data;

@Data
public class TableRowData {
    private final double x;
    private final double yExact;
    private final double yEuler;
    private final double yImprovedEuler;
    private final double yAdams;

    public TableRowData(double x, double yExact, double yEuler, double yImprovedEuler, double yAdams) {
        this.x = x;
        this.yExact = yExact;
        this.yEuler = yEuler;
        this.yImprovedEuler = yImprovedEuler;
        this.yAdams = yAdams;
    }
}
