package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Account account = mapper.readValue(context.body(), Account.class);
            Account registerAccount = this.accountService.register(account);

            if (registerAccount != null) {
                context.json(mapper.writeValueAsString(registerAccount));
                context.status(200);
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            context.status(400);
        }
    }

    private void loginHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Account account = mapper.readValue(context.body(), Account.class);
            Account loginAccount = this.accountService.login(account);

            if (loginAccount != null) {
                context.json(mapper.writeValueAsString(loginAccount));
                context.status(200);
            } else {
                context.status(401);
            }
        } catch (Exception e) {
            context.status(401);
        }
    }
}