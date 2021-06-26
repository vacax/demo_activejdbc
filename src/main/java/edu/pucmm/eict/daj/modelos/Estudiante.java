package edu.pucmm.eict.daj.modelos;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;

/**
 * El uso de ActiveJDBC por reflección obtiene la información de directamente de la base de datos.
 * La tabla se estará consultando en plural siempre y cuando no utilice la anotación de @Table.
 * Ver English inflections: https://javalite.io/english_inflections
 */
@IdName("matricula") //Sobre-escribiendo el PK, por defecto es id. Ver https://javalite.io/surrogate_primary_keys
public class Estudiante extends Model {

}
