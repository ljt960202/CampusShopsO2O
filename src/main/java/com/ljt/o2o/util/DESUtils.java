package com.ljt.o2o.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {

	private static Key key;
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			//生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置上密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密钥对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加密
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		//基于BASE64编码，接收byte[]并转换成String
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			
			byte[] bytes = str.getBytes(CHARSETNAME);
			
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] doFinal = cipher.doFinal(bytes);
			
			return base64encoder.encode(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}


	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			//字符串decode成byte[]
			byte[] bytes = base64decoder.decodeBuffer(str);
			//获取解密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化解密信息
			cipher.init(Cipher.DECRYPT_MODE, key);
			//解密
			byte[] doFinal = cipher.doFinal(bytes);
			//返回解密之后的信息
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getEncryptString("root"));
		System.out.println(getEncryptString("Ljt123456!"));
		System.out.println(getEncryptString("123456"));
		System.out.println(getEncryptString("665ae80dba31fc91ab6191e7da4d676d"));
	}

}