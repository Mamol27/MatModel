package uni;

import dao.EcotmmDaoImpl;
import dao.MaterialDaoImpl;
import model.Ecotmm;
import model.Material;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;


/**
 * Created by Илья on 13.05.2015.
 */
public class TheTable extends JPanel {
    JTable table;
    Font font;
    private JButton addMaterial, removeMaterial;
    private JTextField tfID, tfRo, tfC, tfT0, tfDescription, tfType;
    private JTextField tfMu0, tfB, tfTr, tfN, tfAlfau;
    JLabel errorMessage;
    public DefaultTableModel defaultTableModel;
    private Object[][] databaseResults;
    public Object[] columns;


    private class ListenForAction implements ActionListener { // Слушаем кнопки
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMaterial) { // Если тыкнули в "Добвать материал"
                int pk_materialID = (int) table.getValueAt(table.getRowCount() - 1, 0);
                Material material = new Material(pk_materialID + 1, tfType.getText(), Double.parseDouble(tfRo.getText()),
                        Double.parseDouble(tfC.getText()), Double.parseDouble(tfT0.getText()), tfDescription.getText()); //Модель строки материала
                MaterialDaoImpl materialDao = new MaterialDaoImpl();
                materialDao.insert(material); // Добавили строку в таблицу МАтериалов

                EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();
                Ecotmm ecotmm = new Ecotmm(pk_materialID + 1, Double.parseDouble(tfMu0.getText()),
                        Double.parseDouble(tfB.getText()), Double.parseDouble(tfTr.getText()), Double.parseDouble(tfN.getText()),
                        Double.parseDouble(tfAlfau.getText())); //Модель строки для Эмпирических коэффициентов материалов
                ecotmmDao.insert(ecotmm); //Так как таблица Эмпирических коффициэнтов вторичная нам понадобился номер строки, в которую добавили материалы
                createTable();
                table.setModel(defaultTableModel);

            } else if (e.getSource() == removeMaterial) {
                try { // Если тыкнули в "Удалить материал"
                    int idSelectedRow = (int) table.getValueAt(table.getSelectedRow(), 0);
                    EcotmmDaoImpl.delete(idSelectedRow); // Сначала удаляем строку из вторичной таблицы
                    MaterialDaoImpl.delete(idSelectedRow);
                    defaultTableModel.removeRow(table.getSelectedRow());
                    table.setModel(defaultTableModel);
                } catch (ArrayIndexOutOfBoundsException e1) {
                    System.out.println(e1.getMessage());
                    errorMessage.setText("Для удаления выдели строку");
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

                    // Go to the row in the db
//                        MainDialog.db.res1.absolute(table.getSelectedRow() + 1);
                    updateColumn = defaultTableModel.getColumnName(table.getSelectedColumn());
//                        MainDialog.db.res1.updateString(updateColumn, value);
//                        MainDialog.db.res1.updateRow();
//                        }
                }
            }
        }
    }

    private class ListenForFocus implements FocusListener {
        // My terrible and possibly hack-ish way of implementing 'placeholders' in the JTextFields.
        public void focusGained(FocusEvent e) { // If a text field gains focus and has the default text, remove the text

            if (tfRo.getText().equals("Плотность") && e.getSource() == tfRo) {
                tfRo.setText("");
            } else if (tfC.getText().equals("Теплоемкость") && e.getSource() == tfC) {
                tfC.setText("");
            } else if (tfT0.getText().equals("Т плавления") && e.getSource() == tfT0) {
                tfT0.setText("");
            } else if (tfDescription.getText().equals("Описание") && e.getSource() == tfDescription) {
                tfDescription.setText("");
            } else if (tfType.getText().equals("Название") && e.getSource() == tfType) {
                tfType.setText("");
            } else if (tfMu0.getText().equals("Коэффициэет консистениции") && e.getSource() == tfMu0) {
                tfMu0.setText("");
            } else if (tfB.getText().equals("Коэффициэнт вязкости") && e.getSource() == tfB) {
                tfB.setText("");
            } else if (tfTr.getText().equals("Т приведения") && e.getSource() == tfTr) {
                tfTr.setText("");
            } else if (tfN.getText().equals("Индекс Течения") && e.getSource() == tfN) {
                tfN.setText("");
            } else if (tfAlfau.getText().equals("Коэффициэнт теплоотдачи от крышки") && e.getSource() == tfAlfau) {
                tfAlfau.setText("");
            }
        }

        public void focusLost(FocusEvent e) { // If the text field loses focus and is blank, set the default text back
            if (tfType.getText().equals("") && e.getSource() == tfType) {
                tfType.setText("Название");
            } else if (tfRo.getText().equals("") && e.getSource() == tfRo) {
                tfRo.setText("Плотность");
            } else if (tfC.getText().equals("") && e.getSource() == tfC) {
                tfC.setText("Теплоемкость");
            } else if (tfT0.getText().equals("") && e.getSource() == tfT0) {
                tfT0.setText("Т плавления");
            } else if (tfDescription.getText().equals("") && e.getSource() == tfDescription) {
                tfDescription.setText("Описание");
            } else if (tfMu0.getText().equals("") && e.getSource() == tfMu0) {
                tfMu0.setText("Коэффициэет консистениции");
            } else if (tfB.getText().equals("") && e.getSource() == tfB) {
                tfB.setText("Коэффициэнт вязкости");
            } else if (tfTr.getText().equals("") && e.getSource() == tfTr) {
                tfTr.setText("Т приведения");
            } else if (tfN.getText().equals("") && e.getSource() == tfN) {
                tfN.setText("Индекс Течения");
            } else if (tfAlfau.getText().equals("") && e.getSource() == tfAlfau) {
                tfAlfau.setText("Коэффициэнт теплоотдачи от крышки");
            }


        }
    }

    public TheTable() throws SQLException {
        super();
        createTable();
        table = new JTable(defaultTableModel);
        font = new Font("Serif", Font.PLAIN, 18);

        table.setFont(font);
        table.setRowHeight(table.getRowHeight() + 4);
        table.setAutoCreateRowSorter(true);

        ListenForMouse mouseListener = new ListenForMouse();
        table.addMouseListener(mouseListener);


        // Create a JScrollPane and add it to the center of the window
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        scrollPane.setMinimumSize(new Dimension(800, 300));
        scrollPane.setMaximumSize(new Dimension(1000, 300));
        scrollPane.setPreferredSize(new Dimension(900, 300));


        // Set button values
        addMaterial = new JButton("Добавить материал");
        removeMaterial = new JButton("Удалить материал");

        ListenForAction actionListener = new ListenForAction();
        addMaterial.addActionListener(actionListener);
        removeMaterial.addActionListener(actionListener);

//        tfID = new JTextField("Номер", 3);
        tfType = new JTextField("Название", 8);
        tfRo = new JTextField("Плотность", 8);
        tfC = new JTextField("Теплоемкость", 8);
        tfT0 = new JTextField("Т плавления", 8);
        tfDescription = new JTextField("Описание", 8);
        tfMu0 = new JTextField("Коэффициэет консистениции", 15);
        tfB = new JTextField("Коэффициэнт вязкости", 15);
        tfTr = new JTextField("Т приведения", 10);
        tfN = new JTextField("Индекс Течения", 10);
        tfAlfau = new JTextField("Коэффициэнт теплоотдачи от крышки", 18);

        //Ставлю фокус листеннер для корректного отображения полей внизу
        ListenForFocus focusListenner = new ListenForFocus();
        tfType.addFocusListener(focusListenner);
        tfRo.addFocusListener(focusListenner);
        tfC.addFocusListener(focusListenner);
        tfT0.addFocusListener(focusListenner);
        tfDescription.addFocusListener(focusListenner);
        tfMu0.addFocusListener(focusListenner);
        tfB.addFocusListener(focusListenner);
        tfTr.addFocusListener(focusListenner);
        tfN.addFocusListener(focusListenner);
        tfAlfau.addFocusListener(focusListenner);


        //панель для вставки новой строки
        JPanel inputPanel = new JPanel();
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

    public void createTable() {
        MaterialDaoImpl materialDao = new MaterialDaoImpl();
        EcotmmDaoImpl ecotmmDao = new EcotmmDaoImpl();

        columns = new Object[]{"Номер", "Название", "Плотность", "Теплоемкость", "Т плавления", "Описание",
                "Коэффициэет консистениции", "Коэффициэнт вязкости", "Т приведения", "Индекс Течения", "Коэффициэнт теплоотдачи от крышки"};

        Vector columns2 = new Vector<String>();
        for (int i = 0; i < columns.length; i++) {
            columns2.add(columns[i]);
        }

        Vector<Vector<Object>> materials = materialDao.get();
        Vector<Vector<Object>> ecotmm = ecotmmDao.get();

        defaultTableModel = new DefaultTableModel(databaseResults, columns) {
            public Class getColumnClass(int column) {  // Override the getColumnClass method to get the
                Class classToReturn;                   // class types of the data retrieved from the database

                if ((column >= 0) && column < getColumnCount()) {
                    classToReturn = getValueAt(0, column).getClass();
                } else {
                    classToReturn = Object.class;
                }
                return classToReturn;
            }
        };


        for (int i = 0; i < ecotmm.size(); i++) {
            materials.get(i).addAll(ecotmm.get(i));
        }
        defaultTableModel.setDataVector(materials, columns2);

    }

}
