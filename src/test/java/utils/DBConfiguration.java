package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfiguration
{
    private static Connection connection = null;
    private static Statement statement = null;
    private static Statement statementToQuery = null;

    private static String DB_SERVER_IP = new ReadProperties().readProperties("DB_SERVER");
    private static String DB_SERVER_PORT = new ReadProperties().readProperties("DB_SERVER_PORT");
    private static String DB_USERNAME = new ReadProperties().readProperties("DB_USERNAME");
    private static String DB_PASSWORD = new ReadProperties().readProperties("DB_PASSWORD");
    private static String DB_NAME = new ReadProperties().readProperties("DB_NAME");

    static
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
        }
    }


    public static Connection createConnection()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:postgresql://"+DB_SERVER_IP+":"+DB_SERVER_PORT+"/"+DB_NAME+"", DB_USERNAME, DB_PASSWORD);
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection createLEMRConnection()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:postgresql://"+DB_SERVER_IP+":"+DB_SERVER_PORT+"/lemr", DB_USERNAME, DB_PASSWORD);
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return connection;
    }

    public static PreparedStatement prepareStatement(String query) {
        try {
            statement = connection.prepareStatement(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (PreparedStatement) statement;
    }

    public static Statement createtatement()
    {
        try
        {
            statement = connection.createStatement();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return statement;
    }


    public static void updateQuery(String query, String username)
    {
        if (connection != null && prepareStatement(query) != null)
        {
            try
            {
                ((PreparedStatement) statement).setString(1, username);
                ((PreparedStatement) statement).executeUpdate();
            }
            catch (SQLException sqle)
            {
                System.out.println("SQL Exception thrown: " + sqle);
            }
        }
    }

  
    public static Statement createStatement() {
        try {
            statementToQuery = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statementToQuery;
    }

    /**
     * Execute the Query and close the statement 
     * @param qureys
     */
    public static void excecuteQuery(String qureys)
    {
        if (connection != null)
        {
            try
            {
                statement = prepareStatement(qureys);
                ((PreparedStatement)statement).execute();
            }
            catch (SQLException sqle)
            {
                System.out.println("SQL Exception thrown: " + sqle);
            }
            finally
            {
                try
                {
                    statement.close();
                }
                catch (SQLException e)
                {

                }
                statement = null;
            }
        }
    }

    public static ResultSet resultSet(String qureys)
    {
        ResultSet rs = null;

        if (connection != null && createtatement() != null)
        {
            try
            {
                rs = statement.executeQuery(qureys);
            }
            catch (SQLException sqle)
            {
                System.out.println("SQL Exception thrown: " + sqle);
            }
        }

        return rs;
    }


    public static void closeDBConnection() {
        try {
            if (statementToQuery != null)
            {
                statementToQuery.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            if (connection != null)

            {

                connection.close();
            }
        } catch (SQLException se) {

            se.printStackTrace();
        }
    }

    /**
     * Turn on feature flag
     * @param key
     * @return
     */
    public static boolean turnOnFeatureFlag(String key)
    {

        PreparedStatement smt = null;
        try
        {

            String sql = "update ff4j_features set enable = 1 where feat_uid = ?";
            String params[] = { key };
            smt = createStatement(sql, params);
            return smt.execute();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            System.out.println("Exception occured while getting additional_parameters of a rule ");
            //Do nothing
        }
        finally
        {
            closeConnection(smt, null);
        }
        return false;
    }
    /**
     * Get the additional parameter for a rule
     * @param groupName
     * @param ruleName
     * @return
     */
    public static String getAdditionalParametersOfRule(String groupName, String ruleName)
    {

        PreparedStatement smt = null;
        ResultSet rs = null;
        try
        {

            String sql = "Select additional_parameters from t_demographic_rule_config "
                + "where  rule_set_id = (Select rule_set_id from t_group_of_persons where group_name = ? ) "
                + "and  rule_id = (Select id from t_demographic_rule where name = ?)";
            String parameter[] = {groupName, ruleName};
            smt = createStatement(sql, parameter);
            rs = smt.executeQuery();
            while (rs.next())
            {
                return rs.getString(1);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            System.out.println("Exception occured while getting additional_parameters of a rule ");
            //Do nothing
        }
        finally
        {
            closeConnection(smt, rs);
        }
        return null;
    }

    public static ResultSet getResultSetUsingPrepStatement(String query)
    {
        
        ResultSet rs = null;
        if(connection == null)
        {
            connection = createConnection();
        }
        if(connection != null && prepareStatement(query) != null)
        {
            try
            {
				rs = ((PreparedStatement)statement).executeQuery();
            }
            catch (SQLException sqle)
            {
                System.out.println("SQL Exception thrown: " + sqle);
            }
        }
		return rs;
    }

    public static ResultSet queryResult(String query)
    {
        ResultSet rs = null;
        if (connection != null)
        {
            try
            {
                statement = connection.prepareStatement(query);
                rs = ((PreparedStatement)statement).executeQuery();
            }
            catch (SQLException sqle)
            {
                System.out.println("SQL Exception thrown: " + sqle);
            }
        }
        return rs;
    }



    /**
     * Create the {@link PreparedStatement}
     * @param sql
     * @param parameters
     * @return
     * @throws SQLException
     */
    private static PreparedStatement createStatement(String sql, String[] parameters) throws SQLException
    {
        Connection con;
        PreparedStatement smt;
        con = createConnection();
        smt = con.prepareStatement(sql);
        int index = 1;
        for (String parameter : parameters)
        {
            smt.setString(index, parameter);
            index++;
        }
        return smt;
    }/**
     * Close connection and statement
     * @param smt
     * @param rs
     */
    private static void closeConnection(PreparedStatement smt, ResultSet rs)
    {
        if (rs != null)
        {
            try
            {

                rs.close();
            }
            catch (SQLException e)
            {
                //Do nothing
            }
        }
        if (smt != null)
        {
            try
            {
                smt.close();
            }
            catch (SQLException e)
            {
                //Do nothing
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                //Do nothing
            }
        }
        if (statementToQuery != null) {
            try {
                statementToQuery.close();
            } catch (SQLException e) {
                //Do nothing
            }
        }

        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static ResultSet resultSetForPrepareStatement(String qureys)
    {
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(qureys);

            if (connection != null) {
                try {
                    rs = statement.executeQuery(qureys);
                } catch (SQLException sqle) {
                    System.out.println("SQL Exception thrown: " + sqle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public static ResultSet resultSetForThePrepareStatement(String qureys)
    {
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(qureys);

            if (connection != null) {
                try {
                    rs = ((PreparedStatement)statement).executeQuery();
                } catch (SQLException sqle) {
                    System.out.println("SQL Exception thrown: " + sqle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
