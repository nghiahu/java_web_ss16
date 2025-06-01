package com.example.session16.controller;

import com.example.session16.model.BusTrip;
import com.example.session16.model.Pagination;
import com.example.session16.service.busTrip.BusTripServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TripController {

    @Autowired
    private BusTripServiceImp busTripServiceImp;

    @GetMapping("/listBusTrip")
    public String listBusTrip(@RequestParam(value = "type", required = false, defaultValue = "START") String type,
                              @RequestParam(value = "keyword", required = false,defaultValue = "") String keyword,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        List<BusTrip> result;
        Pagination pagination = new Pagination();
        pagination.setPageSize(5);
        pagination.setPage(page);
        pagination.setTotalItems(busTripServiceImp.totalBusTrips());
        pagination.setTotalPages((int )Math.ceil((double) pagination.getTotalItems() / pagination.getPageSize()));

        if (type != null && keyword != null && !keyword.isEmpty()) {
            keyword = removeAccent(keyword);
           result = busTripServiceImp.searchBusTrips(type,keyword, pagination.getPageSize(), pagination.getPage());
           pagination.setTotalItems(busTripServiceImp.totalSearch(type, keyword));
           pagination.setTotalPages((int )Math.ceil((double) pagination.getTotalItems() / pagination.getPageSize()));
            System.out.println(pagination.getTotalItems());
        } else {
            result = busTripServiceImp.getAllBusTrips(pagination.getPageSize(), pagination.getPage());
        }

        model.addAttribute("listBusTrip", result);
        model.addAttribute("pagination", pagination);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        return "listBusTrip";
    }
    public static String removeAccent(String input) {
        if (input == null) return null;
        return java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")  // loại bỏ dấu
                .replaceAll("đ", "d")
                .replaceAll("Đ", "D");
    }
}
