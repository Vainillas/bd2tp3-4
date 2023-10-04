package ar.unrn.tp.ui;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.dto.MarcaDTO;
import ar.unrn.tp.dto.ProductoDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditProductWindow extends JFrame {
    private ProductoService productoService;
    private JPanel contentPane;
    private JList<ProductoDTO> listaProductos;

    public EditProductWindow(ProductoService productoService) {
        this.productoService = productoService;
        initJFrame();
    }

    public void initJFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);
    }

}
