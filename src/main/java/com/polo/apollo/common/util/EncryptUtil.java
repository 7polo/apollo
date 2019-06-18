package com.polo.apollo.common.util;

import com.polo.apollo.entity.modal.system.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * @author baoqianyong
 * @date 2019/06/03
 */
public class EncryptUtil {

    public static final String HASH_NAME= "md5";

    public static final int ENCRYPT_COUNT = 7;

    public static void encryptUser(User user, String password) {
        String salt = UUID.randomUUID().toString();
        String newPassword = password(salt, password);
        user.setPassword(newPassword);
        user.setSalt(salt);
    }

    public static boolean isPasswordEqual(User user, String passwd) {
        passwd = password(user.getSalt(), passwd);
        return passwd.equalsIgnoreCase(user.getPassword());
    }

    private static String password(String salt, String passwd) {
        return new SimpleHash(HASH_NAME, passwd, ByteSource.Util.bytes(salt), ENCRYPT_COUNT).toHex();
    }
}
