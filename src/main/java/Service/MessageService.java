package Service;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message)
    {
        return messageDAO.createMessage(message);
    }
    
}
