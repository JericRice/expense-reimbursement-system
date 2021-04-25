package controllers;

import dao.ProductionConfig;
import io.javalin.http.Context;
import models.User;
import models.UserRole;
import services.UserService;
import services.UserServiceImpl;
import utilities.AccountCreationEmail;
import utilities.Encryption;

import javax.mail.MessagingException;

/**
 * controller for all users
 * */
public class UserController {
    static UserService userService;

    public UserController() {
        userService = new UserServiceImpl(ProductionConfig.url, ProductionConfig.username, ProductionConfig.password);
    }

    /**
     * get all users from service
     * */
    public void getAllUsers(Context ctx){
        ctx.json(userService.getAll());
    };

    /**
     * get user from service
     * */
    public void getOneUser(Context ctx){
        String username = ctx.pathParam("username");
        ctx.json(userService.getOneByUsername(username));
    };

    /**
     * login user given username and password
     * */
    public void loginUser(Context ctx){
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        System.out.println(password);
        System.out.println(Encryption.encrypt(password));
        User res = userService.validateCredentials(username, password);
        System.out.println(res);
        if(res != null){
            //give user token
            //String token = Encryption.encrypt(res.getUsername());
            //Token t = new Token(token,res.getRole().getId());
            ctx.sessionAttribute("killua",res);
            ctx.sessionAttribute("feedback",null);
            if(res.getRole().getId() == 1) {
                ctx.redirect("/employee/dashboard");
            }else if(res.getRole().getId() == 2){
                ctx.redirect("/financemanager/dashboard");
            }
        }else {
            ctx.sessionAttribute("feedback", "invalid credentials");
            ctx.redirect("/");
        }


    };

    /**
     * logout user
     * */
    public void logout(Context ctx){
        ctx.sessionAttribute("killua",null);
        ctx.redirect("/");
    };

    /**
     * check if user has auth token
     * */
    public void checkSession(Context ctx){
        //check if session
        User user = ctx.sessionAttribute("killua");
        System.out.println("CHECK SESSION: " + user);
        ctx.json(user);
    };

    /**
     * register user as financemanager
     * */
    public void registerUser(Context ctx) throws MessagingException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String firstName = ctx.formParam("firstname");
        String lastName = ctx.formParam("lastname");
        String email = ctx.formParam("email");
        String role = ctx.formParam("role");
        UserRole userRole = new UserRole(Integer.parseInt(role));

        //create encrypted password;
        String encPassword = Encryption.encrypt(password);
        System.out.println(encPassword);
        String decPassword = Encryption.decrypt(encPassword);
        System.out.println(decPassword);

        User user = new User(username,encPassword,firstName,lastName,email,userRole);

        //check if username exists in db
        User usernameExists = userService.getOneByUsername(username);
        User emailExists = userService.getOneByEmail(email);
        if(usernameExists != null) {
            ctx.sessionAttribute("feedback","username already exists in the system");
            ctx.redirect("/financemanager/create-employee");
        }else if(emailExists != null){
            ctx.sessionAttribute("feedback","email already exists in the system");
            ctx.redirect("/financemanager/create-employee");
        }
        else{
            userService.createOne(user);
            try {
                AccountCreationEmail.sendMail(user);
            }catch(Exception ex){
                String message = "Failed on send an e-mail to: "+user.getEmail()
                        +". Reason: "+ex.getMessage();
                ctx.sessionAttribute("feedback", message );
            }
            //ctx.json("user has been created");
            ctx.sessionAttribute("feedback",null);
            ctx.redirect("/financemanager/accounts");
        }
        //check if email exists in db
    }

    public void getFeedback(Context context) {

        String val = context.sessionAttribute("feedback");

        if(val==null){
            context.json("null");
        }else {
            System.out.println(val);
            context.json(val);
        }
    }
}
