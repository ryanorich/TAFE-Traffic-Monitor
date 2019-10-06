package testing;

import java.util.ArrayList;
import java.util.Random;
import library.RRCompare;
import library.AllSorts.sortType;
import library.AllSorts;
import testing.TestClass;

/**
 * Testing the operation of the RRArrays methods, using 
 * the TestClass objects.
 * 
 * @author Ryan Rich
 *
 */
public class TestingRRArrays
{

    public static void main(String[] args)
    {
        new TestingRRArrays();
    }

    /**
     * Constructor containign testing routines for RRArrays sorting algorithms.
     */
    TestingRRArrays()
    {
        System.out.println("Testing RRArrays array sorting");
        System.out.println("Input represents an array to be sorted");
        System.out.println("Output 1 sortes the array by the integer");
        System.out.println("Output 2 sorts the array by the character");
        System.out.println("Input should be unchanged after the sort operation");
        ArrayList<TestClass> in1 = new ArrayList<TestClass>();
        ArrayList<TestClass> in2 = new ArrayList<TestClass>();
        ArrayList<TestClass> in3 = new ArrayList<TestClass>();

        Random rnd = new Random();

        int num;
        String str;

        // populate test arrays with random data
        for (int i = 0; i < 8; i++)
        {
            num = rnd.nextInt(10);
            str = "" + (char) ((int) 'a' + rnd.nextInt(26));
            in1.add(new TestClass(num, str));
            num = rnd.nextInt(10);
            str = "" + (char) ((int) 'a' + rnd.nextInt(26));
            in2.add(new TestClass(num, str));
            num = rnd.nextInt(10);
            str = "" + (char) ((int) 'a' + rnd.nextInt(26));
            in3.add(new TestClass(num, str));
        }

        System.out.println("\nTesting INSERT:");
        System.out.println("Input    : " + in1);

        // comparitors implemented using lambda expressions
        
        // compares integer component of TestClass
        ArrayList<TestClass> out1a = library.AllSorts.RRSort(in1, AllSorts.sortType.INSERT,

                (a, b) -> ((TestClass) a).i == ((TestClass) b).i ? 0
                        : (((TestClass) a).i > ((TestClass) b).i ? 1 : -1));
        
     // compares string component of TestClass
        ArrayList<TestClass> out1b = library.AllSorts.RRSort(in1, AllSorts.sortType.INSERT,

                (a, b) -> ((TestClass) a).s.compareTo(((TestClass) b).s));

        System.out.println("Output 1 : " + out1a);
        System.out.println("Output 2 : " + out1b);
        System.out.println("Input    : " + in1);

        // comparitors implemented using lambda objects
        RRCompare<TestClass> cmp1 = (a, b) -> a.i == b.i ? 0 : a.i > b.i ? 1 : -1;
        RRCompare<TestClass> cmp2 = (a, b) -> a.s.compareTo(b.s);

        System.out.println("\nTesting SELECTION:");
        System.out.println("Input    : " + in2);
        ArrayList<TestClass> out2a, out2b;
        out2a = AllSorts.RRSort(in2, sortType.SELECTION, cmp1);
        out2b = AllSorts.RRSort(in2, sortType.SELECTION, cmp2);

        System.out.println("Output 1 : " + out2a);
        System.out.println("Output 2 : " + out2b);
        System.out.println("Input    : " + in2);

        // comparitors implemented using classes that use the RRCompare interface and the compare function
        /**
         * Comparitor used for testing - compares based only on integer
         * component of TestClass.
         * @author Ryan Rich
         *
         */
        class cmp3a implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                return (a.i == b.i ? 0 : a.i > b.i ? 1 : -1);
            };
        }
        
        /**
         * Comparitor used for testing - compares based only on string
         * component of TestClass.
         * @author ryan
         *
         */
        class cmp3b implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                return a.s.compareTo(b.s);
            };
        }

        System.out.println("\nTesting BUBBLE:");
        System.out.println("Input    : " + in3);
        System.out.println("Output 1 : " + AllSorts.RRSort(in3, sortType.BUBBLE, new cmp3a()));
        System.out.println("Output 2 : " + AllSorts.RRSort(in3, sortType.BUBBLE, new cmp3b()));
        System.out.println("Input    : " + in3);
    }
}
