package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import javax.mail.MessagingException;
import uk.brdr.handlers.JwtCookieHandler;
import uk.brdr.model.Mail;
import uk.brdr.services.MailService;

public class AdminController {

  private final MailService mailService;
  private final JwtCookieHandler jwtCookieHandler;

  public AdminController(MailService mailService, JwtCookieHandler jwtCookieHandler) {
    this.mailService = mailService;
    this.jwtCookieHandler = jwtCookieHandler;
  }

  public void sendMessage(Context ctx) {
    var decodedJwt = jwtCookieHandler.verifyJwtTokenFromContext(ctx);
    try {
      var mail = ctx.bodyAsClass(Mail.class);
      // TODO: use jwt to get userId, and then add email to user.
      mailService.sendMessage(mail);
      ctx.status(HttpCode.OK);
    } catch (MessagingException e) {
      ctx.status(HttpCode.SERVICE_UNAVAILABLE);
    }
  }
}
