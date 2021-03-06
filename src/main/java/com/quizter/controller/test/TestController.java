package com.quizter.controller.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestController {

    @GetMapping("/viewTest")
    public ModelAndView getTestViewPagePage() {
        return new ModelAndView("test-view-page");
    }

    @GetMapping("/viewTest/{id}")
    public ModelAndView getTestViewPagePageById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("test-view-page");
        modelAndView.addObject("testId", id);
        return modelAndView;
    }

    @GetMapping("/editTest")
    public ModelAndView getTestEditPage() {
        return new ModelAndView("test-editor-page");
    }

    @GetMapping("/editTest/{id}")
    public ModelAndView getTestEditPageById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("test-editor-page");
        modelAndView.addObject("testEditId", id);
        return modelAndView;
    }

}
