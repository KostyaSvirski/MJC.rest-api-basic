package com.epam.esm.pool;

import com.epam.esm.exception.DBCPDataSourceException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Repository
public class DBCPDataSource {

    private static DBCPDataSource instance;
    private final BasicDataSource dataSource;

    private DBCPDataSource() throws DBCPDataSourceException {
        dataSource = new BasicDataSource();
        File file = new File("D:\\java\\EPAM\\Lab\\Task3_gift_certificates\\dao\\src\\main\\resources\\database.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new DBCPDataSourceException("property not found");
        }
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(properties.getProperty("maxOpenPreparedStatements")));
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
        dataSource.addConnectionProperty("characterEncoding", properties.getProperty("characterEncoding"));
        dataSource.addConnectionProperty("autoReconnect", properties.getProperty("autoReconnect"));
        dataSource.addConnectionProperty("serverTimezone", properties.getProperty("serverTimezone"));
        dataSource.addConnectionProperty("useUnicode", properties.getProperty("useUnicode"));

    }

    public static synchronized DBCPDataSource getInstance() throws DBCPDataSourceException {
        if (instance == null) {
            instance = new DBCPDataSource();
        }
        return instance;
    }

    public Connection getConnection() throws DBCPDataSourceException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBCPDataSourceException("can't get connection", e.getCause());
        }
    }

    public void closeConnection(Connection connection) throws DBCPDataSourceException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBCPDataSourceException("can't close connection", e.getCause());
        }

    }

}
