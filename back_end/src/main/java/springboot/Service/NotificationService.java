package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Entity.IUser;
import springboot.Entity.Notification;
import springboot.Repository.NotificationRepository;
import springboot.auth.MyUserDetailsService;

import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void add(String message, IUser user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        repository.save(notification);
    }

    public boolean delete(long id) {
        repository.deleteById(id);
        return true;
    }

    public List<Notification> fetchAllByUser(String email) {
       return repository.findByUser(userService.findByEmail(email).get());
    }

    public int countByUser(String email) {
        System.out.println("************************* count *******************************");
        System.out.println(repository.findByUser(userService.findByEmail(email).get()).size());
        System.out.println("********************************************************");

        return repository.countByUser(userService.findByEmail(email).get());
    }

    public boolean deleteAll(String email) {
        repository.deleteAll(repository.findByUser(userService.findByEmail(email).get()));
        return true;
    }
}
