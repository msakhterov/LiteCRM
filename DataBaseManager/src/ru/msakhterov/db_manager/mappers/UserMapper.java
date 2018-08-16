package ru.msakhterov.db_manager.mappers;

import ru.msakhterov.crm_common.entity.User;
import ru.msakhterov.db_manager.DataBaseSchema.UsersTable;
import ru.msakhterov.db_manager.SqlStatementsConstants;
import ru.msakhterov.db_manager.maps.UserMap;

import java.sql.*;

public class UserMapper {

    private UserMap userMap;
    private Connection connection;

    public UserMapper(Connection connection) {
        this.userMap = new UserMap();
        this.connection = connection;
    }

    public User find(String login) {
        User user = userMap.getUser(login);
        if (user == null) {

//  Антипаттерн "Слепая вера"
//            String request = "SELECT * " +
//                    " FROM " + UsersTable.NAME +
//                    " WHERE " + UsersTable.Colons.LOGIN +
//                    " = '" + login + "';";
//            try (Statement statement = connection.createStatement()) {
//                ResultSet set = statement.executeQuery(request);
//                user = getUser(set);
            try (PreparedStatement ps = connection.prepareStatement(SqlStatementsConstants.FIND_USER_SQL_QUERY)) {
                ps.setString(1, login);
                ResultSet set = ps.executeQuery();
                user = getUser(set);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public int insert(User user) {
        int result = 0;
//  Антипаттерн "Слепая вера"
//        String request = "INSERT INTO " + UsersTable.NAME + " (" +
//                UsersTable.Colons.FIRST_NAME + ", " +
//                UsersTable.Colons.LAST_NAME + ", " +
//                UsersTable.Colons.BIRTHDAY + ", " +
//                UsersTable.Colons.PHONE_NUMBER + ", " +
//                UsersTable.Colons.EMAIL + ", " +
//                UsersTable.Colons.ORGANIZATION_ID + ", " +
//                UsersTable.Colons.ACCESS_LEVEL + ", " +
//                UsersTable.Colons.LOGIN + ", " +
//                UsersTable.Colons.PASSWORD + ") " +
//                " VALUES ('" +
//                user.getFirstName() + "', '" +
//                user.getLastName() + "', " +
//                user.getBirthday() + ", '" +
//                user.getPhoneNumber() + "', '" +
//                user.getEmail() + "', " +
//                user.getOrganization() + ", " +
//                user.getAccessLevel() + ", '" +
//                user.getLogin() + "', '" +
//                user.getPassword() + "');";
//        try (Statement statement = connection.createStatement()) {
//            result = statement.executeUpdate(request);
//            System.out.println("Результат внесения данных " + user.getLogin() + ": " + result);
        try (PreparedStatement ps = connection.prepareStatement(SqlStatementsConstants.INSERT_USER_SQL_QUERY)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());

//  Антипаттерн "Заглушка". Решение - Скорректировать форму регистрации
            ps.setLong(3, 1);
            ps.setInt(6, 1);
            ps.setInt(7, 1);
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getEmail());
            ps.setString(8, user.getLogin());
            ps.setString(9, user.getLogin());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Исключение при внесении данных " + user.getLogin() + ": " + e);
        }
        System.out.println("Результат внесения данных: " + result);
        return result;
    }

    public int update(User user) {
        int result = 0;
//  Антипаттерн "Слепая вера"
//        String request = "UPDATE " + UsersTable.NAME +
//                " SET " +
//                UsersTable.Colons.FIRST_NAME + " = " +  user.getFirstName() + ", " +
//                UsersTable.Colons.LAST_NAME + " = " + user.getLastName() + ", " +
//                UsersTable.Colons.BIRTHDAY + " = " + user.getBirthday() + ", " +
//                UsersTable.Colons.PHONE_NUMBER + " = " + user.getPhoneNumber() + ", " +
//                UsersTable.Colons.EMAIL + " = " + user.getEmail() + ", " +
//                UsersTable.Colons.ORGANIZATION_ID + " = " + user.getOrganization() + ", " +
//                UsersTable.Colons.ACCESS_LEVEL + " = " + user.getAccessLevel() + ", " +
//                UsersTable.Colons.LOGIN + " = " + user.getLogin() + ", " +
//                UsersTable.Colons.PASSWORD + " = " + user.getPassword() + ") " +
//                " WHERE " +
//                UsersTable.Colons.ID + " = " + "'" +
//                user.getId() + "';";
//        try (Statement statement = connection.createStatement()) {
//            result = statement.executeUpdate(request);
//            System.out.println("Результат изменения данных " + user.getLogin() + ": " + result);
        try (PreparedStatement ps = connection.prepareStatement(SqlStatementsConstants.UPDATE_USER_SQL_QUERY)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setLong(3, user.getBirthday());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getAccessLevel());
            ps.setInt(7, user.getOrganization());
            ps.setString(8, user.getLogin());
            ps.setString(9, user.getLogin());
            ps.setInt(10, user.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Исключение при изменении данных " + user.getLogin() + ": " + e);
        }
        if (result == 1) {
            userMap.addUser(user);
        }
        return result;
    }

    public int delete(User user) {
        int result = 0;
//  Антипаттерн "Слепая вера"
//        String request = "DELETE * FROM " + UsersTable.NAME +
//                " WHERE " +
//                UsersTable.Colons.ID + " = " + "'" +
//                user.getId() + "';";
//        try (Statement statement = connection.createStatement()) {
//            result = statement.executeUpdate(request);
//            System.out.println("Результат удаления данных " + user.getLogin() + ": " + result);
        try (PreparedStatement ps = connection.prepareStatement(SqlStatementsConstants.DELETE_USER_SQL_QUERY)){
            ps.setInt(1, user.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Исключение при удалении данных " + user.getLogin() + ": " + e);
        }
        if (result == 1) {
            userMap.removeUser(user.getLogin());
        }
        return result;
    }

    private User getUser(ResultSet rs) {
        User.Builder builder = new User.Builder();
        try {
            if (rs.next()) {
                builder.setId(rs.getInt(UsersTable.Colons.ID));
                builder.setFirstName(rs.getString(UsersTable.Colons.FIRST_NAME));
                builder.setLastName(rs.getString(UsersTable.Colons.LAST_NAME));
                builder.setBirthday(rs.getLong(UsersTable.Colons.BIRTHDAY));
                builder.setPhoneNumber(rs.getString(UsersTable.Colons.PHONE_NUMBER));
                builder.setEmail(rs.getString(UsersTable.Colons.EMAIL));
                builder.setOrganization(rs.getInt(UsersTable.Colons.ORGANIZATION_ID));
                builder.setAccessLevel(rs.getInt(UsersTable.Colons.ACCESS_LEVEL));
                builder.setLogin(rs.getString(UsersTable.Colons.LOGIN));
                builder.setPassword(rs.getString(UsersTable.Colons.PASSWORD));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = builder.build();
        if (user.getId() != null) {
            userMap.addUser(user);
        }
        return user;
    }
}
