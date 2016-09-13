import config.BotConfig;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SimpleBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage()) {

            if (message.getPhoto() != null) {

                duplicatePhoto(message);
            }

            if (message.getDocument() != null) {

                duplicateDocument(message);
            }

            if (message.hasText()) {

                duplicateText(message);
            }
        }
    }

    private void duplicatePhoto(Message message) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setPhoto(message.getPhoto().get(3).getFileId());
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            //TODO: so some error handling
        }
    }


    private void duplicateDocument(Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId().toString());
        sendDocument.setDocument(message.getDocument().getFileId());
        try {
            sendDocument(sendDocument);
        } catch (TelegramApiException e) {
            //TODO: so some error handling
        }
    }

    private void duplicateText(Message message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId().toString());
        sendMessageRequest.setText(message.getText());
        try {
            sendMessage(sendMessageRequest);
        } catch (TelegramApiException e) {
            //TODO: so some error handling
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USER;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }
}
