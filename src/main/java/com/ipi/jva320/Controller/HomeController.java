package com.ipi.jva320.Controller;

import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


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





}
