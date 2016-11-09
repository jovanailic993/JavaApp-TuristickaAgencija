/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.putnik;

import domen.Putnik;
import forme.putnik.FormaPretragaPutnika;
import forme.putnik.FormaUnosPutnika;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import modeli.PutnikTableModel;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerPretragaPutnika {
    List<Putnik> listaPutnika;
    String tipOperacije="";

    public KontrolerPretragaPutnika() {
        listaPutnika = new ArrayList<>();
    }

    public List<Putnik> getListaPutnika() {
        return listaPutnika;
    }

    public void setListaPutnika(List<Putnik> listaPutnika) {
        this.listaPutnika = listaPutnika;
    }

    public void setTipOperacije(String tipOperacije) {
        this.tipOperacije = tipOperacije;
    }
    
    public void srediFormu(FormaPretragaPutnika fpp) {
        PutnikTableModel ptm = new PutnikTableModel(listaPutnika);
        fpp.getJtblPutnici().setModel(ptm);    
        ptm.fireTableDataChanged();
    }
    
    public void nadjiPoKriterijumu(FormaPretragaPutnika fpp) {
        try {
            String kriterijum = fpp.getJtxtKriterijum().getText().trim();
            
            if (kriterijum.isEmpty()) {
                JOptionPane.showMessageDialog(fpp, "Unesite kriterijum pretrage", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Putnik p = new Putnik();
            p.setKriterijumZaPretragu(kriterijum);
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.NADJI_PUTNIKE);
            toZahtev.setParametar(p);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                listaPutnika = (List<Putnik>) toOdgovor.getRezultat();
            } else {
                JOptionPane.showMessageDialog(fpp, toOdgovor.getPoruka(), "Greška", JOptionPane.ERROR_MESSAGE);
                listaPutnika = new ArrayList<>();
            }           
                   
            srediFormu(fpp);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fpp, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
    }
    
    public void prikaziPutnika(FormaPretragaPutnika fpp) {
        int red = fpp.getJtblPutnici().getSelectedRow();
        if(red == -1){
            JOptionPane.showMessageDialog(fpp, "Odaberite red!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;       
        }
        
        FormaUnosPutnika forma = new FormaUnosPutnika(null, false, listaPutnika.get(red), tipOperacije);
        forma.setVisible(true);
    }   
    
}
