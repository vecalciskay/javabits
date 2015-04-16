package exampledao.gui;

import exampledao.dao.FactoryDAO;
import exampledao.dao.PersonaDAO;

import exampledao.dao.PersonaDTO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Date;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

/**
 * esta clase es observer porque debe observar lo que ocurre con los mensajes 
 * que deja la aplicacion todo el tiempo y poder mostrarlos en el status.
 */
public class VentanaEjemplo extends JFrame implements Observer {
    private static Logger logger = Logger.getLogger(VentanaEjemplo.class);

    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private JButton buttonNuevo = new JButton();
    private JButton buttonEditar = new JButton();
    private JButton buttonEliminar = new JButton();
    private ImageIcon imageNuevo = 
        new ImageIcon(VentanaEjemplo.class.getResource("add-item.png"));
    private ImageIcon imageEditar = 
        new ImageIcon(VentanaEjemplo.class.getResource("edit-item.png"));
    private ImageIcon imageEliminar = 
        new ImageIcon(VentanaEjemplo.class.getResource("delete-item.png"));
    private JMenuItem menuFileConfig = new JMenuItem();
    private JTable listObjectsTable = new JTable();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel panelBotones = new JPanel();
    private JButton refreshButton = new JButton();
    private JMenuItem menuFileSetupDB = new JMenuItem();
    private ModeloDatos modeloPersona = new ModeloDatos();
    private NuevoEditarDialog nuevoDialog;
    private NuevoEditarDialog editarDialog;

    public VentanaEjemplo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        panelCenter.setLayout(borderLayout1);
        this.setSize(new Dimension(400, 300));
        this.setTitle("Ejemplo DAO");
        menuFile.setText("File");
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileExit_ActionPerformed(ae);
                    }
                });
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        helpAbout_ActionPerformed(ae);
                    }
                });
        statusBar.setText("");
        buttonNuevo.setToolTipText("Nuevo Registro");
        buttonNuevo.setIcon(imageNuevo);
        buttonNuevo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonNuevo_actionPerformed(e);
                    }
                });
        buttonEditar.setToolTipText("Editar Seleccionado");
        buttonEditar.setIcon(imageEditar);
        buttonEditar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonEditar_actionPerformed(e);
                    }
                });
        buttonEliminar.setToolTipText("Eliminar Seleccionado");
        buttonEliminar.setIcon(imageEliminar);
        buttonEliminar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonEliminar_actionPerformed(e);
                    }
                });
        menuFileConfig.setText("Configuración BD");
        refreshButton.setText("Actualizar");
        refreshButton.setActionCommand("refresh");
        refreshButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        refreshButton_actionPerformed(e);
                    }
                });
        menuFileSetupDB.setText("Instalar BD");
        menuFileSetupDB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuFileSetupDB_actionPerformed(e);
                    }
                });
        menuFile.add(menuFileSetupDB);
        menuFile.add(menuFileConfig);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonNuevo);
        toolBar.add(buttonEditar);
        toolBar.add(buttonEliminar);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        // Prepara la tabla
        JScrollPane contenedorListObjectsTable = new JScrollPane(listObjectsTable);
        panelCenter.add(contenedorListObjectsTable, BorderLayout.CENTER);
        panelBotones.add(refreshButton, null);
        panelCenter.add(panelBotones, BorderLayout.SOUTH);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);

        listObjectsTable.setModel(modeloPersona);
        modeloPersona.load();
        
        MensajesSistema.getOrCreate().addObserver(this);

        nuevoDialog = new NuevoEditarDialog(this, "Nuevo Registro");
        nuevoDialog.pack();
        editarDialog = new NuevoEditarDialog(this, "Editar Registro");
        editarDialog.pack();
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, 
                                      new VentanaEjemplo_AboutBoxPanel1(), 
                                      "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void menuFileSetupDB_actionPerformed(ActionEvent e) {
        JFrame helpInstall = new InstallBDFrame();
        helpInstall.setVisible(true);
    }

    private void buttonNuevo_actionPerformed(ActionEvent e) {
        nuevoDialog.setId(0);
        nuevoDialog.setLocationRelativeTo(this);
        nuevoDialog.setVisible(true);
    }

    private void buttonEditar_actionPerformed(ActionEvent e) {

        int row = listObjectsTable.getSelectedRow();
        int id = Integer.parseInt(modeloPersona.getValueAt(row, 0).toString());

        editarDialog.setId(id);
        editarDialog.setLocationRelativeTo(this);
        editarDialog.setVisible(true);
    }

    public void actualizarDatos() {
        this.modeloPersona.load();
    }

    private void buttonEliminar_actionPerformed(ActionEvent e) {

        int row = listObjectsTable.getSelectedRow();
        int id = Integer.parseInt(modeloPersona.getValueAt(row, 0).toString());

        PersonaDAO dao = FactoryDAO.getOrCreate().newPersonaDao();
        try {
            dao.eliminar(new PersonaDTO(id, null, null, 0.0f));
            this.actualizarDatos();
            MensajesSistema.nuevoMensaje("Se elimino correctamente el registro " + id);
        } catch (Exception err) {
            logger.error("Error al tratar de eliminar", err);
            MensajesSistema.nuevoMensaje("No pudo eliminar el registro");
        }
    }

    private void refreshButton_actionPerformed(ActionEvent e) {
        this.actualizarDatos();
    }

    /**
     * Cuando cambian los mensajes se cambia el mensaje en el status
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        this.statusBar.setText(MensajesSistema.getMensajes());
    }
}
