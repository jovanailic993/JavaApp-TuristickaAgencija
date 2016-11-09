/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import domen.Mesto;
import domen.OpstiDomenskiObjekat;
import domen.Putnik;
import domen.Putovanje;
import domen.Rezervacija;
import domen.Zaposleni;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.ModelTabeleZaposlenih;
import poslovnalogika.Kontroler;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Jovana
 */
public class NitKlijent extends Thread {
    private Socket socket;
    List<NitKlijent> klijenti;
    private boolean aktivan;
    Zaposleni zaposleni;

    public NitKlijent(Socket socket, List<NitKlijent> klijenti) {
        this.socket = socket;
        this.klijenti = klijenti;
        this.aktivan = true;
        this.zaposleni = new Zaposleni();
    }

    @Override
    public void run() {
        try {
            izvrsenjeOperacija();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Nit je zavrsila sa radom.");
    }
    
    private void izvrsenjeOperacija() throws IOException, ClassNotFoundException {
        while (aktivan) {
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            TransferObjekatZahtev toZahtev = (TransferObjekatZahtev) inSocket.readObject();
            TransferObjekatOdgovor toOdgovor = new TransferObjekatOdgovor();
            try {
                switch (toZahtev.getOperacija()) {
                    case Konstante.KRAJ:
                        try {
                            List<Zaposleni> listaZap = (List<Zaposleni>) Kontroler.getInstance().pretrazi(zaposleni);
                            if (listaZap.isEmpty()) {
                                toOdgovor.setKrajRada(false);
                            } else {
                                toOdgovor.setKrajRada(true);
                                aktivan = false;
                            }
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception e) {
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }
                        break;
                    case Konstante.LOGIN:
                        List<Zaposleni> listaZap;
                        try {
                            zaposleni = (Zaposleni) toZahtev.getParametar();
                            listaZap = (List<Zaposleni>) Kontroler.getInstance().pretrazi(zaposleni);
                        
                            if (listaZap.isEmpty()) {
                                throw new Exception();
                            }      
                            
                            Kontroler.getInstance().izmeni((OpstiDomenskiObjekat) zaposleni);
                            zaposleni.setUlogovan(true);   
                            int id = Kontroler.getInstance().vratiIDZaposlenog((OpstiDomenskiObjekat) toZahtev.getParametar());
                            zaposleni.setZaposleniID(id);
                            ((ModelTabeleZaposlenih) Kontroler.getInstance().getSf().getJtblZaposleni().getModel()).dodajZaposlenog(zaposleni);
                            toOdgovor.setRezultat(zaposleni);
                            toOdgovor.setPoruka("Zaposleni "+zaposleni.getKorisnickoIme().toUpperCase()+" je uspešno ulogovan/a!");
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception e) {
                            toOdgovor.setPoruka("Zaposleni ne postoji ili je već ulogovan!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                           
                        break;
                    case Konstante.LOGOUT:   
                        try {
                            Kontroler.getInstance().izmeni((OpstiDomenskiObjekat) zaposleni);    
                            ((ModelTabeleZaposlenih) Kontroler.getInstance().getSf().getJtblZaposleni().getModel()).obrisiZaposlenog(zaposleni);
                            toOdgovor.setPoruka("Doviđenja!");
                            aktivan = false;
                            break;
                        } catch (Exception e) {
                            toOdgovor.setPoruka("Neuspesan logout!");
                        }                                           
                    case Konstante.VRATI_MESTA:
                        try {
                            List<OpstiDomenskiObjekat> listaMesta = Kontroler.getInstance().vrati(new Mesto());
                            toOdgovor.setRezultat(listaMesta);
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da pronadje mesta!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }
                        break;
                    case Konstante.VRATI_PUTNIKE:
                        try {
                            toOdgovor.setRezultat(Kontroler.getInstance().vrati(new Putnik()));
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da pronadje putnike!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.VRATI_PUTOVANJA:
                        try {
                            toOdgovor.setRezultat(Kontroler.getInstance().vrati(new Putovanje()));
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da pronadje putovanja!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                         
                         break;
                    case Konstante.VRATI_ZAPOSLENE:
                        try {
                            toOdgovor.setRezultat(Kontroler.getInstance().vrati(new Zaposleni()));
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da pronadje zaposlene!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.VRATI_ID_REZERVACIJE:
                        try {
                            toOdgovor.setRezultat(Kontroler.getInstance().vratiIDRezervacije(new Rezervacija()));
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da pronadje id!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.SACUVAJ_PUTNIKA:
                        try {
                            Kontroler.getInstance().sacuvaj((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je sačuvao putnika!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da sačuva putnika!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.SACUVAJ_REZERVACIJU:
                        try {
                            Kontroler.getInstance().sacuvajRezervaciju((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je sačuvao rezervaciju!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da sačuva rezervaciju!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                          
                        break;
                    case Konstante.IZMENI_PUTNIKA:
                        try {
                            Kontroler.getInstance().izmeni((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je izmenio putnika!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da sačuva putnika!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                         
                        break;
                    case Konstante.IZMENI_REZERVACIJU:
                        try{
                            Kontroler.getInstance().izmeniRezervaciju((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je izmenio rezervaciju!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da promeni rezervaciju!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.OBRISI_PUTNIKA:
                        try {
                            Kontroler.getInstance().obrisi((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je obrisao putnika!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da obriše putnika!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                          
                        break;
                    case Konstante.OBRISI_REZERVACIJU:
                        try {
                            Kontroler.getInstance().obrisiRezervaciju((OpstiDomenskiObjekat) toZahtev.getParametar());
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                            toOdgovor.setPoruka("Sistem je obrisao rezervaciju!");
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da obriše rezervaciju!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                         
                        break;
                    case Konstante.NADJI_PUTNIKE:
                        try{
                            toOdgovor.setRezultat(Kontroler.getInstance().pretrazi((OpstiDomenskiObjekat) toZahtev.getParametar()));
                            List<OpstiDomenskiObjekat> lista = (List<OpstiDomenskiObjekat>) toOdgovor.getRezultat();
                            if (lista.isEmpty()) {
                                throw new Exception();
                            }                            
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da nađe putnike za dati kriterijum!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.NADJI_PUTOVANJA:
                        try{
                            toOdgovor.setRezultat(Kontroler.getInstance().pretrazi((OpstiDomenskiObjekat) toZahtev.getParametar()));;
                            if (((List<OpstiDomenskiObjekat>) toOdgovor.getRezultat()).isEmpty()) {
                                throw new Exception();
                            }                            
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da nađe putovanja za dati kriterijum!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                        
                        break;
                    case Konstante.NADJI_REZERVACIJE:
                        try{
                            toOdgovor.setRezultat(Kontroler.getInstance().pretrazi((OpstiDomenskiObjekat) toZahtev.getParametar()));;
                            if (((List<OpstiDomenskiObjekat>) toOdgovor.getRezultat()).isEmpty()) {
                                throw new Exception();
                            }                            
                            toOdgovor.setStatus(Konstante.STATUS_OK);
                        } catch (Exception ex) {
                            toOdgovor.setPoruka("Sistem nije uspeo da nađe rezervacije za dati kriterijum!");
                            toOdgovor.setStatus(Konstante.STATUS_NOK);
                        }                         
                        break;

                }
            } catch (Exception ex) {
                toOdgovor.setPoruka(ex.getMessage());
            }
            posaljiOdgovor(toOdgovor);
        }
    }

    private void posaljiOdgovor(TransferObjekatOdgovor toOdgovor) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(toOdgovor);
    }
    
}
