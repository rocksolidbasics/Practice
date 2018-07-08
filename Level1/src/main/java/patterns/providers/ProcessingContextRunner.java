package patterns.providers;

import patterns.providers.HTMLProcessingContext.PROVIDER;

public class ProcessingContextRunner {

	public static void main(String[] args) {
		HTMLProcessingContext hpc = new HTMLProcessingContext();
		hpc.addProvider(PROVIDER.LINK_CONFIG_PROVIDER, null);
		hpc.addProvider(PROVIDER.CSS_CONFIG_PROVIDER, null);
		
		hpc.getProvider(PROVIDER.LINK_CONFIG_PROVIDER, new HTMLLinkConfigProvider()).print();
		hpc.getProvider(PROVIDER.CSS_CONFIG_PROVIDER, new HTMLCSSConfigProvider()).print();
	}

}
