/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Putovanje;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class PutovanjaTableModel extends AbstractTableModel{
    private List<Putovanje> listaPutovanja;
    private final String[] kolone = new String[]{"Naziv", "Polazište", "Odredište", "Datum od", "Datum do", "Cena"};
    
    public PutovanjaTableModel(List<Putovanje> listaPutovanja) {
        this.listaPutovanja = listaPutovanja;
    }

    public List<Putovanje> getListaPutovanja() {
        return listaPutovanja;
    }

    public void setListaPutovanja(List<Putovanje> listaPutovanja) {
        this.listaPutovanja = listaPutovanja;
    }

    public Putovanje getPutovanje(int red){
        return listaPutovanja.get(red);
    }

    @Override
    public int getRowCount() {
        if (listaPutovanja == null) {
            return 0;
        }
        return listaPutovanja.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putovanje p = listaPutovanja.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getNaziv();
            case 1:
                return p.getPolaziste();
            case 2:
                return p.getOdrediste();
            case 3:
                return p.getDatumOD();
            case 4:
                return p.getDatumDO();
            case 5:
                return p.getCena();
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
