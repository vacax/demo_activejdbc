package edu.pucmm.eict.daj.services;

import edu.pucmm.eict.daj.config.BootStrap;
import edu.pucmm.eict.daj.modelos.Estudiante;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class EstudianteServices {

    private static EstudianteServices instancia;

    private EstudianteServices(){

    }

    public static EstudianteServices getInstance(){
        if(instancia == null){
            instancia = new EstudianteServices();
        }
        return instancia;
    }

    public LazyList<Estudiante> getAllEstudiante(){
        return Estudiante.findAll();
    }

    public Estudiante getByMatricula(int matricula){
        return Estudiante.findById(matricula);
    }

    public boolean saveEstudiante(@NotNull Estudiante e){
        return e.insert();
    }

    public boolean updateEstudiante(Estudiante e){
        return e.saveIt();
    }

    public boolean deleteEstudiante(Estudiante e){
        return e.delete();
    }

    public boolean deleteEstudianteByMatricula(int matricula){
        Estudiante e = Estudiante.findById(matricula);
        if(e!=null){
            return e.delete();
        }
        return false;
    }

}
