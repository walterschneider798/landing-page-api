package ch.schneider.landingpageapi.service;

import ch.schneider.landingpageapi.model.WebsiteOwner;
import ch.schneider.landingpageapi.model.LandingPageModel;
import ch.schneider.landingpageapi.util.EmailHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LandingPageServiceImp implements LandingPageService {

  private final RedisTemplate<String, WebsiteOwner> redisTemplate;

  private final EmailHandler emailHandler;

  public LandingPageServiceImp(RedisTemplate<String, WebsiteOwner> redisTemplate, EmailHandler emailHandler) {
    this.redisTemplate = redisTemplate;
    this.emailHandler = emailHandler;
  }

  @Override
  public String generateAccessToken() {
    return RandomStringUtils.randomAlphanumeric(30);
  }

  @Override
  public void addWebsiteOwner(WebsiteOwner websiteOwner, String accessToken) {
    this.redisTemplate.opsForValue().set(accessToken, websiteOwner);
  }

  @Override
  public boolean formEmail(LandingPageModel landingPageModel, String accessKey) {
    WebsiteOwner websiteOwner = this.redisTemplate.opsForValue().get(accessKey);
    if (websiteOwner != null) {
      emailHandler.formSendOwner(landingPageModel, websiteOwner.getEmail(), websiteOwner.getWebsite());
      emailHandler.formSendCustomer(websiteOwner, landingPageModel.getEmail());
      return true;
    }
    return false;
  }

  @Override
  public boolean landingPageMessage(LandingPageModel landingPageModel, String accessKey) {
    WebsiteOwner websiteOwner = this.redisTemplate.opsForValue().get(accessKey);
    if (websiteOwner != null) {
      emailHandler.landingPageSendCustomer(websiteOwner, landingPageModel.getEmail());
      emailHandler.landingPageSendOwner(landingPageModel, websiteOwner.getEmail(), websiteOwner.getWebsite());
      return true;
    }
    return false;
  }

  @Override
  public List<String> getKeys() {
    Set<String> redisKeys = this.redisTemplate.keys("*");
    return new ArrayList<>(Objects.requireNonNull(redisKeys));
  }
}
