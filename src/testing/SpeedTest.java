package testing;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for testing the relative speed of using Array Lists
 * in methods, compared to casting to arrays to carry out work 
 * then back again.
 * 
 * @author ryan
 *
 */
public class SpeedTest
{
    int ITTERA = 2, ITTERB=10000000;
    SpeedTest()
    {
        ArrayList<testData> aL10 = new ArrayList<testData>();
        ArrayList<testData> aL100 = new ArrayList<testData>();
        ArrayList<testData> aL1000 = new ArrayList<testData>();
        
        int i = 0;
        long alStart, alFinish, arStart, arFinish;
        
        while (i<1000)
        {
            if (i<10)     aL10.add(new testData(i));
            if (i<100)   aL100.add(new testData(i));
            if (i<1000) aL1000.add(new testData(i));
            i++;
        }
        System.out.println("Sizes - 10:"+aL10.size()+" 100:"+aL100.size()+" 1000:"+aL1000.size());
        System.out.println("Items: " + aL10.get(9).s + " - " + aL100.get(99).s + " - " + aL1000.get(999).s);
    
        System.out.println("Shuffling AL");
        
        alStart = System.currentTimeMillis();
        for (int j = 1; j<ITTERA; j++)
        {
        ALShuffle(aL10);
        ALShuffle(aL100);
        ALShuffle(aL1000);
        }
        alFinish = System.currentTimeMillis();

        
        System.out.println("Shuffling AR");
        arStart = System.currentTimeMillis();
        
        for (int j = 1; j<ITTERA; j++)
        {
        aL10=ARShuffle(aL10);
        aL100 = ARShuffle(aL100);
        aL1000 = ARShuffle(aL1000);
        }
        
        arFinish = System.currentTimeMillis();
       
        System.out.println("Times:");
        System.out.println("----------------------------------------------");
        System.out.println("Array List Start: " + alStart + " Finish: " + alFinish + " Delta: " + (alFinish-alStart));
        System.out.println("Array      Start: " + arStart + " Finish: " + arFinish + " Delta: " + (arFinish-arStart));
        
    }

    
    private <T> void ALShuffle(ArrayList <T> al)
    {
        for (int j = 0; j<ITTERB; j++)
        {
        T t = al.get(0);
        for (int i = 0; i < al.size()-1; i++)
        {
            al.set(i, al.get(i+1));
        }
        al.set(al.size()-1, t);
        }
        
    }
    
    private <T> ArrayList<T>  ARShuffle(ArrayList<T> al)
    {
        @SuppressWarnings("unchecked")
        T[] ar = (T[])al.toArray();
        
        for (int j = 0; j<ITTERB; j++)
        {
        T t = ar[0];
        for (int i=0; i<ar.length -1; i++)
        {
            ar[i]=ar[i+1];
        }
        ar[ar.length-1]=t;
        }
        al = new ArrayList<T>(Arrays.asList(ar));
        return al;

    }
    
    public static void main(String[] args)
    {
        new SpeedTest();
    }
    

    public class testData
    {
        public int n = 0;
        public String s = "";
        
        public testData(int i)
        {
            n = i;
            s = ""+i;
        }
        
        public String toString()
        {
            return ("~"+s+"~");
        }
    }
    
    

}


