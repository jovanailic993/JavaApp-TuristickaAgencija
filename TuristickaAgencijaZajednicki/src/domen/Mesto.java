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
public class Mesto implements OpstiDomenskiObjekat{
    private int ptt;
    private String naziv;

    public Mesto() {
    }

    public Mesto(int ptt, String Naziv) {
        this.ptt = ptt;
        this.naziv = Naziv;
    }

    public int getPtt() {
        return ptt;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setPtt(int ptt) {
        this.ptt = ptt;
    }

    public void setNaziv(String Naziv) {
        this.naziv = Naziv;
    } 
    
    @Override
    public String toString() {
        return ptt + " - " + naziv;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Mesto) {
            Mesto m = (Mesto) obj;
            return m.getPtt() == this.ptt;
        }
        return false;
    }

    @Override
    public String vratiNazivTabele() {
        return "mesto";
    }

    @Override
    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception {
        List<OpstiDomenskiObjekat> lm = new LinkedList<>();
        try {            
            while (rs.next()) {
                Mesto m = new Mesto();
                m.setPtt(rs.getInt("mesto.Ptt"));
                m.setNaziv(rs.getString("mesto.Naziv"));
                lm.add(m);
            }
            
        } catch (Exception ex) {
            System.out.println("domen.Mesto.vratiListuIzBaze()");
            return null;
        }
        return lm;
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
    public String vratiUslovZaPretragu() {
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
    public String vratiAtributeZaInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vratiVrednostiZaInsert() {
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
