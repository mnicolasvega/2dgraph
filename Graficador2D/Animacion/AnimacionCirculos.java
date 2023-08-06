package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Definiciones.Point;
import Definiciones.Variable;
import Figuras.Elipse;
import Figuras.Punto;
import Figuras.TextoEstatico;
import Figuras.Vector;
import Figuras.VectorCanonico;
import Figuras.VectorPolar;
import Graficador2D.Camera;
import Graficador2D.Trayectoria;


public class AnimacionCirculos extends Animacion
{
	private TextoEstatico	txt;
	private Trayectoria		tray;
	
	
	public AnimacionCirculos( String name )
	{
		super( name );
		
		variables.put( "t", new Variable(0.0, 0.001) );
		
		txt = new TextoEstatico( 200, 320, "", new Font("Ubuntu", Font.BOLD, 16), TextoEstatico.ALIGN_LEFT, Color.white );
	}


	
	public void firstPlay(Graphics2D g, Camera c)
	{
	}
	
	public void play(Graphics2D g, Camera c)
	{
		if (tray == null)
			tray = new Trayectoria( c );
		
		double t = variables.get( "t" ).getValue();
		double x, y;
	
		/*
		 * Dibujo con fechas de nacimiento de Nati y Nico.
		double[][] circulos =
		{
			{ 1.8,	2.8 },
			{ 0.98,	0.95 },
			{ 1.2,	0.9 }
		};*/
		double[][] circulos =
		{
			/*{ 2.0,	2.0 },
			{ 0.2,	75.0 }*/
			
			{ 1.995,	2.8 },
			{ 0.9,		20.00 }
		};
		/*double[][] circulos =
		{
			{ 1.0,	4.0 },
			{ 0.1,	60.0 }
		};*/
		
		Elipse e;
		Punto p;
		Vector v;
		x = 0.0;
		y = 0.0;
		
		for (int i = 0; i < circulos.length; i ++)
		{
			e = new Elipse(x, y, circulos[i][0], circulos[i][0], Color.DARK_GRAY );
			e.draw(g, c);

			v = new VectorPolar(x, y, circulos[i][0], circulos[i][1] * Math.PI * t, "", Color.DARK_GRAY);
			v.draw(g, c);
			
			x += circulos[i][0] * Math.cos(circulos[i][1] * Math.PI * t);
			y += circulos[i][0] * Math.sin(circulos[i][1] * Math.PI * t);
			
			p = new Punto(x, y, "", Color.WHITE);
			p.draw(g, c);
		}

		v = new VectorCanonico(0, 0, x, y, "", Color.GREEN);
		v.draw(g, c);
				
		
		g.setColor( Color.cyan );
		tray.add( new Point(x, y) );
		tray.draw( g );
		
		
		txt.setText( String.format("'t' = %.3f", t) );
		txt.draw(g, c);
	}
}
