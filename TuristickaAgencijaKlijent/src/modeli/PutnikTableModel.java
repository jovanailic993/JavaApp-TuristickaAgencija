/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Putnik;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class PutnikTableModel extends AbstractTableModel{
    private List<Putnik> listaPutnika;
    private final String[] kolone = new String[]{"Ime", "Prezime", "JMBG", "Kontakt", "Email", "Ulica", "Broj", "Mesto"};

    public PutnikTableModel() {
        listaPutnika = new ArrayList<>();
    }
    
    public PutnikTableModel(List<Putnik> listaPutnika) {
        this.listaPutnika = listaPutnika;
    }

    public List<Putnik> getListaPutnika() {
        return listaPutnika;
    }
    
    public Putnik getPutnik(int red) {
        return listaPutnika.get(red);
    }    
    
    @Override
    public int getRowCount() {
        if (listaPutnika == null) {
            return 0;
        }
        return listaPutnika.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putnik p = listaPutnika.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getIme();
            case 1:
                return p.getPrezime();
            case 2:
                return p.getJmbg();
            case 3:
                return p.getKontakt();
            case 4:
                return p.getEmail();
            case 5:
                return p.getUlica();
            case 6:
                return p.getBroj();
            case 7:
                return p.getMesto();
            default:
                return "n/a";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {        
        return false;        
    } 
    

    
    
}
