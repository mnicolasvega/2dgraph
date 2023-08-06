package Figuras;

import java.awt.Color;
import java.awt.Graphics2D;

import Graficador2D.Camera;


public class VectorCanonico extends Vector
{	
	private double	x,
					y;
	
	
	public VectorCanonico(double posX, double posY, double x, double y, String name, Color color)
	{
		this.posX		= posX;
		this.posY		= posY;
		this.x			= x;
		this.y			= y;
		this.name		= name;
		this.color		= color;
		this.tip_size	= DEFAULT_ARROW_SIZE;
	}
	
	
	
	public void setX( double x )
	{
		this.x = x;
	}
	
	public double getX( )
	{
		return x;
	}
	
	public void setY( double y )
	{
		this.y = y;
	}
	
	public double getY( )
	{
		return y;
	}
	
	

	public double getMagnitude( )
	{
		return Math.sqrt( x*x + y*y );
	}
	
	public double getArg( )
	{
		return Math.atan2(y, x);
	}
}
