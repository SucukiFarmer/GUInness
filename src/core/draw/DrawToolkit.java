package core.draw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import core.feature.image.ImageToolkit;
import core.gui.font.Font;
import core.gui.font.FontLoader;

public class DrawToolkit
{
	private static FontLoader fontLoader = new FontLoader();
	
	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	public static void drawChar(Graphics g, char letter, int xPos, int yPos, Font font)
	{
		int index = fontLoader.getSymbolIndex(letter);

		int dim = 30;

		if(fontLoader.isImplemented(letter) && index > -1)
		{
			int x = (index - 1) * 30 + index, y = 1;

			BufferedImage img = font.getImage().getSubimage(x, y, dim, dim);

			Image colorized = ImageToolkit.colorize(img, font.getFontColor()).getScaledInstance(font.getFontSize(), font.getFontSize(),
					Image.SCALE_SMOOTH);

			g.drawImage(colorized, xPos, yPos, null);
		}
		else
		{
			int indexSymbolNotFound = fontLoader.getDigitIndex('0');

			int xSymbolNotFound = (indexSymbolNotFound - 1) * 30 + indexSymbolNotFound, ySymbolNotFound = 1;

			BufferedImage img = font.getImage().getSubimage(xSymbolNotFound, ySymbolNotFound, dim, dim);

			Image colorized = ImageToolkit.colorize(img, font.getFontColor()).getScaledInstance(font.getFontSize(), font.getFontSize(),
					Image.SCALE_SMOOTH);

			g.drawImage(colorized, xPos, yPos, null);
		}
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified font size.
	public static Dimension drawString(Graphics g, String text, int xPos, int yPos, Font font)
	{
		char[] converted = text.toCharArray();

		int offset = 0;

		for (char c : converted)
		{
			drawChar(g, c, xPos + font.getFontSize() * offset, yPos, font);

			offset++;
		}

		return new Dimension(font.getFontSize() * text.length(), font.getFontSize());
	}

	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	// Uses the specified scale relative to the fonts original size (30px).
	public static void drawChar(Graphics g, char letter, int xPos, int yPos, float scale)
	{
		int dim = 30, scaled = (int) (dim * scale);

		drawChar(g, letter, xPos, yPos, scaled);
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified value.
	public static Dimension drawString(Graphics g, String text, int xPos, int yPos, float scale)
	{
		char[] converted = text.toCharArray();

		int dim = 30, scaled = (int) (dim * scale);

		int offset = 0;

		for (char c : converted)
		{
			drawChar(g, c, xPos + scaled * offset, yPos, scale);

			offset++;
		}

		return new Dimension((int) (scale * dim) * text.length(), (int) (scale * dim));
	}
}