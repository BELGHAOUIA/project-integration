package springboot.RestController.impl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springboot.Entity.Notification;

import java.util.List;

@RequestMapping("/notification/")
public interface NotificationControllerImlp {
    @DeleteMapping("delete")
    public boolean delete(@RequestParam("id") long id);

    @DeleteMapping("deleteAll")
    public boolean deleteAll(@AuthenticationPrincipal UserDetails userDetails);

    @GetMapping("fetchAllByUser")
    public List<Notification> fetchAllByUser(@AuthenticationPrincipal UserDetails userDetails);

    @GetMapping("countByUser")
    public int countByUser(@AuthenticationPrincipal UserDetails userDetails);
}
