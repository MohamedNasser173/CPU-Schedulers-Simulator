package schedulers;

import java.util.*;

public class MultiLevel {
	int Quantum;
	int NumOfProcesses;

	ArrayList<Process> processesList = new ArrayList<Process>(); /// Info table of the processes
	ArrayList<Process> excudeOrder = new ArrayList<Process>(); /// execution queue, (simulating Gantt chart)
	Queue<Process> RRqueue = new LinkedList<Process>();
	Queue<Process> FCFSqueue = new LinkedList<Process>();
	ArrayList<Integer> endTime = new ArrayList<Integer>();

	double averageWaitingTime = 0.0;
	double averageTurnAroundTime = 0.0;
	int timer = 0;

	public static class sortByArrivalTime implements Comparator<Process> {

		@Override
		public int compare(Process o1, Process o2) { /// If arrival times are equal, favor queue 1 process
			if (o1.arrivalTime - o2.arrivalTime == 0) {
				return o1.queueNumber - o2.queueNumber;
			}
			return o1.arrivalTime - o2.arrivalTime;

		}
	}

	void GetData() { // Take processes, arranges them in queue according to their arrival time.
		Scanner input = new Scanner(System.in);
		System.out.println("Enter number of processes");
		NumOfProcesses = input.nextInt();

		System.out.println("Enter the process name || Burst time || arrival time || Queue Number");
		for (int i = 0; i < NumOfProcesses; i++) {
			String processName = input.next();
			Process p = new Process();
			p.name = processName;

			p.CopyBurstTime = input.nextInt();
			p.burstTime = p.CopyBurstTime;
			Integer n = input.nextInt();
			p.arrivalTime = n;
			p.queueNumber = input.nextInt();
			processesList.add(p);
		}

		System.out.println("Enter Time Quantum");
		Quantum = input.nextInt();

		Collections.sort(processesList, new MultiLevel.sortByArrivalTime());

	}

	void run_multiLevel() {
		GetData();
		executeProcess();
		print_output();

	}

	void executeProcess() {
		/// start process, look at processTable w get
		endTime.add(0);
		int t = 0;
		int quantumTimer = 0;
		Process prevProcess = null; /// A process to placed to the back of the queue if its quantum ended,
		// it will not be directly added, so we can check if new process arrived at that
		// same moment
		// remProcesses =processesList.size();
		for (timer = 0; NumOfProcesses > 0; timer++) {

			for (int i = 0; i < processesList.size(); i++) { /// if process arrived, add it to its queue
				Process p = processesList.get(i);
				if (p.arrivalTime == timer) {
					if (p.queueNumber == 1) {
						RRqueue.add(p);
					} else {
						FCFSqueue.add(p);
					}
				}
			}
			if (prevProcess != null) {
				RRqueue.add(prevProcess);
				prevProcess = null;
			}
			if (RRqueue.size() > 0) {

				if (t != 0) {
					endTime.add(t);
					t = 0;

				}
				/// fetch process
				Process p = RRqueue.peek();
				if (excudeOrder.size() == 0 || excudeOrder.get(excudeOrder.size() - 1) != p) { // check if the same
																								// process is being
																								// executed
					excudeOrder.add(p);
				}
				p.CopyBurstTime--;
				quantumTimer++;
				if (p.CopyBurstTime == 0) { // process ended
					p.turnAroundTime = (timer + 1) - p.arrivalTime;
					p.waitingTime = p.turnAroundTime - p.burstTime;
					quantumTimer = 0;
					prevProcess = null;
					RRqueue.poll();
					NumOfProcesses--;
					processesList.set(processesList.indexOf(p), p);
					endTime.add(timer + 1);
					continue;
				}
				// quantum time ended and process didn't end
				else if (timer > 0 && quantumTimer % Quantum == 0) {
					if (RRqueue.size() > 1) { // It is not the only process in the queue
//                           RRqueue.add(p);
						prevProcess = p;
						endTime.add(timer + 1);

						RRqueue.poll();
						continue;
					}

				}

			} else if (FCFSqueue.size() > 0) { /// else Fetch FCFS
				quantumTimer = 0; // an FCFS process is under exeuction, reset the quantumTimer in case
									// interruption happened
				Process p = FCFSqueue.peek();
				if (excudeOrder.size() == 0 || excudeOrder.get(excudeOrder.size() - 1) != p) {
					excudeOrder.add(p);
				}
				t = timer + 1;
				p.CopyBurstTime--;
				if (p.CopyBurstTime == 0) { // process ended
					p.turnAroundTime = (timer + 1) - p.arrivalTime;
					p.waitingTime = p.turnAroundTime - p.burstTime;
					processesList.set(processesList.indexOf(p), p); /// update process p with waiting time and turn
																	/// around time in the processList
					endTime.add(timer + 1);
					NumOfProcesses--;
					FCFSqueue.poll();
				}
				continue;

			}

		}
	}

	void print_output() {
		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");
		System.out.println("");
		{
			for (int i = 0; i < excudeOrder.size(); i++)
				System.out.print("|  " + excudeOrder.get(i).name + "  ");
			System.out.println("|");
		}

		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");
		for (int i = 0; i < endTime.size(); i++)
			System.out.print(endTime.get(i) + "      ");
		System.out.println("    ");
		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");

		System.out.println("Process Name    Turnaround Time   Waiting Time ");
		for (int i = 0; i < processesList.size(); i++) {
			Process p = processesList.get(i);
			averageTurnAroundTime += p.turnAroundTime;
			averageWaitingTime += p.waitingTime;
			System.out.println(" " + p.name + "     \t    " + p.turnAroundTime + "    \t     " + p.waitingTime);
		}

		averageTurnAroundTime /= processesList.size();
		averageWaitingTime /= processesList.size();

		System.out.println("Average Turnaround Time = " + averageTurnAroundTime);
		System.out.println("Average WaitingTime = " + averageWaitingTime);
		System.out.println("    ");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("    ");
	}
}

/*
 * p1 4 0 1 p2 3 0 1 p3 8 0 2 p4 5 10 1
 */
