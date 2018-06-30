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

        if (isSource()) {
            return CommandType.source;
        }

        if (isOp()) {
            return CommandType.op;
        }

        if (isSend()) {
            return CommandType.send;
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

        if (this.message.replyToMessage().text().split("/").length < 2) {
            return false;
        }

        return true;
    }

    private boolean isAbout() {
        return genericCommandDetect("about");
    }

    private boolean isSource() {
        return genericCommandDetect("source");
    }

    private boolean isOp() {
        return genericCommandTxtDetect("op");
    }

    private boolean isSend() {
        return genericCommandTxtDetect("send");
    }

    private boolean genericCommandDetect(String command) {
        if (this.message.text().equals("/" + command)) {
            return true;
        }

        if (this.message.text().equals("/" + command + "@" + Bot.botUserName)) {
            return true;
        }

        return false;
    }

    private boolean genericCommandTxtDetect(String command) {
        if (!this.message.text().matches("/" + command + "(.*)")) {
            return false;
        }

        if (this.message.text().split("\\s+").length < 2) {
            return false;
        }

        return true;
    }
}
