package com.itechart.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

/**
 * In memory db tests with Liquibase.
 *
 * @author anton.nekrasov@itechart-group.com (Anton Nekrasov)
 */
public class LiquibaseTest {

    private static Connection conn;

    private static Liquibase liquibase;

    private static Contexts ctx = new Contexts("test");

    /**
     * Prepares in memory database and populates with test data
     */
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

    /**
     * Closes connection & rolls all the changes back, when the test is completed
     */
    @AfterClass
    public static void removeTestData() throws LiquibaseException, SQLException {
        liquibase.rollback(1000, ctx);
        conn.close();
    }

    /**
     * Checks the number of students in db.
     * There are 3 Students added in src/main/liquibase/test/db.testdata.xml, so it is expected 3 records to be returned
     */
    @Test
    public void testStudents() throws SQLException {

        final int EXPECTED_NUMBER = 3;
        final String QUERY = "select count(*) as ST_NUM from STUDENT";

        try(PreparedStatement stmt = conn.prepareStatement(QUERY);
            ResultSet rs = stmt.executeQuery()) {

            rs.next();
            int numberOfUsers = rs.getInt("ST_NUM");
            Assert.assertEquals(EXPECTED_NUMBER, numberOfUsers);

        }
    }
}