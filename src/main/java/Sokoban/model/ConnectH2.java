package Sokoban.model;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectH2 {
    public static Connection conn;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_TESTCONNECTION = "jdbc:h2:~/test";
    private static final String DB_CONNECTION = "jdbc:h2:file:./src/main/java/Sokoban/sokobanDB";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    public ConnectH2() {
        try {
            Class.forName(DB_DRIVER).newInstance();
            testDatabase();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            }
            catch (SQLException s){}
        }
    }

    public void testDatabase() {
        try {
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            Statement st = conn.createStatement();

            String CreateQuery = "CREATE TABLE IF NOT EXISTS MAIN_TABLE(" +
                    " NICKNAME VARCHAR(50) NOT NULL," +
                    " PASSWORD VARCHAR(50) NOT NULL," +
                    " LAST_LEVEL VARCHAR(50) NOT NULL)";

            st.execute(CreateQuery);
        //    st.execute("INSERT INTO MAIN_TABLE (NICKNAME, PASSWORD, LAST_LEVEL) VALUES ('alex','alex','10')");
            ResultSet result = st.executeQuery("SELECT * FROM MAIN_TABLE");
            while (result.next()) {
                String name = result.getString("NICKNAME");
                System.out.println(result.getString("PASSWORD")+" "+name);
            }
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException s){}
        }
    }

    // returns last level or "-1" if nickname or password does not exists
    public int getLastLevel(String nickname, String password) {
        String lastLevel="-1";
        try {
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            Statement st = conn.createStatement();
            String LevelQuery = "SELECT LAST_LEVEL FROM MAIN_TABLE WHERE NICKNAME='" + nickname + "' AND PASSWORD='" + password+"'";
            ResultSet rs = st.executeQuery(LevelQuery);
            while (rs.next()) {
                lastLevel = rs.getString("LAST_LEVEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException s){}
        }
        return Integer.parseInt(lastLevel);
    }

    public boolean createUser() {
        boolean created = false;
        try {
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            Statement st = conn.createStatement();

            created = st.execute("INSERT INTO MAIN_TABLE (NICKNAME, PASSWORD, LAST_LEVEL) VALUES ('alex','alex','10')");
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException s){}
        }
        return created;
    }

    public void clearDB() {
        boolean created = false;
        try {
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            Statement st = conn.createStatement();
            st.execute("DELETE FROM MAIN_TABLE");
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException s){}
        }
    }


}
