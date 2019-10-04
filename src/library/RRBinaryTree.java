package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import library.RRCompare;

/**
 * Binary Search Tree of objects
 * 
 * @author Ryan Rich
 *
 * @param <T> Object to be stored
 */
public class RRBinaryTree<T>
{
    BTNode<T> head;
    RRCompare<T> cmp;
    
    /**
     * Constructor
     * 
     * @param cmp Comparitor to use for sorting nodes.
     */
    public RRBinaryTree(RRCompare<T> cmp)
    {
        this.cmp = cmp;
    }
    
    /**
     * Adds the object to the binary tree
     * 
     * @param t Object to add
     */
    public void add(T t)
    {
        BTNode<T> node = new BTNode<T>(t);
        if (head == null)
        {
            head = node;
        }
        else
        {
            _add(head, node);
        }
    }
    
    /**
     * Private recursive function to traverse the tree for adding node
     * @param node      The node being traversed
     * @param newNode   The node to to be added
     */
    private void _add(BTNode<T> node, BTNode<T> newNode)
    {
        if ( cmp.compare(newNode.t, node.t) < 0)
        {//New Node less - add to left
            if (node.left == null)
            {// left node empty - assign
                node.left = newNode;
            }
            else
            {// left node populated, add to it
                _add(node.left, newNode);
            }
        }
        else
        {// new node greater or equal - add to right
            if (node.right == null)
            {//Right node empty = assign
                node.right = newNode;
            }
            else
            {//right node populated, add to it
                _add(node.right, newNode);
            }
        }
    }
    
    // TODO - Implement delete
    
    /**
     * Returns the root node object
     * 
     * @return The root node object
     */
    public T getHead()
    {
        return head.t;
    }
    
