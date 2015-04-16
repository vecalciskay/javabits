package exampledao.gui;

import exampledao.dao.FactoryDAO;
import exampledao.dao.PersonaDAO;
import exampledao.dao.PersonaDTO;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.Insets;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Date;

import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class NuevoEditarDialog extends JDialog {

    private static Logger logger = Logger.getLogger(NuevoEditarDialog.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel formPanel = new JPanel();
    private JTextField nombreTextField = new JTextField();
    private JLabel nombreLabel = new JLabel();
    private JLabel idLabel = new JLabel();
    private JLabel salarioLabel = new JLabel();
    private JTextField salarioTextField = new JTextField();
    private JLabel fechaLabel = new JLabel();
    private JTextField fechaTextField = new JTextField();
    private JButton grabarButton = new JButton();
    private JButton cancelarButton = new JButton();

    private VentanaEjemplo myParent;

    private int id;

    public NuevoEditarDialog(VentanaEjemplo aFrame, String aWord) {
        super(aFrame, true);
        myParent = aFrame;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(199, 232));
        formPanel.setLayout(null);
        nombreTextField.setBounds(new Rectangle(10, 40, 170, 25));
        nombreTextField.setFont(new Font("Tahoma", 0, 12));
        nombreLabel.setText("Nombre");
        nombreLabel.setBounds(new Rectangle(10, 25, 125, 20));
        idLabel.setText("jLabel2");
        idLabel.setBounds(new Rectangle(10, 10, 125, 15));
        idLabel.setFont(new Font("Tahoma", 1, 12));
        salarioLabel.setText("Salario");
        salarioLabel.setBounds(new Rectangle(10, 70, 105, 15));
        salarioTextField.setBounds(new Rectangle(10, 85, 170, 25));
        fechaLabel.setText("Fecha de nacimiento (yyyy-mm-dd)");
        fechaLabel.setBounds(new Rectangle(10, 115, 180, 15));
        fechaTextField.setBounds(new Rectangle(10, 130, 170, 25));
        grabarButton.setText("Grabar");
        grabarButton.setBounds(new Rectangle(10, 165, 73, 22));
        grabarButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        grabarButton_actionPerformed(e);
                    }
                });
        cancelarButton.setText("Cancelar");
        cancelarButton.setBounds(new Rectangle(90, 165, 60, 20));
        cancelarButton.setBorderPainted(false);
        cancelarButton.setContentAreaFilled(false);
        cancelarButton.setForeground(new Color(49, 49, 49));
        cancelarButton.setMargin(new Insets(1, 5, 1, 5));
        cancelarButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cancelarButton_actionPerformed(e);
                    }
                });
        formPanel.add(cancelarButton, null);
        formPanel.add(grabarButton, null);
        formPanel.add(fechaTextField, null);
        formPanel.add(fechaLabel, null);
        formPanel.add(salarioTextField, null);
        formPanel.add(salarioLabel, null);
        formPanel.add(idLabel, null);
        formPanel.add(nombreLabel, null);
        formPanel.add(nombreTextField, null);
        this.getContentPane().add(formPanel, BorderLayout.CENTER);

        this.setResizable(false);
    }

    public Dimension getPreferredSize() {
        return new Dimension(215, 240);
    }

    public void cargarObjetoYColocarValores() {
        PersonaDTO objPersona = null;
        PersonaDAO dao = FactoryDAO.getOrCreate().newPersonaDao();
        try {
            objPersona = (PersonaDTO)dao.seleccionar(new Integer(id));
        } catch (Exception err) {
            logger.error("No pudo recuperar objeto con id: " + id);
        }

        if (objPersona == null) {
            logger.error("No muestra ningun dato, estado inconsistente");
            limpiarFormulario();
            return;
        }

        idLabel.setText("ID: " + objPersona.getId());
        nombreTextField.setText(objPersona.getNombre());
        fechaTextField.setText(objPersona.getFechaNacimientoForTextField());
        salarioTextField.setText(String.valueOf((objPersona.getSalario())));
    }

    public void limpiarFormulario() {
        idLabel.setText("nuevo");
        nombreTextField.setText("");
        fechaTextField.setText("");
        salarioTextField.setText("");
    }

    public void setId(int newid) {
        this.id = newid;

        if (newid == 0) {
            logger.info("Llamo al id 0, entonces se limpian los datos");
            limpiarFormulario();
        } else {
            logger.info("Llamo con id " + id + 
                        " entonces carga el objeto en el form");
            cargarObjetoYColocarValores();
        }
    }

    public int getId() {
        return id;
    }
    
    private void cancelarButton_actionPerformed(ActionEvent e) {
        this.dispose();
        MensajesSistema.nuevoMensaje("Cierra la edicion SIN modificaciones.");
    }

    private void grabarButton_actionPerformed(ActionEvent e) {
        PersonaDTO obj = obtenerPersonaDelFormulario();

        if (obj == null) {
            logger.error("Alguno de los datos esta en mal formato");
            return;
        }

        PersonaDAO dao = FactoryDAO.getOrCreate().newPersonaDao();
        try {
            if (obj.getId() == 0) {
                dao.insertar(obj);
                logger.info("Se inserto un nuevo registro");
                MensajesSistema.nuevoMensaje("Nuevo registro insertado" );
            } else {
                dao.actualizar(obj);
                logger.info("Se actualizo la informacion de objeto con id: " + 
                            this.id);
                MensajesSistema.nuevoMensaje("Se actualizo la informacion de objeto con id: " + 
                            this.id );
            }
        } catch (Exception err) {
            logger.error("No pudo actualizar la informacion del objeto con id: " + 
                         this.id, err);
            MensajesSistema.nuevoMensaje("No pudo actualizar la informacion del registro con id: " + 
                         this.id);
        }

        this.dispose();

        this.myParent.actualizarDatos();
    }

    private PersonaDTO obtenerPersonaDelFormulario() {
        String nombre = nombreTextField.getText().trim();

        String fechaYYYYMMDD = fechaTextField.getText().trim();
        StringTokenizer fechaTokens = new StringTokenizer(fechaYYYYMMDD, "-");

        int year = 0;
        int month = 0;
        int day = 0;

        try {
            year = Integer.valueOf(fechaTokens.nextToken());
            month = Integer.valueOf(fechaTokens.nextToken());
            day = Integer.valueOf(fechaTokens.nextToken());
        } catch (Exception err) {
            logger.error("Fecha en mal formato", err);
            return null;
        }

        Calendar objCalendar = Calendar.getInstance();
        objCalendar.set(year, month, day);

        Date fecha = new Date(objCalendar.getTimeInMillis());

        float salario = 0.0f;
        try {
            salario = Float.parseFloat(salarioTextField.getText());
        } catch (Exception err) {
            logger.error("Salario en mal formato", err);
            return null;
        }

        PersonaDTO objRespuesta = new PersonaDTO();
        objRespuesta.setId(this.id);
        objRespuesta.setNombre(nombre);
        objRespuesta.setFechaNacimiento(fecha);
        objRespuesta.setSalario(salario);

        return objRespuesta;
    }
}
