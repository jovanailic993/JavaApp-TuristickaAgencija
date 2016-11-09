/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Jovana
 */
public interface OpstiDomenskiObjekat extends Serializable {

    public String vratiNazivTabele();
    
    public String dodatniUslovZaSelect(); //join

    public List<OpstiDomenskiObjekat> vratiListuIzBaze(ResultSet rs) throws Exception;

    public String vratiAtributeZaInsert();
    public String vratiVrednostiZaInsert();
    
    public String vratiUslovZaBrisanje();
    
    public String vratiUslovZaPretragu();
    public String vratiDodatniUslovZaPretragu();

    public String vratiVrednostiZaOperacijuUpdate();
    public String vratiUslovZaOperacijuUpdate();
    
    public int vratiIdRezervacije(ResultSet rs); // za pravljenje nove rez

    public int vratiID(ResultSet rs);//za vracanje ZaposleniID
    public String vratiUslovZaID();

}
