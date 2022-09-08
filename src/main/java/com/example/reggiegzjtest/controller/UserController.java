package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.entities.User;
import com.example.reggiegzjtest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
        @Autowired  // java已经整合了Mail javaMailSender.send(message)  发送邮箱
        private JavaMailSender javaMailSender;

        @Autowired
        private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
//        获取手机号 | 邮箱号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone))
        {
//        生产随机的4为验证码
            String code = "";
            for (int i = 0; i < 4; i++) {
                String num = String.valueOf(new Random().nextInt(10));
                code += num;
            }
            log.info("验证码位：{}",code);
//        通过邮箱发送验证码
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("204126329@qq.com");  //发件人
            message.setTo(phone);  //收件人
            message.setSubject("验证码");  //标题
            message.setText("你的验证码为：" + code); // 内容
            javaMailSender.send(message);

//        需要将生成的验证码保存到Session
//            session.setAttribute(phone,code);
//            将生成的验证码保存到redis缓存
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
        }


        return R.success("手机验证码发成功");
    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map ,HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
//            从session中取出验证码
//        String phoneCode = session.getAttribute(phone).toString();
//            从redis缓存中取出验证码
        String phoneCode = redisTemplate.opsForValue().get(phone).toString();
        if(phoneCode != null)
        if(phoneCode.equals(code) && code != null){
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(userLambdaQueryWrapper);
            if(user == null){
//                判断当前手机号 对应的用户是否为新用户 ， 如果事新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            log.info("缓存中的验证码:{}",phoneCode);
            return R.success(user);
        }
        return R.error("登陆失败");
    }
//
}
