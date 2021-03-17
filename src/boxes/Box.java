
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
package boxes;

import elements.User;

/**
 * Parent class of {@link boxes.Inbox} and {@link boxes.Outbox} 
 * 
 * @author Salim Kemal Tirit
 *
 */
public abstract class Box {
	User owner;

	/**
	 * Constructor method of Box class.
	 * @param owner is the owner of the box (inbox or outbox)
	 */
	Box(User owner) {
		this.owner = owner;
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
