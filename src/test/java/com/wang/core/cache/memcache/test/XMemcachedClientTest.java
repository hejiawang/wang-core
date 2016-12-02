package com.wang.core.cache.memcache.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wang.core.cache.memcache.XMemcachedClient;

/**
 * memcache 测试
 * 
 * @author HeJiawang
 * @date   2016.12.02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:memcached/spring-memcached.xml")
public class XMemcachedClientTest {
	
	@Test
	public void addTest() throws Exception{
		XMemcachedClient.add("test1", "result1");
		XMemcachedClient.add("test2", "result2");
		XMemcachedClient.add("test3", "result3");
		XMemcachedClient.add("test4", "result4");

		XMemcachedClient.add("test1", "result5");
		XMemcachedClient.set("test2", "result6");
	}
	
	@Test
	public void getTest() throws Exception{
		String result1 = (String)XMemcachedClient.get("test1");
		String result2 = (String)XMemcachedClient.get("test2");
		System.out.println("-----result-----: " + result1 );
		System.out.println("-----result-----: " + result2 );
	}
	
}
