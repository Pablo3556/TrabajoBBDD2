package es.upsa.ssbbdd2.trabajo.application.impl;

import es.upsa.ssbbdd2.trabajo.application.Dao;
import es.upsa.ssbbdd2.trabajo.application.UseCases;
import es.upsa.ssbbdd2.trabajo.domain.entities.CantidadIngrediente;
import es.upsa.ssbbdd2.trabajo.domain.entities.Menu;
import es.upsa.ssbbdd2.trabajo.domain.entities.Plato;
import es.upsa.ssbbdd2.trabajo.domain.entities.Tipo;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;
import lombok.Builder;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
@Builder(setterPrefix = "with")
public class UseCasesImpl implements UseCases {
    private Dao dao;

    @Override
    public Plato registrarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions {
        return dao.insertarPlato(nombre,descripcion,precio,tipo,cantidadesIngredientes);
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
        return null;
    }
}
