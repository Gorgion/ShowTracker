/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.showtracker.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import net.sf.showtracker.domain.Show;
import net.sf.showtracker.domain.UserAccount;
import net.sf.showtracker.exception.ServiceException;
import net.sf.showtracker.security.CurrentUsername;
import net.sf.showtracker.service.ShowService;
import net.sf.showtracker.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Represents show related pages.
 *
 * @author Tomáš Svoboda
 */
@Controller
@RequestMapping(value = "/auth/shows")
public class ShowController
{

    @Autowired
    private ShowService showService;

    @Autowired
    private UserAccountService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String listShows(Model model, @CurrentUsername String userEmail)
    {
        UserAccount user = null;

        try
        {
            user = userService.getByEmail(userEmail).get();
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Listing all shows error.", ex);
        }

//        List<List<Show>> shows = new ArrayList<>();
        Map<String, List<Show>> shows = new LinkedHashMap<>(27);
        
        try
        {
//            List<Show> tmpShows = showService.getAllForUser(user).get();
            String firstLetter;
            
            for(Show show : showService.getAllForUser(user).get())
            {
                firstLetter = show.getName().substring(0, 1).toUpperCase();
                
                if(!shows.containsKey(firstLetter))
                {
                    List<Show> tmpShows = new ArrayList<>();
                    tmpShows.add(show);
                    
                    shows.put(firstLetter, tmpShows);
                } else {                
                    shows.get(firstLetter).add(show);
                }
            }
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Listing all shows error.", ex);
        }

        model.addAttribute("shows", new ShowsWrapper(shows));

        return "show-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String creatingShow(@RequestParam(value = "name", required = true) String name, Model model, RedirectAttributes redirectAttributes, @CurrentUsername String userEmail)//BindingResult result)
    {
        Show show = new Show();
        try
        {
            UserAccount user = userService.getByEmail(userEmail).get();
            if (user != null)
            {
                show.setName(name.trim());
                show.setSeries(0);
                show.setEpisode(0);

                show.setUser(user);

                show.setId(showService.create(show).get());
            }

        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Creating new show error.", ex);
        }

        redirectAttributes.addFlashAttribute("created", true);

        return "redirect:/auth/shows/edit/" + show.getId();
    }

    @RequestMapping(value = "/edit/{id}")
    public String editingShow(@PathVariable Long id, Model model)
    {
        try
        {
            model.addAttribute("show", showService.getById(id).get());
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Update show error.", ex);
        }

        return "show-item";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editingShow(@Valid @ModelAttribute Show show, BindingResult result, @CurrentUsername String userEmail)
    {
        try
        {
            if (result.hasErrors())
            {
                return "show-item";
            }

            UserAccount user = userService.getByEmail(userEmail).get();

            if (user != null)
            {
                show.setUser(user);
                show.setName(show.getName().trim());
                
                showService.update(show).get();
            }
            
        } catch (InterruptedException | ExecutionException ex)
        {
            throw new ServiceException("Update show error.", ex);
        }

        return "redirect:/auth/shows";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deletingShow(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes)
    {
        try
        {
            showService.removeById(id).get();
        } catch (InterruptedException | ExecutionException e)
        {
            throw new ServiceException("Delete show error.", e);
        }

        return "redirect:/auth/shows";
    }

    public class ShowsWrapper
    {
        private Map<String, List<Show>> shows;

        public ShowsWrapper()
        {
        }

        public ShowsWrapper(Map<String, List<Show>> shows)
        {
            this.shows = shows;
        }

        public Map<String, List<Show>> getShows()
        {
            return shows;
        }

        public void setShows(Map<String, List<Show>> shows)
        {
            this.shows = shows;
        }
    }
}
