package me.zhanshi123.vipsystem.data;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.storage.VipStorage;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.convert.Info;
import me.zhanshi123.vipsystem.data.connector.ConnectionData;
import me.zhanshi123.vipsystem.data.connector.DatabaseHandler;
import me.zhanshi123.vipsystem.data.connector.PoolHandler;
import me.zhanshi123.vipsystem.data.connector.SQLHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                        "PRIMARY KEY (`player`),\n" +
                        "UNIQUE INDEX `player` (`player`) USING BTREE \n" +
                        ")\n" +
                        ";\n" +
                        "\n");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "storage` (" +
                        "`id`  int UNSIGNED NOT NULL AUTO_INCREMENT ," +
                        "`player`  varchar(40) NOT NULL ," +
                        "`vip`  varchar(20) NOT NULL ," +
                        "`previous`  varchar(20) NOT NULL ," +
                        "`activate`  bigint UNSIGNED NOT NULL ," +
                        "`left`  bigint UNSIGNED NOT NULL ," +
                        "PRIMARY KEY (`id`),\n" +
                        "INDEX `player` (`player`) USING BTREE ," +
                        "UNIQUE INDEX `id` (`id`) USING BTREE " +
                        ");");
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
                statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS \"main\".\"" + table + "player\"\n" +
                        "ON \"" + table + "players\" (\"player\" ASC);\n" +
                        "\n");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS \"main\".\"" + table + "storage\" (\n" +
                        "\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\"player\"  TEXT NOT NULL,\n" +
                        "\"vip\"  TEXT NOT NULL,\n" +
                        "\"previous\"  TEXT NOT NULL,\n" +
                        "\"activate\"  TEXT NOT NULL,\n" +
                        "\"left\"  TEXT NOT NULL\n" +
                        ")\n" +
                        ";");
                statement.executeUpdate("CREATE INDEX IF NOT EXISTS \"main\".\"" + table + "storage_player\"\n" +
                        "ON \"" + table + "storage\" (\"player\" ASC);\n" +
                        "\n");
                statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS \"main\".\"" + table + "storage_id\"\n" +
                        "ON \"" + table + "storage\" (\"id\" ASC);\n" +
                        "\n");
            }
            statement.close();
            if (newData != null) {
                connection.setAutoCommit(false);
                Statement convertStatement = connection.createStatement();
                newData.forEach(data -> {
                    try {
                        String sql = "INSERT INTO `" + table + "players` (`player`,`vip`,`previous`,`start`,`duration`) VALUES('" + data.getPlayer() + "','" + data.getVip() + "','" + data.getPrevious() + "','" + data.getStart() + "','" + data.getDuration() + "');";
                        convertStatement.addBatch(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                convertStatement.executeBatch();
                connection.setAutoCommit(true);
                convertStatement.close();
                Main.getInstance().getLogger().info("Database from previous version has been converted.");
            }
            getAllPlayer = connection.prepareStatement("SELECT `player`,`vip`,`previous`,`start`,`duration` FROM `" + table + "players`;");
            getPlayer = connection.prepareStatement("SELECT `vip`,`previous`,`start`,`duration` FROM `" + table + "players` WHERE `player` = ? LIMIT 1;");
            insertPlayer = connection.prepareStatement("INSERT INTO `" + table + "players` (`player`,`vip`,`previous`,`start`,`duration`) VALUES(?,?,?,?,?);");
            updatePlayer = connection.prepareStatement("UPDATE `" + table + "players` SET `vip` = ?, `previous` = ?, `start` = ?, `duration` = ? WHERE `player` = ?;");
            deletePlayer = connection.prepareStatement("DELETE FROM `" + table + "players` WHERE `player` = ?;");
            insertStorage = connection.prepareStatement("INSERT INTO `" + table + "storage` (`player`,`vip`,`previous`,`activate`,`left`) VALUES(?,?,?,?,?);");
            removeStorage = connection.prepareStatement("DELETE FROM `" + table + "storage` WHERE `id`= ?;");
            getStorageByPlayer = connection.prepareStatement("SELECT `id`,`vip`,`previous`,`activate`,`left` FROM `" + table + "storage` WHERE `player` = ?;");
            getStorageByID = connection.prepareStatement("SELECT `player`,`vip`,`previous`,`activate`,`left` FROM `" + table + "storage` WHERE `id` = ? LIMIT 1;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<VipData> newData = null;

    public void checkOldVersion() {
        if (!Main.getConvertManager().isOldVersion()) {
            return;
        }
        List<Info> oldData = new ArrayList<>();
        try {
            Statement queryStatement = connection.createStatement();
            ResultSet resultSet = queryStatement.executeQuery("SELECT * FROM `" + table + "players`;");
            while (resultSet.next()) {
                Info data = new Info(resultSet.getString("player"), resultSet.getLong("time"), resultSet.getString("vipg"), resultSet.getInt("left"), resultSet.getInt("expired"));
                oldData.add(data);
            }
            resultSet.close();
            queryStatement.close();
            newData = new ArrayList<>();
            oldData.forEach(old -> {
                String[] args = old.getGroup().split("#");
                String previous = args[1];
                String vip = args[0];
                VipData vipData = new VipData(old.getPlayer(), vip, previous, old.getTime(), old.getLeft() * 1000);
                newData.add(vipData);
            });
            Statement dropStatement = connection.createStatement();
            dropStatement.executeUpdate("DROP TABLE `" + table + "players`;");
            dropStatement.executeUpdate("DROP TABLE `" + table + "vipkeys`;");
            dropStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private PreparedStatement getAllPlayer, getPlayer, insertPlayer, updatePlayer, deletePlayer, insertStorage, removeStorage, getStorageByPlayer, getStorageByID;

    public Database() {
        init();
        checkOldVersion();
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
            updatePlayer.setString(5, data.getPlayer());
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

    public void deletePlayer(String player) {
        checkConnection();
        try {
            deletePlayer.setString(1, player);
            deletePlayer.executeUpdate();
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

    public Set<VipData> getVipDatum() {
        checkConnection();
        Set<VipData> dataSet = new HashSet<>();
        try {
            ResultSet resultSet = getAllPlayer.executeQuery();
            while (resultSet.next()) {
                dataSet.add(new VipData(resultSet.getString("player"), resultSet.getString("vip"), resultSet.getString("previous"), resultSet.getLong("start"), resultSet.getLong("duration")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public Set<VipStorage> getVipStorage(String name) {
        checkConnection();
        Set<VipStorage> set = new HashSet<>();
        try {
            getStorageByPlayer.setString(1, name);
            ResultSet resultSet = getStorageByPlayer.executeQuery();
            while (resultSet.next()) {
                set.add(new VipStorage(resultSet.getInt("id"), name, resultSet.getString("vip"), resultSet.getString("previous"), resultSet.getLong("activate"), resultSet.getLong("left")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        validVerify(set);
        return set;
    }

    public void addVipStorage(VipStorage vipStorage) {
        checkConnection();
        try {
            insertStorage.setString(1, vipStorage.getPlayer());
            insertStorage.setString(2, vipStorage.getVip());
            insertStorage.setString(3, vipStorage.getPrevious());
            insertStorage.setLong(4, vipStorage.getActivate());
            insertStorage.setLong(5, vipStorage.getLeft());
            insertStorage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public VipStorage getVipStorage(int id) {
        checkConnection();
        VipStorage vipStorage = null;
        try {
            getStorageByID.setInt(1, id);
            ResultSet resultSet = getStorageByID.executeQuery();
            if (resultSet.next()) {
                vipStorage = new VipStorage(id, resultSet.getString("player"), resultSet.getString("vip"), resultSet.getString("previous"), resultSet.getLong("activate"), resultSet.getLong("left"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (vipStorage == null) {
            return null;
        }
        if (vipStorage.isValid()) {
            return vipStorage;
        }
        return null;
    }

    public void removeVipStorage(int id) {
        checkConnection();
        try {
            removeStorage.setInt(1, id);
            removeStorage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validVerify(Set<VipStorage> data) {
        new HashSet<>(data).stream()
                .filter(entry -> !entry.isValid())
                .forEach(entry -> {
                            removeVipStorage(entry.getId());
                            data.remove(entry);
                        }
                );
    }
}
