package ar.unrn.tp.ui;

import ar.unrn.tp.dto.ProductoDTO;
import ar.unrn.tp.modelo.Producto;

import javax.swing.*;
import java.util.List;

public class ListaProductos extends JList<ProductoDTO> {
    private DefaultListModel<ProductoDTO> model;
    public ListaProductos(List<ProductoDTO> list) {
        this.model = new DefaultListModel<>();
        setModel(model);
        setBounds(24, 37, 385, 143);
        model.addAll(list);
    }

    public void actualizarLista(List<ProductoDTO> list) {
        model.clear();
        model.addAll(list);
    }
    public void removeAllElements(){
        model.removeAllElements();
    }
    public void addElement(ProductoDTO productoDTO){
        model.addElement(productoDTO);
    }



}
