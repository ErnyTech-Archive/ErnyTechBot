import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetMe;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class DetectCommand {
    private Message message;
    private TelegramBot bot;

    DetectCommand(Message message, TelegramBot bot) {
        this.message = message;
        this.bot = bot;
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
        var botUserName = this.bot.execute(new GetMe()).user().username();

        if (this.message.text().equals("/about")) {
            return true;
        }

        if (this.message.text().equals("/about@" + botUserName)) {
            return true;
        }

        return false;
    }
}
