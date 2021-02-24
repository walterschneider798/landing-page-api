package ch.schneider.landingpageapi.util;

import ch.schneider.landingpageapi.model.LandingPageModel;
import ch.schneider.landingpageapi.model.WebsiteOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailHandler {

  @Autowired
  private JavaMailSender emailSender;

  private static final String SUBJECT_LANDING_PAGE_OWNER = "Neuer Eintrag";
  private static final String SUBJECT_LANDING_PAGE_CUSTOMER = "Vielen Dank für deine Eintragung";
  private static final String SUBJECT_FORM_CUSTOMER = "Vielen Dank für deine Rückmeldung!";
  private static final String SUBJECT_FORM_OWNER = "Kontakt Formular";

  public void formSendOwner(LandingPageModel landingPageModel, String to, String website) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(SUBJECT_FORM_OWNER);
    message.setText("Website: " + website + "\nFrom: " + landingPageModel.getEmail() + "\n" + landingPageModel.getMessage());
    message.setTo(to);
    emailSender.send(message);
  }

  public void formSendCustomer(WebsiteOwner websiteOwner, String to) {
    MimeMessagePreparator mailMessage = mimeMessage -> {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setFrom(websiteOwner.getEmail(), websiteOwner.getName());
      message.setSubject(SUBJECT_FORM_CUSTOMER);
      message.setText(
          "Guten Tag");
      message.setTo(to);
    };
    emailSender.send(mailMessage);
  }

  public void landingPageSendCustomer(WebsiteOwner websiteOwner, String to) {
    MimeMessagePreparator mailMessage = mimeMessage -> {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setFrom(websiteOwner.getEmail(), websiteOwner.getName());
      message.setSubject(SUBJECT_LANDING_PAGE_CUSTOMER);
      message.setText(
          "Hey, grüß dich!\n" +
              "Vielen Dank dir für deine Eintragung zu einem kostenlosen Beratungsgespräch mit uns! \n" +
              "Wie es nun für dich weitergeht:\n" +
              "In den folgenden 1-2 Tagen wird dich " + websiteOwner.getName() +" oder jemand aus unserem Team unter deiner Nummer anrufen," +
              " um mit dir einen Termin für das kostenlose Beratungsgespräch zu vereinbaren und dich ein wenig kennenzulernen, " +
              "damit wir dir optimal helfen können.\n" +
              "Bis dahin wünschen wir dir alles Gute und freuen uns auf das Gespräch mit dir!");
      message.setTo(to);
    };

    emailSender.send(mailMessage);
  }

  public void landingPageSendOwner(LandingPageModel landingPageModel, String to, String website) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(SUBJECT_LANDING_PAGE_OWNER);
    message.setText("Website: " + website + "\nNummer: " + landingPageModel.getPhone()
        + "\nE-Mail: " + landingPageModel.getEmail() + "\nName: " + landingPageModel.getName());
    message.setTo(to);
    emailSender.send(message);
  }
}
