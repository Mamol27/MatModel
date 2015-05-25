import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

import java.awt.*;

/**
 * Created by Илья on 23.05.2015.
 */
public class TheChart extends JFreeChart {
    public TheChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
    }

}
