package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.entity.AddressBook;
import pers.lwb.result.Result;
import pers.lwb.service.AddressBookService;

import java.util.List;

@Tag(name = "AddressBookController")
@Slf4j
@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @Operation(summary = "查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        log.info("查询当前登录用户的所有地址信息...");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(LocalContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    @Operation(summary = "新增地址")
    @PostMapping
    public Result<String> save(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success(MessageConstant.ADDRESS_BOOK_INSERT_SUCCESS);
    }

    @Operation(summary = "根据id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @Operation(summary = "根据id修改地址")
    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success(MessageConstant.ADDRESS_BOOK_UPDATE_SUCCESS);
    }

    @Operation(summary = "设置默认地址")
    @PutMapping("/default")
    public Result<String> setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址：{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success(MessageConstant.ADDRESS_BOOK_SET_DEFAULT_SUCCESS);
    }

    @Operation(summary = "根据id删除地址")
    @DeleteMapping("/")
    public Result<String> deleteById(Long id) {
        log.info("根据id删除地址：{}", id);
        addressBookService.deleteById(id);
        return Result.success(MessageConstant.ADDRESS_BOOKT_DELETE_SUCCESS);
    }

    @Operation(summary = "查询默认地址")
    @GetMapping("default")
    public Result<AddressBook> getDefault() {
        log.info("查询默认地址...");
        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(LocalContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }
}
