import com.pengrad.telegrambot.model.Message;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class DetectCommand {
    private Message message;

    DetectCommand(Message message) {
        this.message = message;
    }

    CommandType get() {
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

        if (!this.message.text().matches("s/(.*)/(.*)/")) {
            return false;
        }

        if (this.message.replyToMessage() == null) {
            return false;
        }

        if (this.message.replyToMessage().text() == null) {
            return false;
        }

        if (this.message.replyToMessage().text().trim().length() < 1) {
            return false;
        }

        return true;
    }

    private boolean isAbout() {
        if (this.message.text().equals("/about")) {
            return true;
        }

        if (this.message.text().equals("/about@" + Bot.botUserName)) {
            return true;
        }

        return false;
    }
}
