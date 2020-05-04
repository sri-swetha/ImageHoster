package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageService imageService;
    @RequestMapping(value="/image/{imageId}/{imageTitle}/comments",method = RequestMethod.POST)
    public String persistComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String title, @RequestParam(name = "comment") String comment, Model model, HttpSession session) {
        Image image = imageService.getImage(imageId);
        Comment newComment=new Comment();
        newComment.setText(comment);
        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);
        newComment.setCreatedDate(LocalDate.now());
        newComment.setImage(image);
        commentService.persistComment(newComment);
        List<Comment> imageComments=new ArrayList<>();
        imageComments.add(newComment);
        image.setComments(imageComments);
        System.out.println("/images/"+imageId+"/"+title);
        return "redirect:/images/"+imageId+"/"+title;
    }
}
