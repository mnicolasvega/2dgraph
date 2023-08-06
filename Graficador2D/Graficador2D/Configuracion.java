package Graficador2D;

import javax.swing.JTextField;

import MetodosNumericos.Base;


public class Configuracion
{
	private boolean		VISTA_DEBUG,
						VISTA_GRILLA_EUCLIDEA,
						VISTA_GRILLA,
						VISTA_GRILLA_NUMEROS,
						VISTA_EJES,
						VISTA_ORIGEN,
						CFG_FRAMES;
	private JTextField	CFG_FRAMES_PATH;
	private Base		base = new Base( Base.CANONICA );
	
	private static Configuracion c = null;
	
	
	private Configuracion( )
	{
	}
	
	
	public static Configuracion crear( )
	{
		if (c == null)
			c = new Configuracion( );
		
		return c;
	}
	
	
	
	
	public void setBase( Base b )
	{
		this.base = b;
	}
	
	public Base getBase( )
	{
		return base;
	}
	

	public void setDebug( boolean set )
	{
		VISTA_DEBUG = set;
	}
	
	public boolean getDebug( )
	{
		return VISTA_DEBUG;
	}
	
	
	public void setGrillaEuclidea( boolean set )
	{
		VISTA_GRILLA_EUCLIDEA = set;
	}
	
	public boolean getGrillaEuclidea( )
	{
		return VISTA_GRILLA_EUCLIDEA;
	}
	
	
	public void setGrilla( boolean set )
	{
		VISTA_GRILLA = set;
	}
	
	public boolean getGrilla( )
	{
		return VISTA_GRILLA;
	}
	
	
	public void setGrillaNumeros( boolean set )
	{
		VISTA_GRILLA_NUMEROS = set;
	}
	
	public boolean getGrillaNumeros( )
	{
		return VISTA_GRILLA_NUMEROS;
	}
	
	
	public void setEjes( boolean set )
	{
		VISTA_EJES = set;
	}
	
	public boolean getEjes( )
	{
		return VISTA_EJES;
	}
	
	
	public void setOrigen( boolean set )
	{
		VISTA_ORIGEN = set;
	}
	
	public boolean getOrigen( )
	{
		return VISTA_ORIGEN;
	}
	
	
	public void setFrames( boolean set )
	{
		CFG_FRAMES = set;
	}
	
	public boolean getFrames( )
	{
		return CFG_FRAMES;
	}
	
	
	public void setFramesPathObject( JTextField txt )
	{
		CFG_FRAMES_PATH = txt;
	}
	
	public String getFramesPath( )
	{
		return CFG_FRAMES_PATH.getText();
	}
}
