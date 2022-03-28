package schedulers;

import java.util.ArrayList;
import java.util.List;

public class PreemptiveScheduling {
	private List<int[]> Gantt;
	private int currentTime;
	private int exeTime;
	private WaitingQueue WaitingQueue;

	public PreemptiveScheduling() {
		Gantt = new ArrayList<int[]>();
		currentTime = 0;
		exeTime = 0;
		WaitingQueue = new WaitingQueue();
	}

	public List<int[]> getGantt(ArrayList<processs> processes) {
		currentTime = this.getFirstArrivingTime(processes);
		int in = currentTime, out = currentTime;

		ArrayList<processs> processes1 = this.getFirstArrivingProcesses(processes);
		// exeTime= totalBurstTime(processes);

		for (processs process : processes1) {
			WaitingQueue.enqueue(process);
			processes.remove(process);
		}

		ArrayList<processs> orderedByArrivingTime = this.AT_Ordered(processes);

		while (!WaitingQueue.isEmpty()) {
			processs process = WaitingQueue.dequeue();

			if (orderedByArrivingTime.size() > 0) {

				for (int i = 0; i < orderedByArrivingTime.size(); i++) {
					processs p = orderedByArrivingTime.get(i);

					if (p.getArrivingTime() >= process.getArrivingTime()
							&& p.getArrivingTime() < (process.getBurstTime() + currentTime)
							&& p.getPriority() >= process.getPriority()) {

						WaitingQueue.enqueue(p);
						orderedByArrivingTime.remove(p);
						i--;
					}

					else if (p.getArrivingTime() >= process.getArrivingTime()
							&& p.getArrivingTime() < (process.getBurstTime() + currentTime)
							&& p.getPriority() < process.getPriority()) {

						in = currentTime;
						currentTime = p.getArrivingTime();
						out = currentTime;
						process.reduceTime(out - in);

						WaitingQueue.enqueue(process);

						Gantt.add(new int[] { process.getProcessID(), in, out });

						WaitingQueue.enqueue(p);
						orderedByArrivingTime.remove(p);
						i--;
						break;
					}

					if (i == orderedByArrivingTime.size() - 1) {

						in = currentTime;
						currentTime += process.getBurstTime();
						out = currentTime;

						Gantt.add(new int[] { process.getProcessID(), in, out });

						if (orderedByArrivingTime.size() > 0 && WaitingQueue.isEmpty()) {
							WaitingQueue.enqueue(orderedByArrivingTime.get(0));
						}
					}
				}

			}

			else {

				in = currentTime;
				currentTime += process.getBurstTime();
				out = currentTime;

				Gantt.add(new int[] { process.getProcessID(), in, out });
			}
			exeTime--;
		}

		return Gantt;
	}

	public static int getCompletionTime(processs p, List<int[]> Gantt) {
		int completionTime = 0;
		for (int i = 0; i < Gantt.size(); i++) {

			if (Gantt.get(i)[0] == p.getProcessID())
				completionTime = Gantt.get(i)[2];
		}
		return completionTime;
	}

	public static int getTurnAroundTime(processs p, List<int[]> Gantt) {
		int completionTime = PreemptiveScheduling.getCompletionTime(p, Gantt);
		return completionTime - p.getArrivingTime();
	}

	public static int getWaitingTime(processs p, List<int[]> Gantt) {
		int turnAroundTime = PreemptiveScheduling.getTurnAroundTime(p, Gantt);
		return turnAroundTime - p.getBurstTime();
	}

	private ArrayList<processs> AT_Ordered(ArrayList<processs> processes) {
		ArrayList<processs> newProcesses = new ArrayList<>();
		while (processes.size() != 0) {
			processs p = this.getFirstArrivingProcess(processes);
			processes.remove(p);
			newProcesses.add(p);
		}
		return newProcesses;
	}

	private processs getFirstArrivingProcess(ArrayList<processs> processes) {
		int min = Integer.MAX_VALUE;
		processs process = null;
		for (processs p : processes) {
			if (p.getArrivingTime() < min) {
				min = p.getArrivingTime();
				process = p;
			}
		}
		return process;
	}

	private ArrayList<processs> getFirstArrivingProcesses(ArrayList<processs> processes) {
		int min = this.getFirstArrivingTime(processes);
		ArrayList<processs> processes1 = new ArrayList<>();
		for (processs p : processes) {
			if (p.getArrivingTime() == min) {
				processes1.add(p);
			}
		}
		return processes1;
	}

	private int getFirstArrivingTime(ArrayList<processs> processes) {
		int min = Integer.MAX_VALUE;
		for (processs p : processes) {
			if (p.getArrivingTime() < min) {
				min = p.getArrivingTime();
			}
		}
		return min;
	}

	private int totalBurstTime(ArrayList<processs> processes) {
		int total = 0;
		for (processs p : processes) {
			total += p.getBurstTime();
		}
		return total;
	}
}