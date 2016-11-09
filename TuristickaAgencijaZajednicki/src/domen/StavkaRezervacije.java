/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class StavkaRezervacije implements OpstiDomenskiObjekat{
    Rezervacija rezervacija;
    private int redniBroj;
    private Date datumRezervacije;
    private Date datumVazenjaRezervacije;
    private String aktivnaRezervacija;
    private Date datumPlacanja;
    private Putovanje putovanje;
    private String status;
    
    public StavkaRezervacije() {
    }

    public StavkaRezervacije(Rezervacija rezervacija, int redniBroj, Date datumVazenjaRezervacije, String aktivnaRezervacija, Date datumPlacanja, Putovanje putovanje) {
        this.rezervacija = rezervacija;
        this.redniBroj = redniBroj;
        this.datumRezervacije = new Date();
        this.datumVazenjaRezervacije = datumVazenjaRezervacije;
        this.aktivnaRezervacija = aktivnaRezervacija;
        this.datumPlacanja = datumPlacanja;
        this.putovanje = putovanje;
        this.status = "";
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public void setDatumRezervacije(Date datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }
    
    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }
    
    public Date getDatumPlacanja() {
        return datumPlacanja;
    }

    public void setDatumPlacanja(Date datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public Date getDatumRezervacije() {
        return datumRezervacije;
    }
    
    public Date getDatumVazenjaRezervacije() {
        return datumVazenjaRezervacije;
    }

    public void setDatumVazenjaRezervacije(Date datumVazenjaRezervacije) {
        this.datumVazenjaRezervacije = datumVazenjaRezervacije;
    }

    public String getAktivnaRezervacija() {
        return aktivnaRezervacija;
    }

    public void setAktivnaRezervacija(String aktivnaRezervacija) {
        this.aktivnaRezervacija = aktivnaRezervacija;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkaRezervacije";
    }

    @Override
    public String vratiAtributeZaInsert() {
        return "RezervacijaID,RedniBroj,DatumRezervacije,DatumVazenjaRezervacije,AktivnaRezervacija,DatumPlacanja,PutovanjeID";
    }
    
    @Override
    public String vratiVrednostiZaInsert() {
        String aktivna = "ne";
        if(aktivnaRezervacija.equals("da")){
            aktivna = "da";
        }
        return " "+rezervacija.getRezervacijaID()+", "+redniBroj+", '"+(new SimpleDateFormat("yyyy-MM-dd").format(datumRezervacije))+"'"+", '"+(new SimpleDateFormat("yyyy-MM-dd").format(datumVazenjaRezervacije))+"','"+aktivna+"', '"+(new SimpleDateFormat("yyyy-MM-dd").format(datumPlacanja))+"',"+putovanje.getPutovanjeID();
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String dodatniUslovZaSelect() {        
        return "";    
    }

    @Override
    public String vratiUslovZaBrisanje() {
        return "RezervacijaID = "+ rezervacija.getRezervacijaID()+ " AND RedniBroj = " + redniBroj;
    }

    @Override
    public String vratiVrednostiZaOperacijuUpdate() {
        String aktivna = "ne";
        if(aktivnaRezervacija.equals("da")){
            aktivna = "da";
        }
        return " DatumRezervacije= '"+(new SimpleDateFormat("yyyy-MM-dd").format(datumRezervacije))+"', DatumVazenjaRezervacije='"+(new SimpleDateFormat("yyyy-MM-dd").format(datumVazenjaRezervacije))+"', AktivnaRezervacija='"+aktivna+"', DatumPlacanja='"+(new SimpleDateFormat("yyyy-MM-dd").format(datumPlacanja))+"',PutovanjeID="+putovanje.getPutovanjeID();
    }

    @Override
    public String vratiUslovZaOperacijuUpdate() {
        return "RezervacijaId = " + rezervacija.getRezervacijaID() + " AND RedniBroj = " + redniBroj;
    }

    @Override
    public int vratiIdRezervacije(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String vratiUslovZaPretragu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String vratiDodatniUslovZaPretragu() {
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
