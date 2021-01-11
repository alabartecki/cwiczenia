package com.pluralsight.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


class Bazka {

    public static void main(String[] args) throws SQLException {
        //zapytanie do bazy zainicjowane
        Statement stmt = null;
        //wynik z bazy zainicjowany
        ResultSet rs = null;
        Connection conn = null;
        try {
            //polaczenie conn do bazy - zaladuj driver/sterownik
            Class.forName("org.h2.Driver");
//            Class.forName("org.postgresql.Driver");
            //podlacz sie do silnika bazy h2 i do bazy ala (nie podajemy portu i ip dla bazy tymczasowej) z uzytkownikiem sa haslo brak

            conn = DriverManager.getConnection("jdbc:h2:~/ala", "sa", "");
            // Dla innej bazy postgres na hoscie lokalnym 127.0.0.1 port 5432 uzytkownik sa haslo sa
//             Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "sa","sa");
            stmt = conn.createStatement();
//            stmt.executeUpdate("drop table  rodzina");
            //tworzy tabele
            stmt.executeUpdate("CREATE  table if not exists rodzina( ID int auto_increment,  FirstName varchar(255),  CONSTRAINT m_rodzina UNIQUE (FirstName), LastName varchar(255))");
            stmt.executeUpdate("INSERT into rodzina (firstName,lastName) values ('mirmek','bartecki')");
//            stmt.executeUpdate("DELETE from rodzina where id=1");
//            stmt.executeUpdate(" rodzina ")

            rs = stmt.executeQuery("SELECT * FROM rodzina");
            while (rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
            }


            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...


            // Now do something with the ResultSet ....
        } catch (
                SQLException | ClassNotFoundException ex) {
            // handle any errors
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            conn.close();
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }
}