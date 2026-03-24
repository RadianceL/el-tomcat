package com.eddie.tomcat.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author eddie
 * @createTime 2026-03-24
 * @description 服务器配置类
 */
public class ServerConfig {

    private static final String CONFIG_FILE = "server.properties";
    private static ServerConfig instance;

    private int port = 8888;
    private String contextPath = "";
    private int maxThreads = 200;
    private boolean keepAlive = true;

    private ServerConfig() {
        loadConfig();
    }

    public static synchronized ServerConfig getInstance() {
        if (instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is != null) {
                props.load(is);
                this.port = Integer.parseInt(props.getProperty("server.port", "8888"));
                this.contextPath = props.getProperty("server.contextPath", "");
                this.maxThreads = Integer.parseInt(props.getProperty("server.maxThreads", "200"));
                this.keepAlive = Boolean.parseBoolean(props.getProperty("server.keepAlive", "true"));
                System.out.println("配置文件加载成功");
            } else {
                System.out.println("未找到配置文件，使用默认配置");
            }
        } catch (IOException e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
        }
    }

    public int getPort() {
        return port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }
}
