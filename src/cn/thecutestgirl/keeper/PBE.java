package cn.thecutestgirl.keeper;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
 
/**
 * Created by xiang.li on 2015/2/28.
 * PBE 加解密工具类
 */
public class PBE {
    /**
     * 定义加密方式
     * 支持以下任意一种算法
     * <p/>
     * <pre>
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     * </pre>
     */
    private final static String KEY_PBE = "PBEWITHMD5andDES";
 
    private final static int SALT_COUNT = 100;
    
    private static  byte[] salt= {1,0,0,0,1,1,0,0};
 
 
    /**
     * 转换密钥
     *
     * @param key 字符串
     * @return
     */
    public static Key stringToKey(String key) {
        SecretKey secretKey = null;
        try {
            PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_PBE);
            secretKey = factory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return secretKey;
    }
 
    /**
     * PBE 加密
     *
     * @param str 需要加密的字符串
     * @param key  密钥
     * @param salt 盐
     * @return
     */
    public static String encryptPBE(String str, String key) {
        byte[] data = str.getBytes();
        byte[] bytes=null;
        try {
            // 获取密钥
            Key k = stringToKey(key);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, SALT_COUNT);
            Cipher cipher = Cipher.getInstance(KEY_PBE);
            cipher.init(Cipher.ENCRYPT_MODE, k, parameterSpec);
            bytes = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        String encStr = Base64.encode(bytes);
        return encStr;
    }
 
    /**
     * PBE 解密
     *
     * @param encStr 需要解密的字符串
     * @param key  密钥
     * @param salt 盐
     * @return
     */
    public static String decryptPBE(String encStr, String key) {
    	byte[] data=Base64.decode(encStr);
        byte[] bytes = null;
        try {
            // 获取密钥
            Key k = stringToKey(key);
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, SALT_COUNT);
            Cipher cipher = Cipher.getInstance(KEY_PBE);
            cipher.init(Cipher.DECRYPT_MODE, k, parameterSpec);
            bytes = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        String decStr = null;
		try {
			decStr = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return decStr;
    }
 
}
