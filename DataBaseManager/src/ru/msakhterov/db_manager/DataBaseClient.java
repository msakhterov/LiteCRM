package ru.msakhterov.db_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseClient {
    private static final String DB_NAME = "crm_db.db";

    public static Connection getDataBaseConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
            Statement statement = connection.createStatement();
            boolean res1 = statement.execute("CREATE TABLE IF NOT EXISTS " + DataBaseSchema.UsersTable.NAME + "  ( " +
                    DataBaseSchema.UsersTable.Colons.ID + DataBaseSchema.DataType.PK_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.FIRST_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.LAST_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.BIRTHDAY + DataBaseSchema.DataType.DATE_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.PHONE_NUMBER + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.EMAIL + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.ORGANIZATION_ID + DataBaseSchema.DataType.INT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.ACCESS_LEVEL + DataBaseSchema.DataType.INT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.LOGIN + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.UsersTable.Colons.PASSWORD + DataBaseSchema.DataType.TEXT_TYPE + ");");

            statement.execute("CREATE TABLE IF NOT EXISTS " + DataBaseSchema.RepresentativesTable.NAME + "(" +
                    " " + DataBaseSchema.RepresentativesTable.Colons.ID + DataBaseSchema.DataType.PK_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.FIRST_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.LAST_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.BIRTHDAY + DataBaseSchema.DataType.DATE_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.PHONE_NUMBER + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.EMAIL + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.RepresentativesTable.Colons.ORGANIZATION_ID + DataBaseSchema.DataType.INT_TYPE + ");");

            statement.execute("CREATE TABLE IF NOT EXISTS " + DataBaseSchema.OrganizationsTable.NAME + "(" +
                    " " + DataBaseSchema.OrganizationsTable.Colons.ID + DataBaseSchema.DataType.PK_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.LEGAL_FORM + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.FULL_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.SHORT_NAME + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.TIN + DataBaseSchema.DataType.INT_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.ADDRESS + DataBaseSchema.DataType.TEXT_TYPE + ", " +
                    DataBaseSchema.OrganizationsTable.Colons.TYPE + DataBaseSchema.DataType.INT_TYPE + ");");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}
