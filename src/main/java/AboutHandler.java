import com.pengrad.telegrambot.request.SendMessage;

class AboutHandler {
    private Long chatId;

    AboutHandler(Long chatId) {
        this.chatId = chatId;
    }

    void run() {
        var sendMessage = new SendMessage(this.chatId, "Il bot è stato sviluppato interamente in Java da @ErnyTech");
        Bot.bot.execute(sendMessage);
    }
}
