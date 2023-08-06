package Figuras;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import Graficador2D.Camera;

public abstract class Vector
{
	protected final int	DEFAULT_ARROW_SIZE = 10;
	protected double	posX,
						posY;
	protected Color		color;
	protected String	name;
	protected int		tip_size;
	

	
	public abstract double getX( );
	public abstract double getY( );
	public abstract double getMagnitude( );
	public abstract double getArg( );
	
	
	
	public void setPosX( double x )
	{
		posX = x;
	}
	
	public double getPosX( )
	{
		return posX;
	}
	
	
	public void setPosY( double y )
	{
		posY = y;
	}
	
	public double getPosY( )
	{
		return posY;
	}
	
	
	
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	public void setName( String name )
	{
		this.name = name;
	}
	
	public String getName( )
	{
		return name;
	}
	
	public void setTipSize( int size )
	{
		tip_size = size;
	}
	

	
	protected int[] inScreen( Camera c )
	{
		int[] r = new int[4];
		r[0] = c.adjustX( posX );
		r[1] = c.adjustY( posY );
		r[2] = c.adjustX( posX + getX() );
		r[3] = c.adjustY( posY + getY() );
		
		return r;
	}
	
	
	protected void drawVectorTip( Graphics2D g, int endX, int endY, double angle )
	{
		Polygon p;
		
		int[] arrowX, arrowY;
		arrowX = new int[3];
		arrowY = new int[3];
		
		double	vecX = Math.cos(angle),
				vecY = Math.sin(angle),
				vecPX = Math.cos(angle + Math.PI / 2),
				vecPY = Math.sin(angle + Math.PI / 2);
		
		arrowX[0] = endX;
		arrowY[0] = endY;
		
		arrowX[1] = (int) Math.round( endX - (tip_size * vecX) + (vecPX * (tip_size / 2.0)) );
		arrowY[1] = (int) Math.round( endY + (tip_size * vecY) - (vecPY * (tip_size / 2.0)) );
		
		arrowX[2] = (int) Math.round( endX - (tip_size * vecX) - (vecPX * (tip_size / 2.0)) );
		arrowY[2] = (int) Math.round( endY + (tip_size * vecY) + (vecPY * (tip_size / 2.0)) );
		
		p = new Polygon(arrowX, arrowY, 3);
		
		g.fillPolygon( p );
	}
	
	
	public void draw( Graphics2D g, Camera c )
	{
		int[] v = inScreen( c );

		//if (c.inBounds(v[0], v[1]) || c.inBounds(v[2], v[3]))
		//{
			g.setColor( color );
			g.drawLine( v[0], v[1], v[2], v[3] );
			drawVectorTip( g, v[2], v[3], getArg( ) );
		//}
	}

}
