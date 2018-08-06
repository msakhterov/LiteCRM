package ru.msakhterov.db_manager;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.db_manager.DataBaseSchema.UsersTable;
import ru.msakhterov.db_manager.mappers.UserMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static DataBaseManager dataBaseManager;
    private static Connection connection;
    private static UserMapper userMapper;


    private DataBaseManager() {
        connection = DataBaseClient.getDataBaseConnection();
        userMapper = new UserMapper(connection);
    }

    public synchronized static DataBaseManager getDataBaseManager() {
        if (dataBaseManager == null){
            dataBaseManager = new DataBaseManager();
        }
        return dataBaseManager;
    }

    public synchronized void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int makeReg(User user){
        System.out.println("Регистрация пользователя: " + user.getLogin());
        int result = 0;
        User checkUser = userMapper.find(user.getLogin());
        if (checkUser.getId() != null){
            result = userMapper.insert(user);
        }
        return result;
    }


    public synchronized String checkAuth(String login){
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

    public synchronized User getUser(String login){
        System.out.println("Получение данных пользователя: " + login);
        return userMapper.find(login);
    }
}
