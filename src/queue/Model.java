package queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
	public static void main(String[] args) {
        boolean finish = false;
        int t = 0;
        List<Product> list = new ArrayList<Product>();
        list.add(new Product("p1",50));
        list.add(new Product("p2",40));
        
        int tMesinTahap1 = 2;
        Map<String,Integer> tMesinTahap2 = new HashMap<String,Integer>();
        tMesinTahap2.put("p1", 2);
        tMesinTahap2.put("p2", 3);
        
        Map<String,Integer> tahap2 = new HashMap<String,Integer>();
        
        int index = 0;
        int counter = 0;
        while(!finish){
            Product p = list.get(index);
            
            if(p.count>0) {
            	if(counter<tMesinTahap1-1){
            		counter++;
            	}else{
            		counter = 0;
            		p.count--;
            	}
                
                t++;    
            }else if(index < list.size()-1) {
                index++;
                continue;
            }else {
                finish = true;
            }
            
            
        }
        
        System.out.println("time=" + t);
    }
    
    public static class Product{
        String name;
        int count;
        
        public Product(String name,int count) {
            this.name = name;
            this.count = count;
        }
    }
}
