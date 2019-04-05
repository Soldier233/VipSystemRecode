package me.zhanshi123.vipsystem.data.connector;

import java.sql.Connection;

public interface DatabaseHandler {
    void init(ConnectionData connectionData);

    Connection getConnection();
}
