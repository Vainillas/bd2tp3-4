package ar.unrn.tp.ui;

import ar.unrn.tp.api.*;
import ar.unrn.tp.dto.*;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.ui.exceptions.GUIException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame {
    private VentaService ventaService;
    private ProductoService productoService;
    private MarcaService marcaService;
    private PromocionService promocionService;
    private ClienteService clienteService;
    private final Long idCliente;


    JFrame frame;
    ListaProductos listProductosWindow;
    Last3SalesWindow last3SalesWindow;
    CarritoUI carritoWindow;
    JLabel listaDeProductosNewLabel;
    DefaultListModel<ProductoDTO> listaProductosSeleccionados = new DefaultListModel<>();
    JPanel contentPane;
    JButton agregarAlCarritoNewButton;
    JButton mostrarUltimas3Ventas;
    JButton listarProductosNewButton;
    JButton irAlCarritoNewButton;
    JTextPane textPanePromociones;

    JFrame frame3Sales;
    //TODO: refactor de las ventanas para hacer el tamaño ajustable

    public MainWindow(VentaService ventaService, ProductoService productoService, MarcaService marcaService, PromocionService promocionService, ClienteService clienteService, Long idCliente) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.marcaService = marcaService;
        this.promocionService = promocionService;
        this.clienteService = clienteService;
        this.idCliente = idCliente;
    }

    public void loadUp() throws GUIException {
        System.out.println("Cargando ventana principal");
        inicializarJFrame();
        inicializarListaProductos();
        inicializarJLabelTitulo();
        inicializarJButtonAgregarAlCarrito();
        inicializarJButtonListarProductos();
        inicializarJButtonIrAlCarrito();
        inicializarJPanePromocionesActivas();
        inicializarJButton3Ventas();
        contentPane.add(listProductosWindow);
        contentPane.add(mostrarUltimas3Ventas);
        contentPane.add(listaDeProductosNewLabel);
        contentPane.add(agregarAlCarritoNewButton);
        contentPane.add(listarProductosNewButton);
        contentPane.add(textPanePromociones);
        contentPane.add(irAlCarritoNewButton);

    }

    private void inicializarLast3SalesWindow() {
        last3SalesWindow = new Last3SalesWindow();
        last3SalesWindow.setBounds(24, 37, 385, 143);
    }
    private void initFrame3Sales(){
        JFrame frame3Sales = new JFrame();
        frame3Sales.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3Sales.setBounds(100, 100, 500, 350);
        JPanel contentPane3Sales = new JPanel();
        contentPane3Sales.setBorder(new EmptyBorder(5, 5, 5, 5));
        inicializarLast3SalesWindow();
        contentPane3Sales.add(last3SalesWindow);
        frame3Sales.setContentPane(contentPane3Sales);
        frame3Sales.setVisible(true);

    }
    private void inicializarJButton3Ventas(){
        mostrarUltimas3Ventas = new JButton("Mostrar ultimas 3 ventas");

        mostrarUltimas3Ventas.setBounds(222, 225, 187, 23);
        mostrarUltimas3Ventas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initFrame3Sales();
                last3SalesWindow.actualizarLista(ventaService.ultimas3Ventas(idCliente).stream().map(venta -> new VentaDTO( venta.getId(), venta.getNumero(), venta.getFechaHora(), venta.getCliente().getNombre() + " " +venta.getCliente().getApellido(), venta.getListaProductos().size(), venta.getMontoTotal())).toList());
                last3SalesWindow.setVisible(true);
            }
        });
    }

    private void inicializarJButtonIrAlCarrito() {
        irAlCarritoNewButton = new JButton("Ir al Carrito");
        irAlCarritoNewButton.setBounds(24, 225, 188, 23);
        irAlCarritoNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CarritoUI carritoWindow = new CarritoUI(promocionService, productoService, ventaService, clienteService, 1L, listaProductosSeleccionados);
                carritoWindow.setVisible(true);
            }
        });


    }

    private void inicializarJButtonListarProductos() {
        listarProductosNewButton = new JButton("Listar productos");
        listarProductosNewButton.setBounds(24, 191, 188, 23);

        listarProductosNewButton.addActionListener(e -> {
            listProductosWindow.removeAllElements();
            productoService.listarProductos().stream().map(
                    producto -> new ProductoDTO(producto.getId(), producto.getCodigo(), producto.getDescripcion(), producto.getCategoria(), new MarcaDTO(producto.getMarca().getNombre()), producto.getPrecio(), producto.getVersion()))
                    .forEach(productoDTO -> listProductosWindow.addElement(productoDTO));
        });
    }

    private void inicializarJButtonAgregarAlCarrito() {
        agregarAlCarritoNewButton = new JButton("Agregar al Carrito");
        agregarAlCarritoNewButton.setBounds(222, 191, 187, 23);
        agregarAlCarritoNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] index = listProductosWindow.getSelectedIndices();
                if (index.length == 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int j : index) {
                    listaProductosSeleccionados.addElement(listProductosWindow.getModel().getElementAt(j));
                }
            }
        });
    }

    private void inicializarJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);
    }

    private void inicializarJLabelTitulo() {
        listaDeProductosNewLabel = new JLabel("Lista de Productos: ");
        listaDeProductosNewLabel.setForeground(new Color(0, 0, 0));
        listaDeProductosNewLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 14));
        listaDeProductosNewLabel.setBounds(135, 11, 187, 15);
    }

    private void inicializarListaProductos() {
        List<ProductoDTO> productoDTOList = productoService.listarProductos().stream().map((producto -> new ProductoDTO(producto.getId(), producto.getCodigo(), producto.getDescripcion(), producto.getCategoria(), new MarcaDTO(producto.getMarca().getNombre()), producto.getPrecio(), producto.getVersion()))).toList();
        listProductosWindow = new ListaProductos(productoDTOList);
    }

    private void inicializarJPanePromocionesActivas() throws GUIException {
        textPanePromociones = new JTextPane();
        textPanePromociones.setEditable(false);
        StyledDocument doc = textPanePromociones.getStyledDocument();

        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        textPanePromociones.setCharacterAttributes(attributeSet, true);
        textPanePromociones.setText("Promociones Activas");
        try {
            for (Promocion promocion : promocionService.encontrarPromociones()) {
                doc.insertString(doc.getLength(), promocion.toString(), attributeSet);
            }
        } catch (BadLocationException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar promociones activas", "Error", JOptionPane.ERROR_MESSAGE);
            throw new GUIException(e);
        }
    }
}
