/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Rezervacija;

/**
 *
 * @author Jovana
 */
public class ObrisiRezSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
    }

    @Override
    protected void izvrsiKonkretnuSO(OpstiDomenskiObjekat odo) throws Exception {
        for (OpstiDomenskiObjekat stavka : ((Rezervacija) odo).getListaStavki()) {
            DBBroker.getInstance().obrisi(stavka);
        }
        
        DBBroker.getInstance().obrisi(odo);
    }
    
}
