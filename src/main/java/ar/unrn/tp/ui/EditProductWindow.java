package ar.unrn.tp.ui;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.dto.MarcaDTO;
import ar.unrn.tp.dto.ProductoDTO;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import jakarta.persistence.OptimisticLockException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProductWindow extends JFrame {
    private ProductoService productoService;
    private MarcaService marcaService;
    private JPanel contentPane;
    private JTextField nombreTextField, precioTextField, marcaTextField;
    private JComboBox<Categoria> categoriaComboBox;
    private JLabel idLabel;
    private ProductoDTO productoDTO;

    public EditProductWindow(ProductoService productoService, MarcaService marcaService) {
        this.productoService = productoService;
        this.marcaService = marcaService;
    }
    public void loadUp(){
        productoDTO = productoService.encontrarProducto(1L).getDTO();
        initJFrame();
    }

    public void initJFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Formulario de Producto");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setBounds(100, 100, 450, 300);

        initPanel();
        setVisible(true);
    }
    public void initPanel(){
        // Crear un panel
        contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etiqueta para ID
        JLabel id = new JLabel("ID del Producto:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(id,gbc);
        idLabel = new JLabel(String.valueOf(productoDTO.id()));
        idLabel.setForeground(Color.BLUE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(idLabel,gbc);

        // Etiqueta y campo de texto para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(nombreLabel, gbc);
        nombreTextField = new JTextField(20);
        nombreTextField.setText(productoDTO.descripcion());
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(nombreTextField,gbc);

        // Etiqueta y campo de texto para el precio
        JLabel precioLabel = new JLabel("Precio:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(precioLabel,gbc);

        precioTextField = new JTextField(20);
        precioTextField.setText(String.valueOf(productoDTO.precio()));
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(precioTextField,gbc);
        // Etiqueta y campo de texto para la marca
        JLabel marcaLabel = new JLabel("Marca:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(marcaLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        marcaTextField = new JTextField(20);
        marcaTextField.setText(productoDTO.marca().nombre());
        contentPane.add(marcaTextField,gbc);
        // Etiqueta y combo para la categoría
        JLabel categoriaLabel = new JLabel("Categoría:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        Categoria[] categorias = Categoria.values();
        categoriaComboBox = new JComboBox<>(categorias);
        contentPane.add(categoriaLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPane.add(categoriaComboBox,gbc);






        // Botón Guardar
        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    productoService.modificarProducto(1L, nombreTextField.getText(), (Categoria) categoriaComboBox.getSelectedItem(), marcaTextField.getText(), Double.parseDouble(precioTextField.getText()), productoDTO.version());
                    JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                }
        });
        contentPane.add(guardarButton);
        add(contentPane);
    }

}
