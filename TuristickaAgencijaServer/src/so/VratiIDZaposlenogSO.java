/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domen.OpstiDomenskiObjekat;

/**
 *
 * @author Jovana
 */
public class VratiIDZaposlenogSO extends OpstaSO{
    
    private int id;

    public int getId() {
        return id;
    }

    @Override
    protected void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void izvrsiKonkretnuSO(OpstiDomenskiObjekat odo) throws Exception {
        id = db.DBBroker.getInstance().vratiIDZaposlenog(odo);
    }

}
