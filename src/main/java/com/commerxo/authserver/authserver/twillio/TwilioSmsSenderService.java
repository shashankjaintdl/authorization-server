package com.commerxo.authserver.authserver.twillio;

import com.twilio.rest.api.v2010.account.Message;

public interface TwilioSmsSenderService {

    Message sendSms(SmsRequest smsRequest);
}
