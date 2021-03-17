
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Keeps the messages sent and makes the communication between sender and
 * receiver possible. Until it reaches its full capacity server keeps all
 * messages sent. When a user receives a message it is deleted from server and
 * added to the inbox of the user. Gives some warnings about capacity while
 * using.
 * 
 * @author Salim Kemal Tirit
 *
 */
public class Server {
	private long capacity;
	private long currentSize;

	private Queue<Message> msgs = new LinkedList<Message>();
	private boolean eightyPercent = false;
	private boolean fiftyPercent = false;

	/**
	 * 
	 * @param capacity is the upper limit of the sum of all messages' bodies in the
	 *                 server.
	 */
	public Server(long capacity) {
		this.capacity = capacity;
	}

	/**
	 * After a user sends or receives a message current size of the server changes.
	 * So we check server load to keep things working. Uses
	 * {@link elements.Server#getCurrentSize()} and may change
	 * {@link elements.Server#currentSize}. To not to give the same warning over and
	 * over two booleans are kept: {@link elements.Server#eightyPercent} and
	 * {@link elements.Server#fiftyPercent} When server reaches %50 of its capacity
	 * a warning is printed saying "Warning! Server is %50 full." After it reaches
	 * %80 of its capacity another warning is printed saying "Warning! Server is 80%
	 * full." and if the server reaches %100 or more of its capacity it prints
	 * "Server is full. Deleting all messages..." and deletes everything in the
	 * server. If the server goes from %80 or more to between %50 to %80 percent it
	 * prints the same warning message. If it becomes less than %50 than more than
	 * %50 again it also presses the warning. To sum up if it changes its state and
	 * becomes between %50 and %80 or more than %80 a warning will be printed.
	 * 
	 * @param printer makes it possible for us to print warnings to output file.
	 */
	public void checkServerLoad(PrintStream printer) {
		this.getCurrentSize();
		double d = (double) this.currentSize / (double) this.capacity;
		if (d >= 1) {
			printer.println("Server is full. Deleting all messages...");
			eightyPercent = false;
			fiftyPercent = false;
			this.flush();
		} else if (d >= 0.8 && !eightyPercent) {
			printer.println("Warning! Server is 80% full.");
			eightyPercent = true;
			fiftyPercent = false;
		} else if (d < 0.8 && d >= 0.5 && !fiftyPercent) {
			printer.println("Warning! Server is 50% full.");
			fiftyPercent = true;
			eightyPercent = false;
		} else if (d < 0.5) {
			fiftyPercent = false;
			eightyPercent = false;
		}
	}

	/**
	 * Checks the size of the server every time its called and changes the
	 * {@link elements.Server#currentSize} according to the size.
	 * 
	 * @return the current size of the server by the means of message body lengths.
	 */
	public long getCurrentSize() {
		Iterator<Message> i = this.msgs.iterator();
		long size = 0;
		while (i.hasNext()) {
			size += i.next().getBody().length();
		}
		this.currentSize = size;
		return this.currentSize;
	}

	/**
	 * Deletes everything from server.
	 */
	public void flush() {
		this.currentSize = 0;
		this.msgs.clear();
		eightyPercent = false;
		fiftyPercent = false;
	}

	/**
	 * Adds a new message to {@link elements.Server#msgs}
	 * 
	 * @param m is the new message to be added
	 */
	public void add(Message m) {
		msgs.add(m);
		this.currentSize += m.getBody().length();
	}

	/**
	 * 
	 * @return The queue of server where we keep all messages.
	 */
	public Queue<Message> getMsgs() {
		return msgs;
	}

	/**
	 * Removes a message from {@link elements.Server#msgs} and updates the
	 * {@link elements.Server#currentSize} of the server.
	 * 
	 * @param m is the message to be removed.
	 */
	public void remove(Message m) {
		this.currentSize -= m.getBody().length();
		this.msgs.remove(m);
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
