package org.iesvdm.jardineria.modelo;

import java.math.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;
import org.iesvdm.jardineria.modelo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestExamenStream {

    @Test
    void testSkeletonEmpleado() {

        EmpleadoHome empleadoHome = new EmpleadoHome();

        try {
            empleadoHome.beginTransaction();

            List<Empleado> list = empleadoHome.findAll();

            //TODO STREAMS
            list.stream().collect(toList());

            Assertions.assertEquals(31, list.stream().count());

            empleadoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            empleadoHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testSkeletonOficina() {

        OficinaHome oficinaHome = new OficinaHome();

        try {
            oficinaHome.beginTransaction();

            List<Oficina> list = oficinaHome.findAll();

            //TODO STREAMS
            list.stream().collect(toList());

            Assertions.assertEquals(9, list.stream().count());

            oficinaHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            oficinaHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testSkeletonCliente() {

        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            list.stream().collect(toList());

            Assertions.assertEquals(36, list.stream().count());

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testSkeletonPedido() {

        PedidoHome pedidoHome = new PedidoHome();

        try {
            pedidoHome.beginTransaction();

            List<Pedido> list = pedidoHome.findAll();

            //TODO STREAMS
            list.stream().collect(toList());

            Assertions.assertEquals(115, list.stream().count());

            pedidoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            pedidoHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testSkeletonDetallePedido() {

        DetallePedidoHome detallePedidoHome = new DetallePedidoHome();

        try {
            detallePedidoHome.beginTransaction();

            List<DetallePedido> list = detallePedidoHome.findAll();

            //TODO STREAMS
            list.stream().collect(toList());

            Assertions.assertEquals(318, list.stream().count());

            detallePedidoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            detallePedidoHome.rollbackTransaction();
            throw e; // or display error message
        }
    }


    //TEST A IMPLEMENTAR
    /**TEST1
     * Devuelve un listado con la ciudad y el teléfono de las oficinas de España, ordenadas alfabéticamente en orden
     * descendiente por ciudad.
     */
    @Test
    void test1() {

        OficinaHome oficinaHome = new OficinaHome();

        try {
            oficinaHome.beginTransaction();

            List<Oficina> list = oficinaHome.findAll();

            //TODO STREAMS
            var solList = list.stream().sorted(comparing(Oficina::getCiudad).reversed())
                    .filter(c -> c.getPais().equalsIgnoreCase("España"))
                    .map(c -> c.getCiudad() + ", tlf: " + c.getTelefono());


            solList.forEach(System.out::println);


            oficinaHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            oficinaHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * TEST2
     * Devuelve un listado con el nombre, apellidos y email de los empleados cuyo jefe tiene un código de jefe igual a 7.
     */
    @Test
    void test2() {

        EmpleadoHome empleadoHome = new EmpleadoHome();

        try {
            empleadoHome.beginTransaction();

            List<Empleado> list = empleadoHome.findAll();

            //TODO STREAMS
            var solList = list.stream()
                    .filter(e -> e.getJefe() != null && e.getJefe().getCodigoEmpleado() == 7)
                    .map(e -> e.getNombre() + " " + e.getApellido1() + " " + e.getApellido2() + ", email: " + e.getEmail());

            solList.forEach(System.out::println);

            empleadoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            empleadoHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * TEST3
     * Obtén un listado con el nombre de cada cliente y el nombre y apellido 1 de su representante de ventas.
     * Ordénalo por orden apellido1 de representante descendente y nombre cliente ascendente
     */
    @Test
    void test3() {
        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            var solList = list.stream().sorted(comparing((Cliente c) -> c.getRepresentanteVentas().getApellido1())
                            .reversed()
                            .thenComparing(Cliente::getNombreCliente))
                    .map(c -> c.getNombreCliente() + ", Representante: " + c.getEmpleado().getApellido1());

            solList.forEach(System.out::println);

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST4
     * Devuelve un listado de todos los pedidos que fueron rechazados en 2009 ordenados por
     * fecha descendiente. Mostrando la información de fecha de pedido en formato yyyy-MM-dd, nombre de cliente
     * representante de ventas, estado.
     */
    @Test
    void test4() {

        PedidoHome PedidoHome = new PedidoHome();

        try {
            PedidoHome.beginTransaction();

            List<Pedido> list = PedidoHome.findAll();

            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            final Date fin2008;
            final Date inicio2010;
            try {
                fin2008 = formateador.parse("2008-12-31");
                inicio2010 = formateador.parse("2010-01-01");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //TODO STREAMS
            var solList = list.stream()
                    .sorted(comparing(Pedido::getFechaPedido).reversed())
                    .filter( p -> p.getEstado().equalsIgnoreCase("Rechazado"))
                    .map(p -> p.getFechaPedido() + ", cliente: " + p.getCliente().getNombreCliente()
                            + ", " + p.getEstado() + ", Representante: " + p.getCliente().getRepresentanteVentas().getNombre());

            solList.forEach(System.out::println);

            PedidoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            PedidoHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST5
     * Devuelve un listado con el nombre de los todos los clientes no españoles ordenados alfabéticamente.
     */
    @Test
    void test5() {
        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            var solList = list.stream().sorted(comparing(Cliente::getNombreCliente))
                    .filter(c -> c.getRegion() != null && !c.getPais().equalsIgnoreCase("Spain"))
                    .map(c -> c.getNombreCliente() + ", Region: " + c.getPais());

            solList.forEach(System.out::println);

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST6
     * Lista la dirección de las oficinas que tengan clientes en Fuenlabrada.
     *
     */
    @Test
    void test6() {
        OficinaHome oficinaHome = new OficinaHome();

        try {
            oficinaHome.beginTransaction();

            List<Oficina> list = oficinaHome.findAll();

            //TODO STREAMS
            var solList = list.stream();

            solList.forEach(System.out::println);

            oficinaHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            oficinaHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST7
     * Devuelve un listado con los clientes que han realizado algún pedido pero no han realizado ningún pago.
     */
    @Test
    void test7() {
        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            var solList = list.stream().filter(cliente -> cliente.getPagos().isEmpty() && !cliente.getPedidos().isEmpty());

            solList.forEach(System.out::println);

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST8
     * Cuántos clientes tiene cada país
     */
    @Test
    void test8() {
        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            Map<String, Long> solList = null;//list.stream();

            solList.forEach((s, aLong) -> System.out.println(s + ": " + aLong));

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }


    /**
     * TEST9
     * La facturación que ha tenido la empresa en toda la historia, indicando la base imponible, el IVA y el total facturado.
     * La base imponible se calcula sumando el coste del producto por el número de unidades vendidas de la tabla detalle_pedido.
     * El IVA es el 21 % de la base imponible, y el total la suma de los dos campos anteriores.
     */
    @Test
    void test9() {

        DetallePedidoHome detallePedidoHome = new DetallePedidoHome();

        try {
            detallePedidoHome.beginTransaction();

            List<DetallePedido> list = detallePedidoHome.findAll();

            //TODO STREAMS
            BigDecimal baseImponible = null;//list.stream();

            detallePedidoHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            detallePedidoHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * TEST10
     * Los clientes cuyo límite de crédito sea menor que los pagos que haya realizado
     */
    @Test
    void test10() {
        ClienteHome clienteHome = new ClienteHome();

        try {
            clienteHome.beginTransaction();

            List<Cliente> list = clienteHome.findAll();

            //TODO STREAMS
            var solList = list.stream();

            solList.forEach(System.out::println);

            clienteHome.commitTransaction();
        } catch (RuntimeException e) {
            e.printStackTrace();
            clienteHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

}