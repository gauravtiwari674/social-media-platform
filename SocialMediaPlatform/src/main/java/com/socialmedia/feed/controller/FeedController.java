package com.socialmedia.feed.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.socialmedia.post.dto.PostDTO;
import com.socialmedia.post.service.PostService;

@Controller
public class FeedController {

    @Autowired
    private PostService postService;

    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<PostDTO> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/feed";
    }
}
