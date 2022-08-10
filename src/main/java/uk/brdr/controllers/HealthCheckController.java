package uk.brdr.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class HealthCheckController {

  public HealthCheckController() {}

  public void ping(Context ctx) {
    ctx.status(HttpCode.OK);
  }
}
