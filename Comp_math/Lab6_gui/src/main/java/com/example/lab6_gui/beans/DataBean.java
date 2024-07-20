package com.example.lab6_gui.beans;

import lombok.Data;

@Data
public class DataBean {
    double[] xExact;
    double[] yExact;
    double[] yEuler;
    double[] yImprovedEuler;
    double[] yAdams;
    double epsilon;
}
