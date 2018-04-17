package com.test.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerTest1 {
	//使用AtomicInteger 代替 Integer，我们可以实现线程安全地并行增加数，这里没有使用 synchronizing 关键字。incrementAndGet方法是原子操作，且是线程安全的，所以可以在多线程中调用

	public static void main(String[] args) {
		AtomicInteger ato = new AtomicInteger(0);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		//IntStream.range(0, 100).forEach(i -> executor.submit(ato::incrementAndGet));
		// () -> 一个新线程的run方法
		IntStream.range(1, 10).forEach(i -> executor.submit(() -> {ato.incrementAndGet();System.out.println("执行了-"+i);}));
		
		// shutdown方法：平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
		executor.shutdown();
		//executor.awaitTermination(1, TimeUnit.SECONDS);
		// awaitTermination方法：接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用
		
		System.out.println(ato.get());
		
	}

}
