package uni;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

// used sources: http://www.java2s.com/Code/Java/Chart/JFreeChartLineChartDemo1.htm
public class TheChart extends JPanel implements PlotterInterface {
    private XYPlot plot = null;
    private XYSeries series1 = null;
    private XYSeries series2 = null;
    private XYSeriesCollection xyDataset = null;
    private JFreeChart chart = null;
    private ChartPanel chartPanel = null;
    private NumberAxis rangeAxis = null;
    private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    Vector<Double> L = new Vector<>();
    Vector<Double> T = new Vector<>();
    Vector<Double> H = new Vector<>();
    JTable table;


    public TheChart() {

        // initialise the mainwindow
        initFrame();

        createChart();
    }

    /**
     * Init the mainwindow
     */
    private void initFrame() {
        setBackground(Color.WHITE);
        setSize(640, 480);
    }

    /**
     * Creates a chart and a plotpanel and add it to the mainwindow.
     */
    private void createChart() {

        xyDataset = createDataset("");
        setBackground(Color.getColor("EEEEEE"));

        // create the chart...
        chart = ChartFactory.createXYLineChart(
                "",                        // chart title	"Line Chart Demo 6"
                "Длина",                    // x axis label
                "",                         // y axis label
                xyDataset,                  // data
                PlotOrientation.VERTICAL,
                true,                        // include legend
                true,                        // tooltips
                false                        // urls
        );

        // get a reference to the plot for further customisation...
        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        renderer.setSeriesShapesVisible(0, false);    // a thin line will be painted for series1
        renderer.setSeriesShapesVisible(1, false);    // points will be painted for series2

        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        chartPanel = new ChartPanel(chart);
        add(chartPanel);


        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Слой", L);
        defaultTableModel.addColumn("Вязкость", H);
        defaultTableModel.addColumn("Температура", T);
        table = new JTable(defaultTableModel);
        table.setRowHeight(table.getRowHeight() + 4);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, FlowLayout.LEFT);

    }

    @Override
    public void updateTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Слой", L);
        defaultTableModel.addColumn("Вязкость", H);
        defaultTableModel.addColumn("Температура", T);
//        defaultTableModel.
        table.setModel(defaultTableModel);
//        table.

        L.clear();
        H.clear();
        T.clear();
    }

    /**
     * Creates the dataset.
     *
     * @return a sample dataset.
     */
    private XYSeriesCollection createDataset(String legendTxt) {

        series1 = new XYSeries("Вязкость");
        series2 = new XYSeries("Температура");

        final XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }


    @Override
    public void updateData(Vector<Double> x, Vector<Double> y, Vector<Double> marksX, Vector<Double> marksY) {

        L = x;
        H = y;
        T = marksY;

        XYSeries series1 = new XYSeries("Вязкость");
        XYSeries series2 = new XYSeries("Температура");

        if (x.size() == y.size())
            for (int i = 0; i < x.size(); i++)
                series1.add(x.get(i), y.get(i));
        else {
            JOptionPane.showMessageDialog(null, "Array sizes of x1 and y1 are not equal!");
            System.err.println("Array sizes of x and y are not equal!");
        }

        if (marksX.size() == marksY.size())
            for (int i = 0; i < marksX.size(); i++)
                series2.add(marksX.get(i), marksY.get(i));
        else {
            JOptionPane.showMessageDialog(null, "Array sizes of marking coordinates are not equal!");
            System.err.println("Array sizes of marking coordinates are not equal!");
        }

        updateDataset(series1, series2);
    }

    /**
     * add the new series to the dataset
     *
     * @param series1
     * @param series2
     */
    private void updateDataset(XYSeries series1, XYSeries series2) {

        // Remove all dataset
        xyDataset.removeAllSeries();

        // dereferencing the serieses
        this.series1 = null;
        this.series2 = null;

        // adding the new serieses to dataset
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);

        // referencing the serieses
        this.series1 = series1;
        this.series2 = series2;
    }


    /**
     * Clear the series from the linechart
     */
    @Override
    public void clearFunction() {

        series1.clear();
        series2.clear();
    }


    public ChartPanel getChartPanel() {

        return chartPanel;
    }


}