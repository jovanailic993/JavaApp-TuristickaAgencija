/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Rezervacija;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class RezervacijeTableModel extends AbstractTableModel{
    List<Rezervacija> listaRez;
    private String[] kolone = new String[]{"ID", "Putnik", "Zaposleni"};

    public RezervacijeTableModel(List<Rezervacija> listaRez) {
        this.listaRez = listaRez;
    }
    
    @Override
    public int getRowCount() {
        if (listaRez == null) {
            return 0;
        }
        return listaRez.size();    
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rezervacija r = listaRez.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getRezervacijaID();
            case 1:
                return r.getPutnik();
            case 2:
                return r.getZaposleni();
            default:
                return "N/A";
        }    
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
}
