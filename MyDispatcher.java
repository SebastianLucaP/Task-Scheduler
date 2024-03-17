/* Implement this class. */

import java.util.List;

public class MyDispatcher extends Dispatcher {

    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }
    int i = 0;
    @Override
    public void addTask(Task task) {
        if(algorithm.equals(SchedulingAlgorithm.ROUND_ROBIN)) {
            hosts.get(i % hosts.size()).addTask(task);
            i++;
        }
        else if(algorithm.equals(SchedulingAlgorithm.SHORTEST_QUEUE)) {
            int minID = 0;
            int min = hosts.get(0).getQueueSize();
            for(Host host : hosts) {
                if(host.getQueueSize() < min) {
                    minID = hosts.indexOf(host);
                    min = host.getQueueSize();
                }
                else if(host.getQueueSize() == min) {
                    if(hosts.indexOf(host) < minID) {
                        minID = hosts.indexOf(host);
                    }
                }

            }
            hosts.get(minID).addTask(task);
        }
        else if(algorithm.equals(SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT)) {
            if(task.getType().equals(TaskType.SHORT)) {
                hosts.get(0).addTask(task);
            }
            else if(task.getType().equals(TaskType.MEDIUM)) {
                hosts.get(1).addTask(task);
            }
            else if(task.getType().equals(TaskType.LONG)) {
                hosts.get(2).addTask(task);
            }
        }
        else if(algorithm.equals(SchedulingAlgorithm.LEAST_WORK_LEFT)) {
            int minID = 0;
            long min = hosts.get(0).getWorkLeft();
            for(Host host : hosts) {
                if(host.getWorkLeft() < min) {
                    minID = hosts.indexOf(host);
                    min = host.getWorkLeft();
                }
                else if(host.getWorkLeft() == min) {
                    if (hosts.indexOf(host) < minID) {
                        minID = hosts.indexOf(host);
                    }
                }
            }
            hosts.get(minID).addTask(task);
        }
    }
}
