/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnalogika;

import domen.OpstiDomenskiObjekat;
import forma.ServerskaForma;
import java.util.List;
import so.IzmeniRezSO;
import so.IzmeniSO;
import so.ObrisiRezSO;
import so.ObrisiSO;
import so.OpstaSO;
import so.PretraziSO;
import so.SacuvajRezSO;
import so.SacuvajSO;
import so.VratiIDZaposlenogSO;
import so.VratiIdRezervacijeSO;
import so.VratiSO;



/**
 *
 * @author Jovana
 */
public class Kontroler {
    private static Kontroler instance;
    private ServerskaForma sf;
    
    private Kontroler() {
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public ServerskaForma getSf() {
        return sf;
    }

    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }
    
    public List<OpstiDomenskiObjekat> vrati(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new VratiSO();
        os.izvrsenjeSO(odo);
        return ((VratiSO) os).getLista();
    }    

    public void sacuvaj(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new SacuvajSO();
        os.izvrsenjeSO(odo);
    }

    public Object pretrazi(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new PretraziSO();
        os.izvrsenjeSO(odo);
        return ((PretraziSO) os).getLista();
    }

    public void obrisi(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new ObrisiSO();
        os.izvrsenjeSO(odo);
    }
    
    public void obrisiRezervaciju(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new ObrisiRezSO();
        os.izvrsenjeSO(odo);
    }

    public void izmeni(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new IzmeniSO();
        os.izvrsenjeSO(odo);
    }

    public int vratiIDRezervacije(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new VratiIdRezervacijeSO();
        os.izvrsenjeSO(odo);
        return ((VratiIdRezervacijeSO) os).getId();
    }    
    
    public int vratiIDZaposlenog(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os1 = new VratiIDZaposlenogSO();
        os1.izvrsenjeSO(odo);
        return ((VratiIDZaposlenogSO) os1).getId();
    }
    
    public void sacuvajRezervaciju(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new SacuvajRezSO();
        os.izvrsenjeSO(odo);
    }

    public void izmeniRezervaciju(OpstiDomenskiObjekat odo) throws Exception {
        OpstaSO os = new IzmeniRezSO();
        os.izvrsenjeSO(odo);
    }
    
    
}
