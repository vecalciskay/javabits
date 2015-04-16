package exampledao.dao;

import exampledao.dao.common.Conexion;
import exampledao.dao.common.ObjectDTO;

import exampledao.estructura.Cadena;

import java.sql.Date;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class PersonaDAOMysql extends PersonaDAO {
    
    private static Logger logger = Logger.getLogger(PersonaDAOMysql.class);
    
    public PersonaDAOMysql() {
    }

    public ObjectDTO seleccionar(Object llave) throws Exception {
        
        if (!(llave instanceof Integer)) {
            throw new Exception("Llave debe ser un entero");
        }
        
        int id = ((Integer)llave).intValue();
        if (id <= 0) {
            throw new Exception("Llave debe ser un entero positivo, era " + id);
        }
        
        Conexion con = Conexion.getOrCreate();
        String query = "SELECT `id`,`nombre`,`fechaNacimiento`,`salario` " +
            " FROM `persona` " +
            " WHERE `id` = " + id;
        
        ResultSet rs = con.ejecutar(query);
        
        if (rs == null) {
            logger.error("Error al traer datos.");
            throw new Exception("Error al traer datos");
        }
        
        if (!rs.next()) {
            logger.error("No se encontro objeto Persona con id: "+ id);
            throw new Exception("No se encontro objeto Persona con id: "+ id);
        }
        
        String nombre = rs.getString("nombre");
        Date fecha = rs.getDate("fechaNacimiento");
        float salario = rs.getFloat("salario");
        
        PersonaDTO dto = new PersonaDTO(id, nombre, fecha, salario);
        
        return dto;
    }

    public void insertar(ObjectDTO obj) throws Exception {
        PersonaDTO objPersona = (PersonaDTO)obj;
        
        Conexion con = Conexion.getOrCreate();
        String query = "INSERT INTO `persona` " + 
                       " (nombre, fechaNacimiento, salario) VALUES " +
                       "( '"+ objPersona.getNombre() + "' " + 
                       " ,STR_TO_DATE('" + objPersona.getFechaNacimientoForTextField() + "','%Y-%m-%d')" +
                       " ," + objPersona.getSalario() + ")";
        logger.debug("EXEC Mysql: " + query);
        int upd = con.ejecutarInsert(query);

        if (upd == 0)
                throw new Exception("Error: no ha podido insertar los datos");
        
        objPersona.setId(upd);
    }

    public void actualizar(ObjectDTO obj) throws Exception {
        PersonaDTO objPersona = (PersonaDTO)obj;
        
        Conexion con = Conexion.getOrCreate();
        String query = "UPDATE persona SET " + 
                       " nombre = '" + objPersona.getNombre() + "' " + 
                       " ,fechaNacimiento = STR_TO_DATE('" + objPersona.getFechaNacimientoForTextField() + "','%Y-%m-%d')" +
                       " ,salario = " + objPersona.getSalario() +
                       " WHERE id = " + objPersona.getId();
        logger.debug("EXEC Mysql: " + query);
        int upd = con.ejecutarSimple(query);

        if (upd == 0)
                throw new Exception("Error: no ha podido actualizar los datos");
    }

    public void eliminar(ObjectDTO obj) throws Exception {
        PersonaDTO objPersona = (PersonaDTO)obj;
        
        if (objPersona == null) {
            throw new Exception("El objeto pasado es nulo");
        }
        
        if (objPersona.getId() <= 0) {
            throw new Exception("No puede eliminar un objeto con id <= 0");
        }
        
        Conexion con = Conexion.getOrCreate();
        String query = "DELETE FROM `persona` " +
                       " WHERE id = " + objPersona.getId();
        logger.debug("EXEC Mysql: " + query);
        int upd = con.ejecutarSimple(query);

        if (upd == 0)
                throw new Exception("Error: no ha podido elimiinar los datos");
    }

    public Cadena<PersonaDTO> seleccionarTodos() throws Exception {
        
        Cadena<PersonaDTO> lista = new Cadena<PersonaDTO>();
                
        Conexion con = Conexion.getOrCreate();
        String query = "SELECT `id`,`nombre`,`fechaNacimiento`,`salario` " +
            " FROM `persona` ORDER BY `nombre`";
        
        ResultSet rs = con.ejecutar(query);
        
        if (rs == null) {
            logger.error("Error al traer datos.");
            throw new Exception("Error al traer datos");
        }
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            Date fecha = rs.getDate("fechaNacimiento");
            float salario = rs.getFloat("salario");
            
            PersonaDTO dto = new PersonaDTO(id, nombre, fecha, salario);
            
            lista.insertar(dto);
        }
        
        logger.info("Se obtuvieron " + lista.tamano() + " registros");
        
        return lista;
    }
}
