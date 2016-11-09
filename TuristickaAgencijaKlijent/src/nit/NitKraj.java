/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nit;

import forme.glavne.FormaGlavna;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class NitKraj extends Thread {

    FormaGlavna glavnaForma;

    public NitKraj(FormaGlavna glavnaForma) {
        this.glavnaForma = glavnaForma;
    }

    public void run() {
        while (true) {
            try {
                TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
                toZahtev.setOperacija(Konstante.KRAJ);
                TransferObjekatOdgovor toOdgovor = null;
                
                boolean zauzetPrijem = true;
                while (zauzetPrijem) {
                    if (!Komunikacija.getInstance().isZauzetPrijem()) {
                        Komunikacija.getInstance().posaljiZahtev(toZahtev);
                        toOdgovor = Komunikacija.getInstance().primiOdgovor();
                        zauzetPrijem = false;
                    }
                }

                if (toOdgovor.isKrajRada()) {
                    JOptionPane.showMessageDialog(glavnaForma, "Kraj rada!", "Kraj rada", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NitKraj.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NitKraj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
