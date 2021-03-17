
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package boxes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

import elements.Message;
import elements.Server;
import elements.User;
import executable.Main;

/**
 * Class in which all receiving and reading message activities takes place.
 * Child class of {@link boxes.Box}
 * 
 * @author Salim Kemal Tirit
 *
 */
public class Inbox extends Box {
	private Stack<Message> unread = new Stack<Message>();
	private Queue<Message> read = new LinkedList<Message>();

	/**
	 * Constructor method of this class.
	 * 
	 * @param owner is the owner of this inbox.
	 */
	public Inbox(User owner) {
		super(owner);
	}

	/**
	 * Receives all messages of the owner from server and adds them to "unread"
	 * stack.
	 * 
	 * @param server where all messages are kept.
	 * @param time   is the operation time which is determined in
	 *               {@link executable.Main} Used to set the timeStampReceived of
	 *               message.
	 */
	public void receiveMessages(Server server, int time) {
		Iterator<Message> i = server.getMsgs().iterator();
		while (i.hasNext()) {
			Message m = i.next();
			if (m.getReceiver().equals(this.owner) && this.owner.isFriendsWith(m.getSender())) {
				unread.add(m);
				m.setTimeStampReceived(time);
				i.remove();
				server.checkServerLoad(Main.printer);
			}
		}
		server.getCurrentSize();
	}

	/**
	 * Reads a proper number of messages from unread stack and adds them to read
	 * queue. If the number is zero or more than the size of stack reads all the
	 * messages. Also changes timeStampRead of the messages.
	 * 
	 * @param num  is the number of messages to be read.
	 * @param time is the operation time which is determined in
	 *             {@link executable.Main} and incremented by one every time this
	 *             program reads a message. Also this time is saved in timeStampRead
	 *             field of {@link elements.Message}
	 * @return returns number of read messages. Returns 1 even if no message is
	 *         read.
	 */
	public int readMessages(int num, int time) {
		if (num == 0 || unread.size() < num) {
			int n = unread.size();
			int timeCounter = 0;
			ListIterator<Message> i = unread.listIterator(unread.size());
			while (i.hasPrevious()) {
				Message m = i.previous();
				read.add(m);
				m.setTimeStampRead(time + timeCounter);
				i.remove();
				timeCounter++;
			}
			return n == 0 ? 1 : n;
		} else {
			for (int i = 0; i < num; i++) {
				Message m = unread.pop();
				m.setTimeStampRead(time + i);
				read.add(m);
			}
			return num;
		}
	}

	/**
	 * Reads all messages of one specific sender.Traverses over all elements in the
	 * unread stack and adds to read queue if the message has the right sender.
	 * 
	 * @param sender is the User which has sent the message
	 * @param time   is the operation time which is determined in
	 *               {@link executable.Main} and incremented by one every time this
	 *               program reads a message. Also this time is saved in
	 *               timeStampRead field of {@link elements.Message}
	 * @return returns number of read messages. Returns 1 even if no message is
	 *         read.
	 */
	public int readMessages(User sender, int time) {
		int count = 0;
		ListIterator<Message> i = unread.listIterator(unread.size());
		while (i.hasPrevious()) {
			Message m = i.previous();
			if (m.getSender().equals(sender)) {
				read.add(m);
				m.setTimeStampRead(time + count);
				i.remove();
				count++;
			}
		}
		return count == 0 ? 1 : count;
	}

	/**
	 * Reads a specific message with given id. Traverses over all elements in the
	 * unread stack and adds to read queue if the message has the right id.
	 * 
	 * @param msgId is the unique id of the message
	 * @param time  is the operation time. Also this time is saved in timeStampRead
	 *              field of {@link elements.Message}
	 */
	public void readMessage(int msgId, int time) {
		Iterator<Message> i = unread.iterator();
		while (i.hasNext()) {
			Message m = i.next();
			if (m.getId() == msgId) {
				read.add(m);
				m.setTimeStampRead(time);
				i.remove();
			}
		}
	}

	/**
	 * @return returns the last read message.
	 */
	public Message lastMessage() {
		return ((LinkedList<Message>) this.read).getLast();
	}

}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
