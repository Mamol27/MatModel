import dao.EcotmmDaoImpl;
import dao.MaterialDaoImpl;
import uni.Calculation;
import uni.TheChart;
import uni.TheTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
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
    private JTextArea TextArea3;
    private JTextArea TextArea2;
    private JTextArea TextArea1;
    private JTextArea TextArea7;
    private JTextArea TextArea8;
    private JTextArea TextArea9;
    private JFormattedTextField fieldDeltaL;
    private JFormattedTextField fieldC;
    private JFormattedTextField fieldT0;
    private JTextArea TextArea6;
    private JTextArea TextArea5;
    private JTextArea TextArea4;
    private JFormattedTextField fieldRo;
    private JFormattedTextField fieldMu0;
    private JFormattedTextField fieldAlfau;
    private JTextArea TextArea10;
    private JTextArea TextArea14;
    private JFormattedTextField fieldN;
    private JFormattedTextField fieldTr;
    private JFormattedTextField fieldB;
    private JTextArea TextArea11;
    private JTextArea TextArea12;
    private JTextArea TextArea13;
    private JButton calculateButton;
    private JComboBox comboBox1;
    private JTextArea TextArea15;
    private JPanel tabPanel;
    private TheChart theChart;
    private Calculation calc;


    int box_id;


    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);

//        buttonOK.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onOK();
//            }
//        });
//
//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

//// call onCancel() when cross is clicked
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                onCancel();
//            }
//        });

//// call onCancel() on ESCAPE
//        contentPane.registerKeyboardAction(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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

//                theChart.clearFunction();
                theChart.updateData(calc.getLayer(), calc.getH(), calc.getLayer(), calc.getT());
                theChart.updateTable();
//                theChart.showPlotter();

            }
        });
    }

//    private void onOK() {
//// add your code here
//        dispose();
//    }
//
//    private void onCancel() {
//// add your code here if necessary
//        dispose();
//    }

    public static void main(String[] args) {
        MainDialog dialog = new MainDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            MaterialDaoImpl materialDao = new MaterialDaoImpl();
            EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();

            JComboBox box = (JComboBox) e.getSource();
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
        }
    };

    private void editCombobox() throws SQLException {
        MaterialDaoImpl materialDao = new MaterialDaoImpl();
        EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();

        Vector<Object> items = materialDao.getColumn();

        comboBox1 = new JComboBox<Object>(items);
        comboBox1.setSelectedIndex(0);
        comboBox1.addActionListener(actionListener);
        comboBox1.setMaximumRowCount(5);

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

    }

    private void createUIComponents() throws SQLException {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(3);
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumIntegerDigits(6);
        formatter.setMinimumIntegerDigits(1);


        theTable = new TheTable();
        theTable.setColumnWidths(theTable.columns, 100, 250, 150, 180, 180, 180, 150, 150, 150, 150, 150);
        theTable.setSize(900, 500);


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


        fieldL.setValue(10.6);
        fieldW.setValue(0.27);
        fieldH.setValue(0.05);
        fieldVu.setValue(1.5);
        fieldTu.setValue(210.0);
        fieldDeltaL.setValue(0.1);

        editCombobox();


//        MaterialDao materialDao = new MaterialDaoImpl();
//        List<Material> materials = materialDao.get();

//        comboBox1 = new JComboBox(materials.toArray());
//        comboBox1 = new JComboBox(comboBoxArray);
//        comboBox1.setModel(new DefaultComboBoxModel(comboBoxArray));
//        JComboBox comboBox1 = new JComboBox();

//        comboBox1.setModel(materials.toArray());


        // TODO: place custom component creation code here
    }


}
