package main;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

public class GraphForTask5_1 extends JPanel {
	private ArrayList<Double> values;

	public GraphForTask5_1(ArrayList<Double> values) {
		this.values = values;
	}

	@Override
	protected void paintComponent(Graphics gh) {
		Graphics2D drp = (Graphics2D) gh;

		// Определите масштаб для улучшения отображения
		double scale = 50.0;

		// Подсчет частоты элементов
		Map<Double, Integer> frequencyMap = new HashMap<>();
		for (Double value : values) {
			frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
		}

		// Нарисуйте горизонтальные линии и обозначения на оси Oy
		for (int i = 1; i <= Collections.max(frequencyMap.values()); i++) {
			drp.drawLine(50, 200 - i * (int) scale, 750, 200 - i * (int) scale);
			drp.drawString(String.valueOf(i), 30, 200 - i * (int) scale);
		}

		// Нарисуйте гистограмму
		int index = 0;
		for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
			double element = entry.getKey();
			int frequency = entry.getValue();

			Color color = RED;
			try {
				Field field = Class.forName("java.awt.Color").getField(GrGis.col[index % GrGis.col.length].toLowerCase());
				color = (Color) field.get(null);
			} catch (Exception e) {
			}
			drp.setColor(color);

			int realX = 50 + 40 * index;
			drp.fillRect(realX, 200 - frequency * (int) scale, 30, frequency * (int) scale);

			drp.setColor(BLACK);
			drp.drawString(String.valueOf(element), realX + 10, 220);

			index++;
		}
	}

	public static class GrGis extends JFrame {
		public static String col[] = {"BLUE", "RED", "GREEN"}; // Массив цветов

		public GrGis(ArrayList<Double> values) {
			super("Гистограмма для приведённых частот");
			JPanel jcp = new JPanel(new BorderLayout());
			setContentPane(jcp);
			jcp.add(new GraphForTask5_1(values), BorderLayout.CENTER);
			jcp.setBackground(Color.gray);
			setSize(800, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
}
