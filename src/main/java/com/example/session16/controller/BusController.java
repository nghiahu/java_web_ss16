package com.example.session16.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.session16.model.Bus;
import com.example.session16.model.LoginUser;
import com.example.session16.model.RoleUser;
import com.example.session16.model.User;
import com.example.session16.service.bus.BusServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class BusController {
    @Autowired
    private BusServiceImp busServiceImp;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("admin")
    public String bus(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            model.addAttribute("loginUser", new LoginUser());
            return "login";
        }else {
            if (user.getRole() == RoleUser.ADMIN) {
                return "adminPage";
            }else {
                model.addAttribute("loginUser", new LoginUser());
                return "redirect:/login";
            }
        }
    }
    @GetMapping("manageBus")
    public String managerBus(Model model) {
        model.addAttribute("buses", busServiceImp.getAllBuses());
        return "manageBus";
    }

    @GetMapping("addBuss")
    public String addBus(Model model) {
        model.addAttribute("bus", new Bus());
        return "addBuss";
    }

    @PostMapping("buss-save")
    public String saveBus(@Valid @ModelAttribute("bus") Bus bus, BindingResult bindingResult,
                          @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "addBuss";
        }
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            if (url == null && url.isEmpty()) {
                throw  new RuntimeException("Invalid URL");
            }
            bus.setImage(url);
            busServiceImp.addBus(bus);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/manageBus";
    }
    @GetMapping("updateBus")
    public String updateBus(@RequestParam("id") int id,Model model) {
        model.addAttribute("bus", busServiceImp.getBusById(id));
        return "updateBus";
    }
    @PostMapping("buss-edit")
    public String editBus(@Valid @ModelAttribute("bus") Bus bus, BindingResult bindingResult,
                          @RequestParam("file") MultipartFile file){
        if (bindingResult.hasErrors()) {
            return "updateBus";
        }
        try {
            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("url");
                if (url == null || url.isEmpty()) {
                    throw new RuntimeException("Invalid URL");
                }
                bus.setImage(url);
            } else {
                Bus oldBus = busServiceImp.getBusById(bus.getId());
                bus.setImage(oldBus.getImage());
            }
            busServiceImp.updateBus(bus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/manageBus";
    }

    @GetMapping("deleteBus")
    public String deleteBus(@RequestParam("id") int id) {
        busServiceImp.deleteBus(id);
        return "redirect:/manageBus";
    }
}
