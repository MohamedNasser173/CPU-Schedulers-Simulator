package schedulers;

import java.util.ArrayList;

public class WaitingQueue {
	private ArrayList<processs> queue;

	public WaitingQueue() {
		queue = new ArrayList<>();
	}

	public processs dequeue() {
		processs p = null;
		if (!isEmpty()) {
			p = queue.get(0);
			queue.remove(p);
		}
		return p;
	}

	public void enqueue(processs process) {
		if (queue.isEmpty()) {
			queue.add(process);
		} else if (!this.contain(process)) {
			int i;
			for (i = 0; i < queue.size(); i++) {
				if (queue.get(i).getPriority() > process.getPriority()) {
					queue.add(i, process);
					break;
				}
			}

			if (i == queue.size()) {
				queue.add(process);
			}
		}
	}

	private boolean contain(processs process) {
		for (processs p : queue) {
			if (p.getProcessID() == process.getProcessID())
				return true;
			return false;
		}
		return false;
	}

	public int size() {
		return queue.size();
	}

	public Boolean isEmpty() {
		return (queue.size() == 0);
	}
}
