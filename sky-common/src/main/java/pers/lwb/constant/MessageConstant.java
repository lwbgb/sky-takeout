package pers.lwb.constant;

public class MessageConstant {
    // 用户登录
    public static final String ACCOUNT_NOT_FOUND = "账号不存在！";
    public static final String PASSWORD_ERROR = "密码错误！";
    public static final String ACCOUNT_LOCKED = "账号被锁定！";
    public static final String LOGIN_FAILED = "登录失败！";
    public static final String USER_NOT_LOGIN = "用户未登录！";

    // 员工相关操作
    public static final String EMPLOYEE_INSERT_ERROR = "新增员工失败！";
    public static final String EMPLOYEE_INSERT_SUCCESS = "新增员工成功！";
    public static final String ACCOUNT_SET_STATUS_SUCCESS = "账号状态设置成功！";
    public static final String ACCOUNT_SET_STATUS_ERROR = "账号状态设置失败！";
    public static final String EMPLOYEE_NOT_FOUND = "该员工不存在！";
    public static final String EMPLOYEE_UPDATE_ERROR = "员工信息更新失败！";
    public static final String EMPLOYEE_UPDATE_SUCCESS = "员工信息更新成功！";
    public static final String USERNAME_REPEAT= "用户名重复！";

    public static final String USERNAME_ERROR= "账号输入不符，请输入3-20个字符！";
    public static final String USERNAME_EMPTY= "未输入用户名！";
    public static final String PHONE_NUMBER_ERROR= "请输入正确的手机号！";
    public static final String ID_NUMBER_ERROR= "身份证号码不正确！";

    // 分类相关操作
    public static final String CATEGORY_INSERT_ERROR = "新增菜品失败！";
    public static final String CATEGORY_INSERT_SUCCESS = "新增菜品成功！";
    public static final String CATEGORY_DELETE_SUCCESS = "删除菜品成功！";
    public static final String CATEGORY_DELETE_ERROR = "删除菜品失败！";
    public static final String CATEGORY_UPDATE_ERROR = "菜品信息更新失败！";
    public static final String CATEGORY_UPDATE_SUCCESS = "菜品信息更新成功！";
    public static final String CATEGORY_SET_STATUS_SUCCESS = "菜品状态设置成功！";
    public static final String CATEGORY_SET_STATUS_ERROR = "菜品状态设置失败！";
    public static final String CATEGORY_BE_RELATED_BY_SETMEAL = "当前菜品关联了套餐,不能删除";
    public static final String CATEGORY_BE_RELATED_BY_DISH = "当前菜品关联了分类,不能删除";

    // 菜品相关操作
    public static final String DISH_INSERT_ERROR = "新增菜品失败！";
    public static final String DISH_INSERT_SUCCESS = "新增菜品成功！";
    public static final String DISH_DELETE_SUCCESS = "删除菜品成功！";
    public static final String DISH_DELETE_ERROR = "删除菜品失败！";
    public static final String DISH_UPDATE_ERROR = "菜品信息更新失败！";
    public static final String DISH_UPDATE_SUCCESS = "菜品信息更新成功！";
    public static final String DISH_SET_STATUS_SUCCESS = "菜品状态设置成功！";
    public static final String DISH_SET_STATUS_ERROR = "菜品状态设置失败！";
    public static final String DISH_BE_RELATED_BY_SETMEAL = "当前菜品关联了套餐,不能删除!";
    public static final String DISH_ON_SALE = "无法删除启售中的菜品!";
    public static final String DISH_NOT_FOUND = "菜品不存在！";


    // 口味相关操作
    public static final String FLAVOR_INSERT_ERROR = "新增口味失败！";
    public static final String FLAVOR_INSERT_SUCCESS = "新增口味成功！";
    public static final String FLAVOR_DELETE_ERROR = "菜品口味删除失败！";
    public static final String FLAVOR_DELETE_SUCCESS = "菜品口味删除成功！";
    public static final String FLAVOR_UPDATE_SUCCESS = "菜品口味修改成功！";
    public static final String FLAVOR_UPDATE_ERROR = "菜品口味修改失败！";


    public static final String UNKNOWN_ERROR = "未知错误！";
    public static final String SHOPPING_CART_IS_NULL = "购物车数据为空，不能下单";
    public static final String ADDRESS_BOOK_IS_NULL = "用户地址为空，不能下单";
    public static final String UPLOAD_FAILED = "文件上传失败！";
    public static final String SETMEAL_ENABLE_FAILED = "套餐内包含未启售菜品，无法启售";
    public static final String PASSWORD_EDIT_FAILED = "密码修改失败！";
    public static final String SETMEAL_ON_SALE = "起售中的套餐不能删除";
    public static final String ORDER_STATUS_ERROR = "订单状态错误！";
    public static final String ORDER_NOT_FOUND = "订单不存在！";

    // 其他
    public static final String METHOD_NOT_FOUND = "未找到该方法：";
    public static final String AUTO_FILL_ERROR = "自动填充数据异常！";
}
