package com.itechart.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Liquibase schema creation.
 *
 * @author user@example.com (John Doe)
 */
public class LiquibaseTest {

    private static Connection conn;

    private static Liquibase liquibase;

    private static Contexts ctx = new Contexts("test");

    @BeforeClass
    public static void createTestData() throws SQLException,
            ClassNotFoundException, LiquibaseException {

        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:~/liquitest;MODE=MySQL;IGNORECASE=TRUE;", "sa", "sa");

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(conn));

        liquibase = new Liquibase("src/main/liquibase/test.changelog.xml",
                new FileSystemResourceAccessor(), database);

        liquibase.update(ctx);

    }

    @AfterClass
    public static void removeTestData() throws LiquibaseException, SQLException {
        liquibase.rollback(1000, ctx);
        conn.close();
    }

    @Test
    public void testStudents() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int EXPECTED_NUMBER = 3;// is the number of records added in db.testdata.xml
        try {

            stmt = conn.prepareStatement("select count(*) as ST_NUM from STUDENT");
            rs = stmt.executeQuery();
            rs.next();
            int numberOfUsers = rs.getInt("ST_NUM");
            Assert.assertEquals(EXPECTED_NUMBER, numberOfUsers);

        } finally {
            rs.close();
            stmt.close();
        }
    }
}