package queue;

import java.util.ArrayList;
import java.util.List;

public class GeneratorUtil {
    // Function prints all combinations of numbers 1, 2, ...MAX_POINT
    // that sum up to n.
    // i is used in recursion keep track of index in arr[] where next
    // element is to be added. Initital value of i must be passed as 0
    public void printCompositions(String name, int list[], int n, int i, int max, List<List<Product>> result)
    {
        //List<Integer> list = new ArrayList<Integer>();

        int MAX_POINT = max;
        if (n == 0)
        {
            result.add(printArray(name, list, i));
        }
        else if(n > 0)
        {
            for (int k = 1; k <= MAX_POINT; k++)
            {
                list[i] = k;
                //arr[i]= k;
                printCompositions(name, list, n-k, i+1, max, result);
            }
        }
    }

    // Utility function to print array arr[]
    public List<Product> printArray(String name,int list[], int m)
    {
        List<Product> result = new ArrayList<Product>();
        for (int i = 0; i < m; i++) {
                System.out.print(list[i] + " ");
                Product p = new Product(name,list[i]);
                result.add(p);
        }
        System.out.println();
        return result;
    }


    // Driver program
    public List<List<Product>> generate (String name, int n,int max)
    {
        //int n = 5;
        List<List<Product>> result = new ArrayList<List<Product>>();
        int size = 100;
        int[] arr = new int[size];
        //System.out.println("Different compositions formed by 1, 2 and 3 of "+ n + " are");
        System.out.println("capacity "+ max + " product count " + n);
        printCompositions(name, arr, n, 0, max, result);
        return result;
    }
}
