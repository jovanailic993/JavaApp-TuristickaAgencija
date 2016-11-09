/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.putovanja;

import domen.Putovanje;
import forme.putovanja.FormaPrikazPutovanja;
import forme.rezervacije.FormaPretragaRezervacija;
import forme.rezervacije.FormaUnosRezervacije;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modeli.RezervacijeTableModel;

/**
 *
 * @author Jovana
 */
public class KontrolerPrikazaPutovanja {
    Putovanje putovanje;

    public KontrolerPrikazaPutovanja() {
    }
    
    public KontrolerPrikazaPutovanja(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void srediFormu(FormaPrikazPutovanja fpp) {
        fpp.getJtxtNaziv().setText(putovanje.getNaziv());
        fpp.getjLabelNaziv().setText(putovanje.getNaziv());
        fpp.getJtxtPolaziste().setText(putovanje.getPolaziste());
        fpp.getJtxtOdrediste().setText(putovanje.getOdrediste());
        fpp.getJtxtDatumOd().setText(new SimpleDateFormat("dd.MM.yyyy.").format(putovanje.getDatumOD()));
        fpp.getJtxtDatumDo().setText(new SimpleDateFormat("dd.MM.yyyy.").format(putovanje.getDatumDO()));   
        fpp.getJtxtCena().setText(String.valueOf(putovanje.getCena()));
        
        fpp.getJtxtNaziv().setEditable(false);
        fpp.getJtxtPolaziste().setEditable(false);
        fpp.getJtxtOdrediste().setEditable(false);
        fpp.getJtxtDatumOd().setEditable(false);
        fpp.getJtxtDatumDo().setEditable(false);
        fpp.getJtxtCena().setEditable(false);

        RezervacijeTableModel model = new RezervacijeTableModel(putovanje.getListaRezervacija());
        fpp.getJtblRezervacije().setModel(model);
        
    }    
    
    public void prikaziRezervaciju(FormaPrikazPutovanja fpp) {
        int red = fpp.getJtblRezervacije().getSelectedRow();
        
        if(red == -1){
            JOptionPane.showMessageDialog(fpp, "Odaberite red!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;       
        }
        
        FormaUnosRezervacije forma = new FormaUnosRezervacije(null, false, putovanje.getListaRezervacija().get(red), "view");
        forma.setVisible(true);
    } 
}
