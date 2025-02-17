package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    ObjectMapper om;

    public SocialMediaController(){
        this.accountService = new AccountService();
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


}