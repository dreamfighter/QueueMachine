package queue;

import javafx.scene.control.TextArea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model2 implements Serializable {
    private List<Product> products = new ArrayList<Product>();
    private int capacity = 0;
    private int timeTahap1 = 2;
    private int timeTahap3 = 10;
    private TextArea textLogs;

    public void setTextLogs(TextArea textLogs) {
        this.textLogs = textLogs;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getTimeTahap1() {
        return timeTahap1;
    }

    public int getTimeTahap3() {
        return timeTahap3;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public void setTimeTahap1(int timeTahap1) {
        this.timeTahap1 = timeTahap1;
    }

    public void setTimeTahap3(int timeTahap3) {
        this.timeTahap3 = timeTahap3;
    }

    public void process() {
		//int[] products = {6,4};
        //int capacity = 4;
        int productSum = 0;
        int batch = 0;
        int dueDate = 60;
        int setupTime = 1;

        Map<String,Integer> timeTahap2 = new HashMap<String,Integer>();
        //timeTahap2.put("p1", 3);
        //timeTahap2.put("p2", 4);
        
        for(int i=0;i<products.size();i++){
            Product p = products.get(i);
        	productSum += p.count;
            timeTahap2.put("p" + p.no, p.time);
        }
        
        batch = (int) Math.ceil((1.0 * productSum) / capacity);
        
        Map<Integer,Integer> tahap3 = new HashMap<Integer,Integer>();
        
        for(int i=0;i<batch;i++){
        	int time = dueDate - (((i+1) * timeTahap3) + (setupTime * i));
        	tahap3.put(i, time);
        	
        	System.out.println(i + "=" + time);
            textLogs.appendText(i + "=" + time);
            textLogs.appendText("\n");
        }
        
        List<Product> queue = new ArrayList<Product>();
        queue.add(new Product("p2",1));
        queue.add(new Product("p1",3));
        queue.add(new Product("p2",3));
        queue.add(new Product("p1",3));

        
        Map<Integer,Integer> joinBatch = new HashMap<Integer,Integer>();
        int index = 0;
        int sum = 0;
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	if(sum + p.count > capacity){
        		index++;
        		sum = 0;
        	}
        	sum += p.count;
        	
        	joinBatch.put(i, index);

            textLogs.appendText("Q" + i + "=" + index + "=>" + p.count);
            textLogs.appendText("\n");
        }
        
        Map<Integer,Integer> tahap2 = new HashMap<Integer,Integer>();
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	Integer time = tahap3.get(joinBatch.get(i)) - timeTahap2.get(p.name) * p.count;
        	tahap2.put(i, time);
        	System.out.println(i + "=" + time);
            textLogs.appendText(i + "=" + time);
            textLogs.appendText("\n");
        }
        
        Map<Integer,Integer> tahap1 = new HashMap<Integer,Integer>();
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	Integer time = 0;
        	if(i==0){
        		time = tahap2.get(i) - (timeTahap1 * p.count);
        	}else{
        		int time1 = tahap1.get(i-1) - (timeTahap1 * p.count) - 1;
        		int time2 = tahap2.get(i) - (timeTahap1 * p.count);
        		System.out.println("min("+time1+","+time2+")");
        		time = Math.min(time1, time2);
        	}
        	
        	tahap1.put(i, time);
        	System.out.println(i + "=" + time);
            textLogs.appendText(i + "=" + time);
            textLogs.appendText("\n");
        }
        
        Map<Integer,Integer> aft = new HashMap<Integer,Integer>();
        int total = 0;
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	int time = p.count * (dueDate - tahap1.get(i));
        	total +=time;
        	aft.put(i, time);
        	System.out.println(i + "=" + time);
            textLogs.appendText("AFT[" + i + "]=" + time);
            textLogs.appendText("\n");
        }
        
        System.out.println("Total=" + total);

        textLogs.appendText("Total=" + total);
    }
}
