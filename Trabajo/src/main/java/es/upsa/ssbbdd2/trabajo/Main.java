package es.upsa.ssbbdd2.trabajo;

import es.upsa.ssbbdd2.trabajo.application.impl.Trabajo1UseCasesImplWithJsonB;
import es.upsa.ssbbdd2.trabajo.domain.entities.MenuImport;
import es.upsa.ssbbdd2.trabajo.application.Dao;
import es.upsa.ssbbdd2.trabajo.application.impl.DaoImpl;
import es.upsa.ssbbdd2.trabajo.application.UseCases;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/upsa", "system", "manager"
        );


        Dao dao = new DaoImpl(connection);

        UseCases useCases = new Trabajo1UseCasesImplWithJsonB(dao);

        File jsonFile = new File("menu.json");

        MenuImport menuI = useCases.importMenu(jsonFile);

        System.out.println("Menú registrado: " + menuI.getNombre());
    }
}