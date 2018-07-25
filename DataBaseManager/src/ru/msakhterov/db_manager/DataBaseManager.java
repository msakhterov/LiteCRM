package ru.msakhterov.db_manager;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.db_manager.DataBaseSchema.UsersTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static DataBaseManager dataBaseManager;
    private static Connection connection;

    private DataBaseManager() {
        connection = DataBaseClient.getDataBaseConnection();
    }

    public synchronized static DataBaseManager getDataBaseManager() {
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager();
        }
        return dataBaseManager;
    }

    public synchronized void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int makeReg(String login, String password, String email) {

        System.out.println("Регистрация пользователя: " + login);
        int result = 0;
        String request = "INSERT INTO " + UsersTable.NAME +
                " (" + UsersTable.Colons.LOGIN + ", " + UsersTable.Colons.PASSWORD + ", " + UsersTable.Colons.EMAIL + ") " +
                " VALUES ('" + login + "', " + password + ", '" + email + "');";
        System.out.println(request);
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(request);
            System.out.println("Результат внесения данных " + result);
        } catch (SQLException e) {
            System.out.println("Исключение при регистрации" + e);
        }
        return result;
    }


    public synchronized String checkAuth(String login) {
        System.out.println("Авторизация пользователя: " + login);
        String request = "SELECT " + UsersTable.Colons.PASSWORD +
                " FROM " + UsersTable.NAME +
                " WHERE " + UsersTable.Colons.LOGIN +
                " = " + "'" + login + "';";
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(request);
            if (set.next()) {
                return set.getString(UsersTable.Colons.PASSWORD);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized User getUser(String login) {
        User user = null;
        System.out.println("Получение данных пользователя: " + login);
        String request = "SELECT * " +
                " FROM " + UsersTable.NAME +
                " WHERE " + UsersTable.Colons.LOGIN +
                " = " + "'" + login + "';";
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(request);
            ResultSetWrapper wrapper = new ResultSetWrapper(set);
            user = wrapper.getUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
