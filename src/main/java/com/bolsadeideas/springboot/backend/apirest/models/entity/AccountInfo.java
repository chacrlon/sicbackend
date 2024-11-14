package com.bolsadeideas.springboot.backend.apirest.models.entity;
import com.fasterxml.jackson.annotation.JsonProperty;  

public class AccountInfo {  
    @JsonProperty("makerCommission")  
    public int makerCommission;  
    @JsonProperty("takerCommission")  
    public int takerCommission;  
    @JsonProperty("buyerCommission")  
    public int buyerCommission;  
    @JsonProperty("sellerCommission")  
    public int sellerCommission;  
    @JsonProperty("canTrade")  
    public boolean canTrade;  
    @JsonProperty("canWithdraw")  
    public boolean canWithdraw;  
    @JsonProperty("canDeposit")  
    public boolean canDeposit;  
    @JsonProperty("updateTime")  
    public long updateTime;  
    @JsonProperty("accountType")  
    public String accountType;  
    @JsonProperty("balances")  
    public Balance[] balances;  


    public static class Balance {  
        @JsonProperty("asset")  
        public String asset;  
        @JsonProperty("free")  
        public String free;  
        @JsonProperty("locked")  
        public String locked;  
    }  
}