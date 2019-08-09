package testing;

import java.util.Random;

import library.RRBinaryTree;
import library.RRCompare;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import testing.TestClass;

public  class TestingRRBinaryTree
{
    static Random rnd = new Random();

    public static void main(String[] args)
    {
        
        //Crate comparison objects
        class cmp1 implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                // sort by number, then by string.
                return (a.i == b.i ? ( a.s.compareTo(b.s)) : a.i > b.i ? 1 : -1);
            };
        }
 /*
        class cmp2 implements RRCompare<TestClass>
        {
            public int compare(TestClass a, TestClass b)
            {
                return a.s.compareTo(b.s);
            };
        }
        */
        
        //Crate Binary Tree
        RRBinaryTree<TestClass> rrBT = new RRBinaryTree<TestClass>(new cmp1());
        
        //Adding head
        TestClass tc;
        tc = getRandom();
        System.out.println("Adding head "+tc);
        rrBT.add(tc);
        
        //Retrieving head
        System.out.println("Getting head "+rrBT.getHead());

        
        tc = getRandom();
        System.out.println("Adding node "+tc);
        rrBT.add(tc);
        
        System.out.println("InOrder  : "+rrBT.getInOrder());
        System.out.println("PreOrder : "+rrBT.getPreOrder());
        System.out.println("PostOrder: "+rrBT.getPostOrder());
        
        for (int i  = 0; i < 10; i++)
        {
            tc = getRandom();
            System.out.println("Adding node "+tc);
            rrBT.add(tc); 
            System.out.println("Max Depth: "+rrBT.getMaxDepth());
        }
        
        System.out.println("InOrder  : "+rrBT.getInOrder());
        System.out.println("PreOrder : "+rrBT.getPreOrder());
        System.out.println("PostOrder: "+rrBT.getPostOrder());
        System.out.println("Indexed  : "+rrBT.getIndexed());
        
        

    }
    
    private static TestClass getRandom()
    {

        int num;
        String str;
        num = rnd.nextInt(10);
        str = "" + (char) ((int) 'a' + rnd.nextInt(26));
        return new TestClass(num, str);
    }
    
    

}
