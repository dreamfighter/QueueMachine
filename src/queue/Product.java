package queue;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.io.Serializable;

public class Product implements Serializable {
    int no;
    String name;
    int count;
    int time;

    private transient TextField tfName;
    private transient TextField tfCount;
    private transient TextField tfTime;

    public Product(int no, String name, final int count, int time) {
        this.no = no;
        this.name = name;
        this.count = count;
        this.time = time;

        tfName = new TextField();
        tfName.setText("P"+no);
        tfName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                Product.this.name = tfName.getText();
            }
        });

        tfCount = new TextField();
        tfCount.setText(""+count);
        tfCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfCount.setText(newValue.replaceAll("[^\\d]", ""));
                }
                Product.this.count = Integer.parseInt(tfCount.getText());
            }
        });

        tfTime = new TextField();
        tfTime.setText(""+time);
        tfTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
                Product.this.time = Integer.parseInt(tfTime.getText());
            }
        });
    }

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TextField getTfName() {
        return tfName;
    }

    public void setTfName(TextField tfName) {
        this.tfName = tfName;
    }

    public TextField getTfCount() {
        return tfCount;
    }

    public void setTfCount(TextField tfCount) {
        this.tfCount = tfCount;
    }

    public TextField getTfTime() {
        return tfTime;
    }

    public void setTfTime(TextField tfTime) {
        this.tfTime = tfTime;
    }
}
