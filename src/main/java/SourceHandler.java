import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

class SourceHandler {
    private Long chatId;

    SourceHandler(Long chatId) {
        this.chatId = chatId;
    }

    void run() {
        var sendMessage = new SendMessage(this.chatId, "Puoi ottenere il sorgente completo di questo bot [qui](https://github.com/ErnyTech/ErnyTechBot)");
        sendMessage.parseMode(ParseMode.Markdown);
        Bot.bot.execute(sendMessage);
    }
}
