package library;

import java.util.ArrayList;
import library.RRCompare;

/**
 * A selection of sorting algorithms for Array Lists.
 * Uses an interface for carrying out comparisons, which determines sort order.
 * 
 * @author Ryan Rich
 *
 */
public class AllSorts
{
    public static enum sortType
    {
        INSERT, SELECTION, BUBBLE
        // TODO :  SHELL, MERGE, HEAP, QUICK
    }

    /**
     * General sorting method
     * @param arr the array to be sorted
     * @param st the sorting algorithm to use
     * @param cmp a comparison method, functioning similar to standard .compareTo()
     * @return
     */
    static public <T> ArrayList<T> RRSort(ArrayList<T> arr, sortType st, RRCompare<T> cmp)
    {
        ArrayList<T> out;
        switch (st)
        {
        case INSERT:
            out = RRInsertSort(arr, cmp);
            break;
        case SELECTION:
            out = RRSelectionSort(arr, cmp);
            break;
        case BUBBLE:
            out = RRBubbleSort(arr, cmp);
            break;
        default:
            out=null;
            break;
        }
        
        return out;

    }

    static private <T> ArrayList<T> RRInsertSort(ArrayList<T> arr, RRCompare<T> cmp)
    {
        ArrayList<T> out = new ArrayList<T>(arr);
        T t;

        // reference - https://en.wikipedia.org/wiki/Insertion_sort
        int i,j;
        for (i=1; i<out.size(); i++)
        {
            t=out.get(i);
            for (j=i-1; (j>=0) && (cmp.compare(out.get(j),t) > 0) ; j--)
            {
                out.set(j+1, out.get(j));
            }
            out.set(j+1, t);
        }
        return out;
    }

    static private <T> ArrayList<T> RRSelectionSort(ArrayList<T> arr, RRCompare<T> cmp)
    {
        ArrayList<T> out = new ArrayList<T>(arr);
        T t;
        
        //Reference: https://en.wikipedia.org/wiki/Selection_sort
        int n = out.size();
        int i, j, jMin;
        
        for (i = 0; i < n-1; i++)
        {
            jMin = i;
            for(j = i+1; j < n; j++)
            {
                if (cmp.compare(out.get(j) , out.get(jMin)) < 0 ) jMin = j;
            }
            if (jMin != i) 
            {
                t = out.get(i);
                out.set(i,out.get(jMin));
                out.set(jMin, t);
            }
        }
        return out;
    }
    
    static private <T> ArrayList<T> RRBubbleSort(ArrayList<T> arr, RRCompare<T> cmp)
    {
        ArrayList<T> out = new ArrayList<T>(arr);
        T t;
        int n = out.size(), i;
        boolean swapped = false;
        
        // Reference: https://en.wikipedia.org/wiki/Bubble_sort
        // using optimised and early exit version.
        do
        {
            swapped = false;
            for (i=1; i<n; i++ )
            {
                if(cmp.compare(out.get(i-1), out.get(i)) > 0)
                {
                    t = out.get(i-1);
                    out.set(i-1,out.get(i));
                    out.set(i, t);
                    swapped = true;
                }
            }
            n--; // Top value guaranteed to be bubbled to top.
        } while( swapped == true ); //IF no swaps, the everything is in order
        return out;
    }
   
    

}
