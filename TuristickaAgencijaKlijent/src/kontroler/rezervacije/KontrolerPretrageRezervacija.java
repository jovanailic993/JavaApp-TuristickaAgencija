/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.rezervacije;

import domen.Rezervacija;
import forme.rezervacije.FormaPretragaRezervacija;
import forme.rezervacije.FormaUnosRezervacije;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import modeli.RezervacijeTableModel;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerPretrageRezervacija {
    List<Rezervacija> listaRez;
    String tipOperacije="";

    public KontrolerPretrageRezervacija() {
        listaRez = new ArrayList<>();
    }

    public List<Rezervacija> getListaRez() {
        return listaRez;
    }

    public String getTipOperacije() {
        return tipOperacije;
    }    

    public void setListaRez(List<Rezervacija> listaRez) {
        this.listaRez = listaRez;
    }
    
    public void setTipOperacije(String tipOperacije) {
        this.tipOperacije = tipOperacije;
    }
    
    public void srediFormu(FormaPretragaRezervacija fpr) {
        RezervacijeTableModel model = new RezervacijeTableModel(listaRez);
        fpr.getJtblRezervacije().setModel(model);
        model.fireTableDataChanged();
    }
    
    public void nadjiPoKriterijumu(FormaPretragaRezervacija fpr) {
        try {
            String kriterijum = fpr.getJtxtKriterijum().getText().trim();
            
            if (kriterijum.isEmpty()) {
                JOptionPane.showMessageDialog(fpr, "Unesite kriterijum pretrage", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Rezervacija r = new Rezervacija();
            r.setKriterijumZaPretragu(kriterijum);
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.NADJI_REZERVACIJE);
            toZahtev.setParametar(r);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                listaRez = (List<Rezervacija>) toOdgovor.getRezultat(); 
            } else {
                JOptionPane.showMessageDialog(fpr, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
                listaRez = new ArrayList<>();
            }          

            srediFormu(fpr);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fpr, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
    }
    
    public void prikaziRezervacije(FormaPretragaRezervacija fpr) {
        int red = fpr.getJtblRezervacije().getSelectedRow();
        
        if(red == -1){
            JOptionPane.showMessageDialog(fpr, "Odaberite red!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;       
        }
        
        FormaUnosRezervacije forma = new FormaUnosRezervacije(null, false, listaRez.get(red), tipOperacije);
        forma.setVisible(true);
    }   
        
}
