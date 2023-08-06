package Graficador2D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import Definiciones.Point;

public class Trayectoria
{
	private List<Point> t;
	private Camera c;
	
	public Trayectoria( Camera c )
	{
		this.t = new LinkedList<Point>( );
		this.c = c;
	}
	
	public void add( Point p )
	{
		t.add( p );
	}
	
	public void draw( Graphics2D g )
	{
		Point last = null;
		
		if (t.size() > 1)
		{
			for (Point p : t)
			{
				if (last != null)
				{
					g.drawLine(c.adjustX(last.getX()), c.adjustY(last.getY()), c.adjustX(p.getX()), c.adjustY(p.getY()));
				}
				
				last = p;
			}
		}
	}
	
	public void drawColorGradient( Graphics2D g, Color initial, Color end )
	{
		double cr = (end.getRed()   - initial.getRed())   / ((t.size() - 1) * 1.0);
		double cg = (end.getGreen() - initial.getGreen()) / ((t.size() - 1) * 1.0);
		double cb = (end.getBlue()  - initial.getBlue())  / ((t.size() - 1) * 1.0);
		
		Point last = null;
		int i = 1;
		
		if (t.size() > 1)
		{
			for (Point p : t)
			{
				if (last != null)
				{
					g.setColor(
						new Color(
							(int) Math.round(initial.getRed()   + cr * i),
							(int) Math.round(initial.getGreen() + cg * i),
							(int) Math.round(initial.getBlue()  + cb * i)
						)
					);
					g.drawLine(c.adjustX(last.getX()), c.adjustY(last.getY()), c.adjustX(p.getX()), c.adjustY(p.getY()));
					
					i ++;
				}
				
				last = p;
			}
		}
	}
}
