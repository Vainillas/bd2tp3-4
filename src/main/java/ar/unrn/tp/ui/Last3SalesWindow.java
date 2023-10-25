package ar.unrn.tp.ui;

import ar.unrn.tp.dto.VentaDTO;

import javax.swing.*;
import java.util.List;

public class Last3SalesWindow extends JList<VentaDTO> {
    private DefaultListModel<VentaDTO> model;
    public Last3SalesWindow() {
        this.model = new DefaultListModel<>();
        setModel(model);
        setBounds(24, 37, 385, 143);
    }

    public void actualizarLista(List<VentaDTO> ventasDTO) {
        model.clear();
        model.addAll(ventasDTO);
    }
    public void removeAllElements(){
        model.removeAllElements();
    }
    public void addElement(VentaDTO ventaDTO){
        model.addElement(ventaDTO);
    }
}
