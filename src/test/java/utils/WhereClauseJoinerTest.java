package utils;

import org.junit.jupiter.api.Test;
import org.olenazaviriukha.travel.common.utils.WhereClauseJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhereClauseJoinerTest {

    @Test
    public void ShouldReturnValidStatement() {
        WhereClauseJoiner wcj = new WhereClauseJoiner();
        wcj.addCondition("tour_type", "=", "RELAX");
        wcj.addCondition("max_price", "<=", 10000);
        String expected = "WHERE tour_type = ? AND max_price <= ?";
        String actual = wcj.getWhereClause();
        assertEquals(expected, actual.trim());
    }
}
