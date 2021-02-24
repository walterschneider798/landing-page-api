package ch.schneider.landingpageapi.service;

import ch.schneider.landingpageapi.model.WebsiteOwner;
import ch.schneider.landingpageapi.model.LandingPageModel;

import java.util.List;

public interface LandingPageService {
    String generateAccessToken();

    void addWebsiteOwner(WebsiteOwner websiteOwner, String accessToken);

    boolean formEmail(LandingPageModel landingPageModel, String accessKey);

    boolean landingPageMessage(LandingPageModel landingPage, String accessKey);

    List<String> getKeys();
}
