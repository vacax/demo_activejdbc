package edu.pucmm.eict.daj.config;

import edu.pucmm.eict.daj.modelos.Estudiante;
import org.h2.tools.Server;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import java.sql.SQLException;

/**
 * Para arrancar los logs incluir el parametro: -Dactivejdbc.log
 */
public class BootStrap {

    private static BootStrap instancia;
    private static String NOMBRE_CONEXION="default";

    private BootStrap(){

    }

    public static BootStrap getInstancia(){
        if(instancia == null){
            instancia=new BootStrap();
        }
        return instancia;
    }

    public void startDb() {
        try {
            //Modo servidor H2.
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon",
                    "-ifNotExists").start();
            //Abriendo el cliente web. El valor 0 representa puerto aleatorio.
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            //
            System.out.println("Status Web: "+status);
        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }

    public void init() throws SQLException {
        startDb();
        inicioConexion();
        crearTablas();
        cargarData();
        Base.close();
    }

    public void inicioConexion() throws SQLException {
        Base.open(
                "org.h2.Driver",
                "jdbc:h2:tcp://localhost/~/activejdbc",
                "sa",
                "");

    }

    private void crearTablas() throws SQLException {
        String sqlDrop = "DROP TABLE IF EXISTS estudiantes;";
        String sql="CREATE TABLE IF NOT EXISTS estudiantes(matricula INT PRIMARY KEY, nombre VARCHAR(255), telefono VARCHAR(20), direccion VARCHAR(255));";
        Base.connection().createStatement().execute(sqlDrop);
        Base.connection().createStatement().execute(sql);
    }

    private void cargarData() throws SQLException {
        Base.openTransaction();
        Estudiante.createIt("matricula", 20011136, "nombre", "Carlos Camacho", "telefono", "123123", "direccion", "adasdasda");
        for (int i = 0; i < 100; i++) {
            Estudiante e = new Estudiante();
            e.set("matricula", i);
            e.set("nombre", "Estudiante "+i);
            e.set("telefono", "809-xxx-xxxx");
            e.set("direccion", "Santiago, Republica Dominicana");
            e.validate();
            if(e.isValid()) {
                //como estamos sobre-escribiendo el ID, utilizamos insert. Ver: https://javalite.io/surrogate_primary_keys#override-standard-key-value-behavior
                boolean salvando = e.insert();
                System.out.println(String.format("Fila %d, %b", i, salvando));
                if (e.hasErrors()) {
                    System.out.println("El error: " + e.errors().toString());
                }
            }else{
                System.out.println("No es valido....");
            }
        }
        Base.commitTransaction();
    }
}
