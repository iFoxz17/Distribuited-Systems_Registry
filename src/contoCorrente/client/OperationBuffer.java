package contoCorrente.client;

public class OperationBuffer {
	
	private static final int DEFAULT_SIZE = 6;
	
	private String[] buffer;
	private int first, last, size, numberInBuffer;

	public OperationBuffer(int size) {
		buffer = new String[size];
		last = 0;
		first = 0;
		numberInBuffer = 0;
		this.size = size;
	}
	
	public OperationBuffer() {
		this(DEFAULT_SIZE);
	}
	
	public synchronized void insert(String operation) throws InterruptedException {
		
		while(numberInBuffer == size) {
			wait();
		}
		
		numberInBuffer++;
		last = (last+1) % size;
		buffer[last] = operation;
		notifyAll();
	}
	
	public synchronized String get() throws InterruptedException {

		while(numberInBuffer == 0) {
			wait();
		}
		
		first = (first+1) % size;
		numberInBuffer--;
		notifyAll();
		
		return buffer[first];
	}
}
	

