package ar.unrn.tp.ui;

import ar.unrn.tp.api.*;
import ar.unrn.tp.dto.MarcaDTO;
import ar.unrn.tp.dto.ProductoDTO;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Promocion;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.List;

public class MainWindow {
    private VentaService ventaService;
    private ProductoService productoService;
    private MarcaService marcaService;
    private PromocionService promocionService;
    private ClienteService clienteService;
    private final Long idCliente;


    JFrame frame;

    public MainWindow(VentaService ventaService, ProductoService productoService, MarcaService marcaService, PromocionService promocionService, ClienteService clienteService, Long idCliente) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.marcaService = marcaService;
        this.promocionService = promocionService;
        this.clienteService = clienteService;
        this.idCliente = idCliente;
    }

    public void loadUp() {
        System.out.println("Cargando ventana principal");
        inicializarJFrame();
        frame.add(inicializarListaProductos());
        frame.add(inicializarJPanePromocionesActivas());
        clienteService.listarTarjetas(idCliente);



    }
    private void inicializarJFrame(){
        frame = new JFrame("Ventana principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    private JList<ProductoDTO> inicializarListaProductos() {

        List<ProductoDTO> productoDTOList = productoService.listarProductos().stream().map((producto -> new ProductoDTO(producto.getId(), producto.getCodigo(), producto.getDescripcion(), producto.getCategoria(), new MarcaDTO(producto.getMarca().getNombre()), producto.getPrecio()))).toList();

        JList<ProductoDTO> listaProductos = new ListaProductos(productoDTOList);
        listaProductos.setModel(new DefaultListModel<>());
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProductos.setLayoutOrientation(JList.VERTICAL);
        listaProductos.setVisibleRowCount(-1);
        listaProductos.setVisible(true);
        return listaProductos;
    }
    private JTextPane inicializarJPanePromocionesActivas() {
        JTextPane jTextPane = new JTextPane();
        jTextPane.setEditable(false);
        StyledDocument doc = jTextPane.getStyledDocument();

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        jTextPane.setCharacterAttributes(attributeSet, true);
        jTextPane.setText("Promociones Activas");
        try{
            for(Promocion promocion : promocionService.encontrarPromociones()){
                doc.insertString(doc.getLength(), promocion.toString(), attributeSet);
            }
        }catch (BadLocationException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar promociones activas", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return jTextPane;
    }


}
