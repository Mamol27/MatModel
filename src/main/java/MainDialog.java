import javax.swing.*;
import java.sql.SQLException;
import java.text.NumberFormat;

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
    private JButton CalculateButton;
    private JComboBox comboBox1;
    private JTextArea TextArea15;
    private JPanel tabPanel;
    static DbConnection db;


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

    private void createUIComponents() throws SQLException {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(3);
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumIntegerDigits(4);
        formatter.setMinimumIntegerDigits(1);

        DbConnection db = new DbConnection();
//        theTable;
        theTable = new TheTable(db);
        theTable.setColumnWidths(db.columns, 100, 250, 150, 180, 180, 180, 150, 150, 150, 150, 150);
        theTable.setSize(8000, 5000);


        fieldL = new JFormattedTextField(formatter);
        fieldW = new JFormattedTextField(formatter);
        fieldH = new JFormattedTextField(formatter);
        fieldVu = new JFormattedTextField(formatter);
        fieldTu = new JFormattedTextField(formatter);
        fieldDeltaL = new JFormattedTextField(formatter);

        fieldL.setValue(10.6);
        fieldW.setValue(0.27);
        fieldH.setValue(0.05);
        fieldVu.setValue(1.5);
        fieldTu.setValue(210);
        fieldDeltaL.setValue(0.1);

        comboBox1 = new JComboBox(theTable.table.getColumn("Название").getPropertyChangeListeners());




        // TODO: place custom component creation code here
    }
}
