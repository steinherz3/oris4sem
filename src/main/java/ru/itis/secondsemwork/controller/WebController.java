package ru.itis.secondsemwork.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itis.secondsemwork.model.dto.request.PlayerRequest;
import ru.itis.secondsemwork.security.details.UserDetailsImpl;
import ru.itis.secondsemwork.service.GameService;
import ru.itis.secondsemwork.service.PlayerService;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final PlayerService playerService;

    private final GameService gameService;

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String postCreatePage(@Valid @ModelAttribute PlayerRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/register";
        }
        playerService.createPlayer(request);
        return "redirect:/login";
    }

    @GetMapping("/main")
    public ModelAndView getGameList(){
        ModelAndView modelAndView = new ModelAndView("GameList");
        modelAndView.addObject("gameList", gameService.findAllPreparing());
        return modelAndView;
    }

    @PostMapping("/main")
    public RedirectView postGameList(Authentication authentication, Long gameId){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            gameService.joinToGame(gameId, userDetails.getUser().getId());
        } catch (Exception e){
            return new RedirectView("/main");
        }
        return new RedirectView("/game?id="+gameId);
    }

    @GetMapping("/game")
    public String getGamePage(){
        return "game";
    }
}
