package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/users")
// 也可以在方法上加RestControllerAdvice注解，方法上加ExceptionHandler注解
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("register")
    public JsonResult<Void> register(User user) {
        userService.register(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        User data = userService.login(username, password);
        // 向session对象中完成数据的绑定(session是全局的!)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        //获取session中绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username ,oldPassword , newPassword);

        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return  new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session) {

        // user对象有四部分的数据：username、phone、gender、email
        // uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo( uid , username , user);
        return new JsonResult<>(OK);
    }

    /** 设置上传文件的最大值 */
    public static final int AVATAR_MAX_SIZE = 10 *1024 * 1024;

    /** 设置上传文件的类型 */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * @RequestParam("file") 用来解决前后端名称不一致的问题!与@param()一样
     * MultipartFile：是由springmvc提供的接口(可以接收任何file)
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar (HttpSession session,
                                            @RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出最大限制");
        }
        // 判断文件类型是否为规定的后缀类型
        String contentType = file.getContentType();
        // 如果集合中包含某个元素则返回true！
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        // 上传的文件 ../upload/文件.png
        String parent = session.getServletContext().getRealPath("/upload");
        // file对象指向这个路径，file是否存在
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 获取文件名，UUID工具类生成一个新的随机文件名
        // 例：avatar.png
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename-- >" + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //例：SDAKIDASDA-546DSAFRA-DSAD55ASD.png
        String filename = UUID.randomUUID().toString().toUpperCase(Locale.ROOT) + suffix;
        File dist = new File(dir, filename); // 是一个空文件
        // 参数file中数据写入到这个空文件中!
        try {
            file.transferTo(dist); // 将file文件中的数据写入到dist文件中
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        // 返回用户头像的路径给前端，将来用于头像的展示
        return new JsonResult<>(OK, avatar);
    }


    /*@RequestMapping("register")
    public JsonResult<Void> register (User user) {
        // 创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.register(user);
            result.setState(200);
            result.setMessage("用户注册成功！");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用！");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常！");
        }
        return result;
    }*/
}
