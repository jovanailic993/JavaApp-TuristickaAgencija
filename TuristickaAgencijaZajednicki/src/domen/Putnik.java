/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author Jovana
 */
public class Putnik implements OpstiDomenskiObjekat{
    private int putnikID;
    private String ime;
    private String prezime;
    private String jmbg;
    private String kontakt;
    private String email;
    private String ulica;
    private int broj;
    private Mesto mesto;
    private String kriterijumZaPretragu;
    
    public Putnik() {
    }

    public Putnik(int putnikID, String ime, String prezime, String jmbg, String kontakt, String email, String ulica, int broj, Mesto mesto) {
        this.putnikID = putnikID;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.kontakt = kontakt;
        this.email = email;
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
        this.kriterijumZaPretragu = "";
    }

    public String getKriterijumZaPretragu() {
        return kriterijumZaPretragu;
    }

    public void setKriterijumZaPretragu(String kriterijumZaPretragu) {
        this.kriterijumZaPretragu = kriterijumZaPretragu;
    }
    
    public int getPutnikID() {
        return putnikID;
    }

    public void setPutnikID(int putnikID) {
        this.putnikID = putnikID;
    }
    
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public String toString() {
        return ime +" "+ prezime;
    }    

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Putnik) {
            Putnik p = (Putnik) obj;
            return p.getPutnikID() == this.putnikID;
        }
        return false;
    }
    
    @Override
    public String vratiNazivTabele() {
        return "putnik";
    }    
    
    @Override
    public String dodatniUslovZaSelect() {
        return " JOIN mesto ON putnik.Ptt = mesto.Ptt";
    } 
    
   @Override
    public String vratiDodatniUslovZaPretragu() {
        return "putnik.*,mesto.Naziv";
    }
    
    
    @Override
    public String vratiAtributeZaInsert() {
        return "Ime,Prezime,JMBG,Kontakt,Email,Ulica,Broj,Ptt";
    }
    
    @Override
    public String vratiVrednostiZaInsert() {
        return "'" +ime+ "','"+prezime+"',"+jmbg+","+kontakt+",'"+email+"','"+ulica+"',"+broj+","+mesto.getPtt();
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lp = new LinkedList<>();
        try {            
            while (rs.next()) {                   
                Putnik p = new Putnik();
                int id = rs.getInt("PutnikID");
                p.setPutnikID(id);
                p.setIme(rs.getString("Ime"));
                p.setPrezime(rs.getString("Prezime"));
                p.setJmbg(rs.getString("JMBG"));
                p.setKontakt(rs.getString("Kontakt"));
                p.setEmail(rs.getString("Email"));
                p.setUlica(rs.getString("Ulica"));
                p.setBroj(rs.getInt("Broj"));
                
                Mesto m = new Mesto();
                m.setPtt(rs.getInt("Ptt"));
                m.setNaziv(rs.getString("Naziv"));
                
                p.setMesto(m);
                
                lp.add(p);
            }            
        } catch (Exception ex) {
            throw ex;
        }
        return lp;
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "PutnikID ='" + putnikID + "'";
    }

    @Override
    public String vratiUslovZaPretragu() {
        if(kriterijumZaPretragu.equals("*")){
            return "";
        }
        return " WHERE putnik.PutnikID LIKE '%" + kriterijumZaPretragu + "%' OR putnik.Ime LIKE '%" + kriterijumZaPretragu
                + "%' OR putnik.Prezime LIKE '%" + kriterijumZaPretragu+ "%' OR putnik.JMBG LIKE '%" + kriterijumZaPretragu
                + "%' OR putnik.Kontakt LIKE '%" + kriterijumZaPretragu+ "%' OR putnik.Email LIKE '%" + kriterijumZaPretragu
                + "%' OR putnik.Ulica LIKE '%" + kriterijumZaPretragu+ "%' OR putnik.Broj LIKE '%" + kriterijumZaPretragu
                + "%' OR putnik.Ptt LIKE '%" + kriterijumZaPretragu+ "%'";
    }

    @Override
    public String vratiVrednostiZaOperacijuUpdate() {
        return "Ime = '" + ime + "', Prezime = '" + prezime + "', JMBG = '" + jmbg + "', Kontakt = '" + kontakt + "', Email = '" + email + "', Ulica = '" + ulica + "', Broj = '" + broj + "', Ptt = '" + mesto.getPtt()+"'";
    }

    @Override
    public String vratiUslovZaOperacijuUpdate() {
        return "PutnikID = '" + putnikID + "'";
    }

    @Override
    public int vratiIdRezervacije(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
