package ar.unrn.tp.ui;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.api.exceptions.ServiceException;
import ar.unrn.tp.dto.*;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.exceptions.BusinessException;
import ar.unrn.tp.ui.exceptions.GUIException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CarritoUI extends JFrame {

    private final PromocionService promocionService;
    private ProductoService productoService;
    private VentaService ventaService;
    private ClienteService clienteService;
    private Long idCliente;


    private List<TarjetaDTO> tarjetas = new ArrayList<>();
    private TarjetaDTO tarjetaSeleccionada;
    private DefaultListModel<ProductoDTO> listaProductos;


    JPanel contentPane;
    JList<TarjetaDTO> listTarjetas;
    JList<String> listPromociones;
    DefaultListModel<TarjetaDTO> modeloTarjeta;
    DefaultListModel<String> modeloPromociones;
    JLabel tarjetasNewLabel;
    JLabel tarjetaSelectedNewLabel;
    JButton seleccionarTarjetaNewButton;
    JButton listTarjetasNewButton;
    JList<ProductoDTO> listCarrito;
    JLabel carritoListNewLabel;
    JButton montoTotalNewButton;
    JButton comprarNewButton;
    private JLabel promocionesActivasLabel;
    private MainWindow mainWindow;


    public CarritoUI(PromocionService promocionService, ProductoService productoService, VentaService ventaService, ClienteService clienteService, long l, DefaultListModel<ProductoDTO> listaProductosSeleccionados) {
        this.promocionService = promocionService;
        this.productoService = productoService;
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.idCliente = l;
        this.listaProductos = listaProductosSeleccionados;

        init();
    }

    private void init() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        initTarjetasWindow();
    }

    private void initTarjetasWindow() {

        promocionesActivasLabel = new JLabel("Promociones activas:");
        promocionesActivasLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 14));
        promocionesActivasLabel.setBounds(10, 240, 179, 14);
        contentPane.add(promocionesActivasLabel);

        listPromociones = new JList<String>();
        modeloPromociones = new DefaultListModel<String>();
        for(Promocion p : promocionService.encontrarPromociones()){
            modeloPromociones.addElement(new PromocionDTO(p.toString()).descripcion());
        }
        listPromociones.setModel(modeloPromociones);
        listPromociones.setBounds(10, 262, 420, 63);
        contentPane.add(listPromociones);


        modeloTarjeta = new DefaultListModel<>();
        listTarjetas = new JList<>();
        listTarjetas.setModel(modeloTarjeta);
        listTarjetas.setBounds(10, 36, 256, 63);
        contentPane.add(listTarjetas);


        tarjetasNewLabel = new JLabel("Seleccione una tarjeta para realizar la compra:");
        tarjetasNewLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 14));
        tarjetasNewLabel.setBounds(10, 11, 306, 14);
        contentPane.add(tarjetasNewLabel);

        tarjetaSelectedNewLabel = new JLabel("");
        tarjetaSelectedNewLabel.setBounds(178, 104, 231, 14);
        contentPane.add(tarjetaSelectedNewLabel);

        seleccionarTarjetaNewButton = new JButton("Seleccionar");
        seleccionarTarjetaNewButton.setBounds(284, 70, 140, 23);
        seleccionarTarjetaNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int index = listTarjetas.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new GUIException("Debe seleccionar una tarjeta");
                }
                tarjetaSeleccionada = modeloTarjeta.getElementAt(index);
                tarjetaSelectedNewLabel.setText(tarjetaSeleccionada.toString());

            }


        });
        contentPane.add(seleccionarTarjetaNewButton);

        listTarjetasNewButton = new JButton("Mostrar mis tarjetas");
        listTarjetasNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeloTarjeta.removeAllElements();
                try{
                    tarjetas = clienteService.listarTarjetas(idCliente).stream().map(tarjeta -> new TarjetaDTO(tarjeta.getNumero(), tarjeta.getEmisorTarjeta(), tarjeta.isActiva(), tarjeta.getFondos())).toList();
                }catch (ServiceException | BusinessException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    throw new GUIException(ex);
                }
                for (TarjetaDTO t : tarjetas) {
                    modeloTarjeta.addElement(t);
                }
            }
        });
        listTarjetasNewButton.setBounds(284, 36, 140, 23);
        contentPane.add(listTarjetasNewButton);


        listCarrito = new JList<>();
        listCarrito.setModel(listaProductos);
        listCarrito.setBounds(10, 135, 256, 102);
        contentPane.add(listCarrito);

        carritoListNewLabel = new JLabel("Productos del carrito:");
        carritoListNewLabel.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 14));
        carritoListNewLabel.setBounds(10, 110, 179, 14);
        contentPane.add(carritoListNewLabel);

        montoTotalNewButton = new JButton("Ver Monto Total");
        montoTotalNewButton.setBounds(284, 143, 140, 23);
        montoTotalNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Long> productosCompra = new ArrayList<>();

                    for (int i = 0; i < listaProductos.getSize(); i++) {
                        productosCompra.add(listaProductos.get(i).id());
                    }
                    if (tarjetaSeleccionada == null) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "El monto total es : " + ventaService.calcularMonto(productosCompra, tarjetaSeleccionada.numero()), //Revisar el cambio de ID por el número de tarjeta
                            "Monto", JOptionPane.INFORMATION_MESSAGE);

                } catch (ServiceException | BusinessException | GUIException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        contentPane.add(montoTotalNewButton);


        comprarNewButton = new JButton("Comprar");
        comprarNewButton.setBounds(284, 189, 140, 23);
        comprarNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Long> productosCompra = new ArrayList();

                    for (int i = 0; i < listaProductos.getSize(); i++) {
                        productosCompra.add(listaProductos.get(i).id());
                    }
                    if (tarjetaSeleccionada == null) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ventaService.realizarVenta(idCliente, productosCompra, tarjetaSeleccionada.numero()); //Revisar el cambio de ID por el número de tarjeta
                    JOptionPane.showMessageDialog(null, "La venta se realizo correctametne", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } catch (ServiceException | BusinessException | GUIException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        contentPane.add(comprarNewButton);
    }
}
