package me.zhanshi123.vipsystem.data.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class SQLHandler implements DatabaseHandler {
    private Connection connection = null;
    private ConnectionData connectionData;

    private void reconnect() {
        try {
            if (connectionData.isUseMySQL()) {
                List<String> mysql = connectionData.getMysql();
                connection = DriverManager.getConnection(mysql.get(0), mysql.get(1), mysql.get(2));
            } else {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(connectionData.getSqlite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ConnectionData connectionData) {
        this.connectionData = connectionData;
        reconnect();
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                reconnect();
                return connection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
