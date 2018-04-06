package com.vlkan.demo;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.vlkan.demo.dao.Tables.USER;

public enum SampleApplication {;

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

    public static void main(String[] args) throws Exception {

        LOGGER.info("connecting to database");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/optimus", "pgdeploy", null);
        connection.setAutoCommit(false);

        LOGGER.info("migrating database");
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("liquibase/postgresql.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());

        LOGGER.info("using connection");
        Settings dslContextSettings = new Settings().withRenderNameStyle(RenderNameStyle.AS_IS);
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES, dslContextSettings);
        useConnection(connection, dslContext);

    }

    public static void useConnection(Connection connection, DSLContext dslContext) throws SQLException {
        try {
            int insertedRowCount = dslContext
                    .insertInto(USER)
                    .columns(USER.NAME, USER.ACTIVE)
                    .values("volkan", false)
                    .values("uzay", true)
                    .execute();
            LOGGER.info("insertedRowCount: {}", insertedRowCount);
            dslContext
                    .select(USER.ID, USER.NAME, USER.ACTIVE)
                    .from(USER)
                    .fetchSize(1000)
                    .stream()
                    .forEach(record -> {
                        Integer id = record.getValue(USER.ID);
                        String name = record.getValue(USER.NAME);
                        Boolean active = record.getValue(USER.ACTIVE);
                        LOGGER.info("user: {}\t{}\t{}", id, active, name);
                    });
        } finally {
            connection.rollback();
        }
    }

}
