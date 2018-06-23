import com.pengrad.telegrambot.TelegramBot;

public class Bot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("BOT_TOKEN");
        var updateHandler = new UpdateHandler(bot);

        updateHandler.run((update, message, userId, chatId) -> {
            var detectCommand = new DetectCommand(message);

            switch (detectCommand.get()) {
                case sed: {
                    new SedHandler(message, chatId, bot).run();
                    break;
                }

                case about: {
                    new AboutHandler(chatId, bot).run();
                    break;
                }
            }
        });
    }
}
