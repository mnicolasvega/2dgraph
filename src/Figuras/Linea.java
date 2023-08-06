package Figuras;

import java.awt.Color;
import java.awt.Graphics2D;

import Graficador2D.Camera;

public class Linea
{
	private final int SIZE = 7;
	
	private double	m,
					x0;
	private Color	color;
	private String	name;
	
	
	public Linea(double m, double x0, String name, Color color)
	{
		this.m		= m;
		this.x0		= x0;
		this.name	= name;
		this.color	= color;
	}

	public Linea(double m, double x, double y, String name, Color color)
	{
		this.m		= m;
		this.x0		= y - m*x;
		this.name	= name;
		this.color	= color;
	}
	
	public Linea(double x1, double y1, double x2, double y2, String name, Color color)
	{
		this.m		= (y2 - y1) / (x2 - x1);
		this.x0		= y1 - m*x1;
		this.name	= name;
		this.color	= color;
	}
	
	
	
	public double calcular( double x )
	{
		return (x * m) + x0;
	}
	
	
	public void draw( Graphics2D g, Camera c )
	{
		double	sX = c.getMinX(),
				eX = c.getMaxX();
		
		//if (calcular(sX) || calcular(eX))
		//{
			g.setColor(color);
			g.drawLine(c.adjustX(sX), c.adjustY(calcular(sX)), c.adjustX(eX), c.adjustY(calcular(eX)));
		//}
	}
}
