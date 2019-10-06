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
    
    /**
     * Consgtructor, containing routines for testing Linked Lists
     */
    public TestingRRLinkedLists()
    {
        RRLinkedList<TestClass> rrll = new RRLinkedList<TestClass>();

        System.out.println("Testing for RRLinkedList Linked Lists");
        TestClass tc;

        System.out.println("\nAdding to the end of the list");
        for (int i = 0; i < 8; i++)
        {
            tc = getRandom();
            System.out.println("Adding " + tc);

            rrll.add(tc);
            rrll.printList();
        }
        
        System.out.println("\nTesting Removing from end of the list");
        
        for (int i = 0; i < 3; i++)
        {
            System.out.println("Removing Node");
            rrll.remove();
            rrll.printList();
        }
        
        System.out.println("\nTesting Set and Get");
        tc = getRandom();
        System.out.println("Setting at index [0]:   "+tc);
        rrll.set(0, tc);
        System.out.println("Getting from index [0]: "+ rrll.get(0));
        
        tc = getRandom();
        System.out.println("Setting at index [1]:   "+tc);
        rrll.set(1, tc);
        System.out.println("Getting from index [1]: "+ rrll.get(1));
        
        tc = getRandom();
        System.out.println("Setting at index [2]:   "+tc);
        rrll.set(2, tc);
        System.out.println("Getting from index [2]: "+ rrll.get(2));
        
        System.out.println("\nCurrent List:");
        rrll.printList();
        System.out.println("\nTabulated List:");
        rrll.printList2();
        
        System.out.println("\nTesting indexed removal");
        System.out.println("Removing indes [0]:");
        rrll.remove(0);
        rrll.printList();
        System.out.println("Removing last element:");
        rrll.remove(rrll.getCount()-1);
        rrll.printList();
        System.out.println("Removing form index [2]:");
        rrll.remove(2);
        rrll.printList();
        System.out.println("\nTabulated List:");
        rrll.printList2();
        
        System.out.println("\nTesting indexed addition");
        System.out.println("Adding at index [0]:");
        rrll.add(0,getRandom());
        rrll.printList();
        System.out.println("Adding at end of list:");
        rrll.add(rrll.getCount(), getRandom());
        rrll.printList();
        System.out.println("Adding at index [2]:");
        rrll.add(2,getRandom());
        rrll.printList();
        System.out.println("\nTabulated List:");
        rrll.printList2();
    }
    
    /**
     * Generates a random TestClass object
     * @return TestClass instance with randomised content
     */
    private TestClass getRandom()
    {
        int num;
        String str;
        num = rnd.nextInt(10);
        str = "" + (char) ((int) 'a' + rnd.nextInt(26));
        return new TestClass(num, str);
    }
}
