import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

class AboutHandler {
    private Long chatId;
    private TelegramBot bot;

    AboutHandler(Long chatId, TelegramBot bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    void run() {
        Bot.executor.execute(() -> {
            var sendMessage = new SendMessage(this.chatId, "Il bot è stato sviluppato interamente in Java da @ErnyTech");
            this.bot.execute(sendMessage);
        });
    }
}
