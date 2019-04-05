package me.zhanshi123.vipsystem.data.connector;

import java.util.List;

public class ConnectionData {
    private boolean useMySQL;
    private List<String> mysql;
    private String sqlite = "jdbc:sqlite:plugins/VipSystem/database.db";

    public ConnectionData(boolean useMySQL, List<String> mysql) {
        this.useMySQL = useMySQL;
        this.mysql = mysql;
    }

    public boolean isUseMySQL() {
        return useMySQL;
    }

    public void setUseMySQL(boolean useMySQL) {
        this.useMySQL = useMySQL;
    }

    public List<String> getMysql() {
        return mysql;
    }

    public void setMysql(List<String> mysql) {
        this.mysql = mysql;
    }

    public String getSqlite() {
        return sqlite;
    }

    public void setSqlite(String sqlite) {
        this.sqlite = sqlite;
    }
}
