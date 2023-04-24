package com.flywin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


/**
 * @Description: 获取服务器IP地址
 * @author: lihao
 * @date: 2020/5/18 8:59
 */
public final class WebServiceIpUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(WebServiceIpUtil.class);

    /**
     * @description: 描述
     * @author lihao
     * @date 2020/5/18 9:17
     */
    private WebServiceIpUtil() {
    }

    /**
     * 获取本地IP地址
     *
     * @throws SocketException
     */
    /**
     * @return java.lang.String r
     * @throws UnknownHostException e
     * @throws SocketException      e
     * @description: 描述
     * @author lihao
     * @date 2020/5/18 9:17
     */
    public static String getLocalIP() throws UnknownHostException, SocketException {
        if (isWindowsOS()) {
            return InetAddress.getLocalHost().getHostAddress();
        } else {
            return getLinuxLocalIp();
        }
    }

    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    /**
     * @return boolean r
     * @description: 描述
     * @author lihao
     * @date 2020/5/18 9:18
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException e
     */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                         enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:")
                                    && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("获取ip地址异常");
            ip = "127.0.0.1";
            logger.error(ex.getMessage(), ex);
        }
        System.out.println("IP:" + ip);
        return ip;
    }
}


