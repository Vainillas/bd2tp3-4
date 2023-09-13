package ar.unrn.tp.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TestClass {

    @Test
    public void testDescuentosCaducados() {
        // Arrange
        Marca nike = new Marca("Nike");
        Producto zapatillasNikeHombre = new Producto("5e001191-96d4-4ade-93da-e8c812f4db98", "Zapatillas Nike Masculinas Talle 40", Categoria.ROPA_DEPORTIVA, nike,1000.0);
        Producto zapatillasNikeMujer = new Producto("5e001191-96d4-4ade-93da-e8c812f4d398", "Zapatillas Nike Femeninas Talle 40", Categoria.ROPA_DEPORTIVA, nike,1000.0);
        TarjetaCredito tarjetaCredito = new TarjetaCredito(true, 10000.0,EmisorTarjeta.MASTERCARD);
        Cliente cliente = new Cliente("123456789", "Juan", "Perez", "jperez@correo.com", tarjetaCredito);
        PromocionProducto promocionProducto = new PromocionProducto(LocalDate.now().minusDays(15L), LocalDate.now().minusDays(10L), nike);
        PromocionCompra promocionCompra = new PromocionCompra(LocalDate.now().minusDays(15L), LocalDate.now().minusDays(10L),EmisorTarjeta.MASTERCARD );
        PromocionCollector promocionCollector = new PromocionCollector(List.of(promocionCompra, promocionProducto));

        CarritoCompra carritoCompra = new CarritoCompra(cliente, List.of(zapatillasNikeHombre, zapatillasNikeMujer), promocionCollector);
        // Act
        double total = carritoCompra.calcularTotal(tarjetaCredito);
        // Assert
        Assertions.assertEquals(2000.0,total);
    }
    @Test
    public void testDescuentosVigentesMarcaAcme() {
        // Arrange
        Marca acme = new Marca("Acme");
        Producto dinamitaACME = new Producto("3946fa49-3e14-4e11-a723-7909edfa3e70", "Famosos explosivos marca ACME", Categoria.OTROS, acme,1000.0);
        Producto coheteACME = new Producto("6536c9e8-798c-4f94-aebf-1398ab086816", "Cohete con una alta probabilidad de fallos", Categoria.AUTOMOTOR, acme,2000.0);
        TarjetaCredito tarjetaCredito = new TarjetaCredito(true, 10000.0, EmisorTarjeta.MASTERCARD);
        Cliente cliente = new Cliente("123456789","Juan", "Perez", "jperez@correo.com", tarjetaCredito);
        PromocionProducto promocionProducto = new PromocionProducto(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L), acme);
        PromocionCollector promocionCollector = new PromocionCollector(List.of( promocionProducto));

        CarritoCompra carritoCompra = new CarritoCompra(cliente, List.of(dinamitaACME, coheteACME), promocionCollector);
        // Act
        double total = carritoCompra.calcularTotal(tarjetaCredito);
        // Assert
        Assertions.assertEquals(2850.0,total);
    }
    @Test
    public void testDescuentosVigentesMedioDePago() {
        // Arrange
        Marca acme = new Marca("Acme");
        Producto dinamitaACME = new Producto("3946fa49-3e14-4e11-a723-7909edfa3e70", "Famosos explosivos marca ACME", Categoria.OTROS, acme,1000.0);
        Producto coheteACME = new Producto("6536c9e8-798c-4f94-aebf-1398ab086816", "Cohete con una alta probabilidad de fallos", Categoria.AUTOMOTOR, acme,2000.0);
        TarjetaCredito tarjetaCredito = new TarjetaCredito(true, 10000.0, EmisorTarjeta.MASTERCARD);
        Cliente cliente = new Cliente("123456789","Juan", "Perez", "jperez@correo.com", tarjetaCredito);
        PromocionCompra promocionCompra = new PromocionCompra(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L), EmisorTarjeta.MASTERCARD);
        PromocionCollector promocionCollector = new PromocionCollector(List.of(promocionCompra));

        CarritoCompra carritoCompra = new CarritoCompra(cliente, List.of(dinamitaACME, coheteACME), promocionCollector);
        // Act
        double total = carritoCompra.calcularTotal(tarjetaCredito);
        // Assert
        Assertions.assertEquals(2760,total);
    }
    @Test
    public void testDescuentosVigentes() {
        // Arrange
        Marca comarca = new Marca("Comarca");
        Producto zapatillasComarca = new Producto("5e001191-96d4-4ade-93da-e8c812f4db98", "Zapatillas Comarca ", Categoria.ROPA_DEPORTIVA, comarca,1000.0);
        Producto zapatillasComarcaViedma = new Producto("5e001191-96d4-4ade-93da-e8c812f4d398", "Zapatillas Comarca Viedma ", Categoria.ROPA_DEPORTIVA, comarca,2000.0);
        TarjetaCredito tarjetaCredito = new TarjetaCredito(true, 10000.0,EmisorTarjeta.MEMECARD);
        Cliente cliente = new Cliente("123456789","Juan", "Perez", "jperez@correo.com", tarjetaCredito);
        PromocionProducto promocionProducto = new PromocionProducto(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L), comarca);
        PromocionCompra promocionCompra = new PromocionCompra(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L),EmisorTarjeta.MEMECARD );
        PromocionCollector promocionCollector = new PromocionCollector(List.of(promocionCompra, promocionProducto));

        CarritoCompra carritoCompra = new CarritoCompra(cliente, List.of(zapatillasComarca, zapatillasComarcaViedma), promocionCollector);
        // Act
        double total = carritoCompra.calcularTotal(tarjetaCredito);
        // Assert
        Assertions.assertEquals(2610.0,total);
    }
    @Test
    public void testGeneracionVenta() {
        // Arrange
        Marca comarca = new Marca("Comarca");
        Producto zapatillasComarca = new Producto("5e001191-96d4-4ade-93da-e8c812f4db98", "Zapatillas Comarca ", Categoria.ROPA_DEPORTIVA, comarca,1000.0);
        Producto zapatillasComarcaViedma = new Producto("5e001191-96d4-4ade-93da-e8c812f4d398", "Zapatillas Comarca Viedma ", Categoria.ROPA_DEPORTIVA, comarca,2000.0);
        TarjetaCredito tarjetaCredito = new TarjetaCredito(true, 10000.0,EmisorTarjeta.MEMECARD);
        Cliente cliente = new Cliente("123456789","Juan", "Perez", "jperez@correo.com", tarjetaCredito);
        PromocionProducto promocionProducto = new PromocionProducto(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L), comarca);
        PromocionCompra promocionCompra = new PromocionCompra(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(2L),EmisorTarjeta.MEMECARD );
        PromocionCollector promocionCollector = new PromocionCollector(List.of(promocionCompra, promocionProducto));

        CarritoCompra carritoCompra = new CarritoCompra(cliente, List.of(zapatillasComarca, zapatillasComarcaViedma), promocionCollector);
        // Act
        Venta venta = carritoCompra.generarVenta(tarjetaCredito);
        double expected = tarjetaCredito.getFondos();
        // Assert
        Assertions.assertEquals(7390.0,expected);
        Assertions.assertEquals(2610.0,venta.getMontoTotal());
    }
    @Test
    public void testCreaciónProductoSinAtributos() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            new Producto("codigo", null, null, new Marca("marca"), 0);
        });
    }
    @Test
    public void testCreaciónClienteSinAtributos() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            new Cliente("", "", "", "");
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            new Cliente("43303613", "Juan", "Perez", "asdasd.com");
        });
    }
    @Test
    public void testCreaciónPromociónFechasSuperpuestas() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            new PromocionProducto(LocalDate.now().plusDays(2L), LocalDate.now().minusDays(2L), new Marca("marca"));
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            new PromocionCompra(LocalDate.now().plusDays(2L), LocalDate.now().minusDays(2L), EmisorTarjeta.MEMECARD);
        });
    }



}
