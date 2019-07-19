package cherhio.service.impl;

import cherhio.dao.impl.UserDaoImpl;
import cherhio.domain.User;
import cherhio.dao.UserDao;
import cherhio.service.UserService;
import cherhio.util.MailUtils;
import cherhio.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        //根据用户名查询对象，如果存在返回false,不存在为true
        User byUsername = dao.findByUsername(user.getUsername());

        if (byUsername != null){
            return false;
        }

        //保存用户信息

        //2.1设置激活码，
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);

        //3.激活邮件发送，邮件正文？

        String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
        /*byUsername.setCode(UuidUtil.getUuid());
        byUsername.setStatus("N");
        // 设置激活状态，
        dao.save(user);

        String content="<a href='http://localhost:travel/activeUserServlet?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;*/
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = dao.findByCode(code);
        //2.调用dao修改用户的status状体
        if (user != null){
            dao.updateStatus(user);
            return true;

        }else{
            return false;

        }


    }

    @Override
    public User login(User user) {

        return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }


}
