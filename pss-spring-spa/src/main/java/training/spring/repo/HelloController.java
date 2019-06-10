package training.spring.repo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@RestController
@RequestMapping("hello/{one}")
public class HelloController {
    @GetMapping("world")
    public String m(@PathVariable String one) {
        return "hello "+one;
    }
    @GetMapping("kk")
    public Booking kk(@PathVariable String one) {
        return null;

    }
    static class Booking {}

    @GetMapping("cool")
    public String n() {
        return fromMethodCall(on(HelloController.class).kk("aloha"))
                .buildAndExpand().toUriString();
    }
}
