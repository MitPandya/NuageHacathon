
public class Stack {
	
	private int maxSize;
	private Object[] stackArray;
	private int top;
	
	public Stack(int size){
		stackArray = new Object[size];
		maxSize = size;
		top = -1;
	}
	
	public void push(Object v){
		stackArray[++top] = v;
	}
	public Object pop(){
		return stackArray[top--];
	}
	public Object peek(){
		return stackArray[top];
	}
	public boolean isFull(){
		return top == maxSize-1;
	}
	public boolean isEmpty(){
		return top == -1;
	}

}
