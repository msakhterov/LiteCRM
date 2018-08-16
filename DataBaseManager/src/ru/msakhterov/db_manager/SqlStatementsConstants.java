package ru.msakhterov.db_manager;

public class SqlStatementsConstants {
    public static final String FIND_USER_SQL_QUERY = "SELECT * FROM " + DataBaseSchema.UsersTable.NAME + " WHERE " + DataBaseSchema.UsersTable.Colons.LOGIN + " = ?;";
    public static final String INSERT_USER_SQL_QUERY = "INSERT INTO " + DataBaseSchema.UsersTable.NAME + " (" +
            DataBaseSchema.UsersTable.Colons.FIRST_NAME + ", " +
            DataBaseSchema.UsersTable.Colons.LAST_NAME + ", " +
            DataBaseSchema.UsersTable.Colons.BIRTHDAY + ", " +
            DataBaseSchema.UsersTable.Colons.PHONE_NUMBER + ", " +
            DataBaseSchema.UsersTable.Colons.EMAIL + ", " +
            DataBaseSchema.UsersTable.Colons.ORGANIZATION_ID + ", " +
            DataBaseSchema.UsersTable.Colons.ACCESS_LEVEL + ", " +
            DataBaseSchema.UsersTable.Colons.LOGIN + ", " +
            DataBaseSchema.UsersTable.Colons.PASSWORD + ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_USER_SQL_QUERY = "UPDATE " + DataBaseSchema.UsersTable.NAME + " SET " +
            DataBaseSchema.UsersTable.Colons.FIRST_NAME + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.LAST_NAME + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.BIRTHDAY + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.PHONE_NUMBER + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.EMAIL + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.ORGANIZATION_ID + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.ACCESS_LEVEL + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.LOGIN + " = ?" + ", " +
            DataBaseSchema.UsersTable.Colons.PASSWORD + " = ? " +
            "WHERE " +  DataBaseSchema.UsersTable.Colons.ID + " = ?;";
    public static final String DELETE_USER_SQL_QUERY = "DELETE * FROM " + DataBaseSchema.UsersTable.NAME + " WHERE " + DataBaseSchema.UsersTable.Colons.ID + " = ?;";

}
