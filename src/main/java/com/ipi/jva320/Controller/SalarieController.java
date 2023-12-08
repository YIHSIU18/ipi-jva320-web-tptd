package com.ipi.jva320.Controller;
import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.repository.SalarieAideADomicileRepository;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Controller
public class SalarieController {


   @Autowired SalarieAideADomicileService salarieAideADomicileService;


    @GetMapping(value = "/salaries/{id}")
    public String detailSalarie(final ModelMap model, @PathVariable(value = "id") long id)
    {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);
        model.put ("actionUrl","/salaries/save"+ salarie);

        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String createSalarie(final ModelMap model)
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        model.put ("salarie", salarie);
        model.put ("actionUrl","/salaries/save");

        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String saveSalarie(@ModelAttribute ("salarie") SalarieAideADomicile salarie, ModelMap model){
            try
            {
                salarieAideADomicileService.creerSalarieAideADomicile(salarie);

            } catch (Exception e)
            {
                model.put("error", "Echec de création du salarié : " + e.getMessage());
                return "detail_Salarie";
            }
           // Redirect to the details page for the created salarie
            return "redirect:/salaries/" + salarie.getId();

    }

    @GetMapping(value = "/salaries")
    public String listSalarie(ModelMap model)
    {
        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        model.put("salaries", salaries);
        //model.put("actionUrl", "/salaries/save");

        return "list";
    }

}
