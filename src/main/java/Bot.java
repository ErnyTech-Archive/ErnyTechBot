import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.ChatMember;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.GetChatAdministrators;
import com.pengrad.telegrambot.request.GetMe;
import de.fuerstenau.buildconfig.BuildConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {
    public static ExecutorService executor = Executors.newFixedThreadPool(8);
    public static TelegramBot bot;
    public static String botUserName;
    public static String botToken = BuildConfig.BOT_TOKEN;
    public static List<User> botAdmins = new ArrayList<>();

    public static void main(String[] args) {
        bot = new TelegramBot(botToken);
        botUserName = bot.execute(new GetMe()).user().username();
        var updateHandler = new UpdateHandler();

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

                case source: {
                    new SourceHandler(chatId).run();
                    break;
                }

                case op: {
                    new OpHandler(message, chatId).run();
                    break;
                }

                case send: {
                    new SendHandler(message, chatId).run();
                    break;
                }
            }
        });
    }

    public static boolean isAdmin(User user, Long chat_id) {
        for (User admin : botAdmins) {
            if (admin.id().equals(user.id())) {
                return true;
            }
        }

        var getChatAdministrators = new GetChatAdministrators(chat_id);
        var getChatAdministratorsResponse = Bot.bot.execute(getChatAdministrators);
        var admins = getChatAdministratorsResponse.administrators();

        for (ChatMember chatMember : admins) {
            if (chatMember.user().id().equals(user.id())) {
                return true;
            }
        }

        return false;
    }
}
