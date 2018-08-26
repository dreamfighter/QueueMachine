package queue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializableModel implements Serializable {
    private List<SerializableProduct> products = new ArrayList<SerializableProduct>();
    private int capacity = 0;
    private int timeTahap1 = 2;
    private int timeTahap3 = 10;

    public List<SerializableProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SerializableProduct> products) {
        this.products = products;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTimeTahap1() {
        return timeTahap1;
    }

    public void setTimeTahap1(int timeTahap1) {
        this.timeTahap1 = timeTahap1;
    }

    public int getTimeTahap3() {
        return timeTahap3;
    }

    public void setTimeTahap3(int timeTahap3) {
        this.timeTahap3 = timeTahap3;
    }
}