    /**
     * Returns and In-Order list of objects
     * 
     * @return List of objects.
     */
    public List<T> getInOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getInOrder(head, list);
    }
    
    /**
     * private recursive function for traversing the tree to populate the InOrder list
     * 
     * @param n     Node being traversed
     * @param list  List being populated
     * @return      The populated list
     */
    private List<T> _getInOrder(BTNode<T> n, List<T> list)
    {// In order - Left, Partent, Right
        if ( n == null)
        {//IF head / node is null, nothing to add
            return list;
        }
        
        if (n.left != null)
        {
            _getInOrder(n.left, list);
        }
        
        list.add(n.t);
        
        if (n.right != null)
        {
            _getInOrder(n.right, list);
        }

        return list;
    }
    
    /**
     * Returns a Pre-Order list of objects
     * 
     * @return List of objects.
     */
    public List<T> getPreOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getPreOrder(head, list);
        
    }
    
    /**
     * private recursive function for traversing the tree to populate the PreOrder list
     * 
     * @param n     Node being traversed
     * @param list  List being populated
     * @return      The populated list
     */
    private List<T> _getPreOrder(BTNode<T> n, List<T> list)
    {// Pre order - Parent, Left, Right
        if ( n == null)
        {//IF head / node is null, nothing to add
            return list;
        }
        
        list.add(n.t);
        
        if (n.left != null)
        {
            _getPreOrder(n.left, list);
        }
        
        
        
        if (n.right != null)
        {
            _getPreOrder(n.right, list);
        }

        return list;
    }
    
    /**
     * Returns a Post-Order list of objects
     * 
     * @return List of objects.
     */
    public List<T> getPostOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getPostOrder(head, list);
        
    }
    
    /**
     * private recursive function for traversing the tree to populate the PostOrder list
     * 
     * @param n     Node being traversed
     * @param list  List being populated
     * @return      The populated list
     */
    private List<T> _getPostOrder(BTNode<T> n, List<T> list)
    {// Post order - Left, Right, Parent
        if ( n == null)
        {//IF head / node is null, nothing to add
            return list;
        }
        
       
        
        if (n.left != null)
        {
            _getPostOrder(n.left, list);
        }

        
        if (n.right != null)
        {
            _getPostOrder(n.right, list);
        }
        list.add(n.t);

        return list;
    }
    
    /**
     * Returns the maximum depths of all nodes
     * 
     * @return  The maximum node depth
     */
    public int getMaxDepth()
    {
        if (head==null)
        {
            return -1;
        }
        //IF there is a head, min depth is 1.

        return _getMaxDepth(head, 0, 0);
    }
    
    /**
     * private recursive function for traversing the tree to find maximum depth
     * 
     * @param node      The node being traversed
     * @param depth     The current depth
     * @param maxDepth  The maximum depth found so far
     * @return          The maximum depth found
     */
    private int _getMaxDepth(BTNode<T> node, int depth, int maxDepth)
    {
        //Notes
        // Depth counts head as 0
        // Width = height = depth + 1
        // Number of elements is (n + n*n)/2 (sum of integers from 1 to n)
        
        if(node == null)
        {//Nothing here, report depth of last node and return
            
            return ((depth-1) > maxDepth) ? (depth-1) : maxDepth;
        }
        int d;
        d = _getMaxDepth(node.left, depth+1, maxDepth);
        if (d > maxDepth) maxDepth = d;
        
        d = _getMaxDepth(node.right, depth+1, maxDepth);
        if (d > maxDepth) maxDepth = d;
        
        return maxDepth;
    }
    
    /**
     * Creates and populates an ArrayList representing all possible positions for 
     * nodes with a maximum depth matching the tree's maximum depth.
     * 
     * @return The array with all nodes in their indexed locations.
     */
    public ArrayList<T> getIndexed_AL()
    {
        // TODO - Get largest index size for creating array, instead of maximum index for tree level
        int width = getMaxDepth() + 1;
        int noOfElements = (int) java.lang.Math.pow(2, width) - 1;
        ArrayList<T> list = new ArrayList<T>(noOfElements);
        for (int i = 0; i<noOfElements; i++)
        {
            list.add(null);
        }
        
        _fillIndexed_AL(list, head, 0);
        
        return list;
    }
    
    /**
     * private recursive function to populate the list with all nodes.
     * @param list  The list to be populated
     * @param node  The node being traversed
     * @param index The index of the node (head is index 0)
     */
    public void _fillIndexed_AL(ArrayList<T> list, BTNode<T> node, int index)
    {
        //System.out.println("Filling Tree, at index"+ index);
        list.set(index, node.t);
        if (node.left !=null)
        {
            _fillIndexed_AL(list, node.left, index*2+1);
        }
        if (node.right !=null)
        {
            _fillIndexed_AL(list, node.right, index*2+2);
        }
    }
    
    /**
     * Creates and populates a HashMap with all nodes stored with  
     * a key that represents the index of the node on a theoretical 
     * full tree, reading in layers, left to right, top to bottom.
     * e.g.         0
     *             / \
     *            1   2
     *           / \ / \
     *          3  4 5  6 ...etc
     * @return The array with all nodes in their indexed locations.
     */
    public HashMap<Integer,T> getIndexed()
    {
        int width = getMaxDepth() + 1;
        int noOfElements = (int) java.lang.Math.pow(2, width) - 1;
        
        if (noOfElements == 0) return null;
        HashMap<Integer,T> list = new HashMap<Integer, T>(noOfElements);
 
        _fillIndexed(list, head, 0);
       
        return list;
    }
    
    /**
     * private recursive function to populate the list with all nodes.
     * @param list  The list to be populated
     * @param node  The node being traversed
     * @param index The index of the node (head is index 0)
     */
    public void _fillIndexed(HashMap<Integer, T> list, BTNode<T> node, int index)
    {
        //System.out.println("Filling Tree, at index"+ index);
        list.put(index, node.t);
        if (node.left !=null)
        {
            _fillIndexed(list, node.left, index*2+1);
        }
        if (node.right !=null)
        {
            _fillIndexed(list, node.right, index*2+2);
        }
    }
    
    /**
     * Tree node, containing an object, and links to left/right nodes.
     * 
     * @author Ryan Rich
     *
     * @param <T> The object stored by the node.
     */
    @SuppressWarnings("hiding")
    private class BTNode<T>
    {
        T t;
        BTNode<T> left, right;
        BTNode(T t)
        {
            this.t=t;
        }
    }
}
