package utilities;

import models.Reimbursement;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ReimbursementStatusEmail {
    public static void sendMail(Reimbursement reimbursement) throws MessagingException {
        System.out.println("Sending email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "jericrevature@gmail.com";
        String password = "Cindy229!";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, reimbursement);

        Transport.send(message);
        System.out.println("Message Sent Successfully");

    }

    public static Message prepareMessage(Session session, String myAccountEmail, Reimbursement reimbursement){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(reimbursement.getAuthor().getEmail()));
            message.setSubject(reimbursement.getStatus().getValue() + ": Reimbursement ID " + reimbursement.getId());
            message.setText("ID: " + reimbursement.getId() + "\n" +
                    "Amount: " + reimbursement.getAmount() + "\n" +
                    "Description: " + reimbursement.getDescription() + "\n" +
                    "Type: " + reimbursement.getType().getValue() + "\n" +
                    "Resolver: " + reimbursement.getResolver().getUsername() + "\n" +
                    "Date Resolved: " + reimbursement.getDateResolved()+ "\n"
            );
            return message;
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
