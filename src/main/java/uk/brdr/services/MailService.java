package uk.brdr.services;

import io.javalin.http.BadRequestResponse;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.brdr.data.dao.UserDao;
import uk.brdr.properties.MailServiceProperties;

public class MailService {

  private final Logger logger = LoggerFactory.getLogger(MailService.class);
  private final Session session;
  private final UserDao userDao;

  public MailService(MailServiceProperties mailServiceProperties, UserDao userDao) {
    this.session =
        Session.getInstance(
            mailServiceProperties.getProperties(),
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    mailServiceProperties.getUsername(), mailServiceProperties.getPassword());
              }
            });
    this.userDao = userDao;
  }

  public void sendMessage(String username, String type, String body) throws MessagingException {
    var user = userDao.findByUsername(username);
    if (user.isEmpty()) {
      throw new BadRequestResponse();
    }
    var message = new MimeMessage(session);
    message.setFrom(new InternetAddress(user.get().getEmail()));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("admin@brdr.uk"));
    message.setSubject(String.format("brdr feedback: %s", type));

    MimeBodyPart mimeBodyPart = new MimeBodyPart();
    mimeBodyPart.setContent(body, "text/html; charset=utf-8");
    var multipart = new MimeMultipart();
    multipart.addBodyPart(mimeBodyPart);
    message.setContent(multipart);

    logger.info("sending message {}", message);
    Transport.send(message);
  }
}
