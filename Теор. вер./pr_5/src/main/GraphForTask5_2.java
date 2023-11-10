package main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.HashMap;
import java.util.Map;

public class GraphForTask5_2 extends javax.swing.JFrame {

	public GraphForTask5_2(HashMap<Double, Double> probabilities) {
		initComponents(probabilities);
	}

	@SuppressWarnings("unchecked")
	private void initComponents(HashMap<Double, Double> probabilities) {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Task 5_2");

		XYSeries series = new XYSeries("Probability Distribution");

		for (Map.Entry<Double, Double> entry : probabilities.entrySet()) {
			series.add(entry.getKey(), entry.getValue());
		}

		XYSeriesCollection dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Полигон приведённых частот",
				"Значения",
				"Частота встречаемости",
				dataset,
				PlotOrientation.VERTICAL,
				false,
				false,
				false
		);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
		setContentPane(chartPanel);

		pack();
	}

	public static void drawGraph5_2(HashMap<Double, Double> probabilities) {
		java.awt.EventQueue.invokeLater(() -> new GraphForTask5_2(probabilities).setVisible(true));
	}
}
