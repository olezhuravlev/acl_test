package de.pim.spring.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public ModelAndView handleError(HttpServletRequest request, ModelAndView modelAndView) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        modelAndView.setViewName("error/404");
        modelAndView.addObject("error", status == null ? "404" : status);

        return modelAndView;
    }
}
