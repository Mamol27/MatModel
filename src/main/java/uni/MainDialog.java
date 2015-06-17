package uni;

import dao.EcotmmDaoImpl;
import dao.MaterialDaoImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Vector;

public class MainDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private TheTable theTable;
    private JTabbedPane tabs;
    private JFormattedTextField fieldW;
    private JFormattedTextField fieldH;
    private JFormattedTextField fieldL;
    private JFormattedTextField fieldVu;
    private JFormattedTextField fieldTu;
    private JPanel mainPanel;
    private JTextArea TextAreaH;
    private JTextArea TextAreaW;
    private JTextArea TextAreaL;
    private JTextArea TextAreaVu;
    private JTextArea TextAreaTu;
    private JTextArea TextAreaDeltaL;
    private JFormattedTextField fieldDeltaL;
    private JFormattedTextField fieldC;
    private JFormattedTextField fieldT0;
    private JTextArea TextAreaT0;
    private JTextArea TextAreaC;
    private JTextArea TextAreaRo;
    private JFormattedTextField fieldRo;
    private JFormattedTextField fieldMu0;
    private JFormattedTextField fieldAlfau;
    private JTextArea TextAreaMu0;
    private JTextArea TextAreaAlfau;
    private JFormattedTextField fieldN;
    private JFormattedTextField fieldTr;
    private JFormattedTextField fieldB;
    private JTextArea TextAreaB;
    private JTextArea TextAreaTr;
    private JTextArea TextAreaN;
    private JButton calculateButton;
    private JComboBox comboBox1;
    private JTextArea TextArea15;
    private JPanel tabPanel;
    private TheChart theChart;
    private Calculation calc;


    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calc.setW((Double) fieldW.getValue());
                calc.setH((Double) fieldH.getValue());
                calc.setL((Double) fieldL.getValue());
                calc.setVu((Double) fieldVu.getValue());
                calc.setTu((Double) fieldTu.getValue());
                calc.setDeltaL((Double) fieldDeltaL.getValue());
                calc.calculate();
                setToExtractExcel();

