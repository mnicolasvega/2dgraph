package MetodosNumericos;

import java.awt.Graphics2D;

import Graficador2D.Camera;
import Graficador2D.Function;

public class DerivadaNumerica
{
	private String	expresion,
					name;

	
	public DerivadaNumerica( )
	{
	}
	
	public double calcular( Function f, double x, int hExp )
	{
		return ( f.calcular(x + Math.pow(10.0, hExp)) - f.calcular(x) ) * Math.pow(10.0, -hExp);
	}
	
	
	public void drawFunction( Camera c, Graphics2D g, Function f, int puntos )
	{
		double	startX		= c.getMinX(),
				endX		= c.getMaxX(),
				x			= startX,
				y			= 0,
				lastX		= 0,
				lastY		= 0,
				espaciado	= (endX - startX) / puntos;
		
		for (int i = 0; i < puntos; i ++)
		{
			y = calcular(f, x, -6);
			
			if (i > 0)
			{
				g.drawLine( c.adjustX(lastX), c.adjustY(lastY), c.adjustX(x), c.adjustY(y));
			}
			
			lastX = x;
			lastY = y;
			
			x += espaciado;
		}
	}
	
	public void drawFunction( Camera c, Graphics2D g, Function f, double espaciado )
	{
		double	startX	= c.getMinX(),
				endX	= c.getMaxX(),
				x		= startX,
				y		= 0,
				lastX	= 0,
				lastY	= 0;
		int		puntos	= (int) Math.ceil((endX - startX) / espaciado);
		boolean	jump	= true;
				
		for (int i = 0; i < puntos; i ++)
		{
			y = calcular(f, x, -3);
			
			//System.out.printf("(%.2f, %.2f)\n", x, y);
			
			if (!jump)
			{
				if (Math.abs(c.adjustY(lastY) - c.adjustY(y)) >= 100.0)
					jump = true;
				else
					g.drawLine( c.adjustX(lastX), c.adjustY(lastY), c.adjustX(x), c.adjustY(y));
			}
			else
				jump = false;
			
			lastX = x;
			lastY = y;
			
			x += espaciado;
		}
	}
}
