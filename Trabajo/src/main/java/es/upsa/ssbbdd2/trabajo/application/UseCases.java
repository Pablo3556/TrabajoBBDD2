package es.upsa.ssbbdd2.trabajo.application;

import es.upsa.ssbbdd2.trabajo.domain.entities.*;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;

import java.time.LocalDate;
import java.util.List;

import java.io.File;


public interface UseCases
{
    Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions;
    Menu registrarMenu(String nombre, double precio, LocalDate fechaDesde, LocalDate fechaHasta, List<Plato> platos) throws TrabajoExceptions;

    List<Menu> buscarMenu(LocalDate fecha);

    Menu importMenu(File jsonFile) throws Exception;
}

