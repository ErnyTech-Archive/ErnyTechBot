import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

class AboutHandler {
    private Long chatId;

    AboutHandler(Long chatId) {
        this.chatId = chatId;
    }

    void run() {
        Bot.executor.execute(() -> {
            var sendMessage = new SendMessage(this.chatId, "Il bot è stato sviluppato interamente in Java da @ErnyTech");
            Bot.bot.execute(sendMessage);
        });
    }
}
