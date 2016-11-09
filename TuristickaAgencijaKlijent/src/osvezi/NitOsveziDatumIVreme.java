/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osvezi;

import forme.glavne.FormaGlavna;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jovana
 */
public class NitOsveziDatumIVreme extends Thread{
    FormaGlavna fg;

    public NitOsveziDatumIVreme(FormaGlavna fg) {
        this.fg = fg;
    }    

    @Override
    public void run() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        while (true) {
            Date date = new Date();
            fg.getjLabelDatum().setText(df.format(date));
        }        
    }
    
}
