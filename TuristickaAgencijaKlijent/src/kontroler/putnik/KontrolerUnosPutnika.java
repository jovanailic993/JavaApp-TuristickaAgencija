/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.putnik;

import domen.Mesto;
import domen.Putnik;
import forme.putnik.FormaUnosPutnika;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerUnosPutnika {
    Putnik putnik;
    String tipOperacije;

    public KontrolerUnosPutnika() {
        putnik = null;
        tipOperacije = "";
    }

    public void setPutnik(Putnik putnik) {
        this.putnik = putnik;
    }

    public Putnik getPutnik() {
        return putnik;
    }

    public String getTipOperacije() {
        return tipOperacije;
    }

    public void setTipOperacije(String tipOperacije) {
        this.tipOperacije = tipOperacije;
    }
    
//    
    
    public void srediFormu(FormaUnosPutnika formaUnosPutnika) {
        List<Mesto> lm;
        try {            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.VRATI_MESTA);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            lm = (List<Mesto>) toOdgovor.getRezultat();
            formaUnosPutnika.getJcbMesto().removeAllItems();
            for (Mesto m : lm) {
                formaUnosPutnika.getJcbMesto().addItem(m);                
            }
            
            if(putnik == null){
                formaUnosPutnika.getJbtnIzmeniPutnika().setVisible(false);
                formaUnosPutnika.getJbtnObrisi().setVisible(false);
                formaUnosPutnika.getJbtnSacuvajPutnika().setVisible(true);
                formaUnosPutnika.setTitle("Unos putnika");   
                
                formaUnosPutnika.getjLblID().setVisible(false);
                formaUnosPutnika.getJtxtIDPutnika().setVisible(false);
                formaUnosPutnika.getJtxtIme().setText("");
                formaUnosPutnika.getJtxtPrezime().setText("");
                formaUnosPutnika.getJtxtJMBG().setText("");
                formaUnosPutnika.getJtxtKontakt().setText("");
                formaUnosPutnika.getJtxtEmail().setText("");
                formaUnosPutnika.getJtxtUlica().setText("");
                formaUnosPutnika.getJtxtBroj().setText("");
            }
            else {
                if (tipOperacije.equals("update")){
                    formaUnosPutnika.getJbtnIzmeniPutnika().setVisible(true);
                    formaUnosPutnika.getJbtnSacuvajPutnika().setVisible(false);                
                    formaUnosPutnika.getJbtnObrisi().setVisible(false); 
                    formaUnosPutnika.setTitle("Izmena putnika");
                    
                    formaUnosPutnika.getjLblID().setVisible(true);
                    formaUnosPutnika.getJtxtIDPutnika().setVisible(true);
                    formaUnosPutnika.getJtxtIDPutnika().setEditable(false);
                    formaUnosPutnika.getJtxtIDPutnika().setText(String.valueOf(putnik.getPutnikID()));                    
                    formaUnosPutnika.getJtxtIme().setText(putnik.getIme());
                    formaUnosPutnika.getJtxtPrezime().setText(String.valueOf(putnik.getPrezime()));
                    formaUnosPutnika.getJtxtJMBG().setText(String.valueOf(putnik.getJmbg()));
                    formaUnosPutnika.getJtxtKontakt().setText(String.valueOf(putnik.getKontakt()));
                    formaUnosPutnika.getJtxtEmail().setText(String.valueOf(putnik.getEmail()));
                    formaUnosPutnika.getJtxtUlica().setText(putnik.getUlica());
                    formaUnosPutnika.getJtxtBroj().setText(String.valueOf(putnik.getBroj()));
                    formaUnosPutnika.getJcbMesto().setSelectedItem(putnik.getMesto());
                }
                if(tipOperacije.equals("delete")){
                    formaUnosPutnika.getJbtnIzmeniPutnika().setVisible(false);
                    formaUnosPutnika.getJbtnSacuvajPutnika().setVisible(false);                
                    formaUnosPutnika.getJbtnObrisi().setVisible(true); 
                    formaUnosPutnika.setTitle("Brisanje putnika");
                    
                    srediPrikaz(formaUnosPutnika);
                    
                }    
                if(tipOperacije.equals("view")){
                    formaUnosPutnika.getJbtnIzmeniPutnika().setVisible(false);
                    formaUnosPutnika.getJbtnSacuvajPutnika().setVisible(false);                
                    formaUnosPutnika.getJbtnObrisi().setVisible(false); 
                    formaUnosPutnika.setTitle("Pregled putnika");
                    
                    srediPrikaz(formaUnosPutnika);
                }    
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(formaUnosPutnika, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
    }
    
    private void srediPrikaz(FormaUnosPutnika formaUnosPutnika) {
        formaUnosPutnika.getjLblID().setVisible(true);
        formaUnosPutnika.getJtxtIDPutnika().setVisible(true);
        formaUnosPutnika.getJtxtIDPutnika().setEditable(false);
        formaUnosPutnika.getJtxtIDPutnika().setText(String.valueOf(putnik.getPutnikID()));
        formaUnosPutnika.getJtxtIme().setText(putnik.getIme());
        formaUnosPutnika.getJtxtPrezime().setText(String.valueOf(putnik.getPrezime()));
        formaUnosPutnika.getJtxtJMBG().setText(String.valueOf(putnik.getJmbg()));
        formaUnosPutnika.getJtxtKontakt().setText(String.valueOf(putnik.getKontakt()));
        formaUnosPutnika.getJtxtEmail().setText(String.valueOf(putnik.getEmail()));
        formaUnosPutnika.getJtxtUlica().setText(putnik.getUlica());
        formaUnosPutnika.getJtxtBroj().setText(String.valueOf(putnik.getBroj()));
        formaUnosPutnika.getJcbMesto().setSelectedItem(putnik.getMesto());    

        formaUnosPutnika.getJtxtIme().setEditable(false);
        formaUnosPutnika.getJtxtPrezime().setEditable(false);
        formaUnosPutnika.getJtxtJMBG().setEditable(false);
        formaUnosPutnika.getJtxtKontakt().setEditable(false);
        formaUnosPutnika.getJtxtEmail().setEditable(false);
        formaUnosPutnika.getJtxtUlica().setEditable(false);
        formaUnosPutnika.getJtxtBroj().setEditable(false);
        formaUnosPutnika.getJcbMesto().setEditable(false);
    }
    
    public boolean validacija(FormaUnosPutnika fup){
        boolean validno = true;
        
        String ime = fup.getJtxtIme().getText().trim();
        String prezime = fup.getJtxtPrezime().getText().trim();
        String jmbg = fup.getJtxtJMBG().getText().trim();
        String kontakt = fup.getJtxtKontakt().getText().trim();
        String email = fup.getJtxtEmail().getText().trim();
        String ulica = fup.getJtxtUlica().getText().trim();
            
        if("".equals(ime) || "".equals(prezime) || "".equals(jmbg) || "".equals(kontakt) || 
            "".equals(email) || "".equals(ulica) || "".equals(fup.getJtxtBroj().getText().trim())){
            JOptionPane.showMessageDialog(fup, "Sva polja su obavezna!");
            validno = false;
            return validno;
        }
            
        int broj = Integer.parseInt(fup.getJtxtBroj().getText().trim());
        Mesto mesto = (Mesto) fup.getJcbMesto().getSelectedItem();
        
        if(jmbg.length() != 13){
            JOptionPane.showMessageDialog(fup, "Unesite ispravan jmbg!", "Greška!", JOptionPane.ERROR_MESSAGE);
            validno = false;
            return validno;
        }
        
        if(!email.contains("@")){
            JOptionPane.showMessageDialog(fup, "Unesite ispravan email!", "Greška!", JOptionPane.ERROR_MESSAGE);
            validno = false;
            return validno;           
        }       
        return validno;
    
    }

    public void sacuvajPutnika(FormaUnosPutnika fup) {
        try {
            boolean validno = validacija(fup);
            
            if(!validno){
                return;
            }
            
            putnik = new Putnik(-1, fup.getJtxtIme().getText().trim(), fup.getJtxtPrezime().getText().trim(), fup.getJtxtJMBG().getText().trim(), fup.getJtxtKontakt().getText().trim(), fup.getJtxtEmail().getText().trim(), fup.getJtxtUlica().getText().trim(), Integer.parseInt(fup.getJtxtBroj().getText().trim()), (Mesto) fup.getJcbMesto().getSelectedItem());    
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.SACUVAJ_PUTNIKA);
            toZahtev.setParametar(putnik);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fup, toOdgovor.getPoruka());
            } else {
                JOptionPane.showMessageDialog(fup, toOdgovor.getPoruka(), "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            putnik = null;
            srediFormu(fup);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fup, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void izmeniPutnika(FormaUnosPutnika fup) {
        try {
            boolean validno = validacija(fup);
            
            if(!validno){
                return;
            }
            
            putnik = new Putnik(Integer.parseInt(fup.getJtxtIDPutnika().getText()), fup.getJtxtIme().getText().trim(), fup.getJtxtPrezime().getText().trim(), fup.getJtxtJMBG().getText().trim(), fup.getJtxtKontakt().getText().trim(), fup.getJtxtEmail().getText().trim(), fup.getJtxtUlica().getText().trim(), Integer.parseInt(fup.getJtxtBroj().getText().trim()), (Mesto) fup.getJcbMesto().getSelectedItem());    
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.IZMENI_PUTNIKA);
            toZahtev.setParametar(putnik);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();

            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fup, toOdgovor.getPoruka());
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            srediFormu(fup);
            putnik = null;
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fup, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void obrisiPutnika(FormaUnosPutnika fup) {
        try {            
            putnik = new Putnik(Integer.parseInt(fup.getJtxtIDPutnika().getText()), fup.getJtxtIme().getText().trim(), fup.getJtxtPrezime().getText().trim(), fup.getJtxtJMBG().getText().trim(), fup.getJtxtKontakt().getText().trim(), fup.getJtxtEmail().getText().trim(), fup.getJtxtUlica().getText().trim(), Integer.parseInt(fup.getJtxtBroj().getText().trim()), (Mesto) fup.getJcbMesto().getSelectedItem());    
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.OBRISI_PUTNIKA);
            toZahtev.setParametar(putnik);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();

            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fup, toOdgovor.getPoruka());
            } else {
                JOptionPane.showMessageDialog(fup, toOdgovor.getPoruka(), "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }            
            
            putnik = null;
            fup.dispose();
                        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fup, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
}
