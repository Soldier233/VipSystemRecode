package me.zhanshi123.vipsystem.data;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.data.connector.ConnectionData;
import me.zhanshi123.vipsystem.data.connector.DatabaseHandler;
import me.zhanshi123.vipsystem.data.connector.PoolHandler;
import me.zhanshi123.vipsystem.data.connector.SQLHandler;

import java.sql.*;
import java.util.List;

public class Database {
    private DatabaseHandler handler;
    private ConnectionData connectionData;
    private Connection connection;
    private String table;

    public void init() {
        boolean useMySQL = Main.getConfigManager().isUseMySQL();
        List<String> mysql = Main.getConfigManager().getMySQL();
        table = mysql.get(3);
        connectionData = new ConnectionData(useMySQL, mysql);
        if (Main.getConfigManager().isUsePool()) {
            handler = new PoolHandler();
        } else {
            handler = new SQLHandler();
        }
        handler.init(connectionData);
        connection = handler.getConnection();
    }

    public boolean isAvailable() {
        return connection != null;
    }

    public void prepare() {
        try {
            Statement statement = connection.createStatement();
            if (connectionData.isUseMySQL()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "players` (\n" +
                        "`id`  int UNSIGNED NOT NULL AUTO_INCREMENT ,\n" +
                        "`player`  varchar(40) NOT NULL ,\n" +
                        "`vip`  varchar(20) NOT NULL ,\n" +
                        "`previous`  varchar(20) NOT NULL ,\n" +
                        "`start`  bigint UNSIGNED NOT NULL ,\n" +
                        "`duration`  bigint NOT NULL ,\n" +
                        "PRIMARY KEY (`id`, `player`),\n" +
                        "UNIQUE INDEX `player` (`player`) USING BTREE \n" +
                        ")\n" +
                        ";\n" +
                        "\n");
            } else {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS \"main\".\"" + table + "players\" (\n" +
                        "\"id\"  int NOT NULL,\n" +
                        "\"player\"  varchar(40) NOT NULL,\n" +
                        "\"vip\"  varchar(20) NOT NULL,\n" +
                        "\"previous\"  varchar(20) NOT NULL,\n" +
                        "\"start\"  bigint NOT NULL,\n" +
                        "\"duration\"  bigint NOT NULL,\n" +
                        "PRIMARY KEY (\"id\", \"player\")\n" +
                        ")\n" +
                        ";\n");
                statement.executeUpdate("CREATE UNIQUE INDEX \"main\".\"player\"\n" +
                        "ON \"" + table + "players\" (\"player\" ASC);\n" +
                        "\n");
            }
            statement.close();
            getPlayer = connection.prepareStatement("SELECT `vip`,`previous`,`start`,`duration` FROM `" + table + "players` WHERE `player` = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement getPlayer;

    public Database() {
        init();
        prepare();
    }

    public void checkConnection() {
        try {
            if (connection.isClosed()) {
                connection = handler.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        handler.release();
    }

    public VipData getVipData(String player) {
        checkConnection();
        VipData data = null;
        try {
            getPlayer.setString(1, player);
            ResultSet resultSet = getPlayer.executeQuery();
            if (resultSet.next()) {
                data = new VipData(player, resultSet.getString("vip"), resultSet.getString("previous"), resultSet.getLong("start"), resultSet.getLong("duration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
