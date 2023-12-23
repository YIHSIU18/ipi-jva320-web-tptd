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
import org.springframework.web.server.ResponseStatusException;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Controller
public class SalarieController {


   @Autowired SalarieAideADomicileService salarieAideADomicileService;
   @Autowired SalarieAideADomicileRepository salarieAideADomicileRepository;


    @GetMapping(value = "/salaries/{id}")
    public String detailSalarie(final ModelMap model, @PathVariable(value = "id") long id)
    {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);

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
    public String listSalarie(@RequestParam(name = "nom", required = false) String nom,
                              @RequestParam(name = "action", required = false) String action, ModelMap model)
    {
        try
        {
            if ("recherche".equals(action)) {
                // Effectuer la recherche par nom
                List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries(nom);
                model.put("salaries", salaries);
            } else {
                // Si aucun nom n'est fourni ou si l'action n'est pas "recherche", obtenir tous les salariés
                List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
                model.put("salaries", salaries);
            }
        }catch (EntityNotFoundException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, salarieAideADomicileService.getSalaries(nom)+ " : pas trouvé");
        }

        return "list";

    }

    @GetMapping(value = "/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable(value = "id") long id) {
        try {
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
        } catch (SalarieException e) {
            // Handle exception, if needed
            // You can also redirect to an error page
            e.printStackTrace();
        }

        // Redirect to the list of employees after deletion
        return "redirect:/salaries";
    }

    @PostMapping(value = "/salaries/{id}")
    public String updateSalarie(@ModelAttribute("salarie") SalarieAideADomicile salarieUpdate, Model model) {
        try {
            salarieAideADomicileRepository.save(salarieUpdate);

        } catch (EntityExistsException e) {
            // Gérer l'exception selon les besoins
            model.addAttribute("erreur", e.getMessage());
            return "detail_Salarie";
        }

        // Redirect to the list of employees after deletion
        return "redirect:/salaries/" + salarieUpdate.getId();
    }

}
