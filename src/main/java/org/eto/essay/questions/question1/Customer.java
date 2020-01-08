package org.eto.essay.questions.question1;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 
 * 消费者
 * 
 * @author shanhm1991
 *
 */
public class Customer extends Thread{
    
    private static final Logger LOG = Logger.getLogger(Customer.class);
    
    private static AtomicInteger index = new AtomicInteger(0);
    
    private TaskManager<Task> taskManager;
    
    public Customer(TaskManager<Task> taskManager){
        this.setName("Thread-Customer-" + index.incrementAndGet()); 
        this.taskManager = taskManager;
    }
    
    @Override
    public void run(){
        while(true){
            Task task;
            try {
                task = taskManager.take();
            } catch (InterruptedException e) {
                LOG.info("停止消费"); 
                //可以将未消费的任务持久化
                return;
            }
            LOG.info("射出子弹" + task.getIndex()); 
            task.run();
        }
    }
}

