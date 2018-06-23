import com.pengrad.telegrambot.model.Message;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DetectCommand {
    private Message message;

    public DetectCommand(Message message) {
        this.message = message;
    }

    public CommandType get() {
        if (this.message == null) {
            return CommandType.nothing;
        }

        if (this.message.text() == null) {
            return CommandType.nothing;
        }

        if (isSed()) {
            return CommandType.sed;
        }

        if (isAbout()) {
            return CommandType.about;
        }

        return CommandType.nothing;
    }

    private boolean isSed() {
        try {
            Pattern.compile(this.message.text());
        } catch (PatternSyntaxException  e) {
            return false;
        }

        return this.message.text().matches("s/(.*)/(.*)/");
    }

    private boolean isAbout() {
        return this.message.text().trim().equals("/about");
    }
}
