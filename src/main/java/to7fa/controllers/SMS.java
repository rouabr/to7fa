package to7fa.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SMS {
    public static final String ACCOUNT_SID = "ACce88d5d034e3f2e921ca6ca4b82bad62";
    public static final String AUTH_TOKEN = "026fc9807fee168ee216948142b80e20";
    public static final String TWILIO_NUMBER = "+12406522732"; // Votre numéro Twilio

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                        new com.twilio.type.PhoneNumber(TWILIO_NUMBER),
                        messageBody)
                .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
}