package com.example.userdetail.controller;

import com.example.userdetail.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private List<User> userList = new ArrayList<>();
    private int currentId = 1; // Biến tự động tăng ID

    // Thêm dữ liệu mẫu
    public UserController() {
        userList.add(new User(currentId++, "John Doe", "john.doe@example.com", 25, "123 Main St"));
        userList.add(new User(currentId++, "Jane Smith", "jane.smith@example.com", 30, "456 Elm St"));
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        user.setId(currentId++); // Gán ID tự động
        userList.add(user); // Thêm người dùng vào danh sách
        return "redirect:/user/list"; // Chuyển hướng đến danh sách người dùng
    }

    @GetMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userList);
        return "userlist"; // Trả về trang danh sách người dùng
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        User user = userList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            return "userform"; // Trả về form sửa người dùng
        }
        return "redirect:/user/list"; // Nếu không tìm thấy người dùng, quay lại danh sách
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user) {
        int index = userList.indexOf(user);
        if (index != -1) {
            userList.set(index, user); // Cập nhật thông tin người dùng
        }
        return "redirect:/user/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userList.removeIf(user -> user.getId() == id); // Xóa người dùng khỏi danh sách
        return "redirect:/user/list"; // Chuyển hướng đến danh sách người dùng
    }
}
