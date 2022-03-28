package schedulers;

import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		while (true) {

			System.out.println("(1) preemptiveShortest-Job First(SJF) ");
			System.out.println("(2) RoundRobin(RR)  ");
			System.out.println("(3) preemptive Priority Scheduling ");
			System.out.println("(4) Multi level Scheduling : ");

			Scanner input = new Scanner(System.in);
			System.out.println("Please Enter your choice ");
			int ch = input.nextInt();
			switch (ch) {
			case 1: {
				SJF obj1 = new SJF();
				obj1.runSJF();

				break;
			}

			case 2: {
				RR obj2 = new RR();
				obj2.runRR();
				break;
			}

			case 3: {
				PreePrioHelper obj = new PreePrioHelper();
				obj.main();
				break;
			}

			case 4: {
				MultiLevel obj3 = new MultiLevel();
				obj3.run_multiLevel();

				break;
			}

			}

		}

	}

}
