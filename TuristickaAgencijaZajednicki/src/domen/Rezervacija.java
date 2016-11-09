/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jovana
 */
public class Rezervacija implements OpstiDomenskiObjekat{
    private int RezervacijaID;
    private Putnik putnik;
    private Zaposleni zaposleni;
    private ArrayList<StavkaRezervacije> listaStavki;
    private ArrayList<StavkaRezervacije> listaStavkiSVE;
    private String kriterijumZaPretragu;

    public Rezervacija() {
        listaStavki = new ArrayList<>();
        listaStavkiSVE = new ArrayList<>();
    }

    public Rezervacija(int RezervacijaID, Putnik putnik, Zaposleni zaposleni) {
        this.RezervacijaID = RezervacijaID;
        this.putnik = putnik;
        this.zaposleni = zaposleni;
        this.listaStavki = new ArrayList<>();
        this.listaStavkiSVE = new ArrayList<>();
    }

    public Rezervacija(int RezervacijaID, Putnik putnik, Zaposleni zaposleni, ArrayList<StavkaRezervacije> listaStavki) {
        this.RezervacijaID = RezervacijaID;
        this.putnik = putnik;
        this.zaposleni = zaposleni;
        this.listaStavki = listaStavki;
    }  

    public void setKriterijumZaPretragu(String kriterijumZaPretragu) {
        this.kriterijumZaPretragu = kriterijumZaPretragu;
    }

    public String getKriterijumZaPretragu() {
        return kriterijumZaPretragu;
    }    
    
    public void setListaStavkiSVE(ArrayList<StavkaRezervacije> listaStavkiSVE) {
        this.listaStavkiSVE = listaStavkiSVE;
    }

    public ArrayList<StavkaRezervacije> getListaStavkiSVE() {
        return listaStavkiSVE;
    }
    
    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public int getRezervacijaID() {
        return RezervacijaID;
    }

    public void setRezervacijaID(int RezervacijaID) {
        this.RezervacijaID = RezervacijaID;
    }

    public Putnik getPutnik() {
        return putnik;
    }

    public void setPutnik(Putnik putnik) {
        this.putnik = putnik;
    }

    public ArrayList<StavkaRezervacije> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(ArrayList<StavkaRezervacije> listaStavki) {
        this.listaStavki = listaStavki;
    }

    @Override
    public String toString() {
        return RezervacijaID+" "+putnik;
    }

    @Override
    public String vratiNazivTabele() {
        return "rezervacija";
    }
    
    @Override
    public String vratiAtributeZaInsert() {
        return "RezervacijaID,PutnikID,ZaposleniID";
    }

    @Override
    public String vratiVrednostiZaInsert() {
        return RezervacijaID+","+putnik.getPutnikID()+ ","+zaposleni.getZaposleniID();
    }
    
    @Override
    public String dodatniUslovZaSelect() {
        return " JOIN putnik ON rezervacija.PutnikID=putnik.PutnikID "
                + "JOIN zaposleni ON rezervacija.ZaposleniID=zaposleni.ZaposleniID "
                + "JOIN stavkarezervacije ON rezervacija.RezervacijaID=stavkarezervacije.RezervacijaID "
                + "JOIN putovanje ON stavkarezervacije.PutovanjeID=putovanje.PutovanjeID";
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        try {
            List<OpstiDomenskiObjekat> lp = new LinkedList<>();
            boolean pom = rs.next();
            while (pom) {
                Putnik p = new Putnik();
                p.setPutnikID(rs.getInt("putnik.PutnikID"));
                p.setIme(rs.getString("putnik.Ime"));
                p.setPrezime(rs.getString("putnik.Prezime"));
                p.setJmbg(rs.getString("putnik.JMBG"));
                p.setKontakt(rs.getString("putnik.Kontakt"));
                p.setEmail(rs.getString("putnik.Email"));
                p.setUlica(rs.getString("putnik.Ulica"));
                p.setBroj(rs.getInt("putnik.Broj"));
                
                Zaposleni z = new Zaposleni();
                z.setZaposleniID(rs.getInt("zaposleni.ZaposleniID"));
                z.setKorisnickoIme(rs.getString("zaposleni.KorisnickoIme"));
                z.setKorisnickaSifra(rs.getString("zaposleni.KorisnickaSifra"));
                
                Rezervacija r = new Rezervacija();
                int rezID = rs.getInt("rezervacija.RezervacijaID");
                r.setRezervacijaID(rezID);
                r.setPutnik(p);
                r.setZaposleni(z);
                
                while (pom && rs.getInt("rezervacija.RezervacijaID") == rezID) {
                    StavkaRezervacije stavka = new StavkaRezervacije();
                    stavka.setRezervacija(r);
                    stavka.setRedniBroj(rs.getInt("stavkarezervacije.RedniBroj"));
                    stavka.setDatumRezervacije(rs.getDate("stavkarezervacije.DatumRezervacije"));
                    stavka.setDatumVazenjaRezervacije(rs.getDate("stavkarezervacije.DatumVazenjaRezervacije"));
                    stavka.setAktivnaRezervacija(rs.getString("stavkarezervacije.AktivnaRezervacija"));
                    stavka.setDatumPlacanja(rs.getDate("stavkarezervacije.DatumPlacanja"));
                    
                    Putovanje putovanje = new Putovanje();
                    putovanje.setPutovanjeID(rs.getInt("putovanje.PutovanjeID"));
                    putovanje.setNaziv(rs.getString("putovanje.Naziv"));
                    
                    stavka.setPutovanje(putovanje);
                    
                    
                    r.getListaStavki().add(stavka);
                    r.getListaStavkiSVE().add(stavka);
                    
                    if (!rs.next()) {
                        pom = false;
                    }
                } 
                
                lp.add(r);
            }
            return lp;
        } catch (Exception e) {
            throw e;
        }        
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "RezervacijaID ='" + RezervacijaID + "'";
    }

    @Override
    public String vratiUslovZaPretragu() {
        if(kriterijumZaPretragu.equals("*")){
            return "";
        }
        return " WHERE rezervacija.RezervacijaID LIKE '%" +kriterijumZaPretragu+ "%' OR putnik.Ime LIKE '%" +kriterijumZaPretragu+ "%' OR putnik.Prezime LIKE '%" +kriterijumZaPretragu+ "%'";
    }

    @Override
    public String vratiVrednostiZaOperacijuUpdate() {
        return " PutnikID = '"+putnik.getPutnikID()+"', ZaposleniID = '"+zaposleni.getZaposleniID()+"'";
    }

    @Override
    public String vratiUslovZaOperacijuUpdate() {
        return " RezervacijaId = '" + RezervacijaID + "'";
    }

    @Override
    public int vratiIdRezervacije(ResultSet rs) {
        int id = 0;
        try {
            while (rs.next()) {
                id = rs.getInt("max");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rezervacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
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
