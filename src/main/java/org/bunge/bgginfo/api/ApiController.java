package org.bunge.bgginfo.api;

import java.util.ArrayList;
import java.util.List;

import org.bunge.bgginfo.bggintegration.model.AjaxResponseBody;
import org.bunge.bgginfo.bggintegration.service.BGGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    BGGService bggService;

    @PostMapping("/test")
    public ResponseEntity<?> getTest() {
        AjaxResponseBody result = new AjaxResponseBody();

        List<String> users = new ArrayList<>();
        if (users.isEmpty()) {
            result.msg = "Cool And Good";
        }
        
        return ResponseEntity.ok(result.msg);
    }

    @GetMapping("/getOverview")
    public ResponseEntity<?> getOverview(int quality) {
        return ResponseEntity.ok(bggService.getMasterList(quality));
    }
}