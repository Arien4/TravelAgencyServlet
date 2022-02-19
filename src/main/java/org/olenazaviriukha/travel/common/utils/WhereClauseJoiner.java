package org.olenazaviriukha.travel.common.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class WhereClauseJoiner {
    private static final String IS_NULL = "IS NULL";

    private List<Object> values;
    private StringJoiner whereCause;

    public WhereClauseJoiner() {
        values = new ArrayList<>();
        whereCause = new StringJoiner(" AND ", " WHERE ", " ");
        whereCause.setEmptyValue("");
    }

    public void addCondition(String fieldName, String operation, Object value) {
        whereCause.add(fieldName + " " + operation + " ?");
        values.add(value);
    }

    public String getWhereClause() {
        return whereCause.toString();
    }

    public void fillPreparedStatement(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < values.size(); i++) ps.setObject(i + 1, values.get(i));
    }
}

