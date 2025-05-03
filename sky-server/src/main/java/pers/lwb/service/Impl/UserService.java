package pers.lwb.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.WeChatUserConstant;
import pers.lwb.dto.UserLoginDTO;
import pers.lwb.entity.User;
import pers.lwb.exception.InsertException;
import pers.lwb.exception.LoginFailedException;
import pers.lwb.mapper.UserMapper;
import pers.lwb.properties.WeChatProperties;
import pers.lwb.utils.HttpClientUtils;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserService implements pers.lwb.service.UserService {
    private final static String grantType = "authorization_code";

    private final static String code2Session = "https://api.weixin.qq.com/sns/jscode2session";

    private final WeChatProperties weChatProperties;

    private final UserMapper userMapper;

    public UserService(WeChatProperties weChatProperties, UserMapper userMapper) {
        this.weChatProperties = weChatProperties;
        this.userMapper = userMapper;
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null)
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);

        User user = userMapper.getByOpenid(openid);
        if (ObjectUtils.isEmpty(user)) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            int n = userMapper.insert(user);
            if (n <= 0)
                throw new InsertException(MessageConstant.USER_REGISTER_ERROR);
        }
        return user;
    }

    private String getOpenid(String code) {
        HashMap<String, String> entry = new HashMap<>();
        entry.put(WeChatUserConstant.APP_ID, weChatProperties.getAppid());
        entry.put(WeChatUserConstant.SECRET, weChatProperties.getSecret());
        entry.put(WeChatUserConstant.JS_CODE, code);
        entry.put(WeChatUserConstant.GRANT_TYPE, grantType);
        String json = HttpClientUtils.httpGet(code2Session, entry);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
