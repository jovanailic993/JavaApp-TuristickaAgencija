/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class Putovanje implements OpstiDomenskiObjekat{
    private int putovanjeID;
    private String naziv;
    private String polaziste;
    private String odrediste;
    private Date datumOD;
    private Date datumDO;
    private double cena;
    private String kriterijumZaPretragu;
    private ArrayList<Rezervacija> listaRezervacija;

    public Putovanje() {
        listaRezervacija = new ArrayList<>();
    }

    public Putovanje(int putovanjeID, String naziv, String polaziste, String odrediste, Date datumOD, Date datumDO, double cena) {
        this.putovanjeID = putovanjeID;
        this.naziv = naziv;
        this.polaziste = polaziste;
        this.odrediste = odrediste;
        this.datumOD = datumOD;
        this.datumDO = datumDO;
        this.cena = cena;
        listaRezervacija = new ArrayList<>();
    }

    public void setListaRezervacija(ArrayList<Rezervacija> listaRezervacija) {
        this.listaRezervacija = listaRezervacija;
    }

    public ArrayList<Rezervacija> getListaRezervacija() {
        return listaRezervacija;
    }

    public String getKriterijumZaPretragu() {
        return kriterijumZaPretragu;
    }

    public void setKriterijumZaPretragu(String kriterijumZaPretragu) {
        this.kriterijumZaPretragu = kriterijumZaPretragu;
    }
    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getPutovanjeID() {
        return putovanjeID;
    }

    public void setPutovanjeID(int putovanjeID) {
        this.putovanjeID = putovanjeID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPolaziste() {
        return polaziste;
    }

    public void setPolaziste(String polaziste) {
        this.polaziste = polaziste;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }

    public Date getDatumOD() {
        return datumOD;
    }

    public void setDatumOD(Date datumOD) {
        this.datumOD = datumOD;
    }

    public Date getDatumDO() {
        return datumDO;
    }

    public void setDatumDO(Date datumDO) {
        this.datumDO = datumDO;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String vratiNazivTabele() {
        return "putovanje";
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        try {
            List<OpstiDomenskiObjekat> lp = new LinkedList<>();
            
            while (rs.next()) {
                Putovanje p = new Putovanje();
                p.setPutovanjeID(rs.getInt(1));
                p.setNaziv(rs.getString(2));
                p.setPolaziste(rs.getString(3));
                p.setOdrediste(rs.getString(4));
                p.setDatumOD(rs.getDate(5));
                p.setDatumDO(rs.getDate(6));
                p.setCena(rs.getDouble(7));
                
                lp.add(p);
            }
            return lp;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public String vratiUslovZaPretragu() {
        if(kriterijumZaPretragu.equals("*")){
            return "";
        }
        return " WHERE putovanje.PutovanjeID LIKE '%" + kriterijumZaPretragu + "%' OR putovanje.Naziv LIKE '%" + kriterijumZaPretragu
            + "%' OR putovanje.Polaziste LIKE '%" + kriterijumZaPretragu + "%' OR putovanje.Odrediste LIKE '%" + kriterijumZaPretragu
            + "%' OR putovanje.DatumOD LIKE '%" + kriterijumZaPretragu + "%' OR datumDO LIKE '%" + kriterijumZaPretragu
            + "%' OR putovanje.Cena LIKE '%"+ kriterijumZaPretragu + "%'";
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
    public String vratiVrednostiZaInsert() {
        //ne treba
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String vratiUslovZaBrisanje() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String vratiVrednostiZaOperacijuUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiUslovZaOperacijuUpdate() {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiUslovZaID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
