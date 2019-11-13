package interfaces;

import controladores.Gestor;
import entidades.Agrupacion;
import entidades.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class PrincipalController {
    public TextField txtRutaPostulaciones;
    public TextField txtRutaRegiones;
    public TextField txtRutaConteos;

    public Button contabilizarTotales;
    public ComboBox cmbDistrito;
    public ComboBox cmbSeccion;
    public ComboBox cmbCircuito;

    @FXML
    private TableView tabla;

    private static Gestor controlador;


    @FXML
    public void btnCargarRegionesClick(ActionEvent actionEvent){ cargarArchivo(txtRutaRegiones);
    }
    @FXML
    public void btnCargarPostulacionesClick(ActionEvent actionEvent){ cargarArchivo(txtRutaPostulaciones); }
    @FXML
    public void btnCargarConteosClick(ActionEvent actionEvent){ cargarArchivo(txtRutaConteos); }
    @FXML
    private void cargarArchivo(TextField txtRuta){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos de texto", "*.dsv"));
        File seleccionado = fc.showOpenDialog(null);
        if (seleccionado != null) {
            txtRuta.setText(seleccionado.getAbsolutePath());
        }
    }

    @FXML
    private void btnImportarDatosClick(ActionEvent actionEvent) {
        if (txtRutaRegiones.getText().isEmpty() || txtRutaPostulaciones.getText().isEmpty() || txtRutaConteos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione la ruta de todos los archivos.");
        } else {
            cmbDistrito.setDisable(false);
            cmbSeccion.setDisable(false);
            cmbCircuito.setDisable(false);

            contabilizarTotales.setDisable(false);

            controlador = new Gestor();
            controlador.obtenerRegiones(txtRutaRegiones.getText());
            controlador.obtenerAgrupaciones(txtRutaPostulaciones.getText());
            controlador.contarVotos(txtRutaConteos.getText());

            cargarCombos(controlador.getRegiones().values());
            Collection agrup = controlador.getAgrupaciones().values();
            this.cargarTabla(agrup);
        }
    }
    private void cargarCombos(Collection regiones){
        Iterator it = regiones.iterator();
        while(it.hasNext()){
            Region distrito = (Region) it.next();
            cmbDistrito.getItems().add(distrito.getCodigo() + " " + distrito.getNombre());
            Iterator it2 = distrito.getSubregiones().values().iterator();
            while (it2.hasNext()){
                Region seccion = (Region) it2.next();
                cmbSeccion.getItems().add(seccion.getCodigo() + " " + seccion.getNombre());
                Iterator it3 = seccion.getSubregiones().values().iterator();
                while (it3.hasNext()){
                    Region circuito = (Region) it3.next();
                    cmbCircuito.getItems().add(circuito.getCodigo() + " " + circuito.getNombre());
                }
            }
        }
    }

    public void comboDistritoSeleccion(ActionEvent actionEvent){
        if(cmbDistrito.getValue() != null){
            cmbSeccion.getItems().clear();
            cmbSeccion.setDisable(false);
            cmbCircuito.getItems().clear();

            String[] s = cmbDistrito.getValue().toString().split(" ");
            String cod = s[0];

            Collection<Region> collection = controlador.filtrarSecciones(cod);

            Iterator it = collection.iterator();
            while (it.hasNext()){
                Region seccion = (Region) it.next();
                cmbSeccion.getItems().add(seccion.getCodigo() + " " + seccion.getNombre());
                Iterator it2 = seccion.getSubregiones().values().iterator();
                while (it2.hasNext()){
                    Region circuito = (Region) it2.next();
                    cmbCircuito.getItems().add(circuito.getCodigo() + " " + circuito.getNombre());
                    Iterator it3 = circuito.getSubregiones().values().iterator();
                }
            }
            Region distrito_seleccionado = (Region) controlador.getRegiones().get(cod);
            this.cargarTabla(distrito_seleccionado.getAgrupaciones().values());
        }
    }


    public void comboSeccionSeleccionado(ActionEvent actionEvent){
        if(cmbSeccion.getValue() != null) {
            cmbCircuito.getItems().clear();
            cmbCircuito.setDisable(false);

            String[] s1 = cmbDistrito.getValue().toString().split(" ");
            String codDis = s1[0];

            String[] s2 = cmbSeccion.getValue().toString().split(" ");
            String codSec = s2[0];

            Collection<Region> collection = controlador.filtrarCircuitos(codDis, codSec);

            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                Region circuito = (Region) it2.next();
                cmbCircuito.getItems().add(circuito.getCodigo() + " " + circuito.getNombre());
            }
            Region distrito_seleccionado = (Region) controlador.getRegiones().get(codDis);
            Region seccion_seleccionada = (Region) distrito_seleccionado.getSubregiones().get(codSec);
            this.cargarTabla(seccion_seleccionada.getAgrupaciones().values());
        }
    }

    public void comboCircuitoSeleccion(ActionEvent actionEvent){
        if(cmbCircuito.getValue() != null) {

            String[] s1 = cmbDistrito.getValue().toString().split(" ");
            String codDis = s1[0];

            String[] s2 = cmbSeccion.getValue().toString().split(" ");
            String codSec = s2[0];

            String[] s3 = cmbCircuito.getValue().toString().split(" ");
            String codCir = s3[0];

            Region distrito_seleccionado = (Region) controlador.getRegiones().get(codDis);
            Region seccion_seleccionada = (Region) distrito_seleccionado.getSubregiones().get(codSec);
            Region circuito_seleccionado = (Region) seccion_seleccionada.getSubregiones().get(codCir);
            this.cargarTabla(circuito_seleccionado.getAgrupaciones().values());
        }

    }
    @FXML
    private void cargarTabla(Collection data){
        tabla.getColumns().clear();
        tabla.getItems().clear();
        ObservableList<Agrupacion> agrp = FXCollections.observableArrayList();
        Iterator it = data.iterator();
        while(it.hasNext()){
            agrp.add((Agrupacion) it.next());
        }
        TableColumn<Agrupacion, String> col1  = new TableColumn<>("Agrupación");
        col1.setCellValueFactory(new PropertyValueFactory<Agrupacion, String>("nombre"));
        TableColumn<Agrupacion, Integer> col2 = new TableColumn<>("Votos");
        col2.setCellValueFactory(new PropertyValueFactory<Agrupacion, Integer>("votos"));

        tabla.getColumns().addAll(col1, col2);
        tabla.getItems().addAll(agrp);
    }

    public void btnVerTotalesClick(ActionEvent actionEvent){
        cmbDistrito.getSelectionModel().clearSelection();
        cmbSeccion.getItems().clear();
        cmbCircuito.getItems().clear();;
        Collection agrup = controlador.getAgrupaciones().values();
        this.cargarTabla(agrup);
    }
}
