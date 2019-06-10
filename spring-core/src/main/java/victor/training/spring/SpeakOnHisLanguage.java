package victor.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Service
public class SpeakOnHisLanguage {

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    public void m() {
        String m = messageSource.getMessage("my.error.message",
                new Object[]{"Student"},
                Locale.FRENCH);
        System.out.println("From file: " + m);
    }
}
