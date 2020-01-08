package org.eto.essay.questions.question1;

/**
 * 
 * 设计一个符合生产者和消费者问题的程序。对一个对象（枪膛）进行操作，其最大容量是12颗子弹。
 * 生产者线程是一个压入线程，它不断向枪膛中压入子弹；消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * 
 * 要求：
 *（1）为了防止两个线程访问一个资源时出现忙等待，要使用的wait-notify函数，是两个线程交替执行;
 *（2）程序输出，要模拟体现对枪膛的压入和射出操作；
 * 
 * @author shanhm1991
 *
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {

		//可以启动多个Producer及Customer，只要使用的是同一个TaskManager
		TaskManager<Task> taskManager = new TaskManager<Task>();

		Producer producer1 = new Producer(taskManager);
		Producer producer2 = new Producer(taskManager);

		Customer customer1 = new Customer(taskManager);
		Customer customer2 = new Customer(taskManager);
		Customer customer3 = new Customer(taskManager);

		producer1.start();
		producer2.start();

		customer1.start();
		customer2.start();
		customer3.start();

		Thread.sleep(1);
		producer1.interrupt();
		producer2.interrupt();

		Thread.sleep(5);
		customer1.interrupt();
		customer2.interrupt();
		customer3.interrupt();
	}
}
