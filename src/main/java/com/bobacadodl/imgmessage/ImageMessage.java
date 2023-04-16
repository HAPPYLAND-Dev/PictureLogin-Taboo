package com.bobacadodl.imgmessage;

import com.bobacadodl.imgmessage.bukkit.ChatColor;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * User: bobacadodl
 * Date: 1/25/14
 * Time: 10:28 PM
 */
public class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';

    private Component[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        ChatColor[][] chatColors = toChatColorArray(image, height);
        lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage appendText(Component... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] = lines[y].append(Component.text(" ")).append(text[y]);
            }
        }
        return this;
    }

    private ChatColor[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (double) image.getHeight() / image.getWidth();
        int width = (int) (height / ratio);
        if (width > 10) width = 10;
        BufferedImage resized = resizeImage(image, width, height);

        ChatColor[][] chatImg = new ChatColor[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int rgb = resized.getRGB(x, y);
                ChatColor closest = getClosestChatColor(new Color(rgb, true));
                chatImg[x][y] = closest;
            }
        }
        return chatImg;
    }

    private Component[] toImgMessage(ChatColor[][] colors, char imgchar) {
        lines = new Component[colors[0].length];
        for (int y = 0; y < colors[0].length; y++) {
            StringBuilder line = new StringBuilder();
            for (ChatColor[] chatColors : colors) {
                ChatColor color = chatColors[y];
                line.append((color != null) ? chatColors[y].toString() + imgchar : TRANSPARENT_CHAR);
            }
            lines[y] = LegacyComponentSerializer.legacySection().deserialize(line.toString() + ChatColor.RESET);
        }
        return lines;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(
                width / (double) originalImage.getWidth(),
                height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private double getDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2.0;
        double r = c1.getRed() - c2.getRed();
        double g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2 + (255 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private boolean areIdentical(Color c1, Color c2) {
        return Math.abs(c1.getRed() - c2.getRed()) <= 5 &&
                Math.abs(c1.getGreen() - c2.getGreen()) <= 5 &&
                Math.abs(c1.getBlue() - c2.getBlue()) <= 5;
    }

    private ChatColor getClosestChatColor(Color color) {
        if (color.getAlpha() < 128) return null;
        return ChatColor.of(color);
    }

    private String center(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        } else if (s.length() == length) {
            return s;
        } else {
            int leftPadding = (length - s.length()) / 2;
            return " ".repeat(leftPadding) + s;
        }
    }

    public Component[] getLines() {
        return lines;
    }

    public void sendToPlayer(Player player) {
        player.sendMessage(Component.empty().appendNewline().appendNewline().appendNewline());
        for (Component line : lines) {
            player.sendMessage(line);
        }
    }
}
