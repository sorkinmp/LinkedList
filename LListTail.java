/**
 * A class that implements the ADT list by using a chain of linked nodes that
 * has a head reference.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry MODIFIED BY NORM KRUMPE
 * @version 4.0
 */
public class LListTail<T> implements ListInterface<T> {
	private Node lastNode; // Reference to last node of chain
	private int numberOfEntries;

	public LListTail() {
		initializeDataFields();
	} // end default constructor

	public void clear() {
		initializeDataFields();
	} // end clear

	public void add(T newEntry) // OutOfMemoryError possible
	{
		Node newNode = new Node(newEntry);

		if (isEmpty()) {
			lastNode = newNode;
		    // set lastNode's next node to be itself
			lastNode.setNextNode(newNode);
		}
		else // Add to end of non-empty list
		{
			newNode.next = lastNode.next; // the node after newNode should be lastNode.next
			lastNode.next = newNode; // set lastNode's next to be newNode
			lastNode = newNode; // finally set lastNode to be newNode to be circular
		} // end if

		numberOfEntries++;
	} // end add

	public void add(int newPosition, T newEntry) {
		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
			Node newNode = new Node(newEntry);

			if (isEmpty()) { // if the list is empty
				lastNode = newNode;
				lastNode.setNextNode(newNode);
			}
			else if (newPosition == 1) // Case 2: adding to position 1 if non-empty
			{
				newNode.next = lastNode.next; // set next for newNode
				lastNode.next = newNode; // update lastNode.next
			} else if(newPosition == numberOfEntries + 1) { // Case 3: add to end
				newNode.next = lastNode.next; // the node after newNode should be lastNode.next
				lastNode.next = newNode; // set lastNode's next to be newNode
				lastNode = newNode; // finally set lastNode to be newNode to be circular
			}
			else
			{ // and newPosition > 1
				Node nodeBefore = getNodeAt(newPosition - 1);
				Node nodeAfter = nodeBefore.next;
				nodeBefore.next = newNode; // update beforeNode
				newNode.next = nodeAfter; // update newNode
			} // end if

			numberOfEntries++;
		} else
			throw new IndexOutOfBoundsException("Illegal position given to add operation.");
	} // end add

	public T remove(int givenPosition) {
		T result = null; // Return value

		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {

			if (givenPosition == 1) // Case 1: Remove first entry
			{
				Node firstNode = lastNode.next;
				result = firstNode.getData(); // Save entry to be removed
				lastNode.next = firstNode.getNextNode(); // Remove entry
			} else if (givenPosition == numberOfEntries) { // for lastNode
				Node nodeBefore = getNodeAt(givenPosition - 1);
				Node nodeToRemove = nodeBefore.getNextNode();
				result = nodeToRemove.getData(); // Save entry to be removed
				nodeBefore.next = lastNode.next;
				lastNode = nodeBefore;
			}
			else // Case 3: Not first entry and not last entry
			{
				Node nodeBefore = getNodeAt(givenPosition - 1);
				Node nodeToRemove = nodeBefore.getNextNode();
				result = nodeToRemove.getData(); // Save entry to be removed
				Node nodeAfter = nodeToRemove.getNextNode();
				nodeBefore.setNextNode(nodeAfter); // Remove entry
			} // end if

			numberOfEntries--; // Update count
			return result; // Return removed entry
		} else
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
	} // end remove

	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			Node desiredNode = getNodeAt(givenPosition);
			T originalEntry = desiredNode.getData();
			desiredNode.setData(newEntry);
			return originalEntry;
		} else
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
	} // end replace

	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			return getNodeAt(givenPosition).getData();
		} else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
	} // end getEntry

	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];

		int index = 0;
		Node currentNode = lastNode.next;
		while ((index < numberOfEntries) && (currentNode != lastNode)) {
			result[index] = currentNode.getData();
			currentNode = currentNode.getNextNode();
			index++;
		} // end while
		// add lastNode
		result[index] = currentNode.getData();
		return result;
	} // end toArray

	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = lastNode.next; // update to firstNode

		while (!found && (currentNode != lastNode)) {
			if (currentNode.data.equals(anEntry))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while
		// check lastNode
		if (currentNode.data.equals(anEntry)) {
			found = true;
		}
		
		return found;
	} // end contains

	public int getLength() {
		return numberOfEntries;
	} // end getLength

	public boolean isEmpty() {
		boolean result;

		if (numberOfEntries == 0) // Or getLength() == 0
		{
			result = true;
		} else {
			result = false;
		} // end if

		return result;
	} // end isEmpty

	// Initializes the class's data fields to indicate an empty list.
	private void initializeDataFields() {
		lastNode = null;
		numberOfEntries = 0;
	} // end initializeDataFields

	// Returns a reference to the node at a given position.
	// Precondition: The chain is not empty;
	// 1 <= givenPosition <= numberOfEntries.
	private Node getNodeAt(int givenPosition) {
		Node currentNode = lastNode.next; // change to firstNode

		// Traverse the chain to locate the desired node
		// (skipped if givenPosition is 1)
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();

		return currentNode;
	} // end getNodeAt

	private class Node {
		private T data; // Entry in list
		private Node next; // Link to next node

		private Node(T dataPortion) {
			data = dataPortion;
			next = null;
		} // end constructor

		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		} // end constructor

		private T getData() {
			return data;
		} // end getData

		private void setData(T newData) {
			data = newData;
		} // end setData

		private Node getNextNode() {
			return next;
		} // end getNextNode

		private void setNextNode(Node nextNode) {
			next = nextNode;
		} // end setNextNode
	} // end Node
} // end LList
