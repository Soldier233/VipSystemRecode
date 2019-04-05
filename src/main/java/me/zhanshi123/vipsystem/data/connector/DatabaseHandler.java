package me.zhanshi123.vipsystem.data.connector;

import java.sql.Connection;
import java.util.List;

public interface DatabaseHandler {
    void init(ConnectionData connectionData);

    Connection getConnection();
}
