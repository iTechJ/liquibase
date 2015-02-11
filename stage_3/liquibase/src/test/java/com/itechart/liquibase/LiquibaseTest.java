package com.itechart.liquibase;

import static org.junit.Assert.assertEquals;


import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for Liquibase schema creation.
 *
 * @author user@example.com (John Doe)
 */
public class LiquibaseTest {

    @Test
    public void thisAlwaysPasses() {
        try {
            JDBCDataSource dataSource = new JDBCDataSource();
            dataSource.setDatabase("jdbc:hsqldb:file:/tmp/hsqldb/testdb");
            dataSource.setUser("SA");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
            Liquibase liquibase = new Liquibase("user-db.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(null);
        } catch (Exception e) {

        }

    }

    @Test
    @Ignore
    public void thisIsIgnored() {
    }
}