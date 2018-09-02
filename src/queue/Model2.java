package queue;

import ga.GeneticAlgorithms;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import swarm.FitnessFunction;
import swarm.Multiswarm;
import swarm.SwarmAlgorithms;

import java.io.Serializable;
import java.util.*;

public class Model2 implements Serializable {
    private List<Product> products = new ArrayList<Product>();
    private int capacity = 0;
    private int timeTahap1 = 2;
    private int timeTahap3 = 10;
    private int threshold;
    private int dueDate = 60;
    private TextArea textLogs;
    private Canvas canvas;

    private XYChart.Series[] channels;

    private int productSum = 0;

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

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setChannels(XYChart.Series[] channels) {
        this.channels = channels;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void process(){
        List<Product> queue = new ArrayList<Product>();
        final Map<String,Integer> timeTahap2 = new HashMap<String,Integer>();
        Map<String,List<List<Product>>> result = new HashMap<String, List<List<Product>>>();
        GeneratorUtil generator = new GeneratorUtil();
        //Random rand = new Random();
        productSum = 0;

        for(int i=0;i<products.size();i++){
            Product p = products.get(i);
            productSum += p.count;
            timeTahap2.put("p" + p.no, p.time);
            result.put("p" + p.no,generator.generate("p" + p.no, p.count, capacity));
        }

        /*
        queue.add(new Product("p2",1));
        queue.add(new Product("p1",4));
        queue.add(new Product("p2",2));
        queue.add(new Product("p2",1));
        queue.add(new Product("p1",2));

        processing(queue,timeTahap2, productSum, true);
        */


        GeneticAlgorithms ga = new GeneticAlgorithms();
        ga.setFitnessFunction(new ga.FitnessFunction() {
            @Override
            public int calculateFitness(List<Product> products) {
                return processing(products, timeTahap2, productSum, false);
            }

            @Override
            public void minimum(int min, List<Product> products) {
                processing(products, timeTahap2, productSum, true);

                textLogs.appendText("Minimum AFT=" + min);
            }

            @Override
            public void bestFitness(final int seq, final int min) {
                Platform.runLater(new Runnable() {

                    public void run() {
                        channels[0].getData().add(new XYChart.Data("" + seq, min));
                    }
                });
            }
        });

        ga.init(result, threshold);


        /*
        for(List<List<Product>> list :result.values()){
            int  n = rand.nextInt(list.size());
            queue.addAll(list.get(n));
        }
        */

        //processing(queue, timeTahap2, productSum);

        /*
        SwarmAlgorithms swarmAlgorithms = new SwarmAlgorithms();
        swarmAlgorithms.setEvaluation(new SwarmAlgorithms.Evaluation() {
            @Override
            public int calculateFitness(List<Product> products) {
                return processing(products, timeTahap2, productSum, false);
            }

            @Override
            public void minimum(int min, List<Product> products) {

                processing(products, timeTahap2, productSum, true);

                textLogs.appendText("Minimum AFT=" + min);
                textLogs.appendText("\n");
            }
        });

        swarmAlgorithms.initialization(result);
        Multiswarm multiswarm = new Multiswarm(3, 5, new FitnessFunction() {
            @Override
            public double getFitness(long[] particlePosition) {
                Long sum = 0l;
                for(int i=0;i<particlePosition.length;i++){
                    sum += particlePosition[i];

                }
                return sum / particlePosition.length;
            }
        });

        multiswarm.mainLoop();
        */
    }

    public int processing(List<Product> queue,Map<String,Integer> timeTahap2, int productSum, boolean draw) {
		//int[] products = {6,4};
        //int capacity = 4;

        //int batch = 0;
        //int dueDate = 60;
        int setupTime = 1;
        
        //batch = (int) Math.ceil((1.0 * productSum) / capacity);
        

        /*
        queue.add(new Product("p2",1));
        queue.add(new Product("p1",3));
        queue.add(new Product("p2",3));
        queue.add(new Product("p1",3));
        */

        
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
            System.out.println("Q" + i + "=" + index + "=>[" + p.name + "," + p.count + "]");
            textLogs.appendText("Q" + i + "=" + index + "=>[" + p.name + "," + p.count + "]");
            textLogs.appendText("\n");
        }

        Map<Integer,Integer> tahap3 = new HashMap<Integer,Integer>();

        for(int i=0;i<index + 1;i++){
            int time = dueDate - (((i+1) * timeTahap3) + (setupTime * i));
            tahap3.put(i, time);

            //System.out.println("T3" + i + "=" + time);
            textLogs.appendText("T3" + i + "=" + time);
            textLogs.appendText("\n");
        }
        
        Map<Integer,Integer> tahap2 = new HashMap<Integer,Integer>();
        for(int i=0;i<queue.size();i++){
        	Product p = queue.get(i);

            //System.out.println("tahap3-" + i + "=" + joinBatch.get(i) + ":"+ tahap3.get(joinBatch.get(i)));

        	Integer time = tahap3.get(joinBatch.get(i)) - timeTahap2.get(p.name) * p.count;
        	tahap2.put(i, time);
        	//System.out.println(i + "=" + time);
            textLogs.appendText(i + "=" + time);
            textLogs.appendText("\n");
        }
        
        Map<Integer,Integer> tahap1 = new HashMap<Integer,Integer>();
        for(int i=0;i<queue.size();i++){
        	Product p = queue.get(i);
        	Integer time = 0;
        	if(i==0){
        		time = tahap2.get(i) - (timeTahap1 * p.count);
        	}else{
        		int time1 = tahap1.get(i-1) - (timeTahap1 * p.count) - 1;
        		int time2 = tahap2.get(i) - (timeTahap1 * p.count);
        		//System.out.println("min("+time1+","+time2+")");
        		time = Math.min(time1, time2);
        	}
        	
        	tahap1.put(i, time);
        	//System.out.println(i + "=" + time);
            textLogs.appendText(i + "=" + time);
            textLogs.appendText("\n");
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Map<Integer,Integer> aft = new HashMap<Integer,Integer>();
        int total = 0;
        for(int i=0;i< queue.size();i++){
        	Product p = queue.get(i);
        	int time = p.count * (dueDate - tahap1.get(i));
        	total += time;
        	aft.put(i, time);
        	System.out.println(i + "=" + time);
            textLogs.appendText("AFT[" + i + "]=" + time);
            textLogs.appendText("\n");
        }

        if(draw) {
            drawShapes(gc, queue, tahap1, tahap2, tahap3, timeTahap2,joinBatch);
        }
        System.out.println("Total=" + total);

        textLogs.appendText("Total=" + total);
        textLogs.appendText("\n");

        return total;
    }

    private void drawShapes(GraphicsContext gc, List<Product> queue, Map<Integer,Integer> tahap1, Map<Integer,Integer> tahap2,
                            Map<Integer,Integer> tahap3, Map<String,Integer> timeTahap2,Map<Integer,Integer> joinBatch) {
        Map<Integer,Color> colors = new HashMap<Integer, Color>();
        Random rand = new Random();
        for(Integer t1:tahap1.keySet()) {
            double red = 1.0 * rand.nextInt(10)/10;
            double green = 1.0 * rand.nextInt(10)/10;
            double blue = 1.0 * rand.nextInt(10)/10;

            colors.put(t1,new Color(red, green, blue,1.0f));
        }
        //gc.setStroke(Color.BLUE);

        int min = Integer.MAX_VALUE;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for(Integer t1:tahap1.keySet()) {
            if(min > tahap1.get(t1)){
                min = tahap1.get(t1);
            }
        }

        int height = 20;
        int width = 20;

        for(Integer t1:tahap1.keySet()) {
            Product p = queue.get(t1);
            int top = ((t1 + 1) * height) + 50;
            int left = 10 + ((tahap1.get(t1) - min) * width);
            gc.setFill(colors.get(t1));
            gc.fillRoundRect(left, top, p.count * timeTahap1 * width, height, 5, 5);
            gc.setFill(Color.BLACK);
            gc.fillText("T1" ,left, top + 15);
        }

        for(Integer t2:tahap2.keySet()) {
            Product p = queue.get(t2);
            int top = ((t2 + 1) * height) + 50;
            int left = 10 + ((tahap2.get(t2) - min) * width);
            gc.setFill(colors.get(t2));
            gc.fillRoundRect(left, top, timeTahap2.get(p.name) * p.count * width, height, 5, 5);
            gc.setFill(Color.BLACK);
            gc.fillText("T2" ,left, top + 15);
        }

        for(Integer t3:tahap2.keySet()) {
            Product p = queue.get(t3);
            int b = joinBatch.get(t3);
            int top = ((t3 + 1) * height) + 50;
            int left = 10 + ((tahap3.get(b) - min) * width);
            gc.setFill(colors.get(t3));
            gc.fillRoundRect(left, top, timeTahap3 * width, height, 5, 5);
            gc.setFill(Color.BLACK);
            gc.fillText("T3" ,left, top + 15);
        }

        gc.setFill(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(0, canvas.getHeight()-15, canvas.getWidth(), canvas.getHeight()-15);

        for(int i=0;i<60;i++){
            int left = 10 + (width * i);
            gc.strokeLine(left, canvas.getHeight()-15, left, canvas.getHeight()-10);
            gc.fillText("" + (i + min),left-5,canvas.getHeight());
        }

    }
}
