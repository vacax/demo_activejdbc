package edu.pucmm.eict.daj;

import edu.pucmm.eict.daj.config.BootStrap;
import edu.pucmm.eict.daj.controllers.ApiControlador;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import org.javalite.activejdbc.Base;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Aplicación Demostración ActiveJDBC");
        try {

            //
            BootStrap.getInstancia().init();
            //
            Javalin app = Javalin.create(config ->{
                config.addStaticFiles("/publico"); //desde la carpeta de resources
                config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
                config.enableCorsForAllOrigins();
            }).start(getHerokuAssignedPort());

            //
            new ApiControlador(app).aplicarRutas();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}
