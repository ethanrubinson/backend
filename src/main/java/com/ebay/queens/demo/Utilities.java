package com.ebay.queens.demo;


/**
 * Represents a class to manage application information and promote code reusability
 */
public class Utilities {
  String securityAppName;

  String globalId;

  String devName;

  String certName;

  String marktplaceId;

  String paypalAuthorizationToken;

  String ebayAuth;

  final int SITE_ID = 3;

  Utilities(String devName, String securityAppName, String globalId, String marketplaceId, String certName,
      String paypalAuth, String ebayAuth) {
    this.devName = devName;
    this.securityAppName = securityAppName;
    this.globalId = globalId;
    this.certName = certName;
    this.marktplaceId = marketplaceId;
    this.paypalAuthorizationToken = paypalAuth;
    this.ebayAuth = ebayAuth;
  }

  Utilities() {

  }

  // Getters 
  public String getDevName() {
    return devName;
  }

  public String getGlobalId() {
    return globalId;
  }

  public String getSecurityAppName() {
    return securityAppName;
  }

  public String getCertName() {
    return certName;
  }

  public String getMarktplaceId() {
    return marktplaceId;
  }

  public String getPaypalAuthorizationToken() {
    return paypalAuthorizationToken;
  }

  public String getEbayAuth() {
    return ebayAuth;
  }

  // Setters
  public void setDevName(String devName) {
    this.devName = devName;
    System.out.println("Utilities Dev name Setter: " + this.getDevName());
  }

  public void setSecurityAppName(String securityAppName) {
    this.securityAppName = securityAppName;
  }

  public void setGlobalId(String globalId) {
    this.globalId = globalId;
    System.out.println("Utilities global id name Setter: " + this.getGlobalId());
  }

  public void setCertName(String certName) {
    this.certName = certName;
    System.out.println("Utilities cert name Setter: " + this.getCertName());
  }

  public void setMarktplaceId(String marktplaceId) {
    this.marktplaceId = marktplaceId;
  }

  public void setPaypalAuthorizationToken(String paypalAuthorizationToken) {
    this.paypalAuthorizationToken = paypalAuthorizationToken;
  }

  public void setEbayAuth(String ebayAuth) {
    this.ebayAuth = ebayAuth;
  }

}
