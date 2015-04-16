package exampledao.gui;

import exampledao.EjemploDao;

import exampledao.dao.FactoryDAO;
import exampledao.dao.PersonaDAO;
import exampledao.dao.PersonaDTO;

import exampledao.estructura.Cadena;

import javax.swing.table.AbstractTableModel;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

public class ModeloDatos extends AbstractTableModel {

    private static Logger logger = Logger.getLogger(ModeloDatos.class);

    Cadena<PersonaDTO> lista;

    public ModeloDatos() {
        super();
        lista = new Cadena<PersonaDTO>();
    }

    public int getRowCount() {
        return lista.tamano();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        PersonaDTO obj = lista.obtener(rowIndex);
        return obj.obtenerColumna(columnIndex);
    }
    
    public String getColumnName(int index) {
        switch (index) {
        case 0:
            return "ID";
        case 1:
            return "Nombre";
        case 2:
            return "Fec. Nacimiento";
        case 3:
            return "Salario";
        default:
            return null;
        }
    }

    /**
     * pour charger
     */
    public void load() {
        new LoadWorker().execute();
    }

    private class LoadWorker extends SwingWorker<Boolean, Boolean> {

        public LoadWorker() {
            super();
        }

        @Override
        protected Boolean doInBackground() {
            FactoryDAO factory = FactoryDAO.getOrCreate();
            PersonaDAO dao = factory.newPersonaDao();

            try {
                logger.debug("Va a seleccionar todos");
                lista = dao.seleccionarTodos();
            } catch (Exception e) {
                logger.error("No pudo obtener datos de la BD", e);
                MensajesSistema.nuevoMensaje("Hubo un error al acceder a los datos de la base de datos");
            }
            return true;
        }

        @Override
        protected void done() {
            fireTableDataChanged();
        }

    }
}
