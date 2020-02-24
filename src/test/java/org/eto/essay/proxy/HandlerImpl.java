package org.eto.essay.proxy;

//RealSubject
public class HandlerImpl implements Handler {

	@Override
	public void query(String id) {
		System.out.println("query[" + id + "]...");
	}

	@Override
	public void delete(String id) {
		System.out.println("delete[" + id + "]...");
	}
}
