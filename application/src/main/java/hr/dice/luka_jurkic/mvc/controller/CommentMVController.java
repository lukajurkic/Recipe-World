package hr.dice.luka_jurkic.mvc.controller;

import hr.dice.luka_jurkic.mapper.CommentMapper;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
import hr.dice.luka_jurkic.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentMVController {

    private final CommentService commentService;
    private final RecipeMVController recipeMVController;
    private final CommentMapper commentMapper;

    public CommentMVController(CommentService commentService, RecipeMVController recipeMVController, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.recipeMVController = recipeMVController;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/new")
    public String createCommentForm(@RequestParam(name = "recipeName") String recipeName, Model model) {
        CommentRequest request = new CommentRequest();
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("request", request);
        return "comment/form_createComment";
    }

    @PostMapping("/create")
    public String createComment(@Valid @ModelAttribute(name = "request") CommentRequest request,
                                BindingResult bindingResult,
                                @RequestParam(name = "recipeName") String recipeName, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipeName", recipeName);
            return "comment/form_createComment";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        request.setRecipeName(recipeName);
        commentService.createComment(auth.getName(), request);
        return recipeMVController.getRecipe(recipeName, model);
    }

    @PostMapping("/delete")
    public String deleteComment(@RequestParam(name = "recipeName") String recipeName, @RequestParam(name = "commentId") Long commentId, Model model) {
        commentService.deleteComment(commentId);
        return recipeMVController.getRecipe(recipeName, model);
    }

    @GetMapping("/update")
    public String updateCommentForm(@RequestParam(name = "recipeName") String recipeName, @RequestParam(name = "commentId") Long commentId, Model model) {
        model.addAttribute("recipeName", recipeName);
        model.addAttribute("commentId", commentId);
        model.addAttribute("request", commentMapper.contentDtoToRequest(commentService.getComment(commentId)));
        return "comment/form_updateComment";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam(name = "recipeName") String recipeName,
                                @RequestParam(name = "commentId") Long commentId,
                                @Valid @ModelAttribute(name = "request") CommentRequest request,
                                BindingResult bindingResult,
                                Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("recipeName", recipeName);
            model.addAttribute("commentId", commentId);
            return "comment/form_updateComment";
        }
        commentService.updateComment(commentId, request);
        return recipeMVController.getRecipe(recipeName, model);
    }
}
