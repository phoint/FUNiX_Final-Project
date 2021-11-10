package edu.funix.model;

public class OptionModel extends AbstractModel<OptionModel> {
    private String logoPath;
    private String bannerPath;

    public OptionModel() {
    }

    public String getLogoPath() {
	return logoPath;
    }

    public void setLogoPath(String logoPath) {
	this.logoPath = logoPath;
    }

    public String getBannerPath() {
	return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
	this.bannerPath = bannerPath;
    }

    @Override
    public String toString() {
	return "OptionModel [logoPath=" + logoPath + ", bannerPath=" + bannerPath + ", toString()=" + super.toString()
		+ "]";
    }
}
