package cherhio.web.servlet;

import cherhio.domain.ResultInfo;
import cherhio.domain.User;
import cherhio.service.UserService;
import cherhio.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author cherhio
 * @date Created in 2019-04-23 16:12
 */
@WebServlet("/user/*")
public class UserServlet extends BasicServlet {
    //声明一个业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 注册方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码的校验
        //用户提交的验证码
        String check = request.getParameter("check");

        //获取session中的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次

        ResultInfo info = new ResultInfo();

        //判断验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码不通过
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            writeValueAsString(info,response);


            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        boolean flag = service.register(user);


        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);


        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名存在");
        }


       writeValueAsString(info,response);

    }

    /**
     * 登录方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();

        ResultInfo info = new ResultInfo();

        //判断验证码,
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

        String check = request.getParameter("check");

        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码不通过
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            /*//将Info对象序列化为json写回客户端
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
            writeValueAsString(info,response);
            return;
        }



        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        UserService service = new UserServiceImpl();
        User u = service.login(user);


        if (u == null) {
            //用户名或者密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }

        //5，判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");

        }

        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            //登录成功
            info.setFlag(true);
        }

        //将用户名存入到session域中


        //响应数据
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info,response);
    }


    /**
     * 查找用户的方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户的名字
        Object user = request.getSession().getAttribute("user");

     /*   //调用方法去数据库进行查询
        UserService service = new UserServiceImpl();
        User byUsername = service.findByUsername(username);*/

        //把结果回应给ajax
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
        writeValue(user,response);
    }

    /**
     * 退出方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //1.销毁session
        request.getSession().invalidate();

        //2.跳转页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 用户激活
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //存在，
            //2.调用service完成激活
//            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;

            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='/travel/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员";

            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}