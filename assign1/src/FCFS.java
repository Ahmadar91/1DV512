

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class FCFS {

	// The list of processes to be scheduled
	public ArrayList<Process> processes;
	private int taskTime;
	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		this.processes = processes;
		taskTime = 0;
	}

	public void run() {
		Queue<Process> processQueue = new LinkedList<Process>();
		sortArrivalTime(); 
		for (int i = 0; i < processes.size(); i++) {
			processQueue.add(processes.get(i));
		}
		while (!processQueue.isEmpty()) {
			if (taskTime >= processQueue.peek().getArrivalTime()) {
				Process process = processQueue.remove();
				for(int i = 0; i< process.getBurstTime(); i++) {
					taskTime++;
				}
				process.setCompletedTime(taskTime); 
				process.setTurnaroundTime( process.completedTime - process.arrivalTime);
				process.setWaitingTime( process.turnaroundTime - process.burstTime);
			}else taskTime++;
		}
	}
	public void sortArrivalTime() {
		Comparator<Process> proc = (p1, p2) -> p1.getArrivalTime() - p2.getArrivalTime();
		Collections.sort(processes, proc);
	}


	public void printTable() {
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("PID		AT		BT		CT		TAT		WT");
		for (int i = 0; i < processes.size(); i++) {

			System.out.println(processes.get(i).getProcessId() + "		" + processes.get(i).getArrivalTime()
					+ "		" + processes.get(i).getBurstTime() + "		" + processes.get(i).getCompletedTime()
					+ "		" + processes.get(i).getTurnaroundTime() + "		" + processes.get(i).getWaitingTime());
		}
		System.out.println("------------------------------------------------------------------------------------");
	}

	public void printGanttChart() {
		System.out.printf("%83s", "%%%%%%%%%%%%GranttChart%%%%%%%%%%%%%%\n");
		System.out.printf("%s", repeatXtimes("=", 119));
		CreateGnatt();
		System.out.printf("\n%s", repeatXtimes("=", 119));
		printTimeUnder();

	}

	public void CreateGnatt() {
		int maxlength = processes.get(processes.size() - 1).getCompletedTime();
		System.out.println();
		int previous = 0;
		String s = "|";

		if (maxlength % 2 == 1) {
			for (int i = 0; i < processes.size(); i++) {
				int scale = (processes.get(i).getCompletedTime()) * 119 / maxlength;

				int cpuwait = getCPUWaitingTime(i);
				if (cpuwait > 0) {
					s += repeatXtimes("¤", cpuwait - 1);
					s += "||";
				}
				int check = scale - previous - 1;
				if (cpuwait > 0)
					check -= cpuwait + 2;
				s += repeatXtimes(" ", (check / 2) - (2 + check % 3) - 1);
				s += ("P" + processes.get(i).getProcessId());
				s += (repeatXtimes(" ", check / 2 - 1));
				s += "||";
				previous = scale;
			}
		} else
			for (int i = 0; i < processes.size(); i++) {
				int scale = (processes.get(i).getCompletedTime()) * 119 / maxlength;

				int cpuwait = getCPUWaitingTime(i);
				if (cpuwait > 0) {
					s += repeatXtimes("¤", cpuwait - 1);
					s += "||";
				}
				int check = scale - previous - 1;
				if (cpuwait > 0)
					check -= cpuwait + 2;
				s += repeatXtimes(" ", (check / 2) - (2 + check % 2) - 1);
				s += ("P" + processes.get(i).getProcessId());
				s += (repeatXtimes(" ", check / 2 - 2));
				s += "||";
				previous = scale;
			}
		System.out.print(s);
	}

	public void printTimeUnder() {
		int maxlength = processes.get(processes.size() - 1).getCompletedTime();
		System.out.println();
		String s = "0";
		int previous = 0;
		if (maxlength % 2 == 1) {
			for (int i = 0; i < processes.size(); i++) {
				int scale = (processes.get(i).getCompletedTime()) * 119 / maxlength;
				int cpuwait = getCPUWaitingTime(i);
				if (cpuwait > 0) {
					s += repeatXtimes(" ", cpuwait - 1);
					s += getCPUWaitingTime(i) + processes.get(i - 1).getCompletedTime();
				}
				if (cpuwait > 0) {
					s += repeatXtimes(" ", scale - previous - 1
							- (String.valueOf(processes.get(i).getCompletedTime()).length()) - (cpuwait + 1));
				} else {
					s += repeatXtimes(" ", scale - previous - 1
							- (String.valueOf(processes.get(i).getCompletedTime()).length()) - (cpuwait + 0));
				}
				s += processes.get(i).getCompletedTime();
				previous = scale;
			}
		} else
			for (int i = 0; i < processes.size(); i++) {
				int scale = (processes.get(i).getCompletedTime()) * 119 / maxlength;
				int cpuwait = getCPUWaitingTime(i);
				if (cpuwait > 0) {
					s += repeatXtimes(" ", cpuwait - 1);
					s += getCPUWaitingTime(i) + processes.get(i - 1).getCompletedTime();
				}
				if (cpuwait > 0) {
					s += repeatXtimes(" ", scale - previous - 1
							- (String.valueOf(processes.get(i).getCompletedTime()).length()) - (cpuwait + 2));
				} else {
					s += repeatXtimes(" ", scale - previous - 1
							- (String.valueOf(processes.get(i).getCompletedTime()).length()) - (cpuwait + 0));
				}
				s += processes.get(i).getCompletedTime();
				previous = scale;
			}
		System.out.println(s);
	}

	public int getCPUWaitingTime(int x) {
		if (x == 0) {
			return 0;
		}
		return processes.get(x).getCompletedTime() - processes.get(x).getBurstTime()
				- processes.get(x - 1).getCompletedTime();
	}

	public String repeatXtimes(String s, int x) {
		String str = s;
		for (int i = 0; i < x; ++i) {
			str += s;
		}
		return str;
	}
}
