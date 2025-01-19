package es.upsa.ssbbdd2.trabajo.application.impl;


import es.upsa.ssbbdd2.trabajo.application.UseCases;
import es.upsa.ssbbdd2.trabajo.domain.entities.*;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import es.upsa.ssbbdd2.trabajo.domain.entities.MenuImport;

public class Trabajo1UseCasesImplWithJsonP implements UseCases {

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
    public Menu importMenu(File jsonFile) {
        try (FileInputStream fis = new FileInputStream(jsonFile);
             JsonReader reader = Json.createReader(fis)) {

            JsonObject jsonObject = reader.readObject();

            String nombre = jsonObject.getString("nombre");
            LocalDate desde = LocalDate.parse(jsonObject.getString("desde"));
            LocalDate hasta = LocalDate.parse(jsonObject.getString("hasta"));

            List<String> platos = jsonObject.getJsonArray("platos")
                    .stream()
                    .map(value -> value.toString().replace("\"", "")) // Convertir a String puro
                    .collect(Collectors.toList());

            return MenuImport.builder()
                    .withNombre(nombre)
                    .withDesde(desde)
                    .withHasta(hasta)
                    .withPlatos(platos)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON con JSONP", e);
        }
    }
}
