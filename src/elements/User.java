
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package elements;

import java.util.ArrayList;

import boxes.Inbox;
import boxes.Outbox;

/**
 * Keeps the properties of users who are using the program as senders and
 * receivers. All users have to have an inbox an outbox a unique id and friends
 * to communicate. Users have the ability to send message and add or remove
 * friends.
 * 
 * @author Salim Kemal Tirit
 *
 */
public class User {
	private int id;
	private Inbox inbox = new Inbox(this);
	private Outbox outbox = new Outbox(this);

	private ArrayList<User> friends = new ArrayList<>();

	/**
	 * 
	 * @param id is unique for every user and id s are determined in
	 *           {@link executable.Main}
	 */
	public User(int id) {
		this.id = id;
	}

	/**
	 * Adds a new friend to friends list of user and also makes the other user
	 * friends with this user.
	 * 
	 * @param other if the friend to be added
	 */
	public void addFriend(User other) {
		if (!this.isFriendsWith(other)) {
			this.friends.add(other);
			other.addFriend(this);
		}
	}

	/**
	 * Removes two users from each others friends list.
	 * 
	 * @param other is the friend to be removed (or should i say ex-friend)
	 */
	public void removeFriend(User other) {
		if (this.isFriendsWith(other)) {
			this.friends.remove(other);
			other.removeFriend(this);
		}
	}

	/**
	 * 
	 * @param other is the user to check if they are friends with this one.
	 * @return true if two users are friends, false if they are not.
	 */
	public boolean isFriendsWith(User other) {
		return friends.contains(other);
	}

	/**
	 * Creates and sends a message.
	 * 
	 * @see boxes.Outbox#add(Message)
	 * @see elements.Server#add(Message)
	 * @param receiver is the user who will (hopefully) receive message
	 * @param body is the readable part of message
	 * @param time is the sending time of message
	 * @param server is {@link elements.Server}
	 */
	public void sendMessage(User receiver, String body, int time, Server server) {
		Message msg = new Message(this, receiver, body, server, time);
		outbox.add(msg);
		server.add(msg);
	}

	/**
	 * 
	 * @return the unique id of user
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return the inbox of user
	 */
	public Inbox getInbox() {
		return this.inbox;
	}

}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
