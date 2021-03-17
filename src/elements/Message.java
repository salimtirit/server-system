
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

/**
 * Stores the properties of messages: id, sender, receiver, timeStampReceived,
 * timeStampSent, timeStampRead. Determines the id of the messages by counting
 * starting from 0. Every message has only one sender and one receiver. Id is
 * unique for every message. Time stamps shows the time in which the specific
 * activity is done on the message.
 * 
 * @author Salim Kemal Tirit
 * 
 */
public class Message implements Comparable<Message> {
	private static int numOfMessages = 0;

	private String body;

	private User sender;
	private User receiver;

	private int id;
	private int timeStampSent;
	private int timeStampRead;
	private int timeStampReceived;

	private boolean isReceived = false;
	private boolean isRead = false;

	/**
	 * Constructor method of Message class.
	 * 
	 * @param sender   is the user which sends the message
	 * @param receiver is the user which the message is sent to
	 * @param body     is the readable part of the message.
	 * @param server   is the connection between sender and receiver
	 * @param time     is the operation time which is determined in
	 *                 {@link executable.Main} Used to set the timeStampSent of
	 *                 message.
	 */
	public Message(User sender, User receiver, String body, Server server, int time) {
		this.id = numOfMessages;
		this.sender = sender;
		this.receiver = receiver;
		this.body = body;
		this.timeStampSent = time;
		numOfMessages++;
	}

	/**
	 * 
	 * @return The id of the message.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * @return The body (readable part) of the message.
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * 
	 * @return The receiver of the message.
	 */
	public User getReceiver() {
		return this.receiver;
	}

	/**
	 * 
	 * @return The sender of the message
	 */
	public User getSender() {
		return this.sender;
	}

	/**
	 * Compares two messages by body length.
	 * 
	 * @return A positive integer if this messages body is longer than other
	 *         messages body, zero if body lengths are equal, a negative integer if
	 *         this messages body is shorter than other messages body.
	 */
	public int compareTo(Message o) {
		return this.getBody().length() - o.getBody().length();
	}

	/**
	 * Checks if the given object and this message is same or not.
	 * 
	 * @param o is the object which will be compared to this message.
	 * @return True if this message and other message is equal to each other.
	 *         Returns false otherwise.
	 */
	public boolean isEqualTo(Object o) {
		return o.equals(this);
	}

	/**
	 * The format of string is : <br>
	 * From: sender id To: receiver id <br>
	 * Received: receive time Read: read time <br>
	 * message body
	 */
	public String toString() {
		if (this.isRead) {
			return "\tFrom: " + this.sender.getId() + " To: " + this.receiver.getId() + "\n\tReceived: "
					+ this.timeStampReceived + " Read: " + this.timeStampRead + "\n\t" + this.body;
		} else if (this.isReceived) {
			return "\tFrom: " + this.sender.getId() + " To: " + this.receiver.getId() + "\n\tReceived: "
					+ this.timeStampReceived + "Read: " + "" + "\n\t" + this.body;
		} else {
			return "\tFrom: " + this.sender.getId() + " To: " + this.receiver.getId() + "\n\tReceived: " + ""
					+ " Read: " + "" + "\n\t" + this.body;
		}
	}

	/**
	 * After sending the message to server and adding it to sent queue of outbox we
	 * keep the sent time of message
	 * 
	 * @see boxes.Outbox
	 * @param timeStampSent sets {@link elements.Message#timeStampSent}
	 */
	public void setTimeStampSent(int timeStampSent) {
		this.timeStampSent = timeStampSent;
	}

	/**
	 * After reading the message and adding it to read queue of inbox we keep the
	 * read time of message and we change two booleans to true which are
	 * {@link elements.Message#isReceived} and {@link elements.Message#isRead}
	 * 
	 * @see boxes.Inbox
	 * @param timeStampRead sets {@link elements.Message#timeStampRead}
	 */
	public void setTimeStampRead(int timeStampRead) {
		this.isReceived = true;
		this.isRead = true;
		this.timeStampRead = timeStampRead;
	}

	/**
	 * After receiving message from server and adding it to unread stack of inbox we
	 * keep the received time of message and we change {@link elements.Message#isReceived}
	 * boolean to true.
	 * 
	 * @see boxes.Inbox
	 * @param timeStampReceived sets {@link elements.Message#timeStampReceived}
	 */
	public void setTimeStampReceived(int timeStampReceived) {
		this.isReceived = true;
		this.timeStampReceived = timeStampReceived;
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
