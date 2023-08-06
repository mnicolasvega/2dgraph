package Figuras;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Graficador2D.Camera;


public class TextoEstatico extends Texto
{
	
	public TextoEstatico( int x, int y, String texto, Font font, int align, Color color )
	{
		this.x		= x;
		this.y		= y;
		this.sz		= texto;
		this.f		= font;
		this.color	= color;
		this.align	= align;
	}
	
	
	public int getScreenX( Camera c )
	{
		return (int) Math.round(x);
	}


	public int getScreenY( Camera c )
	{
		return (int) Math.round(y);
	}

}
