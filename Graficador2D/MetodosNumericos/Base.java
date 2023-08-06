package MetodosNumericos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import Figuras.Linea;
import Graficador2D.Camera;

public class Base
{
	public static final double[][] CANONICA = { { 1.0, 0.0 },	// v1
												{ 0.0, 1.0 } }; // v2
	
	private static final double	TOLERANCIA_DET	= Math.pow(10.0, -3);
	private static final double	TOLERANCIA_ZERO	= Math.pow(10.0, -6);
	
	private double[][]	base;
	private double		Axspacing,
						Bxspacing,
						Ayspacing,
						Byspacing;
	private Color		color;
	
	
	public Base( double[][] base )
	{
		this.base = base;
		
		initializeVariables( );
	}
	
	public Base( double[] v1, double[] v2 )
	{		
		this.base = new double[2][2];
		this.base[0][0] = v1[0];
		this.base[1][0] = v1[1];
		this.base[0][1] = v2[0];
		this.base[1][1] = v2[1];
		
		initializeVariables( );
	}
	
	private void initializeVariables( )
	{
		color = Color.CYAN;
		
		if (Math.abs(base[0][0]) < TOLERANCIA_ZERO) base[0][0] = 0.0;
		if (Math.abs(base[0][1]) < TOLERANCIA_ZERO) base[0][1] = 0.0;
		if (Math.abs(base[1][0]) < TOLERANCIA_ZERO) base[1][0] = 0.0;
		if (Math.abs(base[1][1]) < TOLERANCIA_ZERO) base[1][1] = 0.0;
		
		double	a = base[0][0],
				b = base[1][0],
				c = base[0][1],
				d = base[1][1];

		double	mA = getXSlope(),
				mB = getYSlope();
		
		boolean aValid = false,
				bValid = false;
		
		// intersección con Y, x=0:
		//	y = m(x - x0) + y0
		//  y = -m*x0 + y0
		
		// intersección con X: x / L(x) = 0
		//	m(x - x0) + y0 = 0  <=>  x - x0 = -y0/m
		//	x = -y0/m + x0 
		
		
		// Pendientes válidas
		if (mA != 0.0 && mA != Double.POSITIVE_INFINITY)
		{
			Bxspacing = Math.abs( d * (-1.0 / mA) + c );
			Byspacing = Math.abs( c * -mA + d );
			
			aValid = true;
		}
		
		if (mB != 0.0 && mB != Double.POSITIVE_INFINITY)
		{
			Axspacing = Math.abs( b * (-1.0 / mB) + a );
			Ayspacing = Math.abs( a * -mB + b );
			
			bValid = true;
		}
		
		
		// A trazando horizontales sobre eje Y
		if (mA == 0.0)
		{
			Ayspacing = Math.abs(d);
			Bxspacing = Math.abs(a);
		}
		// A trazando verticales sobre eje X
		else if (mA == Double.POSITIVE_INFINITY)
		{
			Axspacing = Math.abs(c);
		}
		

		// B trazando horizontales sobre eje Y
		if (mB == 0.0)
		{
			Byspacing = Math.abs(b);
			Axspacing = Math.abs(c);
		}
		// B trazando verticales sobre eje X
		else if (mB == Double.POSITIVE_INFINITY)
		{
			Bxspacing = Math.abs(a);
			
			if (aValid)
			{
				Axspacing = Math.abs(a);
				Ayspacing = Math.abs(d);
			}
		}
		
		
		/*if (base[0][0] != 0)
			xspacing = Math.abs( -(base[1][0] / base[0][0]) * base[0][1] + base[1][1] );
		else
			xspacing = Math.abs( base[0][1] );
		
		if (base[1][1] != 0)
			yspacing = Math.abs( -(base[0][1] / base[1][1]) * base[1][0] + base[0][0] );
		else
			yspacing = Math.abs( base[1][0] );*/
	}
	
	
	
	public Color getColor( )
	{
		return color;
	}
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	
	
	public double[] getV1( )
	{
		double[] v = { base[0][0], base[1][0] };
		return v;
	}
	
	public double[] getV2( )
	{
		double[] v = { base[0][1], base[1][1] };
		return v;
	}
	
	public Polygon detPolygon( Camera c )
	{
		int[] x = { c.adjustX(0.0), c.adjustX(base[0][0]), c.adjustX(base[0][0] + base[0][1]), c.adjustX(base[0][1]) };
		int[] y = { c.adjustY(0.0), c.adjustY(base[1][0]), c.adjustY(base[1][0] + base[1][1]), c.adjustY(base[1][1]) };
		Polygon p = new Polygon(x, y, 4);
		return p;
	}
	
	
	
	public int dimension( )
	{
		int dim;
						// a				b
		boolean v1null = (base[0][0] == 0 && base[1][0] == 0),
						// c				d
				v2null = (base[0][1] == 0 && base[1][1] == 0);
		
		if (v1null && v2null)
			dim = 0;
		else if (v1null || v2null)
			dim = 1;
		else
		{
			if (Math.abs( det() ) <= TOLERANCIA_DET)
				dim = 1;
			else
				dim = 2;
		}
		
		return dim;
	}
	
	public double det( )
	{
		return (base[0][0] * base[1][1]) - (base[1][0] * base[0][1]);
	}
	
	
	public double getAXSpacing( )
	{
		return Axspacing;
	}
	
	public double getBXSpacing( )
	{
		return Bxspacing;
	}
	
	public double getAYSpacing( )
	{
		return Ayspacing;
	}
	
	public double getBYSpacing( )
	{
		return Byspacing;
	}
	
	/**
	 * @return - Double.NaN: si no existe pendiente.<br />
	 * - Double.POSITIVE_INFINITY: si es de la forma x = a<br />
	 * - 0: si es de la forma y = a<br />
	 * - otro: si hay una pendiente "común"
	 */
	public double getXSlope( )
	{
		double m;

		boolean v1null = base[0][0] == 0.0,
				v2null = base[1][0] == 0.0;

		// Casos particulares (cae sobre ejes)
		if (v1null || v2null)
		{
			if (v1null && v2null)
				m = Double.NaN;
			else if (v1null)
				m = Double.POSITIVE_INFINITY;
			else // v2null
				m = 0;
		}
		else
			m = base[1][0] / base[0][0];
		
		return m;
	}
	
	/**
	 * @return - Double.NaN: si no existe pendiente.<br />
	 * - Double.POSITIVE_INFINITY: si es de la forma x = a<br />
	 * - 0: si es de la forma y = a<br />
	 * - otro: si hay una pendiente "común"
	 */
	public double getYSlope( )
	{
		double m;

		boolean v1null = base[0][1] == 0.0,
				v2null = base[1][1] == 0.0;

		// Casos particulares (cae sobre ejes)
		if (v1null || v2null)
		{
			if (v1null && v2null)
				m = Double.NaN;
			else if (v1null)
				m = Double.POSITIVE_INFINITY;
			else // v2null
				m = 0;
		}
		else
			m = base[1][1] / base[0][1];
		
		return m;
	}
	
	
	public String toString( )
	{
		String sz = "";
		for (int i = 0, j ; i < 2; i ++)
		{
			for (j = 0; j < 2; j ++)
			{
				sz += base[j][i] + " ";
			}
			sz += "\n";
		}
		return sz;
	}
}
