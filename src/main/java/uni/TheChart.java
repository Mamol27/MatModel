package uni;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

// used sources: http://www.java2s.com/Code/Java/Chart/JFreeChartLineChartDemo1.htm
public class TheChart extends JPanel implements PlotterInterface {
    private XYPlot plot = null, plot2 = null;
    private XYSeries series1 = null;
    private XYSeries series2 = null;
    private XYSeriesCollection xyDataset1 = null, xyDataset2 = null;
    private ChartPanel chartPanel = null, chartPanel2 = null;
    private NumberAxis rangeAxis = null;
    private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    private Vector<Double> L = new Vector<>();
    private Vector<Double> T = new Vector<>();
    private Vector<Long> H = new Vector<>();
    JTable table = new JTable();
    JPanel panelOfCharts = new JPanel(new GridLayout(2, 1));
    GridBagLayout gbl = new GridBagLayout();
    JPanel panelTable = new JPanel(gbl);
    GridBagConstraints c = new GridBagConstraints();
    public String namefile;
    private ExtractExcel extractExcel;
    JFreeChart chart2;
    JFreeChart chart1;
    Date newTime;


    public TheChart() {

        JButton saveReport = new JButton("Сохранить отчет");
//        saveReport.setMaximumSize(new Dimension(250,50));

        saveReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.xls", "*.*");
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileFilter(filter);
                if (fileopen.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    namefile = fileopen.getSelectedFile().getAbsolutePath();
                    try {

                        if (!namefile.endsWith(".xls")) {
                            namefile += ".xls";
                        }
                        saveCharts();
                        extractExcel.setNameFile(namefile);
                        extractExcel.setTable(table);
                        extractExcel.createReport();
                        JOptionPane.showMessageDialog(null, "Успешно");
                    } catch (IOException e1) {
                        System.out.println("Беда!");

                    }
                }
            }
        });

        initFrame();
        createTable();
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        panelTable.add(saveReport, c);
        createChart1();
        createChart2();

        add("West", panelTable);
        add("Center", panelOfCharts);
    }

    private void initFrame() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

    }

    private void createChart1() {
        xyDataset1 = createDataset1("");

        chart1 = ChartFactory.createXYLineChart(
                "Зависимость температуры от длины",                         // chart title	"Line Chart Demo 6"
                "Длина, [м]",               // x axis label
                "Температура, [°С]",        // y axis label
                xyDataset1,                  // data
                PlotOrientation.VERTICAL,
                false,                       // include legend
                true,                       // tooltips
                false                       // urls
        );

        plot = chart1.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        renderer.setSeriesShapesVisible(0, false);    // a thin line will be painted for series1
//        renderer.setSeriesShapesVisible(1, false);    // points will be painted for series2

        plot.setRenderer(renderer);

        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chartPanel = new ChartPanel(chart1);
        chartPanel.setPreferredSize(new Dimension(600, 250));
        panelOfCharts.add(chartPanel);
    }

    private void createChart2() {
        xyDataset2 = createDataset2("");

        chart2 = ChartFactory.createXYLineChart(
                "Зависимость вязкости от длины",                         // chart title	"Line Chart Demo 6"
                "Длина, [м]",               // x axis label
                "Вязкость, [Па*с]",         // y axis label
                xyDataset2,                  // data
                PlotOrientation.VERTICAL,
                false,                       // include legend
                true,                       // tooltips
                false                       // urls
        );

        plot2 = chart2.getXYPlot();
        plot2.setBackgroundPaint(Color.white);
        plot2.setDomainGridlinePaint(Color.lightGray);
        plot2.setRangeGridlinePaint(Color.lightGray);

        renderer.setSeriesShapesVisible(0, false);    // a thin line will be painted for series1
//        renderer.setSeriesShapesVisible(1, false);    // points will be painted for series2

        plot2.setRenderer(renderer);

        rangeAxis = (NumberAxis) plot2.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chartPanel2 = new ChartPanel(chart2);
        chartPanel2.setPreferredSize(new Dimension(600, 250));
        panelOfCharts.add(chartPanel2);
    }

    private void createTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("Длина, м", L);
        defaultTableModel.addColumn("Вязкость Па*с", H);
        defaultTableModel.addColumn("Температура, °С", T);

        table = new JTable(defaultTableModel);
        table.setRowHeight(table.getRowHeight() + 4);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 480));

        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 40, 0, 100);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;

        panelTable.add(scrollPane, c);
    }

    @Override
    public void updateTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Длина, [м]", L);
        defaultTableModel.addColumn("Вязкость, [Па*с]", H);
        defaultTableModel.addColumn("Температура, [°С]", T);
        table.setModel(defaultTableModel);


        L.clear();
        H.clear();
        T.clear();
        newTime = new Date();

    }

    private XYSeriesCollection createDataset1(String legendTxt) {

        series1 = new XYSeries("Вязкость, [Па*с]");

        final XYSeriesCollection dataset1 = new XYSeriesCollection();

        dataset1.addSeries(series1);

        return dataset1;
    }

    private XYSeriesCollection createDataset2(String legendTxt) {

        series2 = new XYSeries("Вязкость, [Па*с]");

        final XYSeriesCollection dataset2 = new XYSeriesCollection();

        dataset2.addSeries(series2);

        return dataset2;
    }

    @Override
    public void updateData(Vector<Double> x, Vector<Double> y, Vector<Double> marksX, Vector<Double> marksY) throws ParseException {

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);

        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMinimumIntegerDigits(1);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        NumberFormat nf = NumberFormat.getInstance();

        for (int i = 0; i < x.size(); i++) {
            L.add(nf.parse(df.format(x.get(i)).replaceAll("[,.]", String.valueOf(symbols.getDecimalSeparator()))).doubleValue());
            H.add(nf.parse(formatter.format(marksY.get(i)).replaceAll("[,.]", String.valueOf(symbols.getDecimalSeparator()))).longValue());
            T.add(nf.parse(df.format(y.get(i)).replaceAll("[,.]", String.valueOf(symbols.getDecimalSeparator()))).doubleValue());
        }

        XYSeries series1 = new XYSeries("Вязкость, [Па*с]");
        XYSeries series2 = new XYSeries("Температура, [°С]");

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

    private void updateDataset(XYSeries series1, XYSeries series2) {

        // Remove all dataset
        xyDataset1.removeAllSeries();
        xyDataset2.removeAllSeries();

        // dereferencing the serieses
        this.series1 = null;
        this.series2 = null;

        // adding the new serieses to dataset
        xyDataset1.addSeries(series1);
        xyDataset2.addSeries(series2);

        // referencing the serieses
        this.series1 = series1;
        this.series2 = series2;
    }

    @Override
    public void clearFunction() {

        series1.clear();
        series2.clear();
    }

    public ChartPanel getChartPanel() {

        return chartPanel;
    }

    public JTable getTable() {
        return table;
    }

    private void saveCharts() {
        try {
            // например в файл
            OutputStream stream = new FileOutputStream("chart1.png");
            ChartUtilities.writeChartAsPNG(stream, chart1, 500, 300);
            stream.close();
        } catch (IOException e) {
            System.err.println("Failed to render chart as png: " + e.getMessage());
            e.printStackTrace();
        }


        try {
            // например в файл
            OutputStream stream = new FileOutputStream("chart2.png");
            ChartUtilities.writeChartAsPNG(stream, chart2, 500, 300);
            stream.close();
        } catch (IOException e) {
            System.err.println("Failed to render chart as png: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setExtractExcel(ExtractExcel extractExcel) {
        this.extractExcel = extractExcel;
    }
}
