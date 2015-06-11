package uni;

import java.text.ParseException;
import java.util.Vector;

public interface PlotterInterface {

    public void updateData(Vector<Double> x, Vector<Double> y, Vector<Double> marksX, Vector<Double> marksY) throws ParseException;

    public void updateTable();

    public void clearFunction();

}