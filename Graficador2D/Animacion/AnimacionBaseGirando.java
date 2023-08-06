package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Definiciones.VariableLimitada;
import Figuras.Linea;
import Figuras.Texto;
import Figuras.TextoEstatico;
import Figuras.Vector;
import Figuras.VectorPolar;
import Graficador2D.Camera;
import Graficador2D.Configuracion;
import MetodosNumericos.Base;


public class AnimacionBaseGirando extends Animacion
{
	Configuracion cfg;
	Texto t1, t2, t3;
	
	public AnimacionBaseGirando( String name )
	{
		super( name );
		
		cfg = Configuracion.crear();
		
		t1 = new TextoEstatico(30, 30, "", new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		t2 = new TextoEstatico(30, 50, "", new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		t3 = new TextoEstatico(30, 70, "", new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		
		variables.put( "t", new VariableLimitada(0.0, 2.0, 0.005) );
	}


	
	public void firstPlay(Graphics2D g, Camera c)
	{
	}
	
	public void play(Graphics2D g, Camera c)
	{
		double t = variables.get( "t" ).getValue();
		
		double[] v1 = { Math.cos(t * Math.PI), Math.sin(t * Math.PI) };
		double[] v2 = { Math.cos((t + 0.5) * Math.PI), Math.sin((t + 0.5) * Math.PI) };
		
		t1.setText( String.format("M(%.2f * pi) =  %.6f  %.6f", t, v1[0], v2[0]) );
		t2.setText( String.format("                %.6f  %.6f",    v1[1], v2[1]) );
		t3.setText( String.format("det(M(a))    =  %.4f", (v1[0] * v2[1]) - (v1[1] * v2[0])) );
		
		t1.draw(g, c);
		t2.draw(g, c);
		t3.draw(g, c);
		
		cfg.setBase( new Base(v1, v2) );
		
		/*Linea gl1 = new Linea( Math.tan(t * Math.PI), Math.cos((t + 0.5) * Math.PI), Math.sin((t + 0.5) * Math.PI), "", Color.cyan);
		gl1.draw(g, c);
		
		Linea gl2 = new Linea( Math.tan(t * Math.PI), 0.0, 0.0, "", Color.cyan);
		gl2.draw(g, c);
		
		Linea gl3 = new Linea( Math.tan((t + 0.5) * Math.PI), Math.cos(t * Math.PI), Math.sin(t * Math.PI), "", Color.orange);
		gl3.draw(g, c);
		
		Linea gl4 = new Linea( Math.tan((t + 0.5) * Math.PI), 0.0, "", Color.orange);
		gl4.draw(g, c);*/
		
		Vector gv1 = new VectorPolar(0.0, 0.0, 1.0, t * Math.PI, "v1", Color.green);
		gv1.draw(g, c);
		
		Vector gv2 = new VectorPolar(0.0, 0.0, 1.0, (t + 0.5) * Math.PI, "v2", Color.red);
		gv2.draw(g, c);
	}
}