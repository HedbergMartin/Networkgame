package rocket.src;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rocket.main.Rocket;

public class SpriteSheet {

	public static BufferedImage getBufferedImage(String path, int x, int y, int width, int height) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Rocket.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getSubimage(x, y, width, height);
	}
}
