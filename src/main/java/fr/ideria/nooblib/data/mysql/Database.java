package fr.ideria.nooblib.data.mysql;

import java.sql.*;
import java.util.Optional;

/**
 * A class to connect your code to a database and get information from it or edit it
 */
public class Database {
    /**
     * If the auth information of the database had been given
     */
    private static boolean hasAuth;
    /**
     * The url, admin username, and admin password of the database.
     * They will be init with the Database.initAuth() function.
     */
    private static String url, user, password;
    /**
     * The connection to the database
     */
    private Connection connection;

    /**
     * Constructor
     */
    public Database(){
        if(!hasAuth){
            new SQLException("The database auth had not been gave, use Database.initAuth(url, user, password) to init them").printStackTrace();
            return;
        }

        // Loading JDBC library and create the connection with the database
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Init the auth information of the database, print an error if the auth had already been gave
     * @param url the url of the database than you need to connect to
     * @param user the database admin username
     * @param password the database admin password
     */
    public static void initAuth(String url, String user, String password){
        if(!hasAuth){
            Database.url = url;
            Database.user = user;
            Database.password = password;

            hasAuth = true;
        }else{
            new SQLException("The auth had already been init").printStackTrace();
        }
    }

    /**
    * Query a request to the database
    * @param query the query that will be executed
    * @return an optional of the result set of the query
    * Optional.empty() if there was an error while executing the query
     **/
    public Optional<DataSet> queryRequest(String query){
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            return Optional.of(new DataSet(result));
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Execute an update to the database
     * @param query the query that will be sent to the database
     * @return -1 if the query failed; the amount of affected rows if the query succeeded
     */
    public int queryUpdate(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Close the connection to the database
     */
    public void closeConnection(){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}