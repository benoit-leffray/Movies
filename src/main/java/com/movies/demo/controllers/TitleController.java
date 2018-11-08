package com.movies.demo.controllers;

import com.movies.demo.commands.FindCoincidence;
import com.movies.demo.commands.FindTitleByGenre;
import com.movies.demo.commands.FindTitleByName;
import com.movies.demo.services.TitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/title")
public class TitleController {

    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping("/find-title")
    public String showTitle(Model model){
        model.addAttribute("title", new FindTitleByName());

        return "title/findTitle";
    }

    @PostMapping("/find-title")
    public String findTitle(@ModelAttribute FindTitleByName command, Model model){
        model.addAttribute("title", titleService.findByName(command.getName()));

        return "title/show";

    }

    @GetMapping("/find-genre")
    public String findGenre(Model model){
        model.addAttribute("title", new FindTitleByGenre());

        return "title/findGenre";
    }

    @PostMapping("/find-genre")
    public String ratedByGenre(@ModelAttribute FindTitleByGenre command, Model model){
        model.addAttribute("titles", titleService.findAllRatedByGenre(command.getGenre()));

        return "title/ratedByGenre";

    }

    @GetMapping("/find-coincidence")
    public String findCoincidences(Model model){
        model.addAttribute("coincidence", new FindCoincidence());

        return "title/findCoincidence";
    }

    @PostMapping("/find-coincidence")
    public String coincidences(@ModelAttribute FindCoincidence command, Model model){
        model.addAttribute("titles", titleService.findAllByPersonCoincidence(command.getPersonName1(),
                                                                                command.getPersonName2()));

        return "title/coincidence";

    }
}