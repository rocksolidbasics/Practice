package patterns.providers;

import java.util.HashMap;

public class HTMLProcessingContext {
	
	public enum PROVIDER {LINK_CONFIG_PROVIDER, CSS_CONFIG_PROVIDER};
	
	private HashMap<PROVIDER, Provider_I> m_Providers = new HashMap<PROVIDER, Provider_I>();
	
	public <T> T getProvider(PROVIDER providerType, T clazz) {
		return (T)m_Providers.get(providerType);
	}
	
	public void addProvider(PROVIDER providerType, Provider_I provider) {
		Provider_I aProvider = null;
		if(providerType == PROVIDER.CSS_CONFIG_PROVIDER)
			aProvider = new HTMLCSSConfigProvider("CSS 4");
		else
			aProvider = new HTMLLinkConfigProvider("Link 2");
		
		m_Providers.put(providerType, aProvider);
	}
	
}
