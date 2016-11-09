/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Rezervacija;
import domen.StavkaRezervacije;

/**
 *
 * @author Jovana
 */
public class SacuvajRezSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
    }

    @Override
    protected void izvrsiKonkretnuSO(OpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().sacuvaj(odo);
        int i = 0;
        for (OpstiDomenskiObjekat stavka : ((Rezervacija) odo).getListaStavkiSVE()) {
            i++;
            ((StavkaRezervacije) stavka).setRedniBroj(i);
            db.DBBroker.getInstance().sacuvaj(stavka);
        }        
    }
    
}
