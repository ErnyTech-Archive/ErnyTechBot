import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class AboutHandler {
    private Long chatId;
    private TelegramBot bot;
    private Thread aboutThread;

    public AboutHandler(Long chatId, TelegramBot bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    public void run() {
        this.aboutThread = new Thread(() -> {
            var sendMessage = new SendMessage(this.chatId, "Il bot è stato sviluppato interamente in Java da @ErnyTech");
            this.bot.execute(sendMessage);
        });

        this.aboutThread.start();
    }
}
