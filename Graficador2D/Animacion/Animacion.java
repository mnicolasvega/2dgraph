package Animacion;

import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.Map;

import Definiciones.Variable;
import Graficador2D.Camera;


public abstract class Animacion
{	
	protected	Map<String, Variable>	variables;
	
	private	boolean			playing,
							hidden,
							first = true;
	private	String			name;
	

	
	
	public abstract void play( Graphics2D g, Camera c );
	public abstract void firstPlay( Graphics2D g, Camera c );
	
	
	
	public Animacion( String name )
	{		
		variables	= new Hashtable<String, Variable>( );
		playing		= false;
		hidden		= false;
		this.name	= name;
	}
	
	
	public String getName( )
	{
		return name;
	}
	
	
	public void hide( )
	{
		hidden = !hidden;
		
		// ocultarla detiene su ejecución
		if (hidden)
			playing = false;
	}
	
	public void setHidden( boolean set )
	{
		hidden = set;
	}
	
	public boolean isHidden( )
	{
		return hidden;
	}
	

	public void play( )
	{
		playing = !playing;
		
		// reproducirla fuerza mostrarla
		if (playing)
			hidden = false;
	}
	
	public void setPlaying( boolean set )
	{
		playing = set;
	}
	
	public boolean isPlaying( )
	{
		return playing;
	}
	
	
	public Map<String, Variable> variables( )
	{
		return variables;
	}
	
	
	public void run( Graphics2D g, Camera c )
	{
		if (!hidden)
		{
			if (first)
			{
				firstPlay( g, c );
				first = false;
			}
			
			play( g, c );
		}
	}
}
