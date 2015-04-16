package exampledao.dao.common;

public interface IDAO {
    
    public ObjectDTO seleccionar(Object llave) throws Exception;
    
    public void insertar(ObjectDTO obj) throws Exception;
    
    public void actualizar(ObjectDTO obj) throws Exception;
    
    public void eliminar(ObjectDTO obj) throws Exception;
}
