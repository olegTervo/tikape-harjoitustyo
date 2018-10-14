package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Kysymys ("
                + "id integer PRIMARY KEY, "
                + "kurssi varchar(255), "
                + "aihe varchar(255), "
                + "teksti varchar(500)"
                + ");");
        
        lista.add("CREATE TABLE Vastaus ("
                + "id integer PRIMARY KEY, "
                + "kysymys_id integer,"
                + "teksti varchar(500), "
                + "oikein boolean, "
                + "FOREIGN KEY (kysymys_id) REFERENCES Kysymys(id)"
                + ");");

        return lista;
    }
}
