package pl.edu.studia.zastinfwmed.logic;

import java.io.*;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Created by Alicja on 2017-05-13.
 */
public class ChartPrinter {


    public void drawChart(List<EcgDataSample> data) throws Exception {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        Integer counter = 0;
        for (EcgDataSample sample : data) {
            counter = counter + 1;
            line_chart_dataset.addValue((Number)counter, "EKG", sample.getValue());
        }


        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Wykres EKG", "t",
                "EKG",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;    /* Width of the image */
        int height = 280;   /* Height of the image */
        File lineChart = new File("LineChart.jpeg");

        ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);

    }
}