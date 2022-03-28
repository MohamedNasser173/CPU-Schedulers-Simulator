package schedulers;

import java.util.ArrayList;
import java.util.Scanner;

public class FCFS {

	String[] names;
	int[] arrivalTime;
	int[] burstTime;
	public int numberOfProcess;

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

	public void run_FCFS() {
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

		for (int i = 0; i < numberOfProcess; i++) {
			copyBurst[i] = burstTime[i];
			flag[i] = 0;
		}

		currTime = arrivalTime[getIndexOfMin(arrivalTime)];
		// execOrder.add(String.valueOf(currTime));
		lasttime.add(0);
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
			currTime += minBurstTime;
			String s = names[indexOfMinBurst];
			order.add(s);
			lasttime.add(currTime);
			flag[indexOfMinBurst] = 1;
			burstTime[indexOfMinBurst] = 0;
			done++;

		}

		for (int i = 0; i < numberOfProcess; i++) {
			turnaroundTime[i] = lasttime.get(i + 1);
			waitingTime[i] = turnaroundTime[i] - burstTime[i];
		}

		System.out.println("____________________________________________________________");

		for (int k = 0; k < numberOfProcess; k++) {
			System.out.print(order.get(k) + " ");
		}
		System.out.println("");
		for (int k = 0; k < lasttime.size(); k++) {
			System.out.print(lasttime.get(k) + " ");

		}
		System.out.println("");
		System.out.println("____________________________________________________________");

		System.out.println("name      wait    turnaround");
		for (int i = 0; i < numberOfProcess; i++) {
			System.out.println(names[i] + "   " + waitingTime[i] + "    " + turnaroundTime[i]);
		}

	}

}
