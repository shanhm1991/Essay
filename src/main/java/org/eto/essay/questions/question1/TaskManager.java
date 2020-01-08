package org.eto.essay.questions.question1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 
 * 使用wait-notify实现的任务管理容器
 * 
 * @author shanhm1991
 *
 */
public class TaskManager<T extends Runnable> {
	 
    private static final Logger LOG = Logger.getLogger(TaskManager.class);
 
    private static final int SIZE = 12;
 
    private AtomicInteger count = new AtomicInteger(0);
 
    /**
     * 使用wait-notify及普通list构建个可以阻塞等待的容器。相当于LinkedBlockingQueue
     */
    List<T> taskList = new LinkedList<T>();
 
    public synchronized void put(T task) throws InterruptedException{ 
        while(isFull()){
            wait();
        }
        boolean isEmpty = isEmpty();
        taskList.add(task);
        count.incrementAndGet();
        /**
         * 这里加一个isEmpty判断减少唤醒的次数，因为没必要每次put都进行唤醒，只有放入之前是empty的时候才需要唤醒customer.
         * 如果每次都唤醒，使producer与customer交替执行。size=12也没有意义了。
         * 这里put与take等待的其实是两个不同的条件，如果使用Condition来代替内置锁的wait-notify可以更好的降低在锁上面的竞争
         */
        if(isEmpty){
            LOG.info("唤醒消费者"); 
            notifyAll();
        }
    }
 
    public synchronized T take() throws InterruptedException{
        while(isEmpty()){
            wait();
        }
        boolean isFull = isFull();
        T task = taskList.remove(0);
        count.decrementAndGet();
        if(isFull){
            LOG.info("唤醒生产者线程"); 
            notifyAll();
        }
        return task;
    }
 
    private boolean isFull(){
        return count.get() == SIZE;
    }
 
    private boolean isEmpty(){
        return count.get() == 0;
    }
}
