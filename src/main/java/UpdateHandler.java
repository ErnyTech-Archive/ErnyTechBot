import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

class UpdateHandler {
    private Thread botUpdateThread;
    private Integer offset = 0;

    void run(UpdateCallback updateCallback) {
        this.botUpdateThread = new Thread(() -> handler(updateCallback));
        this.botUpdateThread.start();
    }

    private void handler(UpdateCallback updateCallback) {
        while (true) {
            var getUpdates = new GetUpdates().limit(200).offset(offset).timeout(60);
            GetUpdatesResponse getUpdatesResponse;

            try {
                getUpdatesResponse = Bot.bot.execute(getUpdates);
            } catch (RuntimeException e) {
                continue;
            }

            var updates = getUpdatesResponse.updates();

            if (updates == null) {
                continue;
            }

            if (updates.size() < 1) {
                continue;
            }

            for (Update update : updates) {
                if (update == null) {
                    continue;
                }

                var message = update.message();
                Long userId = null, chatId = null;

                if (message != null) {
                    userId = message.from().id().longValue();
                    chatId = message.chat().id();
                }

               startCallback(updateCallback, update, message, userId, chatId);
            }

            this.offset = updates.get(updates.size() - 1).updateId() + 1;
        }
    }

    private void startCallback(UpdateCallback updateCallback, Update update, Message message, Long userId, Long chatId) {
        Bot.executor.execute(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    updateCallback.handle(update, message, userId, chatId);
                } catch (RuntimeException e) {
                    continue;
                }

                break;
            }
        });
    }
}
