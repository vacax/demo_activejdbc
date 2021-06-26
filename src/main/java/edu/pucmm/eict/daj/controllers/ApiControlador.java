package edu.pucmm.eict.daj.controllers;

import edu.pucmm.eict.daj.modelos.Estudiante;
import edu.pucmm.eict.daj.services.EstudianteServices;
import edu.pucmm.eict.daj.util.JsonHelper;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ApiControlador {

    private  Javalin app;
    private EstudianteServices estudianteServices = EstudianteServices.getInstance();


    public ApiControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.routes(() -> {
            path("/api", () -> {

                before(ctx -> {

                    Base.open(
                            "org.h2.Driver",
                            "jdbc:h2:tcp://localhost/~/activejdbc",
                            "sa",
                            "");

                });

                //para todas las llamadas convertir al tipo JSON
                after(ctx -> {
                    ctx.header("Content-Type", "application/json");
                    Base.close();
                });

                /**
                 * Ejemplo de una API REST, implementando el CRUD
                 * ir a
                 */
                path("/estudiante", () -> {


                    get("/", ctx -> {
                        ctx.result(estudianteServices.getAllEstudiante().toJson(true)); //TODO: implementar paginación.
                    });

                    get("/:matricula", ctx -> {
                        Estudiante es = estudianteServices.getByMatricula(ctx.pathParam("matricula", Integer.class).get());

                        if(es!=null){
                            ctx.result(es.toJson(true)); //Por la Reflexión es necesario utilizar el metodo toJson.
                        }else{
                            throw new NotFoundResponse("Estudiante no encontrado");
                        }
                    });

                    post("/", ctx -> {
                        //Parseando el JSON desde un helper por la reflexión.
                        String json = ctx.body();

                        Estudiante tmp = new Estudiante();
                        tmp.fromMap(JsonHelper.toMap(json));

                        //creando.
                        ctx.json(estudianteServices.saveEstudiante(tmp));
                    });

                    put("/", ctx -> {
                        //Parseando el JSON desde un helper por la reflexión.
                        String json = ctx.body();

                        Estudiante tmp = new Estudiante();
                        tmp.fromMap(JsonHelper.toMap(json));

                        //actualizando.
                        ctx.json(estudianteServices.updateEstudiante(tmp));
                    });

                    delete("/:matricula", ctx -> {
                        //creando.
                        ctx.json(estudianteServices.deleteEstudianteByMatricula(ctx.pathParam("matricula", Integer.class).get()));
                    });
                });

            });
        });


        app.exception(Exception.class, (exception, ctx) -> {
            ctx.status(500);
            ctx.html("<h1>Error no recuperado:"+exception.getMessage()+"</h1>");
            exception.printStackTrace();
        });
    }
}
