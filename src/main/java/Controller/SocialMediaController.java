package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    ObjectMapper om;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.om = new ObjectMapper();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("register", this::postRegisterHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postMessagesHandler);
        app.get("messages", this::getMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("messages/{message_id}", this::patchMessageByIDHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesFromAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context ctx){
        try{
            String jsonString = ctx.body();
            Account account = om.readValue(jsonString, Account.class);
            Account newAccount = accountService.addAccount(account);
            if(newAccount == null){
                ctx.status(400);
            }
            else{
                ctx.json(newAccount);
            }
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private void postLoginHandler(Context ctx){
        try{
            String jsonString = ctx.body();
            Account account = om.readValue(jsonString, Account.class);
            Account loggedInAccount = accountService.login(account);
            if(loggedInAccount == null){
                ctx.status(401);
            }
            else{
                ctx.json(loggedInAccount);
            }
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private void postMessagesHandler(Context ctx){
        try{
            String jsonString = ctx.body();
            Message message = om.readValue(jsonString, Message.class);
            Message newMessage = messageService.addMessage(message);
            if(newMessage == null){
                ctx.status(400);
            }
            else{
                ctx.json(newMessage);
            }
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private void getMessagesHandler(Context ctx){
        List<Message> messageList = messageService.getAllMessages();
        ctx.json(messageList);
    }

    private void getMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageWithID(message_id);
        if(message == null){
            ctx.result("");
        }
        else{
            ctx.json(message);
        }
    }

    private void deleteMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageWithID(message_id);
        if(message == null){
            ctx.result("");
        }
        else{
            ctx.json(message);
        }
    }

    private void patchMessageByIDHandler(Context ctx){
        try{
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            String jsonString = ctx.body();
            Message messageBody = om.readValue(jsonString, Message.class);

            Message message = messageService.updateMessageWithID(message_id, messageBody.getMessage_text());
            if(message == null){
                ctx.status(400);
            }
            else{
                ctx.json(message);
            }
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private void getMessagesFromAccountHandler(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messageList = messageService.getAllMessagesFromAccount(account_id);
        ctx.json(messageList);
    }
}