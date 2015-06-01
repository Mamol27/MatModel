package uni;

import java.util.Vector;

public interface PlotterInterface {

    public void updateData(Vector<Double> x, Vector<Double> y, Vector<Double> marksX, Vector<Double> marksY);

    public void updateTable();

    public void clearFunction();

}