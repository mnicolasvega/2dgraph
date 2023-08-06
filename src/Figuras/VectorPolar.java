package Figuras;

import java.awt.Color;
import java.awt.Graphics2D;

import Graficador2D.Camera;


public class VectorPolar extends Vector
{	
	private double	r,
					theta;
	
	
	public VectorPolar(double posX, double posY, double r, double theta, String name, Color color)
	{
		this.posX		= posX;
		this.posY		= posY;
		this.r			= Math.abs(r);
		this.theta		= theta;
		this.name		= name;
		this.color		= color;
		this.tip_size	= DEFAULT_ARROW_SIZE;
	}
	
	
	
	public void setMagnitude( double r )
	{
		this.r = Math.abs(r);
	}
	
	public void setArg( double theta )
	{
		this.theta = theta;
	}
	

	public double getMagnitude( )
	{
		return r;
	}
	
	public double getArg( )
	{
		return theta;
	}
	
	
	public double getX( )
	{
		return r * Math.cos(theta);
	}
	
	public double getY( )
	{
		return r * Math.sin(theta);
	}
}
