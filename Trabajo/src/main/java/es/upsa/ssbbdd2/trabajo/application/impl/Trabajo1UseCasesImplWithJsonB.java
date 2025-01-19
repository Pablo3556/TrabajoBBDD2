package es.upsa.ssbbdd2.trabajo.application.impl;

import es.upsa.ssbbdd2.trabajo.application.UseCases;
import es.upsa.ssbbdd2.trabajo.domain.entities.*;
import es.upsa.ssbbdd2.trabajo.application.Dao;

import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Trabajo1UseCasesImplWithJsonB implements UseCases {

    private final Dao dao;

    public Trabajo1UseCasesImplWithJsonB(Dao dao) {
        this.dao = dao;
    }

    @Override
    public Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions {
        return null;
    }

    @Override
    public Menu registrarMenu(String nombre, double precio, LocalDate fechaDesde, LocalDate fechaHasta, List<Plato> platos) throws TrabajoExceptions {
        return null;
    }

    @Override
    public List<Menu> buscarMenu(LocalDate fecha) {
        return List.of();
    }

    @Override
    public Menu importMenu(File jsonFile) throws Exception {
        // Leer el archivo JSON e interpretar como MenuImport
        Jsonb jsonb = JsonbBuilder.create();
        MenuImport menuImport;
        try (FileReader reader = new FileReader(jsonFile)) {
            menuImport = jsonb.fromJson(reader, MenuImport.class);
        }

        // Verificar y cargar los platos desde la base de datos
        List<Plato> platos = dao.findPlatosByNames(menuImport.getPlatos());
        if (platos.size() != menuImport.getPlatos().size()) {
            throw new IllegalArgumentException("Algunos de los platos especificados no existen en la base de datos.");
        }

        // Agrupar los platos por tipo
        Map<String, List<Plato>> platosPorTipo = platos.stream()
                .collect(Collectors.groupingBy(plato -> plato.getTipo().toString()));
        // El método getTipo debe estar implementado en la entidad Plato.

        // Calcular el precio total del menú con un descuento del 15%
        double precioTotal = platos.stream()
                .mapToDouble(Plato::getPrecio) // El método getPrecio también debe estar implementado en la entidad Plato.
                .sum();
        double precioConDescuento = precioTotal * 0.85; // Aplicación del descuento del 15%.

        // Generar el identificador único para el menú
        String menuId = dao.generateMenuId(); // Lógica para generar el ID único del menú.

        // Crear el objeto Menu
        Menu menu = Menu.builder()
                .withNombreM(menuImport.getNombre())
                .withFechaDesde(menuImport.getDesde())
                .withFechaHasta(menuImport.getHasta())
                .withPrecio(precioConDescuento)
                .withPlatos((List<Plato>) platosPorTipo)
                .build();

        // Registrar el menú en la base de datos y asociarlo con los platos
        dao.insertMenu(menu); // Inserta el menú en la tabla correspondiente.
        dao.associateMenuWithPlatos(menuId, platos); // Vincula el menú con los platos.

        return menu; // Devuelve el objeto menú con toda la información.
    }
}

