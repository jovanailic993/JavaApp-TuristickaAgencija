/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.OpstiDomenskiObjekat;
import domen.Zaposleni;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleZaposlenih extends AbstractTableModel{
    List<OpstiDomenskiObjekat> listaUlogovanih;
    private final String[] kolone = new String[]{"ID", "Korisničko ime"};

    public ModelTabeleZaposlenih() {
        this.listaUlogovanih = new ArrayList<>();
    }

    public ModelTabeleZaposlenih(List<OpstiDomenskiObjekat> listaUlogovanih) {
        this.listaUlogovanih = listaUlogovanih;
    }

    public List<OpstiDomenskiObjekat> getListaUlogovanih() {
        return listaUlogovanih;
    }
    
    public OpstiDomenskiObjekat vratiZaposlenog(int rowIndex) {
        return listaUlogovanih.get(rowIndex);
    }
    
    @Override
    public int getRowCount() {
       if (listaUlogovanih == null) {
            return 0;
        }
        return listaUlogovanih.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zaposleni z = (Zaposleni) listaUlogovanih.get(rowIndex);
        switch(columnIndex){
            case 0: return z.getZaposleniID();
            case 1: return z.getKorisnickoIme();
            default: return "n/a";   
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
    
    public void dodajZaposlenog(Zaposleni z){
        listaUlogovanih.add(z);
        fireTableDataChanged();
    }
    
    public void obrisiZaposlenog(Zaposleni z){
        listaUlogovanih.remove(z);
        fireTableDataChanged();
    }
}
