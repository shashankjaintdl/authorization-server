package com.commerxo.authserver.authserver.common;

public class WebConstants {

    public static final String API_Version = "/api/v1";

    public static class Account{

        public static final String ACCOUNT = API_Version + "/account";

        public static final String USER_ACCOUNT_REGISTER = "/register";

    }

    public static class Role{

        public static final String ROLE = API_Version + "/role";

        public static final String CREATE_ROLE = "/create";


    }
}
