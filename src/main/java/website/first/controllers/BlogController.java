package website.first.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import website.first.models.Post;
import website.first.repos.PostRepository;

import java.util.Optional;


@Controller
public class BlogController {

    private final PostRepository database;

    public BlogController(PostRepository postRepository) {
        this.database = postRepository;
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = database.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title, @RequestParam String excerpt,
                              @RequestParam String fullText, Model model) {
        Post post = new Post(title, excerpt, fullText);
        if (!post.getTitle().isEmpty() && !post.getExcerpt().isEmpty()) {
            database.save(post);
        }
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogText(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> idPost = database.findById(id);
        model.addAttribute("post", idPost.get());
        return "blog-text";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogTextEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> idPost = database.findById(id);
        model.addAttribute("post", idPost.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogTextEditPost(@PathVariable(value = "id") Long id, @RequestParam String title,
                                   @RequestParam String excerpt, @RequestParam String fullText, Model model) {
        Post post = database.findById(id).orElseThrow();
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setText(fullText);
        if (!post.getTitle().isEmpty() && !post.getExcerpt().isEmpty()) {
            database.save(post);
        }
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/delete")
    public String blogTextDelete(@PathVariable(value = "id") Long id, Model model) {
        Post post = database.findById(id).orElseThrow();
        database.delete(post);
        return "redirect:/blog";
    }
}
