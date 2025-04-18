package com.rehman.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.GsonBuilder;
import com.rehman.modals.ImageRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.management.openmbean.InvalidKeyException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.io.FileUtils;

/**
 *
 * @author Vaibhav
 */
@Service
public class UtilService {

    private Gson gson = null;
    private ObjectMapper mapper;
    private final String encGenKey = "dahsK56$#@";

    public <T> String objectToJson(T obj) {
        String jsonString = "";
        try {
            mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public <T> T jsonToObject(String jsonString, Class<T> clazz) {
        T obj = null;
        try {
            mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            obj = mapper.readValue(jsonString, clazz);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return obj;
    }

    public <T> List<T> jsonArrayToObjectList(List<?> resplist, Class<T> reqclass) {
        List<T> objlist = null;
        try {
            mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(MapperFeature.ALLOW_COERCION_OF_SCALARS);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
            gson = new GsonBuilder().disableHtmlEscaping().create();
            objlist = mapper.readValue(gson.toJson(resplist),
                    TypeFactory.defaultInstance().constructCollectionType(List.class, reqclass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objlist;
    }


    public <T> Boolean imageUpload(ImageRequest imageRequest,
            String filePath, String finalName) {
        Boolean imageUrl;
        try {
            String imageicon = imageRequest.getValue();
            String fileWithpath = filePath + finalName;
            File directory = new File(filePath);
            Boolean allowed;
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    Set<PosixFilePermission> perms = Files.readAttributes(directory.toPath(), PosixFileAttributes.class).permissions();
                    perms.add(PosixFilePermission.OWNER_READ);
                    perms.add(PosixFilePermission.OWNER_EXECUTE);
                    perms.add(PosixFilePermission.OWNER_WRITE);
                    perms.add(PosixFilePermission.GROUP_WRITE);
                    perms.add(PosixFilePermission.GROUP_READ);
                    perms.add(PosixFilePermission.GROUP_EXECUTE);
                    perms.add(PosixFilePermission.OTHERS_WRITE);
                    perms.add(PosixFilePermission.OTHERS_READ);
                    perms.add(PosixFilePermission.OTHERS_EXECUTE);
                    Files.setPosixFilePermissions(directory.toPath(), perms);
                    allowed = true;
                } else {
                    allowed = false;
                }
            } else {
                allowed = true;
            }
            if (allowed) {
                File catfile = new File(fileWithpath);
                if (catfile.exists()) {
                    catfile.delete();
                    catfile.createNewFile();
                } else {
                    catfile.createNewFile();
                }
                try (FileOutputStream imageOutFile = new FileOutputStream(catfile)) {
                    byte[] imageByteArray = Base64.decodeBase64(imageicon);
                    imageOutFile.write(imageByteArray);
                    imageOutFile.close();
                    catfile.setReadable(true);
                    catfile.setWritable(true);
                    catfile.setExecutable(true);
                    imageUrl = true;
                    Set<PosixFilePermission> perms = Files.readAttributes(catfile.toPath(), PosixFileAttributes.class).permissions();
//                    perms.remove(PosixFilePermission.OWNER_WRITE);
                    perms.add(PosixFilePermission.OWNER_READ);
                    perms.add(PosixFilePermission.OWNER_EXECUTE);
                    perms.add(PosixFilePermission.OWNER_WRITE);
                    perms.add(PosixFilePermission.GROUP_WRITE);
                    perms.add(PosixFilePermission.GROUP_READ);
                    perms.add(PosixFilePermission.GROUP_EXECUTE);
                    perms.add(PosixFilePermission.OTHERS_WRITE);
                    perms.add(PosixFilePermission.OTHERS_READ);
                    perms.add(PosixFilePermission.OTHERS_EXECUTE);
                    Files.setPosixFilePermissions(catfile.toPath(), perms);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    imageUrl = false;

                } catch (IOException ex) {
                    ex.printStackTrace();
                    imageUrl = false;
                }
            } else {
                imageUrl = false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            imageUrl = false;
        }
        return imageUrl;
    }

    public String decrypt(String generatedKey, String initVector, String encrypted, int len) {
        int frm = 16 - len;
        String encryptedAuthToken = encrypted.substring(frm, encrypted.length());
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(generatedKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            try {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            } catch (java.security.InvalidKeyException ex) {
                java.util.logging.Logger.getLogger(UtilService.class.getName()).log(Level.SEVERE, null, ex);
            }

            byte[] original = cipher.doFinal(Base64.decodeBase64(encryptedAuthToken));

            return new String(original);
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String reGenerateEncryptedKey(String mobileNumber, String autho_token) {
        int till = 16 - mobileNumber.length();
        String requiredNumber = autho_token.substring(0, till);

        try {
            int remains = requiredNumber.length();
//            int remains = 16 - len;
            int val = 0;
            String abc = "";
            String abc2 = "";
            if (remains % 2 == 0) {
                val = remains / 2;
                abc = requiredNumber.substring(0, val);
                abc2 = requiredNumber.substring(val, till);
            } else {
                val = remains / 2;
                abc = requiredNumber.substring(0, val);
                abc2 = requiredNumber.substring(val, till);
            }

//            abc = requiredNumber.substring(0, 3);
//            abc2 = requiredNumber.substring(3, 7);
//              String pwdno = "123" + mobileNumber + "123";
            String pwdno = abc + mobileNumber + abc2;
            String ss = getHashingvaluemd5_512(pwdno);
            StringBuffer gendynamikey = new StringBuffer();
            for (int i = 0; i < pwdno.length(); i++) {
                try {
                    int p = Integer.parseInt(pwdno.charAt(i) + "") + i;
                    gendynamikey.append(ss.charAt(p) + "");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return gendynamikey.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHashingvaluemd5_512(String mob) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(mob.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            //convert the byte to hex format method 2
            //def9fd27656380d5e401ea0d2a0bb89c
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            gson = new GsonBuilder().disableHtmlEscaping().create();
            String s2 = gson.toJson(Base64.encodeBase64String(encrypted).toString());
            return s2 = s2.replace("\"", "");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateEncryptedKey(String mobileNumber) {
        try {
            int len = mobileNumber.length();
            int remains = 16 - len;
            int val = 0;
            String abc = "";
            String abc2 = "";
            if (remains % 2 == 0) {
                val = remains / 2;
                abc = RandomStringUtils.randomNumeric(val);
                abc2 = RandomStringUtils.randomNumeric(val);
            } else {
                val = remains / 2;
                abc = RandomStringUtils.randomNumeric(val);
                abc2 = RandomStringUtils.randomNumeric(val + 1);
            }

            String pwdno = abc + mobileNumber + abc2;
//            String pwdno = "123" + mobileNumber + "123";
            String ss = getHashingvaluemd5_512(pwdno);
            StringBuffer gendynamikey = new StringBuffer();
            for (int i = 0; i < pwdno.length(); i++) {
                try {
                    int p = Integer.parseInt(pwdno.charAt(i) + "") + i;
                    gendynamikey.append(ss.charAt(p) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return gendynamikey.toString() + "bizm" + abc + abc2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] p = param.split("=");
            String name = p[0];
            if (p.length > 1) {
                String value = p[1];
                map.put(name, value);
            }
        }
        return map;
    }

}
