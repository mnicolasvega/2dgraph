package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

import Definiciones.VariableLimitada;
import Figuras.Linea;
import Figuras.Texto;
import Figuras.TextoEstatico;
import Figuras.Vector;
import Figuras.VectorCanonico;
import Graficador2D.Camera;
import Graficador2D.Configuracion;
import MetodosNumericos.Base;


public class AnimacionTransformacionM extends Animacion
{
	private Configuracion cfg;
	private Texto[] tx;
	private double[] v1, v2;
	private double[] i1, i2;
	private int step,
				maxSteps;
	
	public AnimacionTransformacionM( String name, double[] vec1, double[] vec2 )
	{
		super( name );
		
		v1 = new double[2];
		v2 = new double[2];
		
		i1 = new double[2];
		i2 = new double[2];
		
		v1[0] = vec1[0];
		v1[1] = vec1[1];
		v2[0] = vec2[0];
		v2[1] = vec2[1];

		maxSteps = 250;
		
		// M - I_2		
		i1[0] = (v1[0] - 1.0);
		i1[1] = v1[1];
		i2[0] = v2[0];
		i2[1] = (v2[1] - 1.0);
		
		cfg = Configuracion.crear();
		
		tx = new Texto[4];
		tx[0] = new TextoEstatico(30, 30, String.format("     M = %.3f  %.3f", v1[0], v2[0]), new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		tx[1] = new TextoEstatico(30, 50, String.format("         %.3f  %.3f", v1[1], v2[1]), new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		tx[2] = new TextoEstatico(30, 70, String.format("det(M) = %.3f", 1.0), new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		tx[3] = new TextoEstatico(30, 90, String.format("angle  = %.3f * pi", 1.0), new Font("Consolas", Font.BOLD, 20), Texto.ALIGN_LEFT, Color.white);
		
		variables.put( "t", new VariableLimitada(0.0, 1.0, 1.0 / maxSteps) );
	}


	
	public void firstPlay(Graphics2D g, Camera c)
	{
	}
	
	public void play(Graphics2D g, Camera c)
	{
		double t = variables.get( "t" ).getValue();
		
		double[] vec1 = { 1.0 + i1[0] * t,       i1[1] * t };
		double[] vec2 = {       i2[0] * t, 1.0 + i2[1] * t };
		
		Base b = new Base(vec1, vec2);
		
		tx[0].setText( String.format("     M = %.3f  %.3f", vec1[0], vec2[0]) );
		tx[1].setText( String.format("         %.3f  %.3f", vec1[1], vec2[1]) );
		tx[2].setText( String.format("det(M) = %.3f", b.det()) );
		tx[3].setText( String.format("angle  = %.3f * pi", Math.acos(b.det() / (Math.sqrt(vec1[0]*vec1[0] + vec1[1]*vec1[1]) * Math.sqrt(vec2[0]*vec2[0] + vec2[1]*vec2[1]))) / Math.PI) );
		tx[0].draw(g, c);
		tx[1].draw(g, c);
		tx[2].draw(g, c);
		tx[3].draw(g, c);

		g.setColor( Color.DARK_GRAY );
		g.fillPolygon( b.detPolygon(c) );

		cfg.setBase( b );

		Vector gv1 = new VectorCanonico(0.0, 0.0, vec1[0], vec1[1], "v1", Color.green);
		gv1.draw(g, c);

		Vector gv2 = new VectorCanonico(0.0, 0.0, vec2[0], vec2[1], "v2", Color.red);
		gv2.draw(g, c);
		
		step ++;
	}
}