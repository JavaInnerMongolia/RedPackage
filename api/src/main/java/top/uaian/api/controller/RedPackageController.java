package top.uaian.api.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redpackage")
public class RedPackageController {


    @GetMapping("/grap")
    public int grap(){

    }
}
