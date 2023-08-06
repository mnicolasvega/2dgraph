package Figuras;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Graficador2D.Camera;

public abstract class Texto
{
	public final static int	ALIGN_LEFT		= 0,
							ALIGN_CENTER	= 1,
							ALIGN_RIGHT		= 2,
							VALIGN_DOWN		= 0,
							VALIGN_CENTER	= 1,
							VALIGN_UP		= 2;
	
	
	protected String		sz;
	protected Font			f;
	protected FontMetrics	fm;
	protected Color			color;
	protected int			align,
							valign;
	protected double		x,
							y;
	
	
	
	public abstract int getScreenX( Camera c );
	public abstract int getScreenY( Camera c );
	
	
	
	public String getText( )
	{
		return sz;
	}
	
	public void setText( String text )
	{
		sz = text;
	}
	
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	
	public void setFont( Font f )
	{
		this.f = f;
	}
	
	
	public void setAlign( int align )
	{
		this.align = align;
	}
	
	public void setVAlign( int valign )
	{
		this.valign = valign;
	}
	
	
	public void setX( double x )
	{
		this.x = x;
	}
	
	public double getX( )
	{
		return this.x;
	}
	
	public void setY( double y )
	{
		this.y = y;
	}
	
	public double getY( )
	{
		return this.y;
	}
	
	
	
	public void draw( Graphics2D g, Camera c )
	{
		g.setColor( color );
		g.setFont( f );
		fm = g.getFontMetrics();
		
		int sX = getScreenX( c ),
			sY = getScreenY( c );
		
		switch (align)
		{
			case ALIGN_LEFT:
			{
				break;
			}
			case ALIGN_CENTER:
			{
				sX -= (int) Math.round(fm.getStringBounds(sz, 0, sz.length(), g).getWidth() / 2.0);
				break;
			}
			case ALIGN_RIGHT:
			{
				sX -= (int) (fm.getStringBounds(sz, 0, sz.length(), g).getWidth());
				break;
			}
		}
		
		switch (valign)
		{
			case VALIGN_DOWN:
			{
				sY += fm.getStringBounds( sz, 0, sz.length(), g).getHeight();
				break;
			}
			case VALIGN_CENTER:
			{
				sY += fm.getStringBounds( sz, 0, sz.length(), g).getHeight() / 2;
			}
			case VALIGN_UP:
			{
				break;
			}
		}
		
		g.drawString(sz, sX, sY);
	}
}
