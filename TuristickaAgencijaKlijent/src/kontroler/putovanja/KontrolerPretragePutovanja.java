/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.putovanja;

import domen.Putovanje;
import domen.Rezervacija;
import domen.StavkaRezervacije;
import forme.putovanja.FormaPretragaPutovanja;
import forme.putovanja.FormaPrikazPutovanja;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import modeli.PutovanjaTableModel;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerPretragePutovanja {
    List<Putovanje> listaPutovanja;
    

    public KontrolerPretragePutovanja() {
        listaPutovanja = new ArrayList<>();
    }

    public List<Putovanje> getListaPutovanja() {
        return listaPutovanja;
    }

    public void setListaPutovanja(List<Putovanje> listaPutovanja) {
        this.listaPutovanja = listaPutovanja;
    }

    public void srediFormu(FormaPretragaPutovanja fpp) {
        PutovanjaTableModel ptm = new PutovanjaTableModel(listaPutovanja);
        fpp.getJtblPutovanja().setModel(ptm);    
        ptm.fireTableDataChanged();        
    }

    public void pretraziPoKriterijumu(FormaPretragaPutovanja fpp) {
        try {
            String kriterijum = fpp.getJtxtKriterijum().getText().trim();
            
            if (kriterijum.isEmpty()) {
                JOptionPane.showMessageDialog(fpp, "Unesite kriterijum pretrage", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Putovanje p = new Putovanje();
            p.setKriterijumZaPretragu(kriterijum);
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.NADJI_PUTOVANJA);
            toZahtev.setParametar(p);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                listaPutovanja = (List<Putovanje>) toOdgovor.getRezultat();                 
            } else {
                JOptionPane.showMessageDialog(fpp, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
                listaPutovanja = new ArrayList<>();
            }           
            
            String kriterijumZvezda = "*"; //svee
            Rezervacija r = new Rezervacija();
            r.setKriterijumZaPretragu(kriterijumZvezda);
            
            TransferObjekatZahtev toZahtev2 = new TransferObjekatZahtev();
            toZahtev2.setOperacija(Konstante.NADJI_REZERVACIJE);
            toZahtev2.setParametar(r);
            Komunikacija.getInstance().posaljiZahtev(toZahtev2);
            TransferObjekatOdgovor toOdgovor2 = Komunikacija.getInstance().primiOdgovor();
            List<Rezervacija> listaRez;
            
            if (toOdgovor2.getStatus() == Konstante.STATUS_OK) {
                listaRez = (List<Rezervacija>) toOdgovor2.getRezultat(); 
            } else {
                JOptionPane.showMessageDialog(fpp, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
                listaRez = new ArrayList<>();
            }                      
            
            for (Putovanje putovanje : listaPutovanja) {
                for (Rezervacija rezervacija : listaRez) {
                    for (StavkaRezervacije stavka : rezervacija.getListaStavki()) {
                        if(putovanje.getPutovanjeID() == stavka.getPutovanje().getPutovanjeID()){
                            //dodajem rezervacije za odredjeno putovanje
                            putovanje.getListaRezervacija().add(rezervacija);
                        }                        
                    }
                }
            }
      
            srediFormu(fpp);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fpp, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }          
    }

    public void prikaziPutovanje(FormaPretragaPutovanja fpp) {
        int red = fpp.getJtblPutovanja().getSelectedRow();
        if(red == -1){
            JOptionPane.showMessageDialog(fpp, "Odaberite red!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;       
        }
        
        FormaPrikazPutovanja formaPregled = new FormaPrikazPutovanja(null, false, listaPutovanja.get(red));
        formaPregled.setVisible(true);        
    }
    
    
}
