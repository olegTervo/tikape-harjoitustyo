/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

/**
 *
 * @author oleg
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String teksti = rs.getString("teksti");
        Boolean oikein = rs.getBoolean("oikein");
        Integer kysymys_id = rs.getInt("kysymys_id");

        Vastaus o = new Vastaus(id, teksti, oikein, kysymys_id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("teksti");
            Boolean oikein = rs.getBoolean("oikein");
            Integer kysymys_id = rs.getInt("kysymys_id");

            vastaukset.add(new Vastaus(id, teksti, oikein, kysymys_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public List<Vastaus> findAll(Integer kID) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setObject(1, kID);

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String teksti = rs.getString("teksti");
            Boolean oikein = rs.getBoolean("oikein");
            Integer kysymys_id = rs.getInt("kysymys_id");

            vastaukset.add(new Vastaus(id, teksti, oikein, kysymys_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public void add(Vastaus v) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Vastaus(teksti, oikein, kysymys_id) VALUES (?, ?, ?)");
        stmt.setObject(1, v.getTeksti());
        stmt.setObject(2, v.onOikein());
        stmt.setObject(3, v.getKysymys());

        stmt.execute();
        
        stmt.close();
        connection.close();
    }
    

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Vastaus WHERE id=?");
        stmt.setObject(1, key);

        stmt.execute();
        
        stmt.close();
        connection.close();
    }
    
    public void deleteAll(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id=?");
        stmt.setObject(1, key);

        stmt.execute();
        
        stmt.close();
        connection.close();
    }

}
