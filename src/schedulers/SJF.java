package schedulers;

import java.util.ArrayList;
import java.util.Scanner;

public class SJF {
	String[] names;
	int[] arrivalTime;
	int[] burstTime;
	public int numberOfProcess;
	public int ContextSwitching;

	public SJF() {

	}

	public void get_data() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of processes:");
		numberOfProcess = input.nextInt();
		names = new String[numberOfProcess];
		arrivalTime = new int[numberOfProcess];
		burstTime = new int[numberOfProcess];

		System.out.println("Enter the process name || Burst time || arrival time");
		for (int i = 0; i < numberOfProcess; i++) {

			names[i] = input.next(); // name of process

			burstTime[i] = input.nextInt(); // Burst time of process

			arrivalTime[i] = input.nextInt(); // arrival time of process
		}

		System.out.println("Enter the Context switching");
		ContextSwitching = input.nextInt();

	}

	public int getIndexOfMin(int[] arr) {
		int Min = arr[0];
		int Index = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < Min) {
				Min = arr[i];
				Index = i;
			}
		}

		return Index;
	}

	public void runSJF() {
		get_data();

		int[] copyBurst = new int[numberOfProcess];
		int[] waitingTime = new int[numberOfProcess];
		int[] turnaroundTime = new int[numberOfProcess];
		int[] completionTime = new int[numberOfProcess];
		int[] flag = new int[numberOfProcess];// for completed process
		ArrayList<String> execOrder = new ArrayList<String>();
		ArrayList<String> order = new ArrayList<String>();
		ArrayList<Integer> lasttime = new ArrayList<Integer>();
		int sp = -1;
		int currTime = 0, done = 0;
		double avgwaiting = 0, avgturnarround = 0;

		for (int i = 0; i < numberOfProcess; i++)
			copyBurst[i] = burstTime[i];

		currTime = arrivalTime[getIndexOfMin(arrivalTime)];
		execOrder.add(String.valueOf(currTime));

		while (true) {
			int minBurstTime = 111111;
			int indexOfMinBurst = numberOfProcess;

			if (done == numberOfProcess)
				break;

			for (int i = 0; i < numberOfProcess; i++) // we make this loop to know Smallest burst time
			{
				if (arrivalTime[i] <= currTime && flag[i] == 0 && burstTime[i] < minBurstTime) {
					minBurstTime = burstTime[i];
					indexOfMinBurst = i;
				}
			}

			String name = names[indexOfMinBurst]; // 00000000000000000000

			if (execOrder.get(execOrder.size() - 1).equals(name))
				execOrder.add(name);
			else {
				execOrder.add(name);
				sp++;
			}
			if (indexOfMinBurst == numberOfProcess) {
				currTime++;
			} else {

				burstTime[indexOfMinBurst]--;
				currTime++;

				if (burstTime[indexOfMinBurst] == 0) {
					completionTime[indexOfMinBurst] = currTime + sp;
					flag[indexOfMinBurst] = 1;
					done++;

				}
			}

		}
		/*
		 * In the End of the while loop we have execOrder array contain the process name
		 * 
		 */

		int time = 1;
		int num = 0; // number of process that executed
		lasttime.add(0);
		for (int i = 1; i < execOrder.size() - 1; i++) {
			if (execOrder.get(i).equals(execOrder.get(i + 1))) {
				time++;
				num = 0;
			} else {
				lasttime.add(time);

				order.add(execOrder.get(i));
				order.add("");

				time += ContextSwitching;
				lasttime.add(time);
				num = 1;
				time++;

			}

		}

		if (num == 0) { // if the number = 0 that means there is one process executed
			order.add(execOrder.get(execOrder.size() - 1));
			lasttime.add(time);

		}

		for (int i = 0; i < numberOfProcess; i++) {

			waitingTime[i] = completionTime[i] - arrivalTime[i] - copyBurst[i];
			turnaroundTime[i] = copyBurst[i] + waitingTime[i];
			avgwaiting += waitingTime[i];
			avgturnarround += turnaroundTime[i];

		}

		avgwaiting /= (float) numberOfProcess;
		avgturnarround /= (float) numberOfProcess;
		System.out
				.println("name      burstTime      arrivalTime      completionTime    turnarroundTime    waitingTime");
		for (int i = 0; i < numberOfProcess; i++) {
			System.out.println(names[i] + "\t\t" + copyBurst[i] + "\t\t" + arrivalTime[i] + "\t\t" + completionTime[i]
					+ "\t\t" + turnaroundTime[i] + "\t\t   " + waitingTime[i]);
		}
		System.out.println("Average waiting time = " + avgwaiting);
		System.out.println("Average turnarround  = " + avgturnarround);

		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");
		System.out.println("");
		if (ContextSwitching != 0) {
			for (int i = 0; i < order.size(); i++)
				System.out.print("|  " + order.get(i) + "  ");
			System.out.println("|");
		}

		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");
		for (int i = 0; i < lasttime.size(); i++)
			System.out.print(lasttime.get(i) + "     ");
		System.out.println("    ");
		System.out.println(
				"_______________________________________________________________________________________________________________________________________________________________");

	}

	/*
	 * 
	 * p1 9 0 p2 7 2 p3 3 1 p4 6 3 p5 5 4
	 * 
	 */

}
