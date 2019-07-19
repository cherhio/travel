package cherhio.web.servlet;

import cherhio.domain.User;
import cherhio.service.UserService;
import cherhio.service.impl.UserServiceImpl;
import cherhio.domain.ResultInfo;
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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码的校验
        //用户提交的验证码
        String check = request.getParameter("check");

        //获取session中的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次

        ResultInfo info = new ResultInfo();

        //判断验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码不通过
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将Info对象序列化为json写回客户端
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.service完成注册操作
        UserService service = new UserServiceImpl();
        boolean flag = service.register(user);


        //4.响应结果
        if (flag){
            //注册成功
            info.setFlag(true);


        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名存在");
        }

        //将Info对象序列化为json写回客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json写回客户端
        //设置contentType的操作
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
