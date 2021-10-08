package edu.funix.config;

import com.cksource.ckfinder.config.Config;

public class CustomConfig extends Config {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled = true;
}
