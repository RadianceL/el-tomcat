package com.eddie.tomcat.core;

import com.eddie.tomcat.provider.Servlet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eddie
 * @createTime 2019-02-16
 * @description Servlet容器
 */
public class ServletContainer {

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);
    private static volatile ServletContainer singleton;

    private Map<String, Servlet> container = new ConcurrentHashMap<>(64);

    private ServletContainer(){
        logger.info("---- Servlet 容器初始化 ----");
    }

    /**
     * 添加路径处理器
     * @param path
     * @param servlet
     */
    public void addServlet(String path, Servlet servlet){
        logger.info("Mapping path [{}] to servlet [{}]", path, servlet.getClass().getSimpleName());
        container.put(path, servlet);
    }

    /**
     * 获取一个路径处理器
     * @param path
     * @return
     */
    public Servlet getServlet(String path){
        if (StringUtils.isBlank(path) || path.length() == 0){
            throw new RuntimeException("需要处理的路径为空");
        }
        Servlet servlet = container.get(path);
        if (Objects.isNull(servlet)){
            logger.warn("未找到路径 [{}] 对应的 Servlet", path);
            throw new RuntimeException("未找到该路径的处理类");
        }
        logger.debug("获取 [{}] 的 Servlet 成功", path);
        return servlet;
    }

    /**
     * 单例模式获取该容器实例
     * @return
     */
    public static ServletContainer getInstance(){
        if (singleton == null) {
            synchronized (ServletContainer.class) {
                if (singleton == null) {
                    singleton = new ServletContainer();
                }
            }
        }
        return singleton;
    }
}
