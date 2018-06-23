import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetMe;
import de.fuerstenau.buildconfig.BuildConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {
    public static ExecutorService executor = Executors.newFixedThreadPool(8);
    public static TelegramBot bot;
    public static String botUserName;

    public static void main(String[] args) {
        bot = new TelegramBot(BuildConfig.BOT_TOKEN);
        botUserName = bot.execute(new GetMe()).user().username();
        var updateHandler = new UpdateHandler(;

        updateHandler.run((update, message, userId, chatId) -> {
            var detectCommand = new DetectCommand(message);

            switch (detectCommand.get()) {
                case sed: {
                    new SedHandler(message, chatId).run();
                    break;
                }

                case about: {
                    new AboutHandler(chatId).run();
                    break;
                }
            }
        });
    }
}
