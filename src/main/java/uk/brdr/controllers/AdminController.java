package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import javax.mail.MessagingException;
import uk.brdr.model.Mail;
import uk.brdr.services.MailService;

public class AdminController {

  private final MailService mailService;

  public AdminController(MailService mailService) {
    this.mailService = mailService;
  }

  public void sendMessage(Context ctx) {
    var mail = ctx.bodyAsClass(Mail.class);
    try {
      mailService.sendMessage(mail.getUsername(), mail.getBody());
      ctx.status(HttpCode.OK);
    } catch (MessagingException e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }

}
