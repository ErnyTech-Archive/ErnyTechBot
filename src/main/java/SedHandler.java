import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

class SedHandler {
    private Message message;
    private Message replyMessage;
    private Long chatId;

    SedHandler(Message message, Long chatId) {
        this.message = message;
        this.replyMessage = message.replyToMessage();
        this.chatId = chatId;
    }

    void run() {
        Bot.executor.execute(() -> {
            var match = this.message.text().split("/")[1];
            var replace = this.message.text().split("/")[2];

            if (!this.replyMessage.text().contains(match)) {
                return;
            }

            var newText = this.replyMessage.text().replace(match, replace);
            var sendMessage = new SendMessage(this.chatId, "*" + newText);
            sendMessage.replyToMessageId(this.replyMessage.messageId());
            sendMessage.disableWebPagePreview(true);
            Bot.bot.execute(sendMessage);
        });
    }
}
