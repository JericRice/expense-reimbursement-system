package controllers;

import dao.ProductionConfig;
import io.javalin.http.Context;
import models.Reimbursement;
import models.Type;
import models.User;
import services.ReimbursementService;
import services.ReimbursementServiceImpl;
import services.UserService;
import services.UserServiceImpl;
import utilities.ReimbursementStatusEmail;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * controller for all reimbursements
 * */
public class ReimbursementController {
    static ReimbursementService reimbursementService;
    static UserService userService;

    public ReimbursementController() {
        reimbursementService = new ReimbursementServiceImpl(ProductionConfig.url,ProductionConfig.username,ProductionConfig.password);
        userService = new UserServiceImpl(ProductionConfig.url,ProductionConfig.username,ProductionConfig.password);
    }


    /**
     * get single reimbursement
     * */
    public void getOneReimbursement(Context ctx){
        ctx.json(reimbursementService.getOneById(Integer.parseInt(ctx.pathParam("id"))));
    };

    /**
     * get all reimbursements
     * */
    public void getAllReimbursements(Context ctx){
        ctx.json(reimbursementService.getAll());
    };

    /**
     * get all reimbursements -> user
     * */
    public void getAllReimbursementsGivenUser(Context ctx){
        ctx.json(reimbursementService.getAllGivenUsername(ctx.pathParam("username")));
    };


    /**
     * approve reimbursements as manager
     * */
    public void approveReimbursement(Context ctx) throws MessagingException {

        int resId = Integer.parseInt(ctx.pathParam("resid"));
        int reimbId = Integer.parseInt(ctx.pathParam("reimbid"));

        ctx.json(reimbursementService.approveOne(reimbId,resId));

        Reimbursement reimbursement = reimbursementService.getOneById(reimbId);
        this.trySendEmailInformingStatus( ctx, reimbursement );
    };

    private void trySendEmailInformingStatus(Context ctx, Reimbursement reimbursement ){
        try {
            ReimbursementStatusEmail.sendMail(reimbursement);
        }catch(Exception ex){
            ex.printStackTrace();
            ctx.attribute( "feedback", "cannot send email." +
                    " Reason: "+ex.getMessage() );
        }
    }

    /**
     * deny reimbursements as financemanager
     * */
    public void denyReimbursement(Context ctx) throws MessagingException {

        int resId = Integer.parseInt(ctx.pathParam("resid"));
        int reimbId = Integer.parseInt(ctx.pathParam("reimbid"));

        ctx.json(reimbursementService.denyOne(reimbId,resId));

        Reimbursement reimbursement = reimbursementService.getOneById(reimbId);
        this.trySendEmailInformingStatus( ctx, reimbursement );
    };

    /**
     * create reimbursement as employee
     * */
    public void createReimbursement(Context ctx) throws IOException {
        String type = ctx.formParam("type");
        String uid = ctx.formParam("userid");
        String amount = ctx.formParam("amount");
        String description = ctx.formParam("description");
        System.out.println(ctx.formParamMap());
        InputStream is = ctx.uploadedFile("receiptfile").getContent();
        System.out.println(is);

        User user = ctx.sessionAttribute("killua");
        Type t = new Type(Integer.parseInt(type));

        Reimbursement reimbursement = new Reimbursement(Double.parseDouble(amount), description, user,t);

        ByteArrayOutputStream os = null;

        try {
            os = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            while (is.read(buffer) != -1) {
                os.write(buffer);
            }

            reimbursement.setReceipt(os.toByteArray());


        } catch (IOException e) {
            System.out.println("Could not upload file!");
            e.printStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
        }


        reimbursementService.createOne(reimbursement);

        System.out.println(ctx.headerMap());
        ctx.redirect("/employee/dashboard");
    };

}
