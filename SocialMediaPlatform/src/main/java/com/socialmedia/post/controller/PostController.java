package com.socialmedia.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.socialmedia.post.dto.PostDTO;
import com.socialmedia.post.service.PostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<PostDTO> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/feed";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        return "posts/create";
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("postDTO") PostDTO postDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "posts/create";
        }

        Long userId = 1L;
        String username = "tempUser";
        postService.createPost(postDTO, userId, username);
        return "redirect:/posts/feed";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("postDTO", post);
        return "posts/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id,
                             @Valid @ModelAttribute("postDTO") PostDTO postDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("postDTO", postDTO);
            return "posts/edit";
        }

        Long userId = 1L;
        postService.updatePost(id, postDTO, userId);
        return "redirect:/posts/feed";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        Long userId = 1L;
        postService.deletePost(id, userId);
        return "redirect:/posts/feed";
    }
}
