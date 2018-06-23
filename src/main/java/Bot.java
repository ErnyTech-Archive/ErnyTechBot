import com.pengrad.telegrambot.TelegramBot;
import de.fuerstenau.buildconfig.BuildConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {
    public static ExecutorService executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot(BuildConfig.BOT_TOKEN);
        var updateHandler = new UpdateHandler(bot);

        updateHandler.run((update, message, userId, chatId) -> {
            var detectCommand = new DetectCommand(message, bot);

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
