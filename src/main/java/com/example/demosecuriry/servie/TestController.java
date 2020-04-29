package com.example.demosecuriry.servie;

import com.example.demosecuriry.security.LoginPassDto;
import com.example.demosecuriry.security.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/secured")
@RestController
@AllArgsConstructor
public class TestController {

    @GetMapping
    public String ok(HttpServletRequest request){

        System.out.println(request.getSession().getId());

        return "ok";
    }
}
