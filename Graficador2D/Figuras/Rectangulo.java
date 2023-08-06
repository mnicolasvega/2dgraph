package Figuras;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Graficador2D.Camera;

public class Rectangulo
{
	private final int SIZE = 7;
	
	private double	x,
					y,
					width,
					height;
	private Color	color;
	private String	name;
	
	
	public Rectangulo(double x1, double y1, double x2, double y2, String name, Color color)
	{
		this.x		= Math.min(x1, x2);
		this.y		= Math.max(y1, y2);
		this.name	= name;
		this.width	= Math.abs(x1 - x2);
		this.height	= Math.abs(y1 - y2);
		this.color	= color;
	}
	
	
	public void draw( Graphics2D g, Camera c )
	{
		int cx = c.adjustX(x),
			cy = c.adjustY(y);

		g.setColor(color);
		g.drawRect(
			cx,
			cy,
			Math.abs(c.adjustX(x + width)  - cx),
			Math.abs(c.adjustY(y + height) - cy)
		);
	}
}
