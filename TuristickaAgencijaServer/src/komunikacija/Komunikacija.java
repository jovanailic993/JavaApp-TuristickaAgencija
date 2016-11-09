/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jovana
 */
public class Komunikacija extends Thread{
    private Socket socket;
    private boolean kraj = false;
    
    public void run() {
        List<NitKlijent> listaKlijenti = new ArrayList<>();
        try {
            Socket socket;
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Server je pokrenut!");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Klijent se povezao!");

                NitKlijent nitKlijenta = new NitKlijent(socket, listaKlijenti);
                listaKlijenti.add(nitKlijenta);
                nitKlijenta.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

}
