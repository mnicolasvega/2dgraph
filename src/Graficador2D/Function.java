package Graficador2D;

import java.awt.Graphics2D;

//TODO: readd dependency
//import net.objecthunter.exp4j.Expression;
//import net.objecthunter.exp4j.ExpressionBuilder;


public class Function
{
	private String	expresion,
					name;
	//private Expression f;
	
	
	public Function( String name, String f )
	{
		this.name		= name;
		this.expresion	= f;
		
		//ExpressionBuilder EB = new ExpressionBuilder( f );
		//EB.variable( "x" );
		//this.f = EB.build();
	}
	
	
	public double calcular( double x )
	{
		//f.setVariable("x", x);
		//return f.evaluate();
		return 0.1 * (x + 2.5) * (x + 1) * x * (x - 1.5) * (x - 2);
	}
	
	
	public void drawFunction( Camera c, Graphics2D g, int puntos )
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
			y = calcular(x);
			
			if (i > 0)
			{
				g.drawLine( c.adjustX(lastX), c.adjustY(lastY), c.adjustX(x), c.adjustY(y));
			}
			
			lastX = x;
			lastY = y;
			
			x += espaciado;
		}
	}
	
	public void drawFunction( Camera c, Graphics2D g, double espaciado )
	{
		double	startX	= c.getMinX(),
				endX	= c.getMaxX(),
				x		= startX,
				y		= 0,
				lastX	= 0,
				lastY	= 0;
		int		puntos	= (int) Math.ceil((endX - startX) / espaciado);
				
		for (int i = 0; i < puntos; i ++)
		{
			y = calcular(x);
			
			//System.out.printf("(%.2f, %.2f)\n", x, y);
			
			if (i > 0)
			{
				g.drawLine( c.adjustX(lastX), c.adjustY(lastY), c.adjustX(x), c.adjustY(y));
			}
			
			lastX = x;
			lastY = y;
			
			x += espaciado;
		}
	}
}
