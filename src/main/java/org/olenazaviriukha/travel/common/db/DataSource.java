package org.olenazaviriukha.travel.common.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private volatile static HikariDataSource ds;

    static {
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/travelerdb");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // ds = new HikariDataSource( config );
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            synchronized (DataSource.class) {
                if (ds == null) {
                    ds = new HikariDataSource(config);
                }
            }
        }
        return ds.getConnection();
    }

}
