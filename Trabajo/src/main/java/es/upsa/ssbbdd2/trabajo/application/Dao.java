package es.upsa.ssbbdd2.trabajo.application;

import es.upsa.ssbbdd2.trabajo.domain.entities.CantidadIngrediente;
import es.upsa.ssbbdd2.trabajo.domain.entities.Menu;
import es.upsa.ssbbdd2.trabajo.domain.entities.Plato;
import es.upsa.ssbbdd2.trabajo.domain.entities.Tipo;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;


public interface Dao extends AutoCloseable
{
    Plato insertarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions;
    Menu insertarMenu (String nombre, double precio, LocalDate fechaDesde, LocalDate fechaHasta, List<Plato> platos) throws TrabajoExceptions;

    boolean existsPlato(String platoNombre);

    Optional<Plato> findPlatoByName(String platoNombre);

    List<Plato> findPlatosByNames(List<String> nombres);

    String generateMenuId();

    String insertMenu(Menu menu);

    void associateMenuWithPlatos(String menuId, List<Plato> platos);



}
