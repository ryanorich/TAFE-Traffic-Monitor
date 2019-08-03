package library;

/**
 * A Doubly-Linked-List of objects
 * 
 * @author Ryan Rich
 *
 * @param <T> Object type to be passed
 */
public class RRLinkedList<T>
{
    LLNode<T> head = null;
    LLNode<T> tail = null;
    int count = 0;

    /**
     * REturns the number of objects in the list.
     * 
     * @return The number of objects in the list
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Returns the object at the specified index
     * 
     * @param n The index of the node to retrieve
     * @return The object stored in the node
     */
    public T get(int n)
    {
        if (n < 0 || n >= count)
        {
            return null;
        }

        LLNode<T> current = head;
        for (int i = 0; i < n; i++)
        {
            current = current.next;
        }

        return current.t;
    }

    /**
     * Adds the object to the end of the list.
     * 
     * @param t The object to add
     */
    public void add(T t)
    {
        LLNode<T> node = new LLNode<T>(t);

        if (head == null)
        {// Nothing in list
            head = node;
            tail = node;
            count++;
            return;
        }

        LLNode<T> current = head;
        while (current.next != null)
        {
            current = current.next;
        }

        current.next = node;
        tail = node;
        tail.previous = current;
        count++;
    }

    /**
     * Adds and object at the specified location, moving all following nodes to the
     * right. The index is 0 based. Using an index equal to the number of objects in
     * the list adds to the end.
     * 
     * @param n index of where to add the object.
     * @param t the object to be added.
     */
    public void add(int n, T t)
    {
        if (n < 0 || n > count)
        {
            return;
        }

        LLNode<T> node = new LLNode<T>(t);

        if (n == 0)
        {// Adding to beginning
            head.previous = node;
            node.next = head;
            head = node;
        } else
        {// Not adding to beginning
            if (n == count)
            {// Adding to end
                tail.next = node;
                node.previous = tail;
                tail = node;

            } else
            {// Adding between two nodes.
                LLNode<T> current = head;
                for (int i = 0; i < n; i++)
                {
                    current = current.next;
                }

                current.previous.next = node;
                node.previous = current.previous;
                node.next = current;
                current.previous = node;

            }
        }
        count++;
    }

    /**
     * Removes an object from the end of the list.
     */
    public void remove()
    {
        if (count <= 0)
            return;

        LLNode<T> node = tail.previous;
        tail = node;
        tail.next = null;
        count--;
    }

    /**
     * Removes and object at the specified location. All following objects are moved
     * to the left.
     * 
     * @param n The inde of the object to remove.
     */
    public void remove(int n)
    {
        if (n < 0 || n >= count)
        {
            return;
        }

        if (n == 0)
        {// Removing head
            if (count == 1)
            {// If only one item, clearing list
                head = null;
                tail = null;

            } else
            {// Otherwise, make next the head
                head = head.next;
                head.previous = null;
            }
        } else
        {// Removing past the head
            LLNode<T> current = head;
            for (int i = 0; i < n; i++)
            {
                current = current.next;
            }
            if (current.next == null)
            {// Removing the end of the list
                current.previous.next = null;
                tail = current.previous;
            } else
            {// Removing between items
                current.previous.next = current.next;
                current.next.previous = current.previous;
            }
        }
        count--;
    }

    /**
     * Replaces an existing object in the list.
     * 
     * @param n the index of the object to replace
     * @param t the replacing object.
     */
    public void set(int n, T t)
    {
        if (n < 0 || n >= count)
        {
            return;
        }
        LLNode<T> node = new LLNode<T>(t);
        LLNode<T> current = head;
        for (int i = 0; i < n; i++)
        {
            current = current.next;
        }
        node.previous = current.previous;
        node.next = current.next;
        node.previous.next = node;
        node.next.previous = node;
    }

    /**
     * For Debugging - Prints all objects int he list in order Requires objects to
     * implement .toString(), or otherwise be able to be printed.
     */
    public void printList()
    {
        System.out.println("No of Nodes:" + count);
        LLNode<T> node = head;
        while (node != null)
        {
            System.out.print(node.t + " ");
            node = node.next;
        }
        System.out.println("");
    }

    /**
     * For Debugging - Prints a table of nodes, with previous and next nodes.
     * Requires objects to implement .toString(), or otherwise be able to be
     * printed.
     */
    public void printList2()
    {
        System.out.println("No of Nodes:" + count);
        LLNode<T> node = head;
        System.out.println("node\tprev\tnext\n----------------------------");
        while (node != null)
        {
            System.out.print(node.t + "\t" + (node.previous == null ? "--" : node.previous.t) + "\t"
                    + (node.next == null ? "--" : node.next.t) + "\n");
            node = node.next;
        }
    }

    /**
     * The node object for the Linked List
     * 
     * @author Ryan Rich
     *
     * @param <T> The object class to be stored.
     */
    private class LLNode<TN> //Note - Using <TN> instead of <T>, as it would 'hide' the origional T type.
    {
        LLNode<TN> previous = null, next = null;
        T t = null;

        LLNode(T t)
        {
            this.t = t;
        }
    }

}
