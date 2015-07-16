package dao;

import uni.DbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;


public class UsersDao extends JDialog {
    private Vector<Object> users;
    public boolean isAdmin = false;
    private JDialog controllingFrame;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    public UsersDao(JDialog owner) throws SQLException {
        super(owner, "Войди в систему", true);
        controllingFrame = owner;
//        setSize(300, 150);
        int D_WIDTH = 300;
        int D_HEIGHT = 150;
        this.setLayout(new GridLayout(3, 1));
        this.setBounds(((int) d.getWidth() - D_WIDTH) / 2, ((int) d.getHeight() - D_HEIGHT) / 2, D_WIDTH, D_HEIGHT);

        final JButton ok = new JButton("Войти");
        ok.setEnabled(false);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox) e.getSource();
                int box_id = (int) box.getSelectedIndex() + 1;
                users = get(box_id);
                ok.setEnabled(true);
            }
        };

        JComboBox<String> comboBox1;
        comboBox1 = new JComboBox<String>(getColumn());

        comboBox1.setMaximumRowCount(3);
        comboBox1.setSelectedIndex(-1);
        comboBox1.addActionListener(actionListener);
        JLabel labelBox = new JLabel("Выбери логин");
        labelBox.setLabelFor(comboBox1);

        JLabel label = new JLabel("Введи пароль");
        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(180, 20));
        label.setLabelFor(passwordField);


        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] input = passwordField.getPassword();
                String corpas1 = (String) users.get(2);
                char[] corpas = corpas1.toCharArray();
                if (isPasswordCorrect(input, corpas)) {
                    if (users.get(1).equals("Администратор")) {
                        isAdmin = true;
                    }
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(controllingFrame,
                            "Пароль неверен, попробуй еще раз"
                            , "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ok.setVisible(true);

        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);

        JPanel boxPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        boxPane.add(labelBox);
        boxPane.add(comboBox1);

        add(boxPane, BorderLayout.CENTER);
        add(textPane, BorderLayout.CENTER);
        add(ok, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    private static boolean isPasswordCorrect(char[] input, char[] correctPassword) {
        boolean isCorrect = true;
//        char[] correctPassword =

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }

        //Zero out the password.
        Arrays.fill(correctPassword, '0');

        return isCorrect;
    }


    public Vector<String> getColumn() throws SQLException {
        Connection connection = DbConnection.createConnection();
        String sql = "SELECT name FROM users;";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        Vector<String> users = new Vector<>();
        while (resultSet.next()) {
            users.add(resultSet.getString("name"));
        }

        return users;
    }

    public Vector<Object> get(int id) {
        Connection connection = null;
        Statement st = null;
        ResultSet resultSet = null;

        Vector<Object> users = null;

        try {
            connection = DbConnection.createConnection();
            String sql = "SELECT * FROM users WHERE id=" + id + ";";
            st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = st.executeQuery(sql);

            while (resultSet.next()) {
                users = getUsersFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            System.out.println("get users exception");
            return users;
        } finally {
            DbConnection.close(resultSet, st, connection);
        }
        return users;
    }

    private Vector<Object> getUsersFromResultSet(ResultSet rset) throws SQLException {

        int id = rset.getInt("id");
        String name = rset.getString("name");
        String password = rset.getString("password");
        Vector<Object> users = new Vector<>();

        users.add(id);
        users.add(name);
        users.add(password);

        return users;
    }


}
