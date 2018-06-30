import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class SendHandler {
    private Message message;
    private Long chatId;

    SendHandler(Message message, Long chatId) {
        this.message = message;
        this.chatId = chatId;
    }

    void run() {
        if (!Bot.isAdmin(this.message.from())) {
            return;
        }

        var strings = message.text().split("\\s+");
        StringBuilder txtStringBuilder = new StringBuilder();

        for (int i = 1; i < strings.length; i++) {
            txtStringBuilder.append(strings[i]);
        }

        var sendMessage = new SendMessage(this.chatId, txtStringBuilder.toString());
        sendMessage.parseMode(ParseMode.Markdown);
        Bot.bot.execute(sendMessage);
    }
}
