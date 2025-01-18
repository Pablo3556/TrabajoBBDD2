package es.upsa.ssbbdd2.trabajo.application;

import es.upsa.ssbbdd2.trabajo.domain.entities.CantidadIngrediente;
import es.upsa.ssbbdd2.trabajo.domain.entities.Menu;
import es.upsa.ssbbdd2.trabajo.domain.entities.Plato;
import es.upsa.ssbbdd2.trabajo.domain.entities.Tipo;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;
import jakarta.json.bind.annotation.JsonbTransient;

import java.time.LocalDate;
import java.util.List;

public interface UseCases
{
    Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions;
    Menu registrarMenu(String nombre, double precio, LocalDate fechaDesde, LocalDate fechaHasta, List<Plato> platos) throws TrabajoExceptions;

    List<Menu> buscarMenu(LocalDate fecha);
}

