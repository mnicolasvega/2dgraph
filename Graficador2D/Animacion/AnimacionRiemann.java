package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Definiciones.Point;
import Definiciones.Variable;
import Figuras.Punto;
import Figuras.Texto;
import Figuras.TextoEstatico;
import Graficador2D.Camera;
import Graficador2D.Trayectoria;
import MetodosNumericos.Complejo;


public class AnimacionRiemann extends Animacion
{
	private TextoEstatico	txt,
							zeros;
	private Trayectoria		tray;
	private Complejo			i,
							z;
	private String			lista = "zeros (im): ";
	private double			tolerancia = 0.01;
	private Punto			p;
	
	
	public AnimacionRiemann( String name )
	{
		super( name );
		
		variables.put( "t", new Variable(5.0, 0.01) );
		
		i = new Complejo(0, 0);
		
		txt = new TextoEstatico( 20, 320, "", new Font("Ubuntu", Font.BOLD, 16), TextoEstatico.ALIGN_LEFT, Color.white );
		zeros = new TextoEstatico( 20, 20, "", new Font("Ubuntu", Font.BOLD, 12), TextoEstatico.ALIGN_LEFT, Color.white );
		zeros.setVAlign( Texto.VALIGN_DOWN );
	}

	
	public void firstPlay(Graphics2D g, Camera c)
	{
		System.out.println("FIRST PLAY!");
		c.setCameraCenteredAt(1.0, 1.0);
	}
	
	public void play(Graphics2D g, Camera c)
	{
		if (tray == null)
			tray = new Trayectoria( c );
		
		double t = variables.get( "t" ).getValue();
		
		i = new Complejo(0.5, t);
		z = i.zetaExtended(i, 1000);
		
		if (z.modulo() <= tolerancia)
			lista += String.format("%.3f", t) + " ; ";
		
		g.setColor( Color.cyan );
		tray.add( new Point(z.re(), z.im()) );
		tray.draw( g );
		
		p = new Punto(z.re(), z.im(), "", Color.RED);
		p.draw(g, c);
		
		
		txt.setText( String.format("|| zeta(0.5 + i*%.2f) || = %.6f", t, z.modulo()) );
		txt.draw(g, c);
		
		zeros.setText( lista );
		//zeros.draw(g, c);
	}
}
