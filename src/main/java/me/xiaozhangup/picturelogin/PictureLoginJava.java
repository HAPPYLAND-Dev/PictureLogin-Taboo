package me.xiaozhangup.picturelogin;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class PictureLoginJava {
    public static final String api = "https://minepic.org/avatar/8/%uuid%";
    public static PictureUtil pictureUtil;
    public static Object plugin;
    public static List<String> message = List.of("HAPPYLAND", "Please set this in config file!");
    public static ProxyServer server;
    public static Logger logger;
    public static Path dataDirectory;

    public PictureLoginJava(ProxyServer server, Logger logger, Path dataDirector) {
        PictureLoginJava.server = server;
        PictureLoginJava.logger = logger;
        dataDirectory = dataDirector;
    }

    public void onEnable() {
        pictureUtil = new PictureUtil();
        plugin = PictureLogin.getPlugin();
        message = PictureLogin.getConfig().getStringList("messages");

        server.getEventManager().register(plugin, new PictureListeners());
    }
}
