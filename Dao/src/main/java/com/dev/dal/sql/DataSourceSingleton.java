package com.dev.dal.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public final class DataSourceSingleton {

    private static final String CONFIG_PATH = "/config/db.properties";

    private static final String SERVER_NAME = "SERVER_NAME";
    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";
    private static final String ENCRYPT = "ENCRYPT";
    private static final String TRUST_SERVER_CERTIFICATE = "TRUST_SERVER_CERTIFICATE";
    private static final String INTEGRATED_SECURITY = "INTEGRATED_SECURITY";

    private static final Properties PROPERTIES = new Properties();

    private static DataSource instance;

    static {
        try (InputStream is = DataSourceSingleton.class.getResourceAsStream(CONFIG_PATH)) {
            if (is == null) {
                throw new Exception("Configuration file not found: " + CONFIG_PATH);
            }
            PROPERTIES.load(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration.", e);
        }
    }

    private DataSourceSingleton() {}

    public static DataSource getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static DataSource createInstance() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(PROPERTIES.getProperty(SERVER_NAME));
        dataSource.setDatabaseName(PROPERTIES.getProperty(DATABASE_NAME));
        dataSource.setEncrypt(Boolean.parseBoolean(PROPERTIES.getProperty(ENCRYPT, "false")));
        dataSource.setTrustServerCertificate(Boolean.parseBoolean(PROPERTIES.getProperty(TRUST_SERVER_CERTIFICATE, "true")));

        // Handle Windows Authentication
        if (Boolean.parseBoolean(PROPERTIES.getProperty(INTEGRATED_SECURITY, "false"))) {
            dataSource.setIntegratedSecurity(true);
        } else {
            String user = PROPERTIES.getProperty(USER);
            String password = PROPERTIES.getProperty(PASSWORD);
            if (user != null && !user.isEmpty() && password != null) {
                dataSource.setUser(user);
                dataSource.setPassword(password);
            }
        }

        return dataSource;
    }

    public static void main(String[] args) {
        System.out.println(PROPERTIES);
    }
}
