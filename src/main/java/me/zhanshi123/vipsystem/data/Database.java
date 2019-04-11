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
                        "\"player\"  TEXT NOT NULL,\n" +
                        "\"vip\"  TEXT NOT NULL,\n" +
                        "\"previous\"  TEXT NOT NULL,\n" +
                        "\"start\"  TEXT NOT NULL,\n" +
                        "\"duration\"  TEXT NOT NULL,\n" +
                        "PRIMARY KEY (\"player\")\n" +
                        ")\n" +
                        ";\n");
                statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS \"main\".\"player\"\n" +
                        "ON \"" + table + "players\" (\"player\" ASC);\n" +
                        "\n");
            }
            statement.close();
            getPlayer = connection.prepareStatement("SELECT `vip`,`previous`,`start`,`duration` FROM `" + table + "players` WHERE `player` = ?;");
            insertPlayer = connection.prepareStatement("INSERT INTO `" + table + "players` (player,vip,previous,start,duration) VALUES(?,?,?,?,?);");
            updatePlayer = connection.prepareStatement("UPDATE `" + table + "vip` SET `vip` = ?, `previous` = ?, `start` = ?, `duration` = ? WHERE `player` = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement getPlayer, insertPlayer, updatePlayer;

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

    public void updateVipData(VipData data) {
        checkConnection();
        if (getVipData(data.getPlayer()) == null) {
            insertVipData(data);
            return;
        }
        try {
            updatePlayer.setString(1, data.getVip());
            updatePlayer.setString(2, data.getPrevious());
            updatePlayer.setLong(3, data.getStart());
            updatePlayer.setLong(4, data.getDuration());
            updatePlayer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVipData(VipData data) {
        checkConnection();
        try {
            insertPlayer.setString(1, data.getPlayer());
            insertPlayer.setString(2, data.getVip());
            insertPlayer.setString(3, data.getPrevious());
            insertPlayer.setLong(4, data.getStart());
            insertPlayer.setLong(5, data.getDuration());
            insertPlayer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
