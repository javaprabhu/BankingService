package com.example.demo.allService;


public enum ServiceEnum {
	PASSBOOKUPDATE("passbookUpdate","PU"),CASHDEPOSIT("cashDeposit","CD"),DEMANDDRAFT("demandDraft","DD");
	
	private String name;
	private String value;
	private ServiceEnum(String name, String value) {
		this.name=name;
		this.value=value;
	}
	
	public static ServiceEnum getEnumByName(String serviceName) {
		for(ServiceEnum s : ServiceEnum.values()) {
			if(s.name.equals(serviceName)) {
				return s;
			}
		}
		return null;
	}
	
	public static ServiceEnum getEnumByCode(String serviceCode) {
		for(ServiceEnum s : ServiceEnum.values()) {
			if(s.value.equals(serviceCode)) {
				return s;
			}
		}
		return null;
	}

	public int token;

	public String getToken() {
		return value+"-"+(++token);
	}
	public static void resetValue() {
		for(ServiceEnum s : ServiceEnum.values()) {
			s.token=0;
		}
	}
}
