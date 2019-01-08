package com.millsofmn.securerest.service;

import java.util.Map;

public interface TokenService {
    String pernament(Map<String,String> attributes);

    String expiring(Map<String,String> attributes);

    Map<String, String> untrusted(String token);

    Map<String, String> verify(String token);
}
