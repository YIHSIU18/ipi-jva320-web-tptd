package com.ipi.jva320.Controller;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;


@Controller
public class HomeController
{
    @Autowired
     private SalarieAideADomicileService salarieAideADomicileService;
    @GetMapping(value = "/")
    public String home(final ModelMap model)
    {
        long nb = salarieAideADomicileService.countSalaries();
        model.put("nb", nb);
        return "home";
    }

    @GetMapping(value ="/page1")
    public String page1(final ModelMap m) {
        return "fpage1";
    }

    @GetMapping(value ="/page2")
    public String page2(final ModelMap m) {
        m.put("elts", Arrays.asList("Page1", "Page2"));
        return "fpage2";
    }




}
