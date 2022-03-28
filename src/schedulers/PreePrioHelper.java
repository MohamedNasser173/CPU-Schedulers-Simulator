package schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreePrioHelper {
	public ArrayList<processs> processes;
	public Scanner input;

	float avgWait = 0;
	float avgTurnAround = 0;

	public PreePrioHelper() {
		processes = new ArrayList<>();
		input = new Scanner(System.in);
		int n, id, priority, pAT, pBT;

		System.out.print("Enter Number of proccesses: ");
		while (!input.hasNextInt()) {
			System.out.print("Error! enter integer pls: ");
			input.next();
		}
		n = input.nextInt();

		System.out.println("\nPlease Enter processes properties. ");
		for (int i = 0; i < n; i++) {
			System.out.print("Enter ID: ");
			while (!input.hasNextInt()) {
				System.out.print("Wrong input!");
				input.next();
			}
			id = input.nextInt();
			System.out.print("Enter Priority: ");
			while (!input.hasNextInt()) {
				System.out.print("Wrong input! ");
				input.next();
			}
			priority = input.nextInt();
			System.out.print("Enter Arriving Time: ");
			while (!input.hasNextInt()) {
				System.out.print("Enter Wrong input! ");
				input.next();
			}
			pAT = input.nextInt();
			System.out.print("Enter Burst Time: ");
			while (!input.hasNextInt()) {
				System.out.print("Wrong input! ");
				input.next();
			}
			pBT = input.nextInt();
			processes.add(new processs(id, priority, pAT, pBT));
			System.out.print("\n");
		}
		;

		main();
	}

	public static ArrayList<processs> cloneInputProcesses(ArrayList<processs> processes) {
		ArrayList<processs> Cpy = new ArrayList<>();
		for (processs p : processes) {
			Cpy.add(new processs(p.getProcessID(), p.getPriority(), p.getArrivingTime(), p.getBurstTime()));
		}
		return Cpy;
	}

	public void main() {
		ArrayList<processs> Cpy = PreePrioHelper.cloneInputProcesses(processes);

		List<int[]> Gantt = new PreemptiveScheduling().getGantt(processes);

		System.out.println("Gantt Chart: ");
		for (int i = 0; i < Gantt.size(); i++) {
			if (i == 0)
				System.out.print("|" + Gantt.get(i)[1] + "| --P" + Gantt.get(i)[0] + "-- |" + Gantt.get(i)[2] + "|");
			else
				System.out.print(" --P" + Gantt.get(i)[0] + "-- |" + Gantt.get(i)[2] + "|");
		}

		System.out.println("\nCompletion Time");
		for (processs p : Cpy) {
			int completionTime = PreemptiveScheduling.getCompletionTime(p, Gantt);
			System.out.println("P" + p.getProcessID() + ": t = " + completionTime);
		}
		System.out.print("\n");

		System.out.println("Turn Around Time");
		for (processs p : Cpy) {
			int turnAroundTime = PreemptiveScheduling.getTurnAroundTime(p, Gantt);
			System.out.println("P" + p.getProcessID() + ": t = " + turnAroundTime);
			avgTurnAround += turnAroundTime;
		}
		System.out.print("\n");

		System.out.println("Waiting Time");
		for (processs p : Cpy) {
			int waitingTime = PreemptiveScheduling.getWaitingTime(p, Gantt);
			System.out.println("P" + p.getProcessID() + ": t = " + waitingTime);
			avgWait += waitingTime;
		}
		System.out.print("\n");

		avgTurnAround = avgTurnAround / Cpy.size();
		avgWait = avgWait / Cpy.size();
		System.out.println("Avg TurnAround Time : " + avgTurnAround);
		System.out.println("Avg Waiting Time : " + avgWait);
	}

}
