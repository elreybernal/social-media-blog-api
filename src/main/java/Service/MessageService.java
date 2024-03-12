package Service;

import Model.Message;

import java.util.List;

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

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMessageID(int messageID)
    {
        return messageDAO.getMessageByMessageID(messageID);
    }

    public Message deleteMessageByMessageID(int messageID)
    {
        return messageDAO.deleteMessageByMessageID(messageID);
    }

    public Message updateMessageByMessageID(int messageID, Message messageUpdate)
    {
        return messageDAO.updateMessageByMessageID(messageID, messageUpdate);
    }

    public List<Message> getAllMessagesByUserID(int userID)
    {
        return messageDAO.getMessagesByUserID(userID);
    }
    
}
