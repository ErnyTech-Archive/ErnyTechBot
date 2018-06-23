import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

public class UpdateHandler {
    private Thread botUpdateThread;
    private TelegramBot bot;
    private Integer offset = 0;

    public UpdateHandler(TelegramBot bot) {
        this.bot = bot;
    }

    public void run(UpdateCallback updateCallback) {
        this.botUpdateThread = new Thread(() -> handler(updateCallback));
        this.botUpdateThread.start();
    }

    private void handler(UpdateCallback updateCallback) {
        while (true) {
            var getUpdates = new GetUpdates().limit(200).offset(offset).timeout(60);
            GetUpdatesResponse getUpdatesResponse;

            try {
                getUpdatesResponse = this.bot.execute(getUpdates);
            } catch (Exception e) {
                continue;
            }

            var updates = getUpdatesResponse.updates();

            if (updates.size() < 1) {
                continue;
            }

            for (Update update : updates) {
                var message = update.message();
                Long userId = null, chatId = null;

                if (message != null) {
                    userId = message.from().id().longValue();
                    chatId = message.chat().id();
                }

                try {
                    updateCallback.handle(update, message, userId, chatId);
                } catch (RuntimeException e) {
                    continue;
                }
            }

            this.offset = updates.get(updates.size() - 1).updateId() + 1;
        }
    }
}
