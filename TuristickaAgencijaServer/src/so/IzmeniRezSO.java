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
public class IzmeniRezSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
    }

    @Override
    protected void izvrsiKonkretnuSO(OpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().izmeni(odo);
        int i=0;
        for (OpstiDomenskiObjekat stavka : ((Rezervacija) odo).getListaStavkiSVE()) {             
            if(((StavkaRezervacije) stavka).getRedniBroj() == 0){                               
                i++;
                if(((StavkaRezervacije) stavka).getStatus().equals("dodaj")){
                    ((StavkaRezervacije) stavka).setRedniBroj(i);
                    db.DBBroker.getInstance().sacuvaj(stavka);
                }
                 
            }
            else{
                if(((StavkaRezervacije) stavka).getStatus() == null){                           
                    db.DBBroker.getInstance().izmeni(stavka);
                    i++;
                }
                else if (((StavkaRezervacije) stavka).getStatus().equals("delete")) {                    
                    db.DBBroker.getInstance().obrisi(stavka);
                }
                
            }
        }                   
    }    
}
