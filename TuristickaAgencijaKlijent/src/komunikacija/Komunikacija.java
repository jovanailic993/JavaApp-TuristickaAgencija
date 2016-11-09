/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;

/**
 *
 * @author Jovana
 */
public class Komunikacija {
    private Socket socket;
    private static Komunikacija instance;
    private boolean zauzetPrijem = false;
    
    private Komunikacija() throws IOException {
        socket = new Socket("localhost", 9000);
    }
    
    public static Komunikacija getInstance() throws IOException {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }
    
    public boolean isZauzetPrijem() {
        return zauzetPrijem;
    }

    public void setZauzetPrijem(boolean zauzetPrijem) {
        this.zauzetPrijem = zauzetPrijem;
    }
    
    public void posaljiZahtev(TransferObjekatZahtev toZahtev) throws IOException, InterruptedException {
           if (zauzetPrijem) {
                sleep(1000);
            }
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(toZahtev);
    }
    
    public TransferObjekatOdgovor primiOdgovor(){ 
        zauzetPrijem = true;
        TransferObjekatOdgovor toOdgovor = new TransferObjekatOdgovor();        
        
        try {
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            toOdgovor = (TransferObjekatOdgovor) inSocket.readObject();
            zauzetPrijem = false;

        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toOdgovor;
    }
    
}
