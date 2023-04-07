package me.xiaozhangup.picturelogin;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

public class PictureListeners {

    @Subscribe
    public void on(ServerConnectedEvent event) {
        if (event.getPreviousServer().isEmpty()) {
            PictureLoginJava.pictureUtil.sendImage(event.getPlayer());
        }
    }

}
