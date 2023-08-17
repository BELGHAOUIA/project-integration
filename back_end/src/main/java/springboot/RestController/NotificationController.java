package springboot.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import springboot.Entity.Notification;
import springboot.RestController.impl.NotificationControllerImlp;
import springboot.Service.NotificationService;

import java.util.List;

@CrossOrigin
@RestController
public class NotificationController implements NotificationControllerImlp {

    private NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @Override
    public boolean delete(long id) {
        return service.delete(id);
    }

    @Override
    public boolean deleteAll(UserDetails userDetails) {
        return service.deleteAll(userDetails.getUsername());
    }

    @Override
    public List<Notification> fetchAllByUser(UserDetails userDetails) {
        return service.fetchAllByUser(userDetails.getUsername());
    }

    @Override
    public int countByUser(UserDetails userDetails) {
        return service.countByUser(userDetails.getUsername());
    }
}
