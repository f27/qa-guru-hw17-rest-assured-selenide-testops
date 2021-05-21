package config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.LoadType.MERGE;

@Config.LoadPolicy(MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/driver.properties",
        "classpath:config/remote_driver.properties"
})
public interface DriverConfig extends Config {
    @Key("driver.remote")
    @DefaultValue("false")
    Boolean isRemote();

    @Key("web.browser")
    String webBrowser();

    @Key("web.browser.version")
    String webBrowserVersion();

    @Key("web.browser.size")
    String webBrowserSize();

    @Key("web.browser.mobile.view")
    String webBrowserMobileView();

    @Key("web.remote.driver.url")
    String webRemoteDriverUrl();

    @Key("web.remote.driver.url.format")
    @DefaultValue("https://%s:%s@%s/wd/hub/")
    String webRemoteDriverUrlFormat();

    @Key("web.remote.driver.user")
    String webRemoteDriverUser();

    @Key("web.remote.driver.password")
    String webRemoteDriverPassword();

    @Key("video.enabled")
    @DefaultValue("true")
    Boolean videoEnabled();

    @Key("video.storage.format")
    @DefaultValue("https://%s/video/")
    String videoStorageFormat();
}
