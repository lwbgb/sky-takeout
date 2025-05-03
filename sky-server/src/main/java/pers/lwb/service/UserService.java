package pers.lwb.service;

import pers.lwb.dto.UserLoginDTO;
import pers.lwb.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);
}
