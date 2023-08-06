package Animacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Definiciones.Variable;
import Figuras.Linea;
import Figuras.Punto;
import Figuras.TextoEstatico;
import Graficador2D.Camera;
import Graficador2D.Function;
import MetodosNumericos.DerivadaNumerica;
import MetodosNumericos.IntegralNumerica;


public class AnimacionDerivada extends Animacion
{
	private TextoEstatico		txt;
	private IntegralNumerica	i;
	private Function			f;
	
	
	public AnimacionDerivada( String name, Function f )
	{
		super( name );
		
		this.f = f;
		
		variables.put( "t", new Variable(-2.25, 0.003) );
		
		txt = new TextoEstatico( 200, 300, "", new Font("Ubuntu", Font.BOLD, 16), TextoEstatico.ALIGN_LEFT, Color.white );
	}

	
	public void firstPlay(Graphics2D g, Camera c)
	{
		i = new IntegralNumerica(f, -3.0, 3.0, -3);
	}
	
	public void play(Graphics2D g, Camera c)
	{
		double	x = variables.get( "t" ).getValue(),
				fy,
				dy;
		
		DerivadaNumerica d = new DerivadaNumerica();

		fy = f.calcular(x);
		dy = d.calcular(f, x, -3);
		
		
		
		g.setColor( Color.red );
		f.drawFunction(c, g, 0.001);
				
		Linea l = new Linea(dy, x, fy, "L", Color.pink);
		l.draw(g, c);

		//Vector v = new VectorPolar(x, fy, dy / 2.0, Math.atan(dy) + ( (dy < 0) ? (Math.PI * 2) : 0.0 ), "v", Color.green);
		//v.draw(g, c);
		
		Punto p = new Punto(x, f.calcular(x), "P", Color.WHITE);
		p.draw(g, c);
		
		//g.setColor( Color.cyan );
		//d.drawFunction(c, g, f, 0.001);
		
		//g.setColor( Color.green );
		//i.drawFunction(c, g, -3, 3);
		
		txt.setText( String.format("'t' = %.3f", x) );
		txt.draw(g, c);
	}
}
