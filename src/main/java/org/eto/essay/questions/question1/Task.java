package org.eto.essay.questions.question1;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 
 * 任务对象：子弹
 * 
 * @author shanhm1991
 *
 */
public class Task implements Runnable{
	 
    private static final Logger LOG = Logger.getLogger(Task.class);
    
    private static AtomicInteger index = new AtomicInteger(0);
    
    private int currentIndex;
    
    public Task(){
        currentIndex = index.incrementAndGet();
    }
    
    @Override
    public void run() {
        LOG.info("子弹" + currentIndex + "执行飞行"); 
    }
 
    public String getIndex(){
        return String.valueOf(currentIndex);
    }
}
