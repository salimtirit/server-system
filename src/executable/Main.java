
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.lang.model.util.Elements;

import elements.Message;
import elements.Server;
import elements.User;

/**
 * Class with main method to run all program. Takes the inputs from input file
 * and prints the corresponding results and warnings to output file. Also
 * contains a PrintStream field for other classes to access.
 * 
 * @author Salim Kemal Tirit
 *
 */
public class Main {
	/**
	 * Will be used to print to a output file. Making printer a public field provide
	 * other classes to reach printer.
	 */
	public static PrintStream printer;

	/**
	 * Driver method of the entire program. Takes two files as arguments. First one
	 * is input file and the second is output file. From file reads the number of
	 * users, queries and capacity of the server first. Initializes the time equal
	 * to zero. The time will be incremented throughout the program by one for each
	 * operation done. Creates new users and stores them in an ArrayList. There are
	 * numberOfQueries lines in the input file after the first line so program will
	 * will read the first integer in all the lines. This integer is the operation
	 * number. Depending on the operation number new readings may be done in the
	 * same line.
	 * <p>
	 * <strong>Operation 0</strong> refers to sending a message. Uses
	 * {@link elements.User#sendMessage(User, String, int, Server)} and
	 * {@link elements.Server#checkServerLoad(PrintStream)}
	 * <p>
	 * <strong>Operation 1</strong> refers to receiving messages from the server.
	 * Uses {@link elements.User#getInbox()} and
	 * {@link boxes.Inbox#receiveMessages(Server, int)}
	 * <p>
	 * <strong>Operation 2</strong> refers to reading a certain amount of messages.
	 * Uses {@link elements.User#getInbox()} and
	 * {@link boxes.Inbox#readMessages(int, int)}
	 * <p>
	 * <strong>Operation 21</strong> refers to reading all the messages from a
	 * sender. Uses {@link elements.User#getInbox()} and
	 * {@link boxes.Inbox#readMessages(User, int)}
	 * <p>
	 * <strong>Operation 22</strong> refers to reading a specific message. Uses
	 * {@link elements.User#getInbox()} and
	 * {@link boxes.Inbox#readMessage(int, int)}
	 * <p>
	 * <strong>Operation 3</strong> refers to adding a friend.
	 * {@link elements.User#addFriend(User)}
	 * <p>
	 * <strong>Operation 4</strong> refers to removing a friend.
	 * {@link elements.User#removeFriend(User)}
	 * <p>
	 * <strong>Operation 5</strong> refers to flushing (deleting everything) from
	 * the server. Uses {@link elements.Server#flush()}
	 * <p>
	 * <strong>Operation 6</strong> refers to printing the current size of the
	 * server. Uses {@link elements.Server#getCurrentSize()}
	 * <p>
	 * <strong>Operation 61</strong> refers to printing the last message a user has
	 * read. Uses {@link elements.User#getInbox()},
	 * {@link boxes.Inbox#lastMessage()} and {@link elements.Message#toString()}
	 * 
	 * @param args						Arguments in witch file names should be given. 
	 * @throws FileNotFoundException	if the given input file can not found.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(args[0]));
		printer = new PrintStream(new File(args[1]));

		int time = 0;

		int numberOfUsers = scanner.nextInt();
		int numberOfQueries = scanner.nextInt();
		long capacityOfServer = scanner.nextInt();

		Server server = new Server(capacityOfServer);

		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < numberOfUsers; i++) {
			users.add(new User(i));
		}
		for (long i = 0; i < numberOfQueries; i++) {
			int queries = scanner.nextInt();

			int senderID, receiverID, id1, id2;

			switch (queries) {
			case 0:
				senderID = scanner.nextInt();
				receiverID = scanner.nextInt();
				String messageBoddy = scanner.nextLine();
				messageBoddy = messageBoddy.substring(1);
				users.get(senderID).sendMessage(users.get(receiverID), messageBoddy, time, server);
				server.checkServerLoad(printer);
				time++;
				break;
			case 1:
				receiverID = scanner.nextInt();
				users.get(receiverID).getInbox().receiveMessages(server, time);
				time++;
				break;
			case 2:
				receiverID = scanner.nextInt();
				int numberOfMesages = scanner.nextInt();
				time += users.get(receiverID).getInbox().readMessages(numberOfMesages, time);
				break;
			case 21:
				receiverID = scanner.nextInt();
				senderID = scanner.nextInt();
				time += users.get(receiverID).getInbox().readMessages(users.get(senderID), time);
				break;
			case 22:
				receiverID = scanner.nextInt();
				int msgId = scanner.nextInt();
				users.get(receiverID).getInbox().readMessage(msgId, time);
				time++;
				break;
			case 3:
				id1 = scanner.nextInt();
				id2 = scanner.nextInt();
				users.get(id1).addFriend(users.get(id2));
				time++;
				break;
			case 4:
				id1 = scanner.nextInt();
				id2 = scanner.nextInt();
				users.get(id1).removeFriend(users.get(id2));
				time++;
				break;
			case 5:
				server.flush();
				time++;
				break;
			case 6:
				printer.print("Current load of the server is " + server.getCurrentSize() + " characters.\n");
				time++;
				break;
			case 61:
				receiverID = scanner.nextInt();
//				Message m = null;// TODO ?????
//				try {
//					m = users.get(receiverID).getInbox().lastMessage();
//				} catch (NoSuchElementException e) {
//				}
				try {
//					printer.println("\tFrom: " + m.getSender().getId() + " to: " + receiverID + "\n\tReceived: "
//							+ m.getTimeStampReceived() + " Read: " + m.getTimeStampRead() + "\n\t" + m.getBody());
					printer.println(users.get(receiverID).getInbox().lastMessage().toString());
				} catch (NullPointerException n) {
				} catch (NoSuchElementException e) {
				}
				time++;
				break;
			}
		}
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
