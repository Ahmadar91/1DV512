

public class Process {
	int processId;
	int arrivalTime;
	int burstTime;
	int completedTime;
	int turnaroundTime;
	int waitingTime;

	public Process(int processId, int arrivalTime, int burstTime) {
		this.processId = processId;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
	}

	public void setCompletedTime(int completedTime) {
		this.completedTime = completedTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public int getCompletedTime() {
		return completedTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public int getProcessId() {
		return processId;
	}
//	@Override 
//	public String toString() {
//	    return processId + " " + arrivalTime + ", " + burstTime ;
//	}
}
