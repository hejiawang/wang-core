package com.wang.core.util.hornetq;

import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

public class HQConnection {

	private volatile static Connection conn	= null;
	
	public static Connection createConn() throws JMSException {
		if(conn == null) {
			synchronized(HQConfiguration.class) {
				if(conn == null) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(TransportConstants.HOST_PROP_NAME, HornetqConfUtil.SEND_HOST);
					map.put(TransportConstants.PORT_PROP_NAME, Integer.parseInt(HornetqConfUtil.SEND_PORT));
					TransportConfiguration server = new TransportConfiguration(NettyConnectorFactory.class.getName(), map);
					ConnectionFactory connectionFactory = (ConnectionFactory) HornetQJMSClient
							.createConnectionFactoryWithoutHA(JMSFactoryType.CF, server);
					conn = connectionFactory.createConnection();
				}
			}
		}
		return conn;
	}
	
	public static void releaseConnection() throws JMSException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}
	
}
