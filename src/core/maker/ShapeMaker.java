package core.maker;

import java.awt.Rectangle;
import java.awt.Shape;

public class ShapeMaker
{
	public static Shape createRectangle(int x, int y, int width, int height)
	{
		return (Shape) new Rectangle(x, y, width, height);
	}
}
