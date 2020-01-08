package org.eto.essay.questions.question1;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 
 * 生产者
 * 
 * @author shanhm1991
 *
 */
public class Producer extends Thread{
    
    private static final Logger LOG = Logger.getLogger(Producer.class);
    
    private static AtomicInteger index = new AtomicInteger(0);
    
    private TaskManager<Task> taskManager;
    
    public Producer(TaskManager<Task> taskManager){
        this.setName("Thread-Producer-" + index.incrementAndGet()); 
        this.taskManager = taskManager;
    }
    
    @Override
    public void run(){
        while(true){
            Task task = new Task();
            try {
                taskManager.put(task);
            } catch (InterruptedException e) {
                LOG.info("停止生产"); 
                return;
            }
            LOG.info("压入子弹" + task.getIndex()); 
        }
        
    }
}
