package com.flywin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.ParseUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @Description: 读取jar包资源工具
 * @author: lihao
 * @date: 2020年4月9日
 */
public final class JarUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(JarUtil.class);

    /**
     * 根据jar路径获取META-INF/MANIFEST.MF里面的内容
     *
     * @param jarPath jar包路径
     * @param file    文件
     * @return Map<String, Object>
     * @throws IOException 异常
     */
    public static Map<String, Object> getMapReadFromJar(String jarPath, String file) throws IOException {
        Map<String, Object> map = new HashMap<>(3);
        URL fileUrl = ParseUtil.fileToEncodedURL(new File(jarPath));
        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{fileUrl}, null);) {
            InputStream inputStream = urlClassLoader.getResourceAsStream(file);
            Manifest manifest = new Manifest(inputStream);
            Attributes mainAttributes = manifest.getMainAttributes();
            String implVersion = mainAttributes.getValue("Implementation-Version");
            String implTitle = mainAttributes.getValue("Implementation-Title");
            String bulidTime = mainAttributes.getValue("Build-Time");
            map.put("implementationVersion", implVersion);
            map.put("implementationTitle", implTitle);
            map.put("buildTime", bulidTime);
            if (inputStream == null) {
                throw new FileNotFoundException("not find file:" + file + " in jar:" + jarPath);
            } else {
                return map;
            }
        } catch (IOException e) {
            throw e;
        }/* finally {
      IOUtils.closeQuietly(urlClassLoader);
    }*/
    }


    /***
     * 功能描述:获取指定文件夹下所有jar文件路径
     * @param: [dirFile, dirAllStrArr]
     * @return: java.util.List<java.lang.String>
     * @throws:
     * @auther: lihao
     * @date: 2020/4/10 17:08
     * @return List<String>
     * @param dirAllStrArr 文件dir集合
     * @param dirFile 文件dir
     * @throws Exception 异常
     */
    public static List<String> dirAll(File dirFile, ArrayList<String> dirAllStrArr) throws Exception {

        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            for (File file : files) {
                // 如果遇到文件夹则递归调用。
                if (file.isDirectory()) {
                    // 递归调用
                    dirAll(file, dirAllStrArr);
                } else {
                    // 如果遇到文件夹则放入数组
                    if (dirFile.getPath().endsWith(File.separator)) {
                        dirAllStrArr.add(dirFile.getPath() + file.getName());
                    } else {
                        if (file.getName().endsWith(".jar")) {
                            dirAllStrArr.add(dirFile.getPath() + File.separator
                                    + file.getName());
                        }
                    }
                }
            }
        }
        return dirAllStrArr;
    }

    /***
     * 功能描述:获取运行服务实例的版本信息
     * @param: [cls]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @throws:
     * @auther: lihao
     * @date: 2020/4/13 15:05
     * @return Map<String, Object>
     * @param cls 泛型集合
     * @throws IOException 异常
     */
    public static Map<String, Object> getVersion(Class<?> cls) throws IOException {

        CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
        //获取jar包路径
        String path = codeSource.getLocation().getPath();
        logger.info(path);
        String jarPath = path.substring(path.indexOf("/"), path.indexOf(".jar") + 4);
        logger.info(jarPath);
        Map<String, Object> map = getMapReadFromJar(jarPath, "META-INF/MANIFEST.MF");
        return map;
    }


}
