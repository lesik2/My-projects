package com.example.bd_laba5_and6;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HelloController {

    private static ObservableList<Patient>patients;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> app_number;

    @FXML
    private TableColumn<?, ?> app_procedure_id;

    @FXML
    private TableColumn<?, ?> application_cause_of_call;

    @FXML
    private TableColumn<?, ?> application_data;

    @FXML
    private TableColumn<?, ?> application_patient_id;

    @FXML
    private TableColumn<?, ?> column_id_application;

    @FXML
    private TableColumn<Patient, Integer> column_id_patient;

    @FXML
    private TableColumn<?, ?> column_id_procedure;
    @FXML
    private ComboBox<String> chooser;
    @FXML
    private AnchorPane background;


    @FXML
    private TableColumn<Patient, String> column_patient_birth;

    @FXML
    private TableColumn<Patient, String> column_patient_fio;

    @FXML
    private TableColumn<Patient, String> column_patient_offer;

    @FXML
    private TableColumn<Patient, String> column_patient_phone;

    @FXML
    private TableColumn<?, ?> procedure_price;

    @FXML
    private TableColumn<?, ?> procedure_type_of_procedure;

    @FXML
    private TableView<?> table_application;

    @FXML
    private TableView<Patient> table_patient;

    @FXML
    private TableView<?> table_procedure;

    @FXML
    private TableColumn<?, ?> time;

    public HelloController() throws SQLException, ClassNotFoundException {


    }

    @FXML
    void  initialize() throws SQLException, ClassNotFoundException {
        patients=FXCollections.observableArrayList();
        addPatient();
        chooser.setValue("Пациенты");
       column_id_patient.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
       column_patient_fio.setCellValueFactory(new PropertyValueFactory<Patient,String>("fioPatient"));
       column_patient_birth.setCellValueFactory(new PropertyValueFactory<Patient,String>("data_of_birth"));
       column_patient_offer.setCellValueFactory(new PropertyValueFactory<Patient,String>("special_offer"));
       column_patient_phone.setCellValueFactory(new PropertyValueFactory<Patient,String>("phone"));
       table_patient.setItems(patients);

    }
    @FXML
    public static void  addPatient()throws SQLException,ClassNotFoundException
    {
        DatabaseHandler db=new DatabaseHandler();
        ResultSet Patient=db.selectPatient();
        while (Patient.next())
        {
            Patient patient=new Patient();
            patient.setIdPatient(Patient.getInt(1));
            patient.setFioPatient(Patient.getString(2));
            patient.setData_of_birth(Patient.getString(3));
            patient.setSpecial_offer(Patient.getString(4));
            patient.setPhone(Patient.getString(5));

            patients.add(patient);
        }

    }






}

