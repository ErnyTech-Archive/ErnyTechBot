import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public class SedHandler {
    private Message message;
    private Message replyMessage;
    private Long chatId;
    private TelegramBot bot;
    private Thread sedThread;

    public SedHandler(Message message, Long chatId, TelegramBot bot) {
        this.message = message;
        this.replyMessage = message.replyToMessage();
        this.chatId = chatId;
        this.bot = bot;
    }

    public void run() {
        this.sedThread = new Thread(() -> {
            var match = this.message.text().split("/")[1];
            var replace = this.message.text().split("/")[2];

            if (!this.replyMessage.text().contains(match)) {
                return;
            }

            var newText = this.replyMessage.text().replace(match, replace);
            var sendMessage = new SendMessage(this.chatId, "*" + newText);
            sendMessage.replyToMessageId(this.replyMessage.messageId());
            sendMessage.disableWebPagePreview(true);
            this.bot.execute(sendMessage);
        });

        this.sedThread.start();
    }
}
