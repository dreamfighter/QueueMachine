package queue;

import java.io.Serializable;

public class SerializableProduct implements Serializable {
    int no;
    String name;
    int count;
    int time;

    public SerializableProduct(int no, String name, final int count, int time){
        this.no = no;
        this.name = name;
        this.count = count;
        this.time = time;
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
}
