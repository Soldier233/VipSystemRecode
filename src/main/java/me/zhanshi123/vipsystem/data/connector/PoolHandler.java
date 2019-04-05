package me.zhanshi123.vipsystem.data.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PoolHandler implements DatabaseHandler {
    private HikariConfig config = new HikariConfig();
    private HikariDataSource dataSource = null;

    @Override
    public void init(ConnectionData connectionData) {
        if (connectionData.isUseMySQL()) {
            List<String> mysql = connectionData.getMysql();
            config.setJdbcUrl(mysql.get(0));
            config.setUsername(mysql.get(1));
            config.setPassword(mysql.get(2));
        } else {
            config.setJdbcUrl(connectionData.getSqlite());
        }
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() {
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
