package MetodosNumericos;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import Definiciones.Point;
import Graficador2D.Camera;
import Graficador2D.Function;

public class IntegralNumerica
{
	private String	expresion,
					name;
	private double	sX,
					eX,
					f0,
					h = Math.pow(10.0, -3);
	
	private double[]	F;

	
	public IntegralNumerica( Function f, double startX, double endX, int expEpsilon )
	{
		this.sX	= startX;
		this.eX	= endX;
		this.h	= Math.pow(10.0, expEpsilon);
				
		double	x		= startX,
				y		= 0.0,
				minDist = Double.MAX_VALUE;
		int		puntos	= (int) Math.ceil((endX - startX) / h);
		
		F = new double[puntos + 1];
		F[0] = 0.0;
		
		for (int i = 1; i <= puntos; i ++)
		{
			y += f.calcular(x) * h;
			F[i] = y;
			
			if (Math.abs(x) < minDist)
			{
				minDist = Math.abs(x);
				f0 = y;
			}
			
			x += h;
		}
		
		eX = x;
	}
	
	
	public double[] dominio( )
	{
		double[] r = { sX, eX };
		return r;
	}
	
	
	private int calcularIndice( double x )
	{
		if (sX <= x && x <= eX)
		{
			x -= sX;
			return (int) Math.round(x / h);
		}
		
		return -1;
	}
	
	
	public double calcular( double x )
	{
		int i = calcularIndice(x);
		if (i >= 0)
			return F[i];
		
		return Double.NaN;
	}
	
	
	public void drawFunction( Camera c, Graphics2D g, double minX, double maxX )
	{
		int	i = calcularIndice(minX);
		
		if (i < 0)
		{
			i = 0;
			minX = sX;
		}
		
		double	lastX	= 0.0,
				lastY	= 0.0,
				x		= minX,
				y;
		boolean first = true;
		
		for ( ; i < F.length; i ++)
		{
			y = F[i];
			
			if (!first)
			{
				if (x >= minX)
					g.drawLine( c.adjustX(lastX), c.adjustY(lastY - f0), c.adjustX(x), c.adjustY(y - f0));
				
				if (x >= maxX)
					break;
			}
			else
				first = false;
			
			lastX = x;
			lastY = y;
			x += h;
		}
	}
}
