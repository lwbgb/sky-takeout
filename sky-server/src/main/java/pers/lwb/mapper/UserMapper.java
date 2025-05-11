package pers.lwb.mapper;


import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.User;

@Mapper
public interface UserMapper {
    User getByOpenid(String openid);

    int insert(User user);

    User getById(Long id);
}
