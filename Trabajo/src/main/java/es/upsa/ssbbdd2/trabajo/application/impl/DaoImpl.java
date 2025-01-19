package es.upsa.ssbbdd2.trabajo.application.impl;

import es.upsa.ssbbdd2.trabajo.application.Dao;
import es.upsa.ssbbdd2.trabajo.domain.entities.*;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.NombrePRequiredException;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.NonControlledSqlException;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.TrabajoExceptions;
import es.upsa.ssbbdd2.trabajo.domain.exceptions.PlatoNotFoundException;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DaoImpl implements Dao {

    private Connection connection;
    public DaoImpl(Connection connection) {
        this.connection = connection;
    }


    public DaoImpl(String url,String user,String password) throws Exception {
        try{
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
            connection=DriverManager.getConnection(url, user, password);
        }catch (SQLException sqlException){
            throw manageException(sqlException);
        }

    }

    @Override
    public void close() throws Exception {
        if(connection !=null){
            connection.close();
            connection = null;
        }
    }

    private TrabajoExceptions manageException(SQLException sqlException)
    {
        String message = sqlException.getMessage();
        if (message.contains("NN_PLATO.NOMBRE")) return new NombrePRequiredException();

        return new NonControlledSqlException(sqlException);
    }


    @Override
    public Plato insertarPlato(String nombre, String descripcion, double precio, Tipo tipo, List<CantidadIngrediente> cantidadesIngredientes) throws TrabajoExceptions {

        /*final String SQL = """
                        INSERT  INTO plato(idP, idI, nombre, descripcion, precio, tipo)
                        VALUES (nextval('seq_platos'), nextval('seq_ingredientes'), ?, ?, ?, ?, ?)
                        """;
        final String sql2 = """
                        INSERT INTO ingredientes(idI, nombre)
                        VALUES (nextval('seq_ingredientes'), ?)
                        """;*/

        final String SQL1= """
                           SELECT idI 
                           FROM ingredientes 
                           WHERE nombreI = ?
                           """;

        final String SQL2 = """
            INSERT INTO cantidadIngrediente(idIng, cantidad, unidad)
            VALUES (nextval('seq_ingredientes'), ?, ?)
            RETURNING idIng
        """;

        String[] fields = {"idP"};

        try (PreparedStatement insertPlatoStmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertIngredienteStmt = connection.prepareStatement(INSERT_INGREDIENTE_SQL);
             PreparedStatement selectIngredienteStmt = connection.prepareStatement(SQL1);

             PreparedStatement insertCantidadIngredienteStmt = connection.prepareStatement(SQL2);
             PreparedStatement insertPlatoIngredienteStmt = connection.prepareStatement(INSERT_PLATO_INGREDIENTE_SQL)) {

            // Insertar el plato
            insertPlatoStmt.setString(1, nombre);
            insertPlatoStmt.setString(2, descripcion);
            insertPlatoStmt.setDouble(3, precio);
            insertPlatoStmt.setString(4, tipo.name());
            insertPlatoStmt.executeUpdate();

            ResultSet generatedKeys = insertPlatoStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException();
            }
            String platoId = generatedKeys.getString(1);

            List<Ingrediente> ingredientes = new ArrayList<>();

            // Insertar ingredientes y asociarlos con el plato
            for (CantidadIngrediente cantidadIngrediente : cantidadesIngredientes) {
                String ingredienteId;

                // Verificar si el ingrediente ya existe
                selectIngredienteStmt.setString(1, cantidadIngrediente.getNombre());
                ResultSet rs = selectIngredienteStmt.executeQuery();
                if (rs.next()) {
                    ingredienteId = rs.getString("idI");
                } else {
                    // Insertar nuevo ingrediente
                    insertIngredienteStmt.setString(1, cantidadIngrediente.getNombre());
                    insertIngredienteStmt.executeUpdate();
                    ResultSet generatedIngredienteKeys = insertIngredienteStmt.getGeneratedKeys();
                    if (!generatedIngredienteKeys.next()) {
                        throw new SQLException("Failed to retrieve the generated id for the new ingrediente.");
                    }
                    ingredienteId = generatedIngredienteKeys.getString(1);
                }

                // Insertar cantidadIngrediente
                insertCantidadIngredienteStmt.setInt(1, cantidadIngrediente.getCantidad());
                insertCantidadIngredienteStmt.setString(2, cantidadIngrediente.getUnidadMedida());
                insertCantidadIngredienteStmt.executeUpdate();
                ResultSet generatedCantidadKeys = insertCantidadIngredienteStmt.getGeneratedKeys();
                if (!generatedCantidadKeys.next()) {
                    throw new SQLException("Failed to retrieve the generated id for the new cantidadIngrediente.");
                }
                String cantidadIngredienteId = generatedCantidadKeys.getString(1);

                // Asociar ingrediente con el plato
                insertPlatoIngredienteStmt.setString(1, platoId);
                insertPlatoIngredienteStmt.setString(2, ingredienteId);
                insertPlatoIngredienteStmt.setInt(3, cantidadIngrediente.getCantidad());
                insertPlatoIngredienteStmt.setString(4, cantidadIngrediente.getUnidadMedida());
                insertPlatoIngredienteStmt.executeUpdate();

                Ingrediente ingrediente = new Ingrediente(ingredienteId, cantidadIngrediente.getNombre());
                ingredientes.add(ingrediente);
            }

            // Crear y devolver el objeto Plato
            Plato plato = new Plato(platoId, nombre, descripcion, precio, tipo, ingredientes);
            return plato;

        } catch (SQLException e) {
            throw manageException(e);
        }
    }

    @Override
    public Menu insertarMenu(String nombre, double precio, LocalDate fechaDesde, LocalDate fechaHasta, List<Plato> platos) throws TrabajoExceptions {
        return null;
    }

    @Override
    public boolean existsPlato(String platoNombre) {
        return false;
    }

    @Override
    public Optional<Plato> findPlatoByName(String platoNombre) {
        return Optional.empty();
    }

    @Override
    public List<Plato> findPlatosByNames(List<String> nombres) {
        return List.of();
    }

    @Override
    public String generateMenuId() {
        return "";
    }

    @Override
    public String insertMenu(Menu menu) {
        return "";
    }

    @Override
    public void associateMenuWithPlatos(String menuId, List<Plato> platos) {

    }

}
