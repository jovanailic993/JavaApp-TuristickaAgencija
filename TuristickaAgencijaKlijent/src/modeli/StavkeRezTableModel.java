/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Putovanje;
import domen.Rezervacija;
import domen.StavkaRezervacije;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class StavkeRezTableModel extends AbstractTableModel{
    List<StavkaRezervacije> listaStavki;
    private String[] kolone = new String[]{"Datum rezervacije", "Datum važenja rezervacije", "Aktivna rezervacija", "Datum plaćanja", "Putovanje"};
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
    Rezervacija rezervacija;

    public StavkeRezTableModel() {
        listaStavki = new ArrayList<>();
    }

    public StavkeRezTableModel(List<StavkaRezervacije> listaStavki) {
        this.listaStavki = listaStavki;
    }   

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public List<StavkaRezervacije> getListaStavki() {
        return listaStavki;
    }   
    
    
    @Override
    public int getRowCount() {
        if (listaStavki == null) {
            return 0;
        }
        return listaStavki.size();    
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
        StavkaRezervacije s = listaStavki.get(rowIndex);
        switch (columnIndex) {
            case 0:
                if(s.getDatumRezervacije()==null){
                    s.setDatumRezervacije(new Date());
                }
                return sdf.format(s.getDatumRezervacije());
            case 1: 
                if(s.getDatumVazenjaRezervacije()==null){
                    return null;
                }
                return sdf.format(s.getDatumVazenjaRezervacije());
            case 2:                
                return s.getAktivnaRezervacija();
            case 3:
                if(s.getDatumPlacanja()==null){
                    return null;
                }                
                return sdf.format(s.getDatumPlacanja());
            case 4:
                return s.getPutovanje();
            default:
                return "N/A";
        }    
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0 || columnIndex == 2) {
            return false;
        }
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        StavkaRezervacije s = listaStavki.get(rowIndex);
        switch (columnIndex) {
            case 1: 
                Date datum2 =(Date)aValue;

                if(datum2.before(s.getDatumRezervacije()) || datum2.equals(s.getDatumRezervacije())){
                    JOptionPane.showMessageDialog(null, "Datum važenja rezervacije mora biti posle datuma rezervacije.");
                    return;
                }
                
                s.setDatumVazenjaRezervacije(datum2);
                
                if(s.getDatumVazenjaRezervacije().before(new Date())){
                    s.setAktivnaRezervacija("ne");
                }else{
                    s.setAktivnaRezervacija("da");
                }
                
                fireTableCellUpdated(rowIndex, columnIndex+1);
                break;             
            case 3:
                Date datum3 = (Date)aValue;

                if(datum3.before(s.getDatumRezervacije()) || datum3.after(s.getDatumVazenjaRezervacije())){
                    JOptionPane.showMessageDialog(null, "Datum plaćanja mora da bude između datuma rezervacije i datuma važenja rezervacije!"); 
                    return;
                }
                s.setDatumPlacanja(datum3);
                break;             
            case 4:
                s.setPutovanje((Putovanje) aValue);                
                if(s.getDatumRezervacije().after(s.getPutovanje().getDatumOD()) 
                || s.getDatumRezervacije().after(s.getPutovanje().getDatumDO()) 
                || s.getDatumVazenjaRezervacije().after(s.getPutovanje().getDatumDO())
                || s.getDatumVazenjaRezervacije().after(s.getPutovanje().getDatumOD())){
                    JOptionPane.showMessageDialog(null, "Datumi rezervacije moraju biti pre datuma putovanja! Izaberite drugo putovanje");
                    return;                    
                }
                break;
        }    
    }
       
}
