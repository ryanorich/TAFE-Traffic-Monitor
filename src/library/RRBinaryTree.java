package library;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import library.RRCompare;

public class RRBinaryTree<T>
{
    BTNode<T> head;
    RRCompare<T> cmp;
    
    public RRBinaryTree(RRCompare<T> cmp)
    {
        this.cmp = cmp;
    }
    
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
    
    public T getHead()
    {
        return head.t;
    }
    
    public List<T> getInOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getInOrder(head, list);
        
    }
    
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
    
    public List<T> getPreOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getPreOrder(head, list);
        
    }
    
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
    
    public List<T> getPostOrder()
    {
        List<T> list = new LinkedList<T>();
        return _getPostOrder(head, list);
        
    }
    
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
    
    public int getMaxDepth()
    {
        if (head==null)
        {
            return -1;
        }
        //IF there is a head, min depth is 1.

        
        
        return _getMaxDepth(head, 0, 0);
    }
    
    private int _getMaxDepth(BTNode node, int depth, int maxDepth)
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
    
    public ArrayList<T> getIndexed()
    {
        int width = getMaxDepth() + 1;
        int noOfElements = (int) java.lang.Math.pow(2, width) - 1;
        ArrayList<T> list = new ArrayList<T>(noOfElements);
        for (int i = 0; i<noOfElements; i++)
        {
            list.add(null);
        }
        
        _fillIndexed(list, head, 0);
        
        return list;
    }
    
    public void _fillIndexed(ArrayList<T> list, BTNode<T> node, int index)
    {
        //System.out.println("Filling Tree, at index"+ index);
        list.set(index, node.t);
        if (node.left !=null)
        {
            _fillIndexed(list, node.left, index*2+1);
        }
        if (node.right !=null)
        {
            _fillIndexed(list, node.right, index*2+2);
        }
    }
    
    private class BTNode<T>
    {
        T t;
        BTNode<T> left, right;

        BTNode(T t)
        {
            this.t=t;
        }
        BTNode(T t, BTNode l, BTNode r)
        {
            left = l;
            right = r;
            this.t=t;
        }
        
    }

}
