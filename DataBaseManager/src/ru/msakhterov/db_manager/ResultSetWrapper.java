package ru.msakhterov.db_manager;

import ru.msakhterov.crm_common.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ResultSetWrapper {

    private ResultSet rs;

    public ResultSetWrapper(ResultSet rs) {
        this.rs = rs;
    }

    public User getUser() {
        User.Builder builder = new User.Builder();
        try {
            if (rs.next()) {
                builder.setFirstName(getString(DataBaseSchema.UsersTable.Colons.FIRST_NAME));
                builder.setLastName(getString(DataBaseSchema.UsersTable.Colons.LAST_NAME));
                builder.setBirthday(getDate(DataBaseSchema.UsersTable.Colons.BIRTHDAY));
                builder.setPhoneNumber(getString(DataBaseSchema.UsersTable.Colons.PHONE_NUMBER));
                builder.setEmail(getString(DataBaseSchema.UsersTable.Colons.EMAIL));
                builder.setOrganization(getInt(DataBaseSchema.UsersTable.Colons.ORGANIZATION_ID));
                builder.setAccessLevel(getInt(DataBaseSchema.UsersTable.Colons.ACCESS_LEVEL));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = builder.build();
        return user;
    }


    private String getString(String label) throws SQLException {
        String value = rs.getString(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    private Integer getInt(String label) throws SQLException {
        int value = rs.getInt(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    private Date getDate(String label) throws SQLException {
        Date value = new Date(rs.getLong(label));
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    private Boolean getBoolean(String label) throws SQLException {
        boolean value = rs.getBoolean(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    private Byte getByte(String label) throws SQLException {
        byte value = rs.getByte(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }
}
