package com.vlkan.demo;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class SampleApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplicationTest.class);

    private static Connection connection = null;

    @BeforeClass
    public static void initConnection() throws SQLException {
        String jdbcUrl = getProperty("jdbc.url");
        String username = getProperty("jdbc.username");
        connection = DriverManager.getConnection(jdbcUrl, username, null);
        connection.setAutoCommit(false);
    }

    private static String getProperty(String key) {
        String value = System.getProperty(key);
        return Objects.requireNonNull(value, key);
    }

    @AfterClass
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException error) {
                LOGGER.error("failed closing connection", error);
            }
        }
    }

    @Test
    public void test_useConnection() throws SQLException {
        DSLContext dslContext = DSL.using(connection, SQLDialect.HSQLDB);
        SampleApplication.useConnection(connection, dslContext);
    }

}
