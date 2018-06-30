import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public class OpHandler {
    private Message message;
    private Long chatId;

    OpHandler(Message message, Long chatId) {
        this.message = message;
        this.chatId = chatId;
    }

    void run() {
        if (this.message.from().id().longValue() != this.chatId) {
            return;
        }

        if (Bot.isAdmin(this.message.from(), this.chatId)) {
            return;
        }

        var code = this.message.text().split("\\s+")[1];

        if (Bot.botToken.equals(code)) {
            Bot.botAdmins.add(this.message.from());
        }

        var sendMessage = new SendMessage(this.chatId, "Sei stato aggiunto alla lista admin del bot!");
        sendMessage.replyToMessageId(this.message.messageId());
        Bot.bot.execute(sendMessage);
    }
}
