package com.commerxo.authserver.authserver.twillio;

import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSenderServiceImpl implements TwilioSmsSenderService{

    private final TwilioConfiguration twilioConfiguration;

    public TwilioSmsSenderServiceImpl(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public Message sendSms(SmsRequest smsRequest) {
        try {
            if (isPhoneNoValid(smsRequest.getPhoneNo())) {
                Message.creator(
                        new PhoneNumber(smsRequest.getPhoneNo()), new PhoneNumber(twilioConfiguration.getPhoneNo()), smsRequest.getMessage()
                ).create();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Boolean isPhoneNoValid(String phoneNo) {
        phoneNo = phoneNo.replaceAll("[\\s()-]", "");
        if ("".equals(phoneNo)) {
            return false;
        }
        try {
            com.twilio.rest.lookups.v1.PhoneNumber.fetcher(String.valueOf(new PhoneNumber(phoneNo))).fetch();
            return true;
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
//            LOGGER.info("Phone number is not valid {}", phoneNo);
            throw new IllegalArgumentException("Phone number is not valid!");
        }
    }
}
