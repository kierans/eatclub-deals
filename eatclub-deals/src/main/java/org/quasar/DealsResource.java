package org.quasar;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/deals")
public class DealsResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> hello() {
    return List.of("deal1", "deal2");
  }
}
