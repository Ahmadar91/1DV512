
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher implements Runnable
{

	private int id;

	private final ChopStick leftChopStick;
	private final ChopStick rightChopStick;

	private Random randomGenerator = new Random();

	private int numberOfEatingTurns = 0;
	private int numberOfThinkingTurns = 0;
	private int numberOfHungryTurns = 0;
	private double thinkingTime = 0;
	private double eatingTime = 0;
	private double hungryTime = 0;
	private boolean debug;
	private AtomicBoolean run = new AtomicBoolean(true);

	public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick, int seed, boolean DEBUG)
	{
		this.debug = DEBUG;
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;

		/*
		 * set the seed for this philosopher. To differentiate the seed from the other
		 * philosophers, we add the philosopher id to the seed. the seed makes sure that
		 * the random numbers are the same every time the application is executed the
		 * random number is not the same between multiple calls within the same program
		 * execution
		 * 
		 * NOTE In order to get the same average values use the seed 100, and set the id
		 * of the philosopher starting from 0 to 4 (0,1,2,3,4). Each philosopher sets
		 * the seed to the random number generator as seed+id. The seed for each
		 * philosopher is as follows: P0.seed = 100 + P0.id = 100 + 0 = 100 P1.seed =
		 * 100 + P1.id = 100 + 1 = 101 P2.seed = 100 + P2.id = 100 + 2 = 102 P3.seed =
		 * 100 + P3.id = 100 + 3 = 103 P4.seed = 100 + P4.id = 100 + 4 = 104 Therefore,
		 * if the ids of the philosophers are not 0,1,2,3,4 then different random
		 * numbers will be generated.
		 */

		randomGenerator.setSeed(id + seed);
	}

	public int getId()
	{
		return id;
	}

	public double getAverageThinkingTime()
	{

		// return the totalThinkingTime / numberofThinkingTurns which will return the
		// average thinking time
		return getTotalThinkingTime() / getNumberOfThinkingTurns();
	}

	public double getAverageEatingTime()
	{

		// return the totalEatingTime / numberOfEatingTurns which will return the
		// average eating time
		return getTotalEatingTime() / getNumberOfEatingTurns();
	}

	public double getAverageHungryTime()
	{

		// return the totalHungryTime / numberOfHungryTurns which will return the
		// average hungry time

		return getTotalHungryTime() / getNumberOfHungryTurns();
	}

	public int getNumberOfThinkingTurns()
	{
		return numberOfThinkingTurns;
	}

	public int getNumberOfEatingTurns()
	{
		return numberOfEatingTurns;
	}

	public int getNumberOfHungryTurns()
	{
		return numberOfHungryTurns;
	}

	public double getTotalThinkingTime()
	{
		return thinkingTime;
	}

	public double getTotalEatingTime()
	{
		return eatingTime;
	}

	public double getTotalHungryTime()
	{
		return hungryTime;
	}

	@Override
	public void run()
	{

		/*
		 * TODO Think, Hungry, Eat, Repeat until thread is interrupted Increment the
		 * thinking/eating turns after thinking/eating process has finished. Add
		 * comprehensive comments to explain your implementation, including deadlock
		 * prevention/detection
		 */

		// while run is true keep looping and do the think,hungry and eat method and
		// increment the turns after the processes is finished until run is false

		while (run.get())// while run is true
		{

			try
			{
				think(); // the think method
				numberOfThinkingTurns++;// increment the thinking turns after think() method is finished
				if (!run.get())
				{
					break;// if run is false stop
				}
				hungry(); // the hungry method
				numberOfHungryTurns++;// increment the hungry turns after hungry() method is finished
				// if run is false stop
				if (!run.get())
				{
					break;
				}
				eat();// the eat method
				numberOfEatingTurns++;// increment the eating turns after eat() method is finished

			} catch (InterruptedException e)
			{

				e.printStackTrace();
			}

		}

	}

	// think method has a integer sleep which is a random number from 0 to 999
	// which is added to the thread.sleep function and then added to the total
	// thinkingTime
	void think() throws InterruptedException
	{
		// number form 0 to 999
		int sleep = this.randomGenerator.nextInt(1000);
		if (debug)
		{
			System.out.printf("Philiospher_%d is THINKING\n", id + 1);
		}

		// add think to thread sleep
		Thread.sleep(sleep);
		// increase the total thinking time
		thinkingTime += sleep;
	}

	// hungry method tests the chopsticks
	void hungry() throws InterruptedException
	{
		// if debug is true print
		if (debug)
		{
			System.out.printf("Philiospher_%d is HUNGRY\n", id + 1);
		}
		do // if the philosopher does not have both chopsticks start again
		{

			while (!leftChopStick.pickUp()) // while leftChopStick is false
			{
				// if run is false return
				if (!run.get())
				{
					return;
				}
				Thread.sleep(1); // sleep thread for 1 millisecond
				hungryTime += 1; // increment the hungry time by one because threads is slept by 1 millisecond

			}
			// if debug is true print
			if (debug)
			{
				System.out.printf("Philiospher_%d picked-up Chopstick_%d\n", id + 1, leftChopStick.getId() + 1);
			}

			if (!rightChopStick.pickUp())// while RightChopStick is false
			{
				// if run is false return
				if (!run.get())
				{
					return;
				}
				// without getting the right chopstick put the left chopstick down and
				// break(start again)
				leftChopStick.putDown();// put down the leftChopStick
				if (debug)
				{
					System.out.printf("Philiospher_%d released Chopstick_%d\n", id + 1, leftChopStick.getId() + 1);
				}
				Thread.sleep(5);
				hungryTime += 5;
			} else
			{
				// if debug is true and the rightChopstick is held by current
				// thread print
				if (debug)
				{
					System.out.printf("Philiospher_%d picked-up Chopstick_%d\n", id + 1, rightChopStick.getId() + 1);
				}
			}

			// if leftChopStick.checkIfHeld() is false and rightChopStick.checkIfHeld() is
			// false do the while loop
		} while (!leftChopStick.checkIfHeld() && !rightChopStick.checkIfHeld());
	}

	// eat method has a integer sleep which is a random number from 0 to 999
	// which is added to the thread.sleep function and then added to the total
	// eatingTime
	void eat() throws InterruptedException
	{
		// number for, 0 to 999
		int sleep = this.randomGenerator.nextInt(1000);
		// if debug is true print
		if (debug)
		{
			System.out.printf("Philiospher_%d is EATING\n", id + 1);
		}
		Thread.sleep(sleep);// add the rand to thread sleep

		eatingTime += sleep;// increase the total eating time
		chopSticksDown();// method which puts down both chopsticks

	}

	// chopSticksDown method unlocks both left and right chopsticks if run is false
	// then it stops the process
	void chopSticksDown()
	{
		// if run is false return
		if (!run.get())
		{
			return;
		}
		leftChopStick.putDown(); // unlock the left chopstick
		if (debug)
		{
			System.out.printf("Philiospher_%d released Chopstick_%d\n", id + 1, leftChopStick.getId() + 1);
		}
		rightChopStick.putDown();// unlock the right chopstick
		if (debug)
		{
			System.out.printf("Philiospher_%d released Chopstick_%d\n", id + 1, rightChopStick.getId() + 1);
		}

	}

	// method which turn the boolean run to false to stop all philosophers in which
	// will stop the the process
	public void terminate()
	{
		run.set(false); // change run to false instead of true
	}

}
