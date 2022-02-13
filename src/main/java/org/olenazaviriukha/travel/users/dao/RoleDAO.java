package org.olenazaviriukha.travel.users.dao;

import org.olenazaviriukha.travel.common.db.DataSource;
import org.olenazaviriukha.travel.users.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private static final String SQL_GET_ROLES = "SELECT role.id, role.name FROM role";
    private static final String SQL_GET_ROLE_BY_ID = SQL_GET_ROLES + " WHERE role.id=?";

    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";

    public static List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ROLES)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) roles.add(getRoleFromResultSet(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return roles;
    }

    public Role getRoleById(Integer roleId) {
        Role role = null;
        try (Connection con = DataSource.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_ROLE_BY_ID)){
            pst.setInt(1, roleId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) role = getRoleFromResultSet(rs);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return role;
    }

    private static Role getRoleFromResultSet(ResultSet rs) {
        Role role = null;
        try {
            role = new Role();
            role.setId(rs.getInt(FIELD_ID));
            role.setName(rs.getString(FIELD_NAME));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return role;
    }
}
