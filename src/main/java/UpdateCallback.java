import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public interface UpdateCallback {
    void handle(Update update, Message message, Long userId, Long chatId);
}
