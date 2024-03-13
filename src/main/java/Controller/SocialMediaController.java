package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
     AccountService accountService;
     MessageService messageService;

     public SocialMediaController() {
          accountService = new AccountService();
          messageService = new MessageService();
     }

     /**
      * In order for the test cases to work, you will need to write the endpoints in
      * the startAPI() method, as the test
      * suite must receive a Javalin object from this method.
      * 
      * @return a Javalin app object which defines the behavior of the Javalin
      *         controller.
      */
     public Javalin startAPI() {
          Javalin app = Javalin.create();
          app.get("example-endpoint", this::exampleHandler);
          app.get("hello-world", ctx -> {
               System.out.println("Hello World");
          });

          app.post("register", this::registerHandler);
          app.post("login", this::loginHandler);
          app.post("messages", this::newMessageHandler);
          app.get("messages", this::getAllMessageHandler);
          app.get("messages/{message_id}", this::getMessageByMessageIDHandler);
          app.delete("messages/{message_id}", this::deleteMessageByMessageIDHandler);
          app.patch("messages/{message_id}", this::updateMessageByMessageIDHandler);
          app.get("accounts/{account_id}/messages", this::getAllMessageByUserIDHandler);

          return app;
     }

     /**
      * This is an example handler for an example endpoint.
      * 
      * @param context The Javalin Context object manages information about both the
      *                HTTP request and response.
      */
     private void exampleHandler(Context context) {
          context.json("sample text");
          System.out.println("Hello World");
     }

     
     private void registerHandler(Context context) throws JsonProcessingException {
          ObjectMapper mapper = new ObjectMapper();
          Account account = mapper.readValue(context.body(), Account.class);
          Account addedAccount = accountService.registerAccount(account);

          if (addedAccount == null) {
               context.status(400);
          } else if (addedAccount.getUsername() == "" || addedAccount.getPassword().length() < 4) {
               context.status(400);
          } else {
               context.json(mapper.writeValueAsString(addedAccount));
          }
     }

     
     private void loginHandler(Context context) throws JsonProcessingException {
          ObjectMapper mapper = new ObjectMapper();
          Account account = mapper.readValue(context.body(), Account.class);
          Account loginAccount = accountService.loginAccount(account);

          if (loginAccount == null) {
               context.status(401);
          } else if (!loginAccount.getPassword().equals(account.getPassword())) {
               context.status(401);
          } else {
               context.json(mapper.writeValueAsString(loginAccount));
          }
     }

     
     private void newMessageHandler(Context context) throws JsonProcessingException {
          ObjectMapper mapper = new ObjectMapper();
          Message message = mapper.readValue(context.body(), Message.class);
          Message newMessage = messageService.createMessage(message);

          if (message.getMessage_text() == "" || message.getMessage_text().length() > 255) {
               context.status(400);
          } else if (accountService.accountExists(message.getPosted_by()) == null) {
               context.status(400);
          } else {
               context.json(mapper.writeValueAsString(newMessage));
          }
          // else
     }

     
     private void getAllMessageHandler(Context context) {
          context.json(messageService.getAllMessages());
     }

     
     private void getMessageByMessageIDHandler(Context context) {
          int messageID = Integer.parseInt(context.pathParam("message_id"));

          Message message = messageService.getMessageByMessageID(messageID);
          if (message == null) {
               context.status(200);
          } else {
               context.json(message);
          }
     }

    
     private void deleteMessageByMessageIDHandler(Context context) {
          int messageID = Integer.parseInt(context.pathParam("message_id"));
          Message messageToBeDeleted = messageService.deleteMessageByMessageID(messageID);

          if (messageToBeDeleted == null) {
               context.status(200);
          } else {
               context.json(messageToBeDeleted);
          }

     }

     
     private void updateMessageByMessageIDHandler(Context context) throws JsonProcessingException {
          ObjectMapper mapper = new ObjectMapper();
          Message message = mapper.readValue(context.body(), Message.class);
          int messageID = Integer.parseInt(context.pathParam("message_id"));
          Message updatedMessage = messageService.updateMessageByMessageID(messageID, message);

          if (updatedMessage == null) {
               context.status(400);
          } else {
               context.json(mapper.writeValueAsString(updatedMessage));
          }

     }

     
     private void getAllMessageByUserIDHandler(Context context) {
          int userID = Integer.parseInt(context.pathParam("account_id"));
          context.json(messageService.getAllMessagesByUserID(userID));
     }
}