/* Implement this class. */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class MyHost extends Host {
    PriorityBlockingQueue<Task> tasks = new PriorityBlockingQueue<>(11, new Comparator<Task>() {
        public int compare(Task a, Task b) {
            if(a.getPriority() == b.getPriority()) {
                return a.getId() - b.getId();
            }
            return b.getPriority() - a.getPriority();
        }
    });
    boolean active = true;
    int activeTasks = 0;
    long timeLeft = 0;
    @Override
    public void run() {
        while(active) {
            Task task = tasks.poll();
            if(task != null) {
                activeTasks = 1;
                timeLeft = task.getLeft();
                while (task.getLeft() > 0) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    task.setLeft(task.getLeft() - 250);
                    timeLeft = task.getLeft();
                    if(task.isPreemptible() && tasks.peek() != null && tasks.peek().getPriority() > task.getPriority()) {
                        tasks.add(task);
                        activeTasks = 0;
                        break;
                    }
                }
                task.finish();
                activeTasks = 0;
            }
        }
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public int getQueueSize() {
        return tasks.size() + activeTasks;
    }

    @Override
    public long getWorkLeft() {
        long workLeft = 0;
        for(Task task : tasks) {
            workLeft += task.getLeft();
        }
        return (workLeft + timeLeft) / 100 * 100;
    }

    @Override
    public void shutdown() {
        active = false;
    }
}
