package testing;

import java.util.Random;
import library.RRLinkedList;

/**
 * Testing the operation of the RRLinkedList class, using
 * TestClass objects.
 * 
 * @author Ryan Rich
 *
 */
public class TestingRRLinkedLists
{
    Random rnd = new Random();

    public static void main(String[] args)
    {
        new TestingRRLinkedLists();
    }

    public TestingRRLinkedLists()
    {
        RRLinkedList<TestClass> rrll = new RRLinkedList<TestClass>();

        System.out.println("Testing for Linked Lists");
        TestClass tc;

        System.out.println("--Testing Adding to end");
        for (int i = 0; i < 10; i++)
        {
            tc = getRandom();
            System.out.println("Adding " + tc);

            rrll.add(tc);
            rrll.printList();
        }
        
        System.out.println("--Testing Removing from end");
        
        for (int i = 0; i < 3; i++)
        {
            System.out.println("Removing Node");
            rrll.remove();
            rrll.printList();
        }
        
        System.out.println("--Testing Set and Get");
        tc = getRandom();
        System.out.println("Setting 2 - "+tc);
        rrll.set(2, tc);
        System.out.println("Getting 2 - "+ rrll.get(2));
        rrll.printList();
        rrll.printList2();
        
        System.out.println("--Testing indexed removal");
        System.out.println("Removing first");
        rrll.remove(0);
        rrll.printList();
        System.out.println("Removing last");
        rrll.remove(rrll.getCount()-1);
        rrll.printList();
        System.out.println("Removing @2");
        rrll.remove(2);
        rrll.printList();
        rrll.printList2();
        
        System.out.println("--Testing indexed addition");
        System.out.println("Adding first");
        rrll.add(0,getRandom());
        rrll.printList();
        System.out.println("Adding last");
        rrll.add(rrll.getCount(), getRandom());
        rrll.printList();
        System.out.println("Adding @ 2");
        rrll.add(2,getRandom());
        rrll.printList();
        rrll.printList2();
    }
    private TestClass getRandom()
    {

        int num;
        String str;
        num = rnd.nextInt(10);
        str = "" + (char) ((int) 'a' + rnd.nextInt(26));
        return new TestClass(num, str);
    }
}
