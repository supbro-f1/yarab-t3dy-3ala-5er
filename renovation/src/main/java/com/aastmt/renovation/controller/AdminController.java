package com.aastmt.renovation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aastmt.renovation.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("workers", adminService.getAllWorkers());
        return "admin-dashboard";
    }

    @GetMapping("/admin/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            adminService.deleteUser(id);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/admin/delete-worker/{id}")
    public String deleteWorker(@PathVariable Long id) {
        try {
            adminService.deleteWorker(id);
            return "success";
        } catch (Exception e) { 
            return "error";
        } 
    }
}