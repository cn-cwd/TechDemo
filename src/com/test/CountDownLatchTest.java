package com.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;

public class CountDownLatchTest {
	private static ThreadLocal<String> threadLocal = new  ThreadLocal<String>();
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
//		countDownLatchTest.test3();
		countDownLatchTest.threadPool();
	}
	
	public void test1() throws InterruptedException{
		CountDownLatch countDownLatch  = new CountDownLatch(5);
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				try {
					Thread.sleep(new Double(Math.random()*3000).longValue());
					System.out.println(Thread.currentThread().getName() + "准备就绪");
					countDownLatch.countDown();
				} catch(InterruptedException exception) {
					exception.printStackTrace();
				}
			}).start();
		}
		countDownLatch.await();
		System.out.println("请选择英雄");
	}
	
	public void test2(){
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				try {
					Thread.sleep(new Double(Math.random()*3000).longValue());
					System.out.println(Thread.currentThread().getName() + "准备就绪");
					cyclicBarrier.await();
					System.out.println("请选择英雄");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				} 
				
			}).start();
		}
	}
	
	public void test3(){
		Phaser phaser = new Phaser(5);
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				try {
					Thread.sleep(new Double(Math.random()*3000).longValue());
					System.out.println(Thread.currentThread().getName() + "准备就绪");
					phaser.arriveAndAwaitAdvance();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}
		if(phaser.getPhase() >= 0){
			switch (phaser.getPhase()) {
			case 0:
				phaser.awaitAdvance(phaser.getPhase());
				System.out.println("第"+ phaser.getPhase() + "号房间游戏开始");
				break;
			case 1:
				phaser.awaitAdvance(phaser.getPhase());
				System.out.println("第"+ phaser.getPhase() + "号房间游戏开始");
				break;

			default:
				break;
			}
			
		}else{
			System.out.println("系统错误");
		}
	}
	
	
	public void threadPool(){
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		for ( int i = 0; i < 100; i++) {
			newFixedThreadPool.execute(new Run(i));
		}
	}
	
	class Run implements Runnable{
		int i = 0;
		
		public Run(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "线程;  "+ " 变量i的值：" +i);
		}
		
	}
}
