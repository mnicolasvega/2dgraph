package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Definiciones.VariableLimitada;
import Figuras.Linea;
import Figuras.Punto;
import Figuras.Rectangulo;
import Figuras.Texto;
import Figuras.TextoMovible;
import Graficador2D.Camera;
import Graficador2D.Function;


public class AnimacionLimite extends Animacion
{
	private double		x,
						y;
	private Function	f;
	private Punto		pizq,
						pder;
	private Rectangulo	rec;
	
	
	
	public AnimacionLimite( String name, Function f, double x )
	{
		super( name );
		
		this.f = f;
		this.x = x;
		this.y = f.calcular(x);
		System.out.println( y );
		
		variables.put( "x+", new VariableLimitada(x - 0.5, x,  0.002) );
		variables.put( "x-", new VariableLimitada(x + 0.5, x, -0.002) );
	}


	
	public void firstPlay(Graphics2D g, Camera c)
	{
		c.setCameraCenteredAt(x, y);
	}
	
	public void play(Graphics2D g, Camera c)
	{
		double	xizq = variables.get("x+").getValue(),
				xder = variables.get("x-").getValue();

		g.setColor(Color.cyan);
		f.drawFunction(c, g, 0.01);
		
		if (xizq != x)
		{
			rec = new Rectangulo(xizq, f.calcular(xizq), xder, f.calcular(xder), "", Color.white);
			rec.draw(g, c);
		}
		else
		{
			Linea l = new Linea(0.0, y, "", Color.white);
			l.draw(g, c);
		}
		
		pizq = new Punto(xizq, f.calcular(xizq), "->", Color.RED);
		pizq.draw(g, c);
		
		pder = new Punto(xder, f.calcular(xder), "<-", Color.RED);
		pder.draw(g, c);
	}
}
