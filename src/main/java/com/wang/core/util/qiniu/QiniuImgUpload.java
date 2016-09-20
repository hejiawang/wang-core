package com.wang.core.util.qiniu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.wang.core.util.WebConstants;

/**
 * 七牛上传
 * @author HeJiawang
 * @date   2016.09.20
 */
public class QiniuImgUpload implements Serializable {
	
	private static final String STATUS_CODE_SUCCESS = "200";
	private static final String STATUS_CODE_ERROR_1 = "202";
	
	private static final String ERROR_DESC_BATCHUPLOAD = "上传七牛图片发生错误，请与技术人员联系。";
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(QiniuImgUpload.class);
	
	private static Mac mac = null;
	private static String bucketName = "";
	private static Auth dummyAuth = null;
	private static BucketManager bucketManager = null;
	
	static {
		Config.ACCESS_KEY = WebConstants.Config_ACCESS_KEY;
		Config.SECRET_KEY = WebConstants.Config_SECRET_KEY;
		bucketName = WebConstants.bucketName;
		mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		dummyAuth = Auth.create(WebConstants.Config_ACCESS_KEY, WebConstants.Config_SECRET_KEY);
		bucketManager = new BucketManager(dummyAuth);
	}

	public static void QNImgUpload(String key, String filePath)
			throws AuthException, JSONException, QiniuException {
		Config.ACCESS_KEY = WebConstants.Config_ACCESS_KEY;
		Config.SECRET_KEY = WebConstants.Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		String bucketName = WebConstants.bucketName;
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		
/*		try {
			Response res = uploadManager.put(new File(filePath), key, uptoken);
			Map map = res.jsonToObject(Map.class);
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()) {
				String k = it.next();
				String value = (String)map.get(k);
				System.out.println(k + " : " + value);
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}*/
		
		PutExtra extra = new PutExtra();
		/*
		 * String key = "store/big/9c608407-7ec4-4d1d-85cf-00612399d1e6"; String
		 * localFile =
		 * "E:"+File.separator+"bfsq"+File.separator+"pic"+File.separator
		 * +"store"+File.separator+"big"+File.separator+
		 * "9c608407-7ec4-4d1d-85cf-00612399d1e6.png";
		 */
		PutRet ret = IoApi.putFile(uptoken, key, filePath, extra);
		if(!ret.ok()) 
		{
			QNFileDelete(key);
		}

	}

	public static void QNImgUpload1(String key, String filePath)
			throws AuthException, JSONException, QiniuException {

		QNDeleteFile(key);
		Config.ACCESS_KEY = WebConstants.Config_ACCESS_KEY;
		Config.SECRET_KEY = WebConstants.Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		// 请确保该bucket已经存在
		String bucketName = WebConstants.bucketName;
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		PutExtra extra = new PutExtra();
		/*
		 * String key = "store/big/9c608407-7ec4-4d1d-85cf-00612399d1e6"; String
		 * localFile =
		 * "E:"+File.separator+"bfsq"+File.separator+"pic"+File.separator
		 * +"store"+File.separator+"big"+File.separator+
		 * "9c608407-7ec4-4d1d-85cf-00612399d1e6.png";
		 */
		PutRet ret = IoApi.putFile(uptoken, key, filePath, extra);
		if(!ret.ok()) 
		{
			QNFileDelete(key);
		}
	}

	// 删除
	public static void QNDeleteFile(String key) {
		Auth dummyAuth = Auth.create(WebConstants.Config_ACCESS_KEY,
				WebConstants.Config_SECRET_KEY);
		BucketManager bucketManager = new BucketManager(dummyAuth);
		try {
			bucketManager.delete(WebConstants.bucketName, key);
		} catch (QiniuException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 批量更新
	 * @throws QiniuException 该处异常为上传发生错误，删除图片出现的异常，在捕获时，将其设置为上传七牛图片异常
	 */
	public static QiNiuOptionResult QNBatchFile(List<QiNiuImg> files) throws QiniuException {
		
		QiNiuOptionResult result = new QiNiuOptionResult();
		
		if(files != null) 
		{
			ExecutorService pool = Executors.newFixedThreadPool(files.size());
			final List<String> uploadKeyRecord = new ArrayList<String>();
			final List<String> uploadKeyErrorRecord = new ArrayList<String>();
			for(final QiNiuImg qnImg : files)  
			{
				if(qnImg != null) 
				{
					pool.execute(new Runnable() {
						@Override
						public void run() {
							String key = qnImg.getKey();
							String path = qnImg.getPath();
							
							try {
								PutRet ret = QNUploadFile(key, path);
								/*
								 * 如果上传成功，上传成功的商品将其key保存进行上传key记录集合中，如果批量操作中出现
								 * 有一张上传不成功的操作，将之前上传的图片信息进行删除。
								 */
								if(ret.ok()) 
								{
									uploadKeyRecord.add(key);
								}
								else 
								{
									uploadKeyErrorRecord.add(key);
								}
							} catch (JSONException | QiniuException | AuthException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
			
			pool.shutdown();
			
			while(true) 
			{
				if(pool.isTerminated()) break;
			}
			
			if(uploadKeyErrorRecord.size() > 0) 
			{
				QNBatchDelete(uploadKeyRecord);
				result.setStatus(STATUS_CODE_ERROR_1);
				result.setErrorDesc(ERROR_DESC_BATCHUPLOAD);
			}
			else
			{
				result.setStatus(STATUS_CODE_SUCCESS);
			}
		}
		  
		return result;
	}
	
	/**
	 * 七牛待返回结果的上传
	 * @param key
	 * @param filePath
	 * @return
	 * @throws AuthException 认证异常
	 * @throws JSONException mac转换异常
	 * @throws QiniuException 
	 */
	public static PutRet QNUploadFile(String key, String filePath) throws JSONException, AuthException, QiniuException {
		
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		PutExtra extra = new PutExtra();
		
		//上传文件
		PutRet ret = IoApi.putFile(uptoken, key, filePath, extra);
		
		if(!ret.ok()) 
		{
			QNFileDelete(key);
		}
		return ret;
	}
	
	/**
	 * 七牛的批量删除操作
	 * @param keyLists
	 * @throws QiniuException 
	 */
	public static void QNBatchDelete(List<String> keyLists) {
		if(keyLists != null) 
		{
			ExecutorService pool = Executors.newFixedThreadPool(keyLists.size());
			for(final String key : keyLists) 
			{
				pool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							QNFileDelete(key);
						} catch (QiniuException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	} 
	
	/**
	 * 文件删除
	 * @param key
	 * @throws QiniuException
	 */
	public static void QNFileDelete(String key) throws QiniuException {
		if(key != null && !"".equals(key)) 
		{
			Entry entry = stat(key);
			if(entry != null && entry.ok()) {
				bucketManager.delete(WebConstants.bucketName, key);
			}
		}
	}
	
	/**
	 * 七牛查看文件方法
	 * @param key
	 * @return
	 */
	public static Entry stat(String key) {
		if(key != null && !"".equals(key)) 
		{
			RSClient client = new RSClient(mac);
			return client.stat(bucketName, key);
		}
		return null;
	}
}
