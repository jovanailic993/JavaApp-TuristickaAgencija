/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler.rezervacije;

import domen.Putnik;
import domen.Putovanje;
import domen.Rezervacija;
import domen.StavkaRezervacije;
import domen.Zaposleni;
import forme.rezervacije.FormaUnosRezervacije;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import komunikacija.Komunikacija;
import modeli.StavkeRezTableModel;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class KontrolerStavkiRezervacija {
    private Rezervacija rezervacija;
    private String tipOperacije;
    StavkeRezTableModel model;

    public KontrolerStavkiRezervacija() {
        rezervacija = null;
        tipOperacije="";
    }     
//
    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public String getTipOperacije() {
        return tipOperacije;
    }

    public void setTipOperacije(String tipOperacije) {
        this.tipOperacije = tipOperacije;
    }    
    
//
    private void popuniCB(FormaUnosRezervacije fur) {
        List<Putnik> listaPutnika;
        List<Zaposleni> listaZaposlenih;
        try {            
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.VRATI_PUTNIKE);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }      
            
            listaPutnika = (List<Putnik>) toOdgovor.getRezultat();
            
            fur.getJcbPutnik().removeAllItems();
            for (Putnik p : listaPutnika) {
                fur.getJcbPutnik().addItem(p);
            }
            
            TransferObjekatZahtev toZahtev2 = new TransferObjekatZahtev();
            toZahtev2.setOperacija(Konstante.VRATI_ZAPOSLENE);
            Komunikacija.getInstance().posaljiZahtev(toZahtev2);
            TransferObjekatOdgovor toOdgovor2 = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor2.getStatus() == Konstante.STATUS_OK) {
            } else {
                throw new Exception(toOdgovor.getPoruka());
            } 
            
            listaZaposlenih = (List<Zaposleni>) toOdgovor2.getRezultat();
            
            fur.getJcbZaposleni().removeAllItems();
            for (Zaposleni z : listaZaposlenih) {
                fur.getJcbZaposleni().addItem(z);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, e.getMessage());
            e.printStackTrace();
        }        
    }
    
    private void postaviSifruRezervacije(FormaUnosRezervacije fur) {
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.VRATI_ID_REZERVACIJE);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();

            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            int idRez = (int) toOdgovor.getRezultat();             
            rezervacija = new Rezervacija();                 
            rezervacija.setRezervacijaID(++idRez);            
            fur.getJtxtID().setEditable(false);
            fur.getJtxtID().setText(String.valueOf(idRez));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fur, ex.getMessage());
            ex.printStackTrace();
        }
         
    }
    
    private void srediTabelu(FormaUnosRezervacije fur) {        
        if(rezervacija.getListaStavki() == null || rezervacija.getListaStavki().isEmpty()){
            //napravi listu stavki u rezervaciji
            rezervacija.setListaStavki(new ArrayList<>());  
            rezervacija.setListaStavkiSVE(new ArrayList<>()); //ova nam sluzi za konacno cuvanje, tu cuvam status stavki
        }
        //napravi model od liste bilo da je prazna ili ima elemenata
        model = new StavkeRezTableModel(rezervacija.getListaStavki());
        //postavi modelu tu rezervaciju
        model.setRezervacija(rezervacija);
        
        fur.getJtblStavke().setModel(model);
        ucitajPutovanja(fur);
        daLiJeAktivnaCB(fur);
        model.fireTableDataChanged();
    }
    
    public void ucitajPutovanja(FormaUnosRezervacije fur){
        //vrati putovanja za cb
        JComboBox putovanjaCB = new JComboBox();
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.VRATI_PUTOVANJA);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            List<Putovanje> listaPutovanja = (List<Putovanje>) toOdgovor.getRezultat();
            
            putovanjaCB.removeAllItems();
            for (Putovanje p : listaPutovanja) {
                putovanjaCB.addItem(p);
            }      
            TableColumnModel tcm = fur.getJtblStavke().getColumnModel();
            TableColumn kolonaPutovanje = tcm.getColumn(4);
            kolonaPutovanje.setCellEditor(new DefaultCellEditor(putovanjaCB));
            
            //datumi
            TableColumn dateColumn = tcm.getColumn(1);
            dateColumn.setCellEditor(new DatePickerCellEditor(new SimpleDateFormat("dd.MM.yyyy.")));
            TableColumn dateColumn2 = tcm.getColumn(3);
            dateColumn2.setCellEditor(new DatePickerCellEditor(new SimpleDateFormat("dd.MM.yyyy.")));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, e.getMessage(), "Greška kod učitavanja putovanja!", JOptionPane.ERROR_MESSAGE);
            return;
        }    
    }
    
    private void daLiJeAktivnaCB(FormaUnosRezervacije fur) {
        String[] a = {"da", "ne"};
        JComboBox<String> aktivnaCB = new JComboBox<>(a);
        TableColumnModel tcm = fur.getJtblStavke().getColumnModel();
        TableColumn kolona = tcm.getColumn(2);
        kolona.setCellEditor(new DefaultCellEditor(aktivnaCB));        
    }
        
    public void srediFormu(FormaUnosRezervacije fur) {
        popuniCB(fur);         
            
        if(rezervacija == null){
            postaviSifruRezervacije(fur); //inicijalizacija rezervacije !!!
            srediTabelu(fur); //napravi novi model tabele i setuj stavkama tu rezervaciju !!!
                    
            fur.getJbtnIzmeni().setVisible(false);
            fur.getJbtnSacuvaj().setVisible(true);
            fur.getJbtnObrisiRezervaciju().setVisible(false);
            fur.getJbtnDodajStavku().setVisible(true);
            fur.getJbtnObrisiStavku().setVisible(true);
            fur.setTitle("Unos rezervacije");              
                
        }
        else{
            fur.getJtxtID().setEditable(false);
            fur.getJtxtID().setText(" "+rezervacija.getRezervacijaID());
            
            fur.getJcbPutnik().setSelectedItem(rezervacija.getPutnik());
            fur.getJcbZaposleni().setSelectedItem(rezervacija.getZaposleni());
            
            srediTabelu(fur);
            
            if(tipOperacije.equals("update")){
                fur.getJbtnIzmeni().setVisible(true);
                fur.getJbtnSacuvaj().setVisible(false);
                fur.getJbtnObrisiRezervaciju().setVisible(false);
                fur.getJbtnDodajStavku().setVisible(true);
                fur.getJbtnObrisiStavku().setVisible(true);
                fur.setTitle("Izmena rezervacije");    
            }
            if(tipOperacije.equals("delete")){
                fur.getJbtnIzmeni().setVisible(false);
                fur.getJbtnSacuvaj().setVisible(false);
                fur.getJbtnObrisiRezervaciju().setVisible(true);
                fur.getJbtnDodajStavku().setVisible(false);
                fur.getJbtnObrisiStavku().setVisible(false);
                fur.setTitle("Brisanje rezervacije");                   
            }            
            if(tipOperacije.equals("view")){
                fur.getJbtnIzmeni().setVisible(false);
                fur.getJbtnSacuvaj().setVisible(false);
                fur.getJbtnObrisiRezervaciju().setVisible(false);
                fur.getJbtnDodajStavku().setVisible(false);
                fur.getJbtnObrisiStavku().setVisible(false);
                fur.setTitle("Pregled rezervacije");                    
            }
        }
        
    }  
    
    public void dodajStavku(FormaUnosRezervacije fur) {
        try {            
            //napravi novu stavku, setuj joj rezervaciju, dodaj stavku u listu stavki
            StavkaRezervacije novaStavka = new StavkaRezervacije();
            novaStavka.setRezervacija(rezervacija);
            novaStavka.setRedniBroj(0);
            //stavka koja ima rb 0 je nova, nije iz baze, zbog cuvanja
            novaStavka.setStatus("dodaj");
            //mora i status jer moze i da se doda nova pa obrise a u tom slucaju ce imati rb 0 a status obrisi i ta stavka nam ne treba u bazi!!!
            rezervacija.getListaStavki().add(novaStavka);
            rezervacija.getListaStavkiSVE().add(novaStavka);
            // sredi tabelu ce napraviti model sa novom listom stavki
            srediTabelu(fur);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, e.getMessage());
            e.printStackTrace();
        }        
    }

    public void obrisiStavku(FormaUnosRezervacije fur) {
        try {
            int red = fur.getJtblStavke().getSelectedRow();
            if (red == -1) {
                JOptionPane.showMessageDialog(fur, "Odaberite stavku.", "Greška!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            rezervacija.getListaStavkiSVE().get(red).setStatus("delete");
            rezervacija.getListaStavki().remove(red);
            srediTabelu(fur);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, e.getMessage());
            e.printStackTrace();
        }        
    }

    public void izmeniRezervaciju(FormaUnosRezervacije fur) {
        Putnik putnik = (Putnik) fur.getJcbPutnik().getSelectedItem();
        Zaposleni zaposleni = (Zaposleni) fur.getJcbZaposleni().getSelectedItem();
        rezervacija.setPutnik(putnik);
        rezervacija.setZaposleni(zaposleni);
        
        if(rezervacija.getListaStavki().isEmpty()){
            JOptionPane.showMessageDialog(fur, "Rezervacija mora imati bar jednu stavku!", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.IZMENI_REZERVACIJU);
            toZahtev.setParametar(rezervacija);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fur, toOdgovor.getPoruka());
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }            

            srediFormu(fur);
            rezervacija = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, "Rezervacija nije izmenjena!", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }         
    }

    public void sacuvajRezervaciju(FormaUnosRezervacije fur) {
        Putnik putnik = (Putnik) fur.getJcbPutnik().getSelectedItem();
        Zaposleni zaposleni = (Zaposleni) fur.getJcbZaposleni().getSelectedItem();
        rezervacija.setPutnik(putnik);
        rezervacija.setZaposleni(zaposleni);
        
        if(rezervacija.getListaStavki().isEmpty()){
            JOptionPane.showMessageDialog(fur, "Rezervacija mora imati bar jednu stavku!", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.SACUVAJ_REZERVACIJU);
            toZahtev.setParametar(rezervacija);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fur, toOdgovor.getPoruka());
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            rezervacija = null;
            srediFormu(fur);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, "Rezervacija nije sacuvana!", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }    
    }

    public void obrisiRezervaciju(FormaUnosRezervacije fur) {
        try {
            TransferObjekatZahtev toZahtev = new TransferObjekatZahtev();
            toZahtev.setOperacija(Konstante.OBRISI_REZERVACIJU);
            toZahtev.setParametar(rezervacija);
            Komunikacija.getInstance().posaljiZahtev(toZahtev);
            TransferObjekatOdgovor toOdgovor = Komunikacija.getInstance().primiOdgovor();
            
            if (toOdgovor.getStatus() == Konstante.STATUS_OK) {
                JOptionPane.showMessageDialog(fur, toOdgovor.getPoruka());
            } else {
                throw new Exception(toOdgovor.getPoruka());
            }
            
            rezervacija = null;
            tipOperacije = "";
            fur.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fur, "Rezervacija nije obrisana!", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }        
    }      
}
