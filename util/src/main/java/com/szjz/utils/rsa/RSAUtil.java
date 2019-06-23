package com.szjz.utils.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * author:szjz
 * date:2019/6/21
 */

@Slf4j
public class RSAUtil {
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        Map<Integer, String> keyMap = genKeyPair();
        //明文
        String message = "nihaoma ";

        //加密解密
        String messageEn = encrypt(message,keyMap.get(0));
        decrypt(messageEn,keyMap.get(1));

        //加签验签
        String sign = sign(message, keyMap.get(1));
        verify(message,sign,keyMap.get(0));

    }

    //加密算法rsa
    public static final String KEY_ALGORITHM = "RSA";
    //签名算法rsa
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map genKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map

        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }
    /**
     * RSA公钥加密
     *
     * @param message 明文
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt( String message, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String encryptedMessages = Base64.encodeBase64String(cipher.doFinal(message.getBytes("UTF-8")));
        log.debug("明文：{}",message);
        log.debug("公钥：{}",publicKey);
        log.info("密文：{}",encryptedMessages);
        return encryptedMessages;
    }

    /**
     * RSA私钥解密
     *
     * @param encryptedMessages 密文
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String encryptedMessages, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(encryptedMessages.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String message = new String(cipher.doFinal(inputByte));
        log.debug("私钥：{}",privateKey);
        log.info("明文：{}",message);
        return message;
    }



    /**
     * 产生签名
     *
     * @param message 明文
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(String message, String privateKey){
        // 解密由base64编码的私钥
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = decryptBASE64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取私钥对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(message.getBytes());
            String sign = encryptBASE64(signature.sign());
            log.info("明文：{}",message);
            log.debug("私钥：{}",privateKey);
            log.debug("加签结果：{}",sign);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证签名
     *
     * @param message 明文
     * @param publicKey 公钥
     * @param sign 签名结果
     * @return
     */
    public static boolean verify(String message,  String sign ,String publicKey){

        // 解密由base64编码的公钥
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = decryptBASE64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            // 取公钥对象
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(message.getBytes());
            boolean verify = signature.verify(decryptBASE64(sign));

            log.info("公钥：{}",publicKey);
            log.info("明文：{}",message);
            log.info("验签结果：{}",verify);

            // 验证签名是否有效
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }


}