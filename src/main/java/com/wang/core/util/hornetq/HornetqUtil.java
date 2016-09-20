package com.wang.core.util.hornetq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.wang.core.bean.BehaviorBean;

/**
 * HornetQ工具类
 * 
 * @author HeJiawang
 * @date   2016.09.20
 */
public class HornetqUtil {

	protected static final Logger logger = LoggerFactory.getLogger(HornetqUtil.class);

	public static void sendMessage(BehaviorBean behavior) throws JMSException {
		if (behavior != null) {
			Connection connection = HQConnection.createConn();
			Session session = null;
			Gson gson = new Gson();
			// 支持HornetQ服务断线重连
			try {
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			} catch (Exception e) {
				HQConnection.releaseConnection();
				connection = HQConnection.createConn();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			}
			MessageProducer producer = session.createProducer(HornetQJMSClient.createQueue(HornetqConfUtil.QUEUE));

			//ObjectMessage message = session.createObjectMessage(behavior);
			
			TextMessage message = session.createTextMessage(AESEncrypterUtil.getInstance().encrypt(gson.toJson(behavior)));
			producer.send(message);	
		}
	}

	public static void receiveMessage() throws JMSException, IOException {
		
		Connection connection = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		map.put(TransportConstants.HOST_PROP_NAME, HornetqConfUtil.RECEIVE_HOST);
		map.put(TransportConstants.PORT_PROP_NAME, Integer.parseInt(HornetqConfUtil.RECEIVE_PORT));
		TransportConfiguration server = new TransportConfiguration(NettyConnectorFactory.class.getName(), map);
		ConnectionFactory connectionFactory = (ConnectionFactory) HornetQJMSClient
				.createConnectionFactoryWithoutHA(JMSFactoryType.CF, server);
		connection = connectionFactory.createConnection();
		BehaviorBean behavior = new BehaviorBean();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = session.createConsumer(HornetQJMSClient.createQueue(HornetqConfUtil.QUEUE));
		connection.start();
		
		for (int i = 0; i < 5000; i++) {
//			 ObjectMessage message = (ObjectMessage) consumer.receive(5000);
			try {
				TextMessage message = (TextMessage) consumer.receive(5000);
//				behavior = (BehaviorBean) message.getObject();
				behavior = gson.fromJson(AESEncrypterUtil.getInstance().decrypt(message.getText()), BehaviorBean.class);
			} catch (NullPointerException e) {
				logger.error("本次执行接收数据未满5000，接收强制结束");
				break;
			} catch (ClassCastException e1) {
//				logger.error("本次接收内容非TextMessage，接收强制结束");
				logger.error("本次接收内容非TextMessage，退出本次接收");
				continue;
			}

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			writeFile(behavior, HornetqConfUtil.FILE_PATH + HornetqConfUtil.FILE_NAME + sf.format(new Date()) + ".txt");
		}
		
		connection.stop();
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * 讲内容写入文件中
	 * 
	 * @param behavior
	 * @param path
	 *            文件存放路径
	 * @throws IOException
	 */
	public static void writeFile(BehaviorBean behavior, String path) throws IOException {
		File file = new File(path);
		FileWriter writer = null;
		Gson gson = new Gson();

		if (file.exists()) {
			if (!file.isDirectory()) {
				file.createNewFile();
			}
		} else {
			File file2 = new File(file.getParent());
			file2.mkdirs();
			if (!file.isDirectory()) {
				file.createNewFile();
			}
		}

		writer = new FileWriter(path, true);
		writer.write(gson.toJson(behavior) + "\r\n");

		if (writer != null) {
			writer.close();
		}
	}
}
