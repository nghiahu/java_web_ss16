package com.example.session16.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.session16.model.BusTrip;
import com.example.session16.service.bus.BusServiceImp;
import com.example.session16.service.managerBustrip.ManagerBusTripServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class BusTripController {
    @Autowired
    private ManagerBusTripServiceImp managerBusTripImp;

    @Autowired
    private BusServiceImp busServiceImp;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("managerBusTrip")
    public String managerBusTrip(Model model) {
        model.addAttribute("busTrips", managerBusTripImp.getAllBusTrips());
        return "managerBusTrip";
    }

    @GetMapping("addBusTrip")
    public String addBusTrip(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        model.addAttribute("buses", busServiceImp.getAllBuses());
        return "addBussTrip";
    }
    @PostMapping("busTrip-save")
    public String saveBusTrip(@Valid @ModelAttribute("busTrip") BusTrip busTrip,
                              BindingResult bindingResult,
                              @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "addBussTrip";
        }
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            if (url == null || url.isEmpty()) {
                throw new RuntimeException("Invalid URL");
            }
            busTrip.setImage(url);
            managerBusTripImp.addBusTrip(busTrip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/managerBusTrip";
    }

    @GetMapping("updateBusTrip")
    public String updateBusTrip(@RequestParam("id") int id, Model model) {
        model.addAttribute("busTrip", managerBusTripImp.getBusTrip(id));
        model.addAttribute("buses", busServiceImp.getAllBuses());
        System.out.println(busServiceImp.getAllBuses().size());
        return "updaterBusTrip";
    }

    @PostMapping("busTrip-edit")
    public String editBusTrip(@Valid @ModelAttribute("busTrip") BusTrip busTrip,
                              BindingResult bindingResult,
                              @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "updaterBusTrip";
        }

        try {
            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("url");
                if (url == null || url.isEmpty()) {
                    throw new RuntimeException("Invalid URL");
                }
                busTrip.setImage(url);
            } else {
                BusTrip old = managerBusTripImp.getBusTrip(busTrip.getId());
                busTrip.setImage(old.getImage());
            }
            managerBusTripImp.updateBusTrip(busTrip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/managerBusTrip";
    }

    @GetMapping("deleteBusTrip")
    public String deleteBusTrip(@RequestParam("id") int id) {
        managerBusTripImp.deleteBusTrip(id);
        return "redirect:/managerBusTrip";
    }
}
