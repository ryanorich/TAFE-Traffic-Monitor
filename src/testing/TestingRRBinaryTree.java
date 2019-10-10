package testing;

import java.util.Random;

import library.RRBinaryTree;
import library.RRCompare;
import testing.TestClass;

public  class TestingRRBinaryTree
{
    static Random rnd = new Random();

    public static void main(String[] args)
    {
        System.out.println("Testing RRBinaryTree binary trees");
        System.out.println("In, Pre, and Post Order diaplays the list in that order");
        System.out.println("Indexed diaplays the elemetns of the binary tree in order of the elemens indexed position");
        System.out.println("Max Depth represents the largest number of steps from the head to reach any notes");
        
        //Crate comparison objects
        class cmp1 implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                // sort by number, then by string.
                return (a.i == b.i ? ( a.s.compareTo(b.s)) : a.i > b.i ? 1 : -1);
            };
        }

        
        //Crate Binary Tree
        RRBinaryTree<TestClass> rrBT = new RRBinaryTree<TestClass>(new cmp1());
        
        //Adding head
        TestClass tc;
        tc = getRandom();
        
        System.out.println("\nMax Depth: "+rrBT.getMaxDepth());
        
        System.out.println("\nAdding head element:  "+tc);
        rrBT.add(tc);
        
        //Retrieving head
        System.out.println("Getting head element: "+rrBT.getHead());
        System.out.println("Max Depth: "+rrBT.getMaxDepth());
        
        
        tc = getRandom();
        System.out.println("\nAdding element: "+tc);
        rrBT.add(tc);
        
        System.out.println("\nCurrent State of binary tree:\n----------------------------");
        System.out.println("InOrder  : "+rrBT.getInOrder());
        System.out.println("PreOrder : "+rrBT.getPreOrder());
        System.out.println("PostOrder: "+rrBT.getPostOrder());
        System.out.println("Indexed  : "+rrBT.getIndexed());
        
        System.out.println("\nAdding random elements to the tree");
        for (int i  = 0; i < 10; i++)
        {
            tc = getRandom();
            System.out.println("\nAdding node: "+tc);
            rrBT.add(tc); 
            System.out.println("Max Depth:   "+rrBT.getMaxDepth());
        }
        
        System.out.println("\nCurrent State of binary tree:\n----------------------------");
        System.out.println("InOrder  : "+rrBT.getInOrder());
        System.out.println("PreOrder : "+rrBT.getPreOrder());
        System.out.println("PostOrder: "+rrBT.getPostOrder());
        System.out.println("Indexed  : "+rrBT.getIndexed());
        
        System.out.println("\nTesting for search");
        tc = getRandom();
        System.out.println("Inserting : "+tc);
        rrBT.add(tc);
        System.out.println("Retrieved when searching for "+tc+" : "+rrBT.search(tc));
        tc = getRandom();
        System.out.println("Not inserting");
        System.out.println("Retrieved when searching for "+tc+" : "+rrBT.search(tc));
    }
    
    /**
     * Generates a random TestClass object
     * @return TestClass instance with randomised content
     */
    private static TestClass getRandom()
    {
        int num;
        String str;
        num = rnd.nextInt(10);
        str = "" + (char) ((int) 'a' + rnd.nextInt(26));
        return new TestClass(num, str);
    }
}
