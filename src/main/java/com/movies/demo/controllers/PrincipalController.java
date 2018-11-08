package com.movies.demo.controllers;

import com.movies.demo.commands.IsTypeCasted;
import com.movies.demo.services.PrincipalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Slf4j
@Controller
@RequestMapping("/principal")
public class PrincipalController {

    private final PrincipalService principalService;

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @GetMapping("/is-typecasted")
    public String isTypeCasted(Model model){
        model.addAttribute("typecasted", new IsTypeCasted());

        return "principal/isTypeCasted";
    }

    @PostMapping("/is-typecasted")
    public String typeCasted(@ModelAttribute IsTypeCasted command, Model model) {
        model.addAttribute("typecasted", principalService.findTypeCasted(command.getName()));

        return "principal/typecast";
    }
}