//                theChart.clearFunction();
                try {
                    theChart.updateData(calc.getLayer(), calc.getH(), calc.getLayer(), calc.getT());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                theChart.updateTable();
//                theChart.showPlotter();

            }
        });
    }


    public static void main(String[] args) {
        MainDialog dialog = new MainDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void fillComboBox() throws SQLException {
        MaterialDaoImpl materialDao = new MaterialDaoImpl();
        EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();

        comboBox1 = new JComboBox();
        for (String item : materialDao.getColumn()) {
            comboBox1.addItem(item);
        }


        int box_id = (int) theTable.defaultTableModel.getValueAt(comboBox1.getSelectedIndex(), 0);
        Vector<Object> materials = materialDao.get(box_id);
        Vector<Object> ecotmm = ecotmmDao.get(box_id);
        calc = new Calculation(materials.get(2), materials.get(3), materials.get(4),
                ecotmm.get(0), ecotmm.get(1), ecotmm.get(2), ecotmm.get(3), ecotmm.get(4));
        fieldRo.setValue(materials.get(2));
        fieldC.setValue(materials.get(3));
        fieldT0.setValue(materials.get(4));
        fieldMu0.setValue(ecotmm.get(0));
        fieldB.setValue(ecotmm.get(1));
        fieldTr.setValue(ecotmm.get(2));
        fieldN.setValue(ecotmm.get(3));
        fieldAlfau.setValue(ecotmm.get(4));

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MaterialDaoImpl materialDao = new MaterialDaoImpl();
                EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();

                JComboBox box = (JComboBox) e.getSource();
                int box_id = (int) theTable.defaultTableModel.getValueAt(box.getSelectedIndex(), 0);
                Vector<Object> materials = materialDao.get(box_id);
                Vector<Object> ecotmm = ecotmmDao.get(box_id);
                calc = new Calculation(materials.get(2), materials.get(3), materials.get(4),
                        ecotmm.get(0), ecotmm.get(1), ecotmm.get(2), ecotmm.get(3), ecotmm.get(4));
                fieldRo.setValue(materials.get(2));
                fieldC.setValue(materials.get(3));
                fieldT0.setValue(materials.get(4));
                fieldMu0.setValue(ecotmm.get(0));
                fieldB.setValue(ecotmm.get(1));
                fieldTr.setValue(ecotmm.get(2));
                fieldN.setValue(ecotmm.get(3));
                fieldAlfau.setValue(ecotmm.get(4));
            }
        });

    }

    private class ListenForFocus implements FocusListener {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            if ((double) fieldL.getValue() < 0.0) fieldL.setValue(Math.abs((double) fieldL.getValue()));
            if ((double) fieldW.getValue() < 0) fieldW.setValue(Math.abs((double) fieldW.getValue()));
            if ((double) fieldH.getValue() < 0) fieldH.setValue(Math.abs((double) fieldL.getValue()));
            if ((double) fieldVu.getValue() < 0) fieldVu.setValue(Math.abs((double) fieldVu.getValue()));
            if ((double) fieldTu.getValue() < 0) fieldTu.setValue(Math.abs((double) fieldTu.getValue()));
            if ((double) fieldDeltaL.getValue() < 0) fieldDeltaL.setValue(Math.abs((Double) fieldDeltaL.getValue()));
        }
    }


    private void createUIComponents() throws SQLException {


        theTable = new TheTable();
        theTable.setColumnWidths(theTable.columns, 100, 250, 150, 180, 180, 180, 150, 150, 150, 150, 150);
        theTable.setSize(900, 500);

        setFormatField();

        fieldL.setValue(10.6);
        fieldW.setValue(0.27);
        fieldH.setValue(0.05);
        fieldVu.setValue(1.5);
        fieldTu.setValue(210.1);
        fieldDeltaL.setValue(0.1);


        ListenForFocus focusListenner = new ListenForFocus();
        fieldL.addFocusListener(focusListenner);
        fieldW.addFocusListener(focusListenner);
        fieldH.addFocusListener(focusListenner);
        fieldVu.addFocusListener(focusListenner);
        fieldTu.addFocusListener(focusListenner);
        fieldDeltaL.addFocusListener(focusListenner);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    fillComboBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setFormatField() {

        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(3);
        formatter.setMinimumFractionDigits(1);
        formatter.setMinimumIntegerDigits(1);

        fieldL = new JFormattedTextField(formatter);
        fieldW = new JFormattedTextField(formatter);
        fieldH = new JFormattedTextField(formatter);
        fieldVu = new JFormattedTextField(formatter);
        fieldTu = new JFormattedTextField(formatter);
        fieldDeltaL = new JFormattedTextField(formatter);
        fieldRo = new JFormattedTextField(formatter);
        fieldC = new JFormattedTextField(formatter);
        fieldT0 = new JFormattedTextField(formatter);
        fieldMu0 = new JFormattedTextField(formatter);
        fieldB = new JFormattedTextField(formatter);
        fieldTr = new JFormattedTextField(formatter);
        fieldN = new JFormattedTextField(formatter);
        fieldAlfau = new JFormattedTextField(formatter);

    }


    private void setToExtractExcel() {
        ExtractExcel extractExcel = new ExtractExcel();
        theChart.setExtractExcel(extractExcel);

        extractExcel.setTextAreaL(TextAreaL.getText());
        extractExcel.setTextAreaW(TextAreaW.getText());
        extractExcel.setTextAreaH(TextAreaH.getText());
        extractExcel.setTextAreaVu(TextAreaVu.getText());
        extractExcel.setTextAreaTu(TextAreaTu.getText());
        extractExcel.setTextAreaDeltaL(TextAreaDeltaL.getText());
        extractExcel.setTextAreaRo(TextAreaRo.getText());
        extractExcel.setTextAreaC(TextAreaC.getText());
        extractExcel.setTextAreaT0(TextAreaT0.getText());
        extractExcel.setTextAreaMu0(TextAreaMu0.getText());
        extractExcel.setTextAreaB(TextAreaB.getText());
        extractExcel.setTextAreaTr(TextAreaTr.getText());
        extractExcel.setTextAreaN(TextAreaN.getText());
        extractExcel.setTextAreaAlfau(TextAreaAlfau.getText());

        extractExcel.setFieldL((Double) fieldL.getValue());
        extractExcel.setFieldW((Double) fieldW.getValue());
        extractExcel.setFieldH((Double) fieldH.getValue());
        extractExcel.setFieldVu((Double) fieldVu.getValue());
        extractExcel.setFieldTu((Double) fieldTu.getValue());
        extractExcel.setFieldDeltaL((Double) fieldDeltaL.getValue());
        extractExcel.setFieldRo((Double) fieldRo.getValue());
        extractExcel.setFieldC((Double) fieldC.getValue());
        extractExcel.setFieldT0((Double) fieldT0.getValue());
        extractExcel.setFieldMu0((Double) fieldMu0.getValue());
        extractExcel.setFieldB((Double) fieldB.getValue());
        extractExcel.setFieldTr((Double) fieldTr.getValue());
        extractExcel.setFieldN((Double) fieldN.getValue());
        extractExcel.setFieldAlfau((Double) fieldAlfau.getValue());

    }

}
