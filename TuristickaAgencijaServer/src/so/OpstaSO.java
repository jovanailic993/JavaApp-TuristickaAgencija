/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;

/**
 *
 * @author Jovana
 */
public abstract class OpstaSO {
    
    public final void izvrsenjeSO(OpstiDomenskiObjekat odo) throws Exception {
        try {
            ucitajDriver();
            otvoriKonekciju();
            proveriPreduslov(odo);
            izvrsiKonkretnuSO(odo);
            commitTransakcije();
        } catch (Exception ex) {
            rollbackTransakcije();
        } finally {
            zatvoriKonekciju();
        }
    }

    private void ucitajDriver() throws Exception {
        DBBroker.getInstance().ucitajDriver();
    }

    private void otvoriKonekciju() throws Exception {
        DBBroker.getInstance().otvoriKonekciju();
    }

    // Specificno za svaku sistemsku operaciju
    protected abstract void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception;
    
    // Specificno za svaku sistemsku operaciju
    protected abstract void izvrsiKonkretnuSO(OpstiDomenskiObjekat odo) throws Exception;

    private void commitTransakcije() throws Exception {
        DBBroker.getInstance().commitTransakcije();
    }

    private void rollbackTransakcije() throws Exception {
        DBBroker.getInstance().rollbackTransakcije();
    }

    private void zatvoriKonekciju() throws Exception {
        DBBroker.getInstance().zatvoriKonekciju();
    }
    
}
