/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.zaposleni;

import domen.Zaposleni;
import forme.glavne.FormaGlavna;
import forme.glavne.LogovanjeZaposlenog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import komunikacija.Komunikacija;
import nit.NitKraj;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerZaposleni {
    private static KontrolerZaposleni instance;
    Zaposleni zaposleni;
    FormaGlavna glavnaForma;
    private NitKraj nitKrajRada;
    
    private KontrolerZaposleni() {
        zaposleni = null;
    }
    
    public static KontrolerZaposleni getInstance(){
        if(instance == null){
            instance = new KontrolerZaposleni();
        }
        return instance;
    }
    
    public void pokreniNitKrajRada(FormaGlavna glavnaForma) {
        nitKrajRada = new NitKraj(glavnaForma);
        nitKrajRada.start();
    }

    public void zaustaviNitKrajRada() {
        nitKrajRada.stop();
    }
    
    public void Login(LogovanjeZaposlenog lz, JTextField jtxtIme, JPasswordField jtxtPass) {
        
        try {
            char[] password = jtxtPass.getPassword();
            String sPassword = "";
            for (char i : password) {
                sPassword += i;
            }
            
            if("".equals(jtxtIme.getText().trim()) && sPassword.isEmpty()){
                JOptionPane.showMessageDialog(lz, "Unesite ime i lozinku!", "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if("".equals(jtxtIme.getText().trim())){
                JOptionPane.showMessageDialog(lz, "Unesite ime!", "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if (sPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Unesite lozinku!", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                if (sPassword.length() > 40) {
                    JOptionPane.showMessageDialog(null, "Uneli ste predugačku lozinku!", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }            
            }
            
            String ime = jtxtIme.getText().trim();

            Zaposleni zaposleni = new Zaposleni(0, ime, sPassword, false);
            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.LOGIN);
            toZahtev.setParametar(zaposleni);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();

            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {                
                this.zaposleni = (Zaposleni) toOdgovor.getRezultat();
            } else {
                JOptionPane.showMessageDialog(lz, toOdgovor.getPoruka(), "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(lz, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lz.dispose();

        glavnaForma = new FormaGlavna();
        glavnaForma.setVisible(true);
        ispisiZaposlenog(glavnaForma.getjLabelZaposleni());
        pokreniNitKrajRada(glavnaForma);
    }
    
    public void Logout() {        
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.LOGOUT);
            toZahtev.setParametar(zaposleni);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            JOptionPane.showMessageDialog(glavnaForma, toOdgovor.getPoruka());
            zaposleni = null;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(glavnaForma, ex.getMessage(), "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (InterruptedException ex) {
            Logger.getLogger(KontrolerZaposleni.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void ispisiZaposlenog(JLabel jLabelZaposleni) {
        jLabelZaposleni.setText("Korisnik: "+zaposleni.getKorisnickoIme());
    }
}
