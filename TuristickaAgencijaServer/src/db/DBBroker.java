/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.OpstiDomenskiObjekat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class DBBroker {
    
    private Connection connection;
    private static DBBroker instance;

    private DBBroker() {
        
    }

    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }
    
    public void ucitajDriver() throws Exception{
        try {          
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new Exception("Neuspesno ucitavanje drivera!", ex);
        }
    }
    
    public void otvoriKonekciju() throws Exception {
        try {          
            connection = DriverManager.getConnection("jdbc:mysql://localhost/turistickaagencija", "root", "");
            connection.setAutoCommit(false);
            // Zahteva eksplicitnu potvrdu transakcije
        } catch (SQLException ex) {
            throw new Exception("Neuspesno otvaranje konekcije!", ex);
        }
    }
    
    public void commitTransakcije() throws Exception {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new Exception("Neuspesan commit transakcije!", ex);
        }
    }
    
    public void rollbackTransakcije() throws Exception {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new Exception("Neuspesan rollback transakcije!", ex);
        }
    }
    
    public void zatvoriKonekciju() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new Exception("Neuspesno zatvaranje konekcije!", ex);
        }
    }
    
    public void sacuvaj(OpstiDomenskiObjekat odo) throws Exception {
        try {
            String upit = "INSERT INTO " + odo.vratiNazivTabele();
                if (odo.vratiAtributeZaInsert() != null) {
                    upit += "(" + odo.vratiAtributeZaInsert() + ")";
                }
            upit += " VALUES (" + odo.vratiVrednostiZaInsert() + ")";
            System.out.println(upit);
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            throw new Exception("Neuspesno cuvanje objekta!", ex);
        }
    }
    
    public List<OpstiDomenskiObjekat> vrati(OpstiDomenskiObjekat odo) throws Exception {
        String sql = "SELECT * FROM " + odo.vratiNazivTabele() + odo.dodatniUslovZaSelect();
        System.out.println(sql);
        Statement sqlStatement = connection.createStatement();
        ResultSet rs = sqlStatement.executeQuery(sql);
        return odo.vratiListuIzBaze(rs);
    }

    public int vratiIdRezervacije(OpstiDomenskiObjekat odo) throws SQLException {
        String upit = "SELECT MAX(RezervacijaID) as max FROM " + odo.vratiNazivTabele();
        System.out.println(upit);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(upit);
        return odo.vratiIdRezervacije(rs);
    }

    public List<OpstiDomenskiObjekat> pretrazi(OpstiDomenskiObjekat odo) throws SQLException, Exception {
        String upit = "SELECT " + odo.vratiDodatniUslovZaPretragu() +  " FROM " + odo.vratiNazivTabele() + odo.dodatniUslovZaSelect() + odo.vratiUslovZaPretragu();
        System.out.println(upit);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(upit);
        return odo.vratiListuIzBaze(rs);
    }  
    
    public void obrisi(OpstiDomenskiObjekat odo) throws SQLException {
        String upit = "DELETE FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaBrisanje();
        System.out.println(upit);
        PreparedStatement st = connection.prepareStatement(upit);
        st.executeUpdate();
        st.close();
    }   
    
    public void izmeni(OpstiDomenskiObjekat odo) throws SQLException {
        String upit = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.vratiVrednostiZaOperacijuUpdate() + " WHERE " + odo.vratiUslovZaOperacijuUpdate();
        System.out.println(upit);
        PreparedStatement st = connection.prepareStatement(upit);
        st.executeUpdate();
        st.close();
    }    
    
    public int vratiIDZaposlenog(OpstiDomenskiObjekat odo) throws SQLException {
        String upit = "SELECT ZaposleniID FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaID();
        System.out.println(upit);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(upit);
        return odo.vratiID(rs);
    }
    
}