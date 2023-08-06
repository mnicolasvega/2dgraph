package Figuras;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Graficador2D.Camera;

public class Punto
{
	private final int SIZE = 7;
	
	private double	x,
					y;
	private Color	color;
	private String	name;
	private Texto	txt;
	
	
	public Punto(double x, double y, String name, Color color)
	{
		this.x		= x;
		this.y		= y;
		this.name	= name;
		this.color	= color;
		this.txt	= new TextoMovible( x, y, name, new Font("Ubuntu", Font.PLAIN, 14), Texto.ALIGN_CENTER, color );
	}
	
	
	public void draw( Graphics2D g, Camera c )
	{
		if (c.inBounds(x, y))
		{
			g.setColor(color);
			g.fillOval(c.adjustX(x) - SIZE / 2, c.adjustY(y) - SIZE / 2, SIZE, SIZE);
			txt.draw(g, c);
		}
	}
}
