package com.eddie.tomcat.handle;

import com.eddie.tomcat.handle.incloud.Request;
import com.eddie.tomcat.handle.incloud.Response;
import com.eddie.tomcat.provider.Servlet;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author eddie
 * @createTime 2026-03-24
 * @description 静态资源处理器
 */
public class StaticResourceHandler extends Servlet {

    private static final String STATIC_PATH = "static";

    @Override
    public void doGet(Request request, Response response) {
        String path = request.getPath();
        // 移除开头的斜杠
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        // 如果路径为空，默认返回 index.html
        if (path.isEmpty()) {
            path = "index.html";
        }

        // 尝试从 classpath 加载静态资源
        URL resource = getClass().getClassLoader().getResource(STATIC_PATH + "/" + path);

        if (resource == null) {
            response.writeError(HttpResponseStatus.NOT_FOUND, "Static resource not found: " + path);
            return;
        }

        try {
            File file = new File(resource.getFile());
            if (!file.exists()) {
                response.writeError(HttpResponseStatus.NOT_FOUND, "Static resource not found: " + path);
                return;
            }

            // 读取文件内容
            byte[] content = Files.readAllBytes(file.toPath());
            String contentType = getContentType(path);

            // 构建响应
            response.write(new String(content, "UTF-8"), HttpResponseStatus.OK, contentType);
        } catch (IOException e) {
            response.writeError(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Error reading resource: " + e.getMessage());
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        response.writeError(HttpResponseStatus.METHOD_NOT_ALLOWED, "Method not allowed");
    }

    private String getContentType(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".html") || path.endsWith(".htm")) {
            return "text/html; charset=UTF-8";
        } else if (path.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        } else if (path.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        } else if (path.endsWith(".json")) {
            return "application/json; charset=UTF-8";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".gif")) {
            return "image/gif";
        } else if (path.endsWith(".ico")) {
            return "image/x-icon";
        } else if (path.endsWith(".svg")) {
            return "image/svg+xml";
        } else {
            return "application/octet-stream";
        }
    }
}
