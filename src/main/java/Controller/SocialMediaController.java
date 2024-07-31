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

        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesHandler);
        app.patch("/messages/{message_id}", this::updateMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

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

    private void postMessagesHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(context.body(), Message.class);
            Message addedMessage = this.messageService.creatMessage(message);

            if (addedMessage != null) {
                context.json(mapper.writeValueAsString(addedMessage));
                context.status(200);
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(this.messageService.getAllMessages());
    }

    private void getMessagesHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = this.messageService.getMessage(message_id);
        if (message != null) {
            context.json(message);
        }
    }

    private void deleteMessagesHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = this.messageService.deleteMessage(message_id);
        if (message != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                context.json(mapper.writeValueAsString(message));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateMessagesHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));

        ObjectMapper mapper = new ObjectMapper();
        try {
            Message partialMessage = mapper.readValue(context.body(), Message.class);
            Message message = this.messageService.updateMessage(message_id, partialMessage.getMessage_text());
            if (message != null) {
                context.json(mapper.writeValueAsString(message));
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            context.status(400);
        }
    }

    private void getAllMessagesFromUserHandler(Context context) {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(this.messageService.getAllMessagesFromUser(account_id));
    }
}