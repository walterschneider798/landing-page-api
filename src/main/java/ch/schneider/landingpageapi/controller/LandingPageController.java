package ch.schneider.landingpageapi.controller;

import ch.schneider.landingpageapi.model.LandingPageModel;
import ch.schneider.landingpageapi.model.WebsiteOwner;
import ch.schneider.landingpageapi.service.LandingPageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin
public class LandingPageController {

  private final LandingPageService landingPageService;

  public LandingPageController(LandingPageService landingPageService) {
    this.landingPageService = landingPageService;
  }

  @PostMapping(value = "/form", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void form(@RequestBody LandingPageModel landingPageModel, @RequestParam String accessToken, HttpServletResponse response) {
    boolean result = landingPageService.formEmail(landingPageModel, accessToken);
    response.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
  }

  @PostMapping(value = "/landing", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void landingPage(@RequestBody LandingPageModel landingPage, @RequestParam String accessToken, HttpServletResponse response) {
    boolean result = landingPageService.landingPageMessage(landingPage, accessToken);
    response.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
  }

  @PostMapping(value = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String addWebsiteOwner(@RequestBody WebsiteOwner websiteOwner) {
    String accessToken = landingPageService.generateAccessToken();
    landingPageService.addWebsiteOwner(websiteOwner, accessToken);
    return accessToken;
  }

  @GetMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<String> getKeys() {
    return landingPageService.getKeys();
  }
}
