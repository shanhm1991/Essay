package org.eto.essay.io.bio.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;

import org.apache.log4j.Logger;
import org.eto.essay.io.Calculator;
import org.eto.essay.io.Util;
import org.eto.essay.io.bio.msg.Request1;

/**
 * 
 * @author shanhm1991
 *
 */
public class Request1Handler extends Handler<Request1>{

	private static final Logger LOG = Logger.getLogger(Request1Handler.class);
	
	private static SecureRandom random = new SecureRandom();

	@Override
	public void handler(Socket socket,Request1 request) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			long beginTime = System.currentTimeMillis();

			LOG.info("处理" + request.getName() + "的请求:" + request.getMsg());
			String response;
			try {
				response = String.valueOf(Calculator.conversion(request.getMsg()));
			} catch (Exception e) {
				response = "server exception：" + e.getMessage();
			}

			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				Util.close(socket);
				LOG.warn("处理被中断");
				return;
			}

			out.println(response);
			LOG.info("返回响应:" + response + " ,处理耗时：" + (System.currentTimeMillis() - beginTime));
		} catch (IOException e) {
			LOG.error("处理异常：" + e.getMessage());
		} finally {
			Util.close(socket);
		}
	}
}