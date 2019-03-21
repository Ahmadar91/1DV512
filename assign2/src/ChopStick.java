

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick
{
	private final int id;

	Lock myLock = new ReentrantLock();
	// used ReentrantLock myLock1 to access the last method checkIfHeld
	ReentrantLock myLock1 = new ReentrantLock();

	public ChopStick(int id)
	{
		this.id = id;
	}

	// pickup method which returns a boolean using the mylock1.trylock() which
	// indicates if the chopstick is locked or not
	boolean pickUp()
	{
		return myLock1.tryLock(); // method provides a boolean value which indicates whether the lock was acquired
									// or not.

	}

	// putDown method which unlocks a chopstick if it is locked using
	// mylock1.unlock()
	void putDown()
	{

		myLock1.unlock(); // method that release the lock

	}

	// check if held method returns a boolean value and checks if a chopstick is
	// held by a a current thread
	public boolean checkIfHeld()
	{
		return myLock1.isHeldByCurrentThread();// method which checks if this lock is held by current thread
	}
	
	public int getId() {
		return id;
	}
}
