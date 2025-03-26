package com.marcuslull.aigm.data.ledger.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class LedgerConnectionFactory {

    private static final Properties properties = new Properties();

    static {
        loadConfig();
    }

    private LedgerConnectionFactory(){}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("ledger.url"),
                properties.getProperty("ledger.user"),
                properties.getProperty("ledger.password")
        );
    }

    private static void loadConfig() {
        try (FileInputStream fileInputStream = new FileInputStream(
                "classpath:config.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
