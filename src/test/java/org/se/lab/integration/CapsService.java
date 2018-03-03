package org.se.lab.integration;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CapsService {
  
    @Inject
    private CapsConverter capsConverter;
     
    public String getConvertedCaps(final String word){
        return capsConverter.getLowerCase().convert(word);
    }
}
