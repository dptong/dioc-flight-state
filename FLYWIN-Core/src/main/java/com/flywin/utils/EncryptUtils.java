/**
 * <li>文件名：EncryptUtil.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月19日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.utils;

import com.flywin.enums.IntConstants;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月15日
 */
public final class EncryptUtils {

    /**
     * 算法
     */
    private static final String AALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 默认AES加密解密Key
     */
    private static final String DEFAULT_AES_KEY = "SkyeKu0lFaAjV8pC";

    /**
     *
     * @Title:
     * @Description:
     */
    private EncryptUtils() {

    }

    /**
     *
     * @Title getEncryptKey
     * @Description 计算秘钥
     * @param strFix
     *            计算秘钥字符串
     * @return
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String getEncryptKey(final String strFix) {
        if (StringUtils.isEmpty(strFix)) {
            return DEFAULT_AES_KEY;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DEFAULT_AES_KEY.length(); i++) {
            if (i % 2 == 0 && i < strFix.length()) { // 部分位替换
                int j = strFix.length() - i - 1;
                sb.append(strFix.substring(j, j + 1));
            } else {
                sb.append(DEFAULT_AES_KEY.substring(i, i + 1));
            }
        }
        return sb.toString();
    }

    /**
     *
     * @Title aesEncrypt
     * @Description 加密返回字符串
     * @param content
     *            内容
     * @param encryptKey
     *            秘钥
     * @return
     * @throws Exception
     *             异常
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     *
     * @Title aesEncryptToBytes
     * @Description 加密返回字节
     * @param content
     *            内容
     * @param encryptKey
     *            秘钥
     * @return
     * @throws Exception
     *             异常
     * @return byte[]
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(IntConstants.INT_128);
        Cipher cipher = Cipher.getInstance(AALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("utf-8"), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     *
     * @Title aesDecrypt
     * @Description 解密
     * @param encryptStr
     *            密文字符串
     * @param decryptKey
     *            秘钥
     * @return
     * @throws Exception
     *             异常
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     *
     * @Title aesDecryptByBytes
     * @Description 解密
     * @param encryptBytes
     *            密文字节
     * @param decryptKey
     *            秘钥
     * @return
     * @throws Exception
     *             异常
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(IntConstants.INT_128);

        Cipher cipher = Cipher.getInstance(AALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes("utf-8"), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes, "utf-8");
    }

    /**
     *
     * @Title base64Encode
     * @Description Base64编码
     * @param bytes
     *            字节
     * @return
     * @return String
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     *
     * @Title base64Decode
     * @Description Base64解码
     * @param base64Code
     *            编码字符串
     * @return
     * @throws Exception
     *             异常
     * @return byte[]
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    private static byte[] base64Decode(String base64Code) throws Exception {
        return Base64.getDecoder().decode(base64Code);
    }

    /**
     *
     * @Title main
     * @Description TODO
     * @param args
     *            参数
     * @throws Exception
     *             异常
     * @author 曾明辉
     * @date: 2019年8月15日
     */
    public static void main(String[] args) throws Exception {

        String key = getEncryptKey("13141520");
        String content = "123456"; // WLvvEnZx2QILX2oLE3Hlng==
        System.out.println("加密前：" + content);

        System.out.println("加密密钥和解密密钥：" + key);

        String encrypt = aesEncrypt(content, key);
        System.out.println(encrypt.length() + ":加密后：" + encrypt);

        String decrypt = aesDecrypt(encrypt, key);
        System.out.println("解密后：" + decrypt);
    }
}
