import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Model2 {
	public static void main(String[] args) {
		int[] products = {6,4};
        int capacity = 4;
        int productSum = 0;
        int batch = 0;
        int dueDate = 60;
        int setupTime = 1;
        int timeTahap3 = 10;
        Map<String,Integer> timeTahap2 = new HashMap<String,Integer>();
        int timeTahap1 = 2;
        timeTahap2.put("p1", 3);
        timeTahap2.put("p2", 4);
        
        for(int i=0;i<products.length;i++){
        	productSum += products[i];
        }
        
        batch = (int) Math.ceil((1.0 * productSum) / capacity);
        
        Map<Integer,Integer> tahap3 = new HashMap<Integer,Integer>();
        
        for(int i=0;i<batch;i++){
        	int time = dueDate - (((i+1) * timeTahap3) + (setupTime * i));
        	tahap3.put(i, time);
        	
        	System.out.println(i + "=" + time);
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
        }
        
        Map<Integer,Integer> tahap2 = new HashMap<Integer,Integer>();
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	Integer time = tahap3.get(joinBatch.get(i)) - timeTahap2.get(p.name) * p.count;
        	tahap2.put(i, time);
        	System.out.println(i + "=" + time);
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
        }
        
        Map<Integer,Integer> aft = new HashMap<Integer,Integer>();
        int total = 0;
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	int time = p.count * (dueDate - tahap1.get(i));
        	total +=time;
        	aft.put(i, time);
        	System.out.println(i + "=" + time);
        }
        
        System.out.println("Total=" + total);
        
    }
}
