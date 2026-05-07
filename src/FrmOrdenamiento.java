import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import servicios.DocumentosServicio;
import servicios.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class FrmOrdenamiento extends JFrame {

    private JButton btnOrdenarBurbuja;
    private JButton btnOrdenarRapido;
    private JButton btnOrdenarInsercion;

    private JToolBar tbOrdenamiento;

    private JComboBox cmbCriterio;

    private JTextField txtTiempo;

    private JButton btnBuscar;
    private JTextField txtBuscar;

    private JTable tblDocumentos;

    private boolean ejecutando;

    public FrmOrdenamiento() {

        tbOrdenamiento = new JToolBar();

        btnOrdenarBurbuja = new JButton();
        btnOrdenarRapido = new JButton();
        btnOrdenarInsercion = new JButton();

        cmbCriterio = new JComboBox();

        txtTiempo = new JTextField();

        btnBuscar = new JButton();
        txtBuscar = new JTextField();

        tblDocumentos = new JTable();

        var dtm = new DefaultTableModel(
                null,
                DocumentosServicio.getEncabezados());

        tblDocumentos.setModel(dtm);

        JScrollPane spDocumentos = new JScrollPane(tblDocumentos);

        setSize(600, 400);

        setTitle("Ordenamiento Documentos");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // BOTÓN BURBUJA

        btnOrdenarBurbuja.setIcon(
                new ImageIcon(
                        getClass().getResource("/iconos/Ordenar.png")));

        btnOrdenarBurbuja.setToolTipText("Ordenar Burbuja");

        btnOrdenarBurbuja.addActionListener(evt -> {
            btnOrdenarBurbujaClick(evt);
        });

        tbOrdenamiento.add(btnOrdenarBurbuja);

        // BOTÓN RÁPIDO

        btnOrdenarRapido.setIcon(
                new ImageIcon(
                        getClass().getResource("/iconos/OrdenarRapido.png")));

        btnOrdenarRapido.setToolTipText("Ordenar Rapido");

        btnOrdenarRapido.addActionListener(evt -> {
            btnOrdenarRapidoClick(evt);
        });

        tbOrdenamiento.add(btnOrdenarRapido);

        // BOTÓN MEZCLA RECURSIVO

        btnOrdenarInsercion.setIcon(
                new ImageIcon(
                        getClass().getResource("/iconos/OrdenarInsercion.png")));

        btnOrdenarInsercion.setToolTipText("Ordenar Mezcla Recursivo");

        btnOrdenarInsercion.addActionListener(evt -> {
            btnOrdenarInsercionClick(evt);
        });

        tbOrdenamiento.add(btnOrdenarInsercion);

        // COMBO CRITERIO

        cmbCriterio.setModel(
                new DefaultComboBoxModel(
                        new String[] {
                                "Nombre Completo, Tipo de Documento",
                                "Tipo de Documento, Nombre Completo"
                        }));

        tbOrdenamiento.add(cmbCriterio);

        // TIEMPO

        txtTiempo.setEditable(false);

        tbOrdenamiento.add(txtTiempo);

        // BOTÓN BUSCAR

        btnBuscar.setIcon(
                new ImageIcon(
                        getClass().getResource("/iconos/Buscar.png")));

        btnBuscar.setToolTipText("Buscar");

        btnBuscar.addActionListener(evt -> {
            btnBuscar(evt);
        });

        tbOrdenamiento.add(btnBuscar);

        tbOrdenamiento.add(txtBuscar);

        // AGREGAR COMPONENTES

        getContentPane().add(
                tbOrdenamiento,
                BorderLayout.NORTH);

        getContentPane().add(
                spDocumentos,
                BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {

        String nombreArchivo =
                System.getProperty("user.dir")
                        + "/src/datos/Datos.csv";

        DocumentosServicio.cargar(nombreArchivo);

        DocumentosServicio.mostrar(tblDocumentos);
    }

    // ==========================================
    // BURBUJA
    // ==========================================

    private void btnOrdenarBurbujaClick(ActionEvent evt) {

        if (cmbCriterio.getSelectedIndex() >= 0) {

            Util.iniciarCronometro();

            ejecutando = true;

            // CRONÓMETRO

            new Thread(() -> {

                while (ejecutando) {

                    Util.pausarMilisegundos(100);

                    txtTiempo.setText(
                            Util.getTextoTiempoCronometro());
                }

            }).start();

            // ORDENAMIENTO

            new Thread(() -> {

                DocumentosServicio.ordenarBurbuja(
                        cmbCriterio.getSelectedIndex());

                ejecutando = false;

                DocumentosServicio.mostrar(tblDocumentos);

            }).start();
        }
    }

    // ==========================================
    // QUICK SORT
    // ==========================================

    private void btnOrdenarRapidoClick(ActionEvent evt) {

        if (cmbCriterio.getSelectedIndex() >= 0) {

            Util.iniciarCronometro();

            ejecutando = true;

            // CRONÓMETRO

            new Thread(() -> {

                while (ejecutando) {

                    Util.pausarMilisegundos(100);

                    txtTiempo.setText(
                            Util.getTextoTiempoCronometro());
                }

            }).start();

            // ORDENAMIENTO

            new Thread(() -> {

                DocumentosServicio.ordenarRapido(
                        cmbCriterio.getSelectedIndex());

                ejecutando = false;

                DocumentosServicio.mostrar(tblDocumentos);

            }).start();
        }
    }

    // ==========================================
    // MEZCLA RECURSIVO (MERGE SORT)
    // ==========================================

    private void btnOrdenarInsercionClick(ActionEvent evt) {

        if (cmbCriterio.getSelectedIndex() >= 0) {

            Util.iniciarCronometro();

            ejecutando = true;

            // CRONÓMETRO

            new Thread(() -> {

                while (ejecutando) {

                    Util.pausarMilisegundos(100);

                    txtTiempo.setText(
                            Util.getTextoTiempoCronometro());
                }

            }).start();

            // ORDENAMIENTO

            new Thread(() -> {

                DocumentosServicio.ordenarMezcla(
                        cmbCriterio.getSelectedIndex());

                ejecutando = false;

                DocumentosServicio.mostrar(tblDocumentos);

            }).start();
        }
    }

    // ==========================================
    // BUSCAR
    // ==========================================

    private void btnBuscar(ActionEvent evt) {

    }

}