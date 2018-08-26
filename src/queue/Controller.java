package queue;

import com.sun.javafx.stage.StageHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.*;

public class Controller {

    @FXML
    private TextField tfJumlahProduct;

    @FXML
    private TextField tfCapacity;

    @FXML
    private TextField tfT1;

    @FXML
    private TextField tfT3;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private Button btnStart;

    @FXML
    private TextArea textLogs;


    @FXML
    public void initialize() {
        File file = new File("model.mdl");
        try {
            if(file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                SerializableModel model = (SerializableModel) ois.readObject();
                tfCapacity.setText(String.valueOf(model.getCapacity()));
                tfT1.setText(String.valueOf(model.getTimeTahap1()));
                tfT3.setText(String.valueOf(model.getTimeTahap3()));
                tfJumlahProduct.setText(String.valueOf(model.getProducts().size()));
                ois.close();

                tableProducts.getItems().clear();
                for(int i=0;i<model.getProducts().size();i++) {
                    SerializableProduct p = model.getProducts().get(i);
                    tableProducts.getItems().add(new Product(p.no,p.name, p.count,p.time));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        TableColumn<Product,String> columnNo = new TableColumn<Product, String>("No");
        columnNo.setCellValueFactory(new PropertyValueFactory<Product,String>("no"));
        columnNo.setMaxWidth(50);
        tableProducts.getColumns().set(0,columnNo);

        TableColumn<Product,TextField> columnName = new TableColumn<Product, TextField>("Name");
        columnName.setCellValueFactory(new PropertyValueFactory<Product,TextField>("tfName"));
        columnName.setMaxWidth(100);
        tableProducts.getColumns().set(1,columnName);

        TableColumn<Product,TextField> columnCount = new TableColumn<Product, TextField>("Jumlah");
        columnCount.setCellValueFactory(new PropertyValueFactory<Product,TextField>("tfCount"));
        columnCount.setMaxWidth(100);
        tableProducts.getColumns().set(2,columnCount);

        TableColumn<Product,TextField> columnTime = new TableColumn<Product, TextField>("Waktu Process Tahap 2");
        columnTime.setCellValueFactory(new PropertyValueFactory<Product,TextField>("tfTime"));
        columnTime.setMaxWidth(200);
        tableProducts.getColumns().set(3,columnTime);

        tfJumlahProduct.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfJumlahProduct.setText(newValue.replaceAll("[^\\d]", ""));
                }else{
                    int count = Integer.parseInt(tfJumlahProduct.getText());
                    tableProducts.getItems().clear();
                    for(int i=0;i<count;i++) {
                        tableProducts.getItems().add(new Product(i+1,"", 0,0));
                    }
                }
            }
        });

        tfCapacity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfCapacity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tfT1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfT1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tfT3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfT3.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Model2 mdl2 = new Model2();
                mdl2.setTextLogs(textLogs);
                try {
                    mdl2.setCapacity(Integer.parseInt(tfCapacity.getText()));
                    mdl2.setTimeTahap1(Integer.parseInt(tfT1.getText()));
                    mdl2.setTimeTahap1(Integer.parseInt(tfT1.getText()));

                    SerializableModel model = new SerializableModel();
                    model.setCapacity(Integer.parseInt(tfCapacity.getText()));
                    model.setTimeTahap1(Integer.parseInt(tfT1.getText()));
                    model.setTimeTahap3(Integer.parseInt(tfT3.getText()));
                    for(Product p:tableProducts.getItems()){
                        mdl2.getProducts().add(new Product(p.no,p.name,p.count,p.time));
                        model.getProducts().add(new SerializableProduct(p.no,p.name,p.count,p.time));
                    }

                    File file = new File("model.mdl");
                    FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    objectOut.writeObject(model);
                    objectOut.close();
                    System.out.println("The Object  was succesfully written to a file");



                } catch (Exception ex) {

                    ex.printStackTrace();

                }

                mdl2.process();

            }
        });
    }
}
