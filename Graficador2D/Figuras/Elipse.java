package Figuras;

import java.awt.Color;
import java.awt.Graphics2D;
import Graficador2D.Camera;

public class Elipse
{
	private double	cX,
					cY,
					rX,
					rY;
	private Color	color;
	
	
	public Elipse( double centerX, double centerY, double radiusX, double radiusY, Color color )
	{
		this.cX		= centerX;
		this.cY		= centerY;
		this.rX		= radiusX;
		this.rY		= radiusY;
		this.color	= color;
	}
	
	public void draw( Graphics2D g, Camera c )
	{
		int x = c.adjustX(cX - rX),
			y = c.adjustY(cY + rY);
		
		g.setColor( color );
		g.drawOval( x, y, Math.abs(c.adjustX(cX + rX) - x), Math.abs(c.adjustY(cY - rY) - y));
	}
}
