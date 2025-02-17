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
    
}
