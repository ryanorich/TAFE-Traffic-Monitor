package library;

public class RRLinkedList <T>
{
	LLNode head = null;
	LLNode tail = null;
	int count = 0;
	

	
	 public T get(int n)
	 {
			if (n<0 || n>= count)
			{
				return null;
			}
			
			
			//for (int i = n, i>0; i++)
			
			return null;
	 }
	
	 public void add(T t)
	 {
		 
		 LLNode node = new LLNode(t);
		 node.previous = tail;
		 tail = node;
		 count++;
	 }
	 
	 public void remove()
	 {
		 if (count<=0) return;
		 
		 
		 
		 LLNode node = tail.previous;
		 tail = node;
		 tail.next = null;
	 }
	 
	public void set(int n, T t) 
	{
		if (n<0 || n>= count)
		{
			return;
		}
		
		LLNode node = new LLNode(t);
		
		
		
	}
	
	
	
	private class LLNode<T>
	{
		LLNode previous = null, next = null;
		T t = null;
		
		LLNode (T t)
		{
			this.t = t;
		}
		
		
	}
	
	
	
}
