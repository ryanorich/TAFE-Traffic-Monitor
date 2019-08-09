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

    TestingRRArrays()
    {
        System.out.println("Testing Arrays");
        ArrayList<TestClass> in1 = new ArrayList<TestClass>();
        ArrayList<TestClass> in2 = new ArrayList<TestClass>();
        ArrayList<TestClass> in3 = new ArrayList<TestClass>();

        Random rnd = new Random();

        int num;
        String str;

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

        System.out.println("Testing INSERT:");
        System.out.println("In   : " + in1);

        // Using streight lambda expressions
        ArrayList<TestClass> out1a = library.AllSorts.RRSort(in1, AllSorts.sortType.INSERT,

                (a, b) -> ((TestClass) a).i == ((TestClass) b).i ? 0
                        : (((TestClass) a).i > ((TestClass) b).i ? 1 : -1));
        ArrayList<TestClass> out1b = library.AllSorts.RRSort(in1, AllSorts.sortType.INSERT,

                (a, b) -> ((TestClass) a).s.compareTo(((TestClass) b).s));

        System.out.println("Out1a: " + out1a);
        System.out.println("Outab: " + out1b);
        System.out.println("In   : " + in1);

        // Using comparison objects
        RRCompare<TestClass> cmp1 = (a, b) -> a.i == b.i ? 0 : a.i > b.i ? 1 : -1;
        RRCompare<TestClass> cmp2 = (a, b) -> a.s.compareTo(b.s);

        System.out.println("Testing SELECTION:");
        System.out.println("In    :" + in2);
        ArrayList<TestClass> out2a, out2b;
        out2a = AllSorts.RRSort(in2, sortType.SELECTION, cmp1);
        out2b = AllSorts.RRSort(in2, sortType.SELECTION, cmp2);

        System.out.println("Out2a: " + out2a);
        System.out.println("Out2b: " + out2b);
        System.out.println("In    :" + in2);

        // Implementing using classes that implement the c ompare function
        class cmp3a implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                return (a.i == b.i ? 0 : a.i > b.i ? 1 : -1);
            };
        }

        class cmp3b implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                return a.s.compareTo(b.s);
            };
        }

        System.out.println("Testing BUBBLE:");
        System.out.println("In    :" + in3);
        System.out.println("Out3a: " + AllSorts.RRSort(in3, sortType.BUBBLE, new cmp3a()));
        System.out.println("Out3b: " + AllSorts.RRSort(in3, sortType.BUBBLE, new cmp3b()));
        System.out.println("In    :" + in3);
    }
}
