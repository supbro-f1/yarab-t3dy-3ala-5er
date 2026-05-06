package com.aastmt.renovation.controller;

import com.aastmt.renovation.model.Role;
import com.aastmt.renovation.model.User;
import com.aastmt.renovation.service.UserService;
import com.aastmt.renovation.dto.UserRegisterDto;
import com.aastmt.renovation.service.WorkerService;
import com.aastmt.renovation.model.JobRequest;
import com.aastmt.renovation.service.JobRequestService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JobRequestService jobRequestService;
    private final WorkerService workerService;

    public UserController(UserService userService,
                          JobRequestService jobRequestService,
                          WorkerService workerService) {
        this.userService = userService;
        this.jobRequestService = jobRequestService;
        this.workerService = workerService;
    }

    private String getSessionUsername(HttpSession session) {
        return (String) session.getAttribute("loggedInUser");
    }

    private String getSessionRole(HttpSession session) {
        return (String) session.getAttribute("userRole");
    }

    @GetMapping("/dashboard")
    public String showUserDashboardHtml(HttpSession session, Model model) {
        String username = getSessionUsername(session);
        String role = getSessionRole(session);

        if (username == null)
            return "redirect:/login";

        if (!role.equals(Role.User.name()))
            return "redirect:/login";

        User loggedInUser = userService.getUserByUsername(username);

        model.addAttribute("firstName", loggedInUser.getFirstName());
        model.addAttribute("lastName", loggedInUser.getLastName());
        model.addAttribute("username", loggedInUser.getUsername());
        model.addAttribute("email", loggedInUser.getEmail());
        model.addAttribute("phoneNumber", loggedInUser.getPhoneNumber());
        model.addAttribute("myJobs", jobRequestService.getJobsForUser(loggedInUser.getId()));
        model.addAttribute("workers", workerService.getAvailableWorkers());
        model.addAttribute("newJob", new JobRequest());

        return "user-dashboard";
    }

    @PostMapping("/request")
    public String submitJobRequest(@ModelAttribute JobRequest job,
                                   HttpSession session) {
        String username = getSessionUsername(session);

        if (username == null)
            return "redirect:/login";

        User loggedInUser = userService.getUserByUsername(username);
        job.setUserId(loggedInUser.getId());
        jobRequestService.createRequest(job);

        return "redirect:/success";
    }

    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute UserRegisterDto dtoObj,
                                    HttpSession session,
                                    Model model) {
        String username = getSessionUsername(session);

        if (username == null)
            return "redirect:/login";

        User loggedInUser = userService.getUserByUsername(username);
        boolean success = userService.updateUserProfile(loggedInUser.getId(), dtoObj);

        if (!success) {
            model.addAttribute("error", "Username, email, or phone number already in use.");
            return "user-dashboard";
        }

        session.setAttribute("loggedInUser", dtoObj.getUsername());

        return "redirect:/success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}