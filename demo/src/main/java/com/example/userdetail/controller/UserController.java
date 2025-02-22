package com.example.userdetail.controller;

import com.example.userdetail.model.User;
import com.example.userdetail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Hiển thị form tạo người dùng mới
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "userform"; // Trả về trang HTML form
    }

    // Thêm hoặc cập nhật người dùng
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/user/list";
    }

    // Danh sách người dùng
    @GetMapping("/list")
    public String userList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userlist";
    }

    // Xem thông tin chi tiết người dùng
    @GetMapping("/details/{id}")
    public String viewUserDetails(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "userDetail";
        }
        return "redirect:/user/list";
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "userform"; // Sử dụng lại form cho edit
        }
        return "redirect:/user/list";
    }

    // Cập nhật thông tin người dùng
    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user) {
        userService.saveUser(user); // Lưu thay đổi
        return "redirect:/user/list";
    }

    // Xóa người dùng
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}
