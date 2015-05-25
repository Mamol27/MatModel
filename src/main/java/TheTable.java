import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;


/**
 * Created by Илья on 13.05.2015.
 */
public class TheTable extends JPanel {
    JTable table, table2;
    Font font;
    private JButton addMaterial, removeMaterial;
    private JTextField tfID, tfRo, tfC, tfT0, tfDescription, tfType;
    private JTextField tfMu0, tfB, tfTr, tfN, tfAlfau;
    JLabel errorMessage;


    public TheTable() {
    }

    private class ListenForAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMaterial) { // If the user clicks Add Customer, add the information into the database
                // Create variables to hold information to be inserted, and get the info from the text fields
                String type, Ro, C, description, T0;
                type = tfType.getText();
                Ro = tfRo.getText();
                C = tfC.getText();
                description = tfDescription.getText();
                T0 = tfT0.getText();

                int pk_materialID = 0;
                try { // Attempt to insert the information into the database
                    MainDialog.db.res1.moveToInsertRow();
                    MainDialog.db.res1.updateString("ash", C);
                    MainDialog.db.res1.updateString("melting_point", T0);
                    MainDialog.db.res1.updateString("description", description);
                    MainDialog.db.res1.updateString("type", type);
                    MainDialog.db.res1.updateString("density", Ro);

                    MainDialog.db.res1.insertRow();
                    MainDialog.db.res1.updateRow();

                    MainDialog.db.res1.last();
                    pk_materialID = class1.db.res1.getInt("material_id");
                    Object[] materials = {pk_materialID, C, T0, description, type, Ro};
                    MainDialog.db.defaultTableModel.addRow(materials); // Add the row to the screen
                    errorMessage.setText(""); // Remove the error message if one was displayed
                } catch (SQLException e2) { // Catch any exceptions and display appropriate errors
                    System.out.println(e2.getMessage());
                    if (e2.getMessage().toString().startsWith("Data")) {
                        errorMessage.setText("A state should be a two-letter abbreviation.");
                    }
                }
            } else if (e.getSource() == removeMaterial) {
                try { // If the user clicked remove customer, delete from database and remove from table
                    MainDialog.db.defaultTableModel.removeRow(table.getSelectedRow());
                    MainDialog.db.res1.absolute(table.getSelectedRow());
                    MainDialog.db.res1.deleteRow();
                } catch (SQLException e1) { // Catch any exceptions
                    System.out.println(e1.getMessage());
                    errorMessage.setText(e1.getMessage());
                } catch (ArrayIndexOutOfBoundsException e1) {
                    System.out.println(e1.getMessage());
                    errorMessage.setText("To delete a customer, you must first select a row.");
                }
            }
        }
    }

    private class ListenForMouse extends MouseAdapter {
        public void mouseReleased(MouseEvent mouseEvent) {
            // If the mouse is released and the click was a right click
            if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                // Create a dialog for the user to enter new data
                String value = JOptionPane.showInputDialog(null, "Введи новое значение:");
                if (value != null) { // If they entered info, update the database
                    table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
                    String updateColumn;

                    try { // Go to the row in the db
                        MainDialog.db.res1.absolute(table.getSelectedRow() + 1);
                        updateColumn = class1.db.defaultTableModel.getColumnName(table.getSelectedColumn());

//                        switch(updateColumn) {
//                            // if the column was date_registered, convert date update using a Date
//                            case "Date_Registered":
//                                sqlDateRegistered = getADate(value);
//                                class1.db.res1.updateDate(updateColumn, (Date) sqlDateRegistered);
//                                class1.db.res1.updateRow();
//                                break;
//                            default: // otherwise update using a String
                        MainDialog.db.res1.updateString(updateColumn, value);
                        MainDialog.db.res1.updateRow();
//                                break;
//                        }
                    } catch (SQLException e1) { // Catch any exceptions and display an error
                        errorMessage.setText("An error has occurred.");
                        System.out.println(e1.getMessage());
                    }
                }
            }
        }
    }

    private class ListenForFocus implements FocusListener {
        // My terrible and possibly hack-ish way of implementing 'placeholders' in the JTextFields.
        public void focusGained(FocusEvent e) { // If a text field gains focus and has the default text, remove the text
            if (tfID.getText().equals("Номер") && e.getSource() == tfID) {
                tfID.setText("");
            } else if (tfRo.getText().equals("Плотность") && e.getSource() == tfRo) {
                tfRo.setText("");
            } else if (tfC.getText().equals("Теплоемкость") && e.getSource() == tfC) {
                tfC.setText("");
            } else if (tfT0.getText().equals("Т плавления") && e.getSource() == tfT0) {
                tfT0.setText("");
            } else if (tfDescription.getText().equals("Описание") && e.getSource() == tfDescription) {
                tfDescription.setText("");
            } else if (tfType.getText().equals("Название") && e.getSource() == tfType) {
                tfType.setText("");
            } else if (tfMu0.getText().equals("Date Registered") && e.getSource() == tfMu0) {
                tfMu0.setText("");
            } else if (tfB.getText().equals("Date Registered") && e.getSource() == tfB) {
                tfB.setText("");
            } else if (tfTr.getText().equals("Date Registered") && e.getSource() == tfTr) {
                tfTr.setText("");
            } else if (tfN.getText().equals("Date Registered") && e.getSource() == tfN) {
                tfN.setText("");
            } else if (tfAlfau.getText().equals("Date Registered") && e.getSource() == tfAlfau) {
                tfAlfau.setText("");
            }
        }

        public void focusLost(FocusEvent e) { // If the text field loses focus and is blank, set the default text back
            if (tfID.getText().equals("") && e.getSource() == tfID) {
                tfID.setText("Название");
            } else if (tfRo.getText().equals("") && e.getSource() == tfRo) {
                tfRo.setText("Плотность");
            } else if (tfC.getText().equals("") && e.getSource() == tfC) {
                tfC.setText("Теплоемкость");
            } else if (tfT0.getText().equals("") && e.getSource() == tfT0) {
                tfT0.setText("Т плавления");
            } else if (tfDescription.getText().equals("") && e.getSource() == tfDescription) {
                tfDescription.setText("Описание");
            }
//              else if(tfMu0.getText().equals("") && e.getSource() == tfMu0) {
//                tfMu0.setText("Коэффициэет консистениции");
//            } else if(tfB.getText().equals("") && e.getSource() == tfB) {
//                tfB.setText("Коэффициэнт вязкости");
//            } else if(tfTr.getText().equals("") && e.getSource() == tfTr) {
//                tfTr.setText("Т приведения");
//            } else if(tfN.getText().equals("") && e.getSource() == tfN) {
//                tfN.setText("Индекс Течения");
//            } else if(tfAlfau.getText().equals("") && e.getSource() == tfAlfau) {
//                tfAlfau.setText("Коэффициэнт теплоотдачи от крышки");
//            }


        }
    }

    public TheTable(DbConnection dbc) throws SQLException {
        super();
//        setLayout(new GridLayout(3, 1));
        table = new JTable(dbc.defaultTableModel);
        font = new Font("Serif", Font.PLAIN, 18);

        table.setFont(font);
        table.setRowHeight(table.getRowHeight() + 4);
        table.setAutoCreateRowSorter(true);

        ListenForMouse mouseListener = new ListenForMouse();
        table.addMouseListener(mouseListener);


        // Create a JScrollPane and add it to the center of the window
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        Dimension minimumsize = new Dimension(800,300);
        Dimension maxsize = new Dimension(1200,600);
        Dimension prefsize = new Dimension(1000,300);
        scrollPane.setMinimumSize(minimumsize);
        scrollPane.setMaximumSize(maxsize);
        scrollPane.setPreferredSize(prefsize);


        // Set button values
        addMaterial = new JButton("Добавить материал");
        removeMaterial = new JButton("Удалить материал");

        ListenForAction actionListener = new ListenForAction();
        addMaterial.addActionListener(actionListener);
        removeMaterial.addActionListener(actionListener);

        tfID = new JTextField("Номер", 3);
        tfType = new JTextField("Название", 8);
        tfRo = new JTextField("Плотность", 8);
        tfC = new JTextField("Теплоемкость", 8);
        tfT0 = new JTextField("Т плавления", 8);
        tfDescription = new JTextField("Описание", 8);
        tfMu0 = new JTextField("Коэффициэет консистениции", 8);
        tfB = new JTextField("Коэффициэнт вязкости", 8);
        tfTr = new JTextField("Т приведения", 8);
        tfN = new JTextField("Индекс Течения", 8);
        tfAlfau = new JTextField("Коэффициэнт теплоотдачи от крышки", 8);

        JPanel inputPanel = new JPanel();
//        inputPanel.add(tfID);
        inputPanel.add(tfType);
        inputPanel.add(tfRo);
        inputPanel.add(tfC);
        inputPanel.add(tfT0);
        inputPanel.add(tfDescription);
        inputPanel.add(addMaterial);
        inputPanel.add(removeMaterial);

        JPanel inputPanel2 = new JPanel();
        inputPanel2.add(tfMu0);
        inputPanel2.add(tfB);
        inputPanel2.add(tfTr);
        inputPanel2.add(tfN);
        inputPanel2.add(tfAlfau);

        errorMessage = new JLabel("");
        errorMessage.setForeground(Color.red);
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.add(errorMessage);

        add(inputPanel, BorderLayout.SOUTH);
        add(inputPanel2, BorderLayout.SOUTH);
        this.add(errorPanel, BorderLayout.NORTH);

        DefaultTableCellRenderer centerColumns = new DefaultTableCellRenderer();
        centerColumns.setHorizontalAlignment(JLabel.LEFT);
        TableColumn tc = table.getColumn("Номер");
        tc.setCellRenderer(centerColumns);
    }

    public void setColumnWidths(Object[] columns, int... widths) {
        TableColumn column;
        for (int i = 0; i < columns.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

}
