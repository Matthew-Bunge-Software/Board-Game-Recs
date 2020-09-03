package org.bunge.bgginfo.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public String index(ModelMap map) {
        return "home/index";
    }

    @GetMapping("/about")
    public String about(ModelMap map) {
        return "home/about";
    }
}
