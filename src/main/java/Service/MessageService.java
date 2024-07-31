package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDao;

    public MessageService () {
        messageDao = new MessageDAO();
    }

    public Message creatMessage(Message message) {
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() >=255) {
            return null;
        }
        AccountDAO accountDAO = new AccountDAO();
        if (accountDAO.getAccount(message.posted_by) == null) {
            return null;
        }

        return this.messageDao.creatMessage(message);
    }

    public List<Message> getAllMessages() {
        return this.messageDao.getAllMessages();
    }

    public Message getMessage(int message_id) {
        return this.messageDao.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) {
        return this.messageDao.deleteMessage(message_id);
    }
    
    public Message updateMessage(int message_id, String message_text) {
        if (message_text.length() == 0 || message_text.length() >=255) {
            return null;
        }
        return this.messageDao.updateMessage(message_id, message_text);
    }

    public List<Message> getAllMessagesFromUser(int account_id) {
        return this.messageDao.getAllMessagesFromUser(account_id);
    }
}
