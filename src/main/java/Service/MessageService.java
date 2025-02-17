package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message addMessage(Message message){
        if(message.getMessage_text() == null || message.getMessage_text() == ""){
            return null;
        }
        if(accountDAO.getAccountWithAccountID(message.getPosted_by()) == null){
            return null;
        }
        return messageDAO.insertNewMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageWithID(int message_id){
        return messageDAO.getMessageWithID(message_id);
    }

    public Message deleteMessageWithID(int message_id){
        return messageDAO.deleteMessageByID(message_id);
    }

    public Message updateMessageWithID(int message_id, String message_text){
        if(message_text == null || message_text == ""){
            return null;
        }
        return messageDAO.updateMessageByID(message_id, message_text);
    }

    public List<Message> getAllMessagesFromAccount(int account_id){
        return messageDAO.getAllMessagesFromAccount(account_id);
    }
    
}
