/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jovana
 */
public class Zaposleni implements OpstiDomenskiObjekat{
    private int zaposleniID;
    private String korisnickoIme;
    private String korisnickaSifra;
    private boolean ulogovan;

    public Zaposleni() {
    }

    public Zaposleni(int zaposleniID, String korisnickoIme, String korisnickaSifra) {
        this.zaposleniID = zaposleniID;
        this.korisnickoIme = korisnickoIme;
        this.korisnickaSifra = korisnickaSifra;
    }

    public Zaposleni(int zaposleniID, String korisnickoIme, String korisnickaSifra, boolean ulogovan) {
        this.zaposleniID = zaposleniID;
        this.korisnickoIme = korisnickoIme;
        this.korisnickaSifra = korisnickaSifra;
        this.ulogovan = ulogovan;
    }

    public String getKorisnickaSifra() {
        return korisnickaSifra;
    }

    public void setKorisnickaSifra(String korisnickaSifra) {
        this.korisnickaSifra = korisnickaSifra;
    }

    public int getZaposleniID() {
        return zaposleniID;
    }

    public void setZaposleniID(int zaposleniID) {
        this.zaposleniID = zaposleniID;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public boolean isUlogovan() {
        return ulogovan;
    }

    public void setUlogovan(boolean ulogovan) {
        this.ulogovan = ulogovan;
    }


    @Override
    public String toString() {
        return korisnickoIme;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Zaposleni) {
            Zaposleni z = (Zaposleni) obj;
            return z.getZaposleniID() == this.zaposleniID;
        }
        return false;
    }
    
    @Override
    public String vratiNazivTabele() {
        return "zaposleni";
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lz = new LinkedList<>();
        try {
            
            while (rs.next()) {
                Zaposleni z = new Zaposleni();
                z.setZaposleniID(rs.getInt(1));
                z.setKorisnickoIme(rs.getString(2));
                z.setKorisnickaSifra(rs.getString(3));                
                z.setUlogovan(rs.getBoolean(4));                
                lz.add(z);
            }
            
        } catch (Exception e) {
            System.out.println("Greska kod vracanja liste zaposlenih");
            throw e;
        }
        return lz;
    }
    
    @Override
    public String vratiVrednostiZaOperacijuUpdate() {
        int login = 1;
        if (ulogovan) {
            login = 0;
        }

        return "Ulogovan = '" + login + "'";
    }
    
    @Override
    public String vratiUslovZaOperacijuUpdate() {
        return "KorisnickoIme = '" + korisnickoIme + "' AND KorisnickaSifra = '" + korisnickaSifra + "'";
    }
    
    @Override
    public String vratiUslovZaPretragu() {
        if (korisnickoIme == null && korisnickaSifra == null) {
            return "Ulogovan = '1'";
        } else {
            return " WHERE KorisnickoIme = '" + korisnickoIme + "' AND KorisnickaSifra = '" + korisnickaSifra + "' AND Ulogovan = '0'";
        }
    }
    
    @Override
    public String vratiVrednostiZaInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiAtributeZaInsert() {
        //ne treba
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String dodatniUslovZaSelect() {
        return "";
    }

    @Override
    public String vratiUslovZaBrisanje() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int vratiIdRezervacije(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiDodatniUslovZaPretragu() {
        return "*";
    }

    @Override
    public int vratiID(ResultSet rs) {
        int id = 0;
        try {
            while (rs.next()) {
                id = rs.getInt("ZaposleniID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Zaposleni.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public String vratiUslovZaID() {
        return "KorisnickoIme = '" + korisnickoIme + "' AND KorisnickaSifra = '" + korisnickaSifra + "'";
    }
    
    
}
