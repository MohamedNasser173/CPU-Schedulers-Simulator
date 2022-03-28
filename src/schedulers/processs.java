package schedulers;

public class processs {
	private int processID;
	private int priority;
	private int arrivingTime;
	private int burstTime;

	public processs() {
		this.processID = 0;
		this.priority = 0;
		this.arrivingTime = 0;
		this.burstTime = 0;
	}

	public processs(int processID, int priority, int arrivingTime, int burstTime) {
		this.processID = processID;
		this.priority = priority;
		this.arrivingTime = arrivingTime;
		this.burstTime = burstTime;
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getArrivingTime() {
		return arrivingTime;
	}

	public void setArrivingTime(int arrivingTime) {
		this.arrivingTime = arrivingTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public void reduceTime(int time) {
		if (burstTime >= time)
			burstTime -= time;
	}

	public void increasePriority(int time) {
		priority -= 1;
	}

}
