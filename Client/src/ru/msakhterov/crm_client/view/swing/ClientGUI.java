package ru.msakhterov.crm_client.view.swing;

import ru.msakhterov.crm_client.controller.ClientController;
import ru.msakhterov.crm_client.events.EventManager;
import ru.msakhterov.crm_client.events.EventType;
import ru.msakhterov.crm_client.events.event_logger.FileLogger;
import ru.msakhterov.crm_client.view.ClientView;
import ru.msakhterov.crm_client.view.ViewStatement;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ClientGUI extends JFrame implements Thread.UncaughtExceptionHandler,
        ActionListener, ListSelectionListener, ClientView {

    private static final String WINDOW_TITLE = "LiteCRM";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private static final int START_WIDTH = 200;
    private static final int START_HEIGHT = 200;

    private final JTextField tfSignInLogin = new JTextField("test");
    private final JPasswordField tfSignInPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Войти");
    private final JButton btnSignUp = new JButton("Панель регистрации");
    private final JPanel loginPanel = new JPanel(new GridLayout(2, 2));
    private final JPanel loginButtonPanel = new JPanel(new GridLayout(2, 1));
    private final Box loginBox = new Box(BoxLayout.Y_AXIS);

    private final JTextField tfSignUpLogin = new JTextField("test");
    private final JPasswordField tfSignUpPassword = new JPasswordField("123");
    private final JTextField tfSignUpEmail = new JTextField("test1@test.ru");
    private final JButton btnReg = new JButton("Зарегистрироваться");
    private final JButton btnSignIn = new JButton("Панель входа");
    private final JPanel registrationPanel = new JPanel(new GridLayout(3, 2));
    private final JPanel registrationButtonPanel = new JPanel(new GridLayout(2, 1));
    private final Box registrationBox = new Box(BoxLayout.Y_AXIS);

    private final JTable table = new JTable();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private Object[] columnsHeader = new String[]{"Наименование", "Размер", "Дата изменения"};
    private JScrollPane tableScrollPane;
    private final JTextArea log = new JTextArea();
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final Box leftPanel = new Box(BoxLayout.Y_AXIS);

    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JPanel topPanel = new JPanel(new GridLayout(1, 2));

    private boolean isSelected = false;
    private int selectedRow;
    private ViewStatement currentStatement;
    private String defaultPath;

    private EventManager eventManager;

    public ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(START_WIDTH, START_HEIGHT);
        setLocationRelativeTo(null);
        setTitle(WINDOW_TITLE);

        loginPanel.add(new Label("Login:"));
        loginPanel.add(tfSignInLogin);
        loginPanel.add(new Label("Password:"));
        loginPanel.add(tfSignInPassword);
        loginButtonPanel.add(btnLogin);
        loginButtonPanel.add(btnSignUp);
        loginBox.add(loginPanel);
        loginBox.add(loginButtonPanel);

        registrationPanel.add(new Label("Login:"));
        registrationPanel.add(tfSignUpLogin);
        registrationPanel.add(new Label("Password:"));
        registrationPanel.add(tfSignUpPassword);
        registrationPanel.add(new Label("Email:"));
        registrationPanel.add(tfSignUpEmail);
        registrationButtonPanel.add(btnReg);
        registrationButtonPanel.add(btnSignIn);
        registrationBox.add(registrationPanel);
        registrationBox.add(registrationButtonPanel);

        topPanel.add(cbAlwaysOnTop);
        topPanel.add(btnDisconnect);

        tableModel.setColumnIdentifiers(columnsHeader);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnsWidth();
        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        ListSelectionModel selModel = table.getSelectionModel();
        selModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        log.setRows(5);
        log.setEditable(false);
        log.setLineWrap(true);

        tableScrollPane = new JScrollPane(table);

        leftPanel.add(tableScrollPane);
        leftPanel.add(new JScrollPane(log));

        //Подключение слушателей кнопок

        cbAlwaysOnTop.addActionListener(this);
        btnLogin.addActionListener(this);
        btnReg.addActionListener(this);
        btnSignUp.addActionListener(this);
        btnSignIn.addActionListener(this);
        btnDisconnect.addActionListener(this);
        selModel.addListSelectionListener(this);

        setView(ViewStatement.SIGN_IN);
        setResizable(false);
        setVisible(true);

        // Подключение наблюдателе
        eventManager = new EventManager();
        eventManager.addListener(new ClientController(this));
        eventManager.addListener(new FileLogger());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSignIn) {
            setView(ViewStatement.SIGN_IN);
        } else if (src == btnSignUp) {
            setView(ViewStatement.SIGN_UP);
        } else if (src == btnLogin) {
            eventManager.notifyListeners(EventType.LOGIN);
        } else if (src == btnReg) {
            eventManager.notifyListeners(EventType.REGISTRATION);
        } else if (src == btnDisconnect) {
            eventManager.notifyListeners(EventType.DISCONNECT);
            clearTable();
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String message;
        if (stackTraceElements.length == 0) {
            message = "Empty Stacktrace";
        } else {
            message = e.getClass().getCanonicalName() +
                    ": " + e.getMessage() + "\n" +
                    "\t at " + stackTraceElements[0];
        }

        JOptionPane.showMessageDialog(this, message, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    @Override
    public String getLogin() {
        if(currentStatement == ViewStatement.SIGN_IN)
        return tfSignInLogin.getText();
        else return tfSignUpLogin.getText();
    }

    @Override
    public String getPassword() {
        if(currentStatement == ViewStatement.SIGN_IN)
        return new String(tfSignInPassword.getPassword());
        else return new String(tfSignUpPassword.getPassword());
    }

    @Override
    public String getEmail() {
        return tfSignUpEmail.getText();
    }

    @Override
    public void logAppend(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) + ": " + msg;
        log.append(msg + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void setView(ViewStatement statement) {
        switch (statement) {
            case SIGN_IN:
                currentStatement = ViewStatement.SIGN_IN;
                remove(registrationBox);
                add(loginBox, BorderLayout.CENTER);
                revalidate();
                repaint();
                break;
            case SIGN_UP:
                currentStatement = ViewStatement.SIGN_UP;
                remove(loginBox);
                add(registrationBox, BorderLayout.CENTER);
                revalidate();
                repaint();
                break;
            case CONNECTED:
                setSize(WIDTH, HEIGHT);
                remove(loginBox);
                remove(registrationBox);
                add(topPanel, BorderLayout.NORTH);
                add(leftPanel, BorderLayout.SOUTH);
                revalidate();
                repaint();
                break;
            case DISCONNECTED:
                remove(leftPanel);
                remove(topPanel);
                setSize(START_WIDTH, START_HEIGHT);
                add(loginBox, BorderLayout.CENTER);
                revalidate();
                repaint();
            default:
                break;
        }
    }

    @Override
    public void setViewTitle(String title) {
        if (title != null) setTitle(WINDOW_TITLE + ": " + title);
        else setTitle(WINDOW_TITLE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if ((selectedRow = table.getSelectedRow()) > -1) {
            isSelected = true;
        }
    }

    private void setColumnsWidth() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JTableHeader tableHeader = table.getTableHeader();
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            String columnName = tableHeader.getTable().getColumnName(i);
            switch (columnName) {
                case "Наименование":
                    column.setPreferredWidth(211);
                    break;
                case "Размер":
                    column.setPreferredWidth(55);
                    break;
                case "Дата изменения":
                    column.setPreferredWidth(120);
                    break;
            }
        }
    }

    private void clearTable() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }
}
