package cherhio.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cherhio
 * @date Created in 2019-04-23 16:13
 */

public class BasicServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法的分发
        //1.获取请求路径，
        String requestURI = req.getRequestURI();// /travel/user/add
        System.out.println(requestURI);
        //2.获取方法的名称
        String methodName = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        System.out.println(methodName); // add
        try {
            //3.获取方法的对象
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            Object invoke = method.invoke( this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将传入的对象序列化为json，并且写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);

    }

    /**
     * 将传入的对象序列化给json返回给调用者
     * @param obj
     */
    public void writeValueAsString(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        //将json写回客户端
        //设置contentType的操作
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

}
