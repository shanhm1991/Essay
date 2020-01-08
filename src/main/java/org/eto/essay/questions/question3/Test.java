package org.eto.essay.questions.question3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 启动三个线程打印递增的数字，
 * 线程1先打印1,2,3,4,5，然后是线程2打印6,7,8,9,10，然后是线程3打印11,12,13,14,15，
 * 接着再由线程1打印16,17,18,19,20...由此类推，直到打印到75
 * 
 * @author shanhm1991
 *
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {

		Lock lock = new ReentrantLock();
		Condition condition_1 = lock.newCondition();
		Condition condition_2 = lock.newCondition();
		Condition condition_3 = lock.newCondition();

		Printer printer1 = new Printer("Print-Thread-1",lock,condition_1,condition_2,5);
		Printer printer2 = new Printer("Print-Thread-2",lock,condition_2,condition_3,5);
		Printer printer3 = new Printer("Print-Thread-3",lock,condition_3,condition_1,5);

		printer1.start();
		printer2.start();
		printer3.start();

		//这里主线程睡一会，等待printer到达阻塞点，如果提前唤醒，printer将会永远阻塞下去
		Thread.sleep(10);

		//放开第一个
		lock.lock();
		try{
			condition_1.signal();
		} finally{
			lock.unlock();
		}
	}
}
