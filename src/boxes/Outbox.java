
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package boxes;

import java.util.LinkedList;
import java.util.Queue;

import elements.Message;
import elements.User;

/**
 * Stores sent messages of users. Child class of {@link boxes.Box}
 * 
 * @author Salim Kemal Tirit
 *
 */
public class Outbox extends Box {
	private Queue<Message> sent = new LinkedList<Message>();

	/**
	 * Constructor method of Outbox.
	 * 
	 * @param owner is the owner of outbox and messages.
	 */
	public Outbox(User owner) {
		super(owner);
	}

	/**
	 * Adds sent messages to sent queue.
	 * 
	 * @param m is the message to be added.
	 */
	public void add(Message m) {
		this.sent.add(m);
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
