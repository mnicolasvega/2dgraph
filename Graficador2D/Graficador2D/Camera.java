package Graficador2D;

public class Camera
{
	private int		jw, jh;			// width y height del JPanel
	private int		cx, cy;
	private int		dxp, dyp;		// desplazamiento para posición de la camara
	private int		escala;
	private int		mlx, mly;		// last mouse position (para saber cuánto desplazar la cámara)
	
	// rectángulo de la camara en unidades, visualiza: [vminx, vmaxx] x [vmaxx, vmaxy]
	private double	vminx, vminy;
	private double	vmaxx, vmaxy;

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public Camera( int width, int height, int scale )
	{
		jw		= width;
		jh		= height;
		escala	= scale;
		
		// inicializar otras variables
		dxp	= jw / 2;
		dyp	= jh / 2;
		
		cx = -dxp;
		cy = dyp;

		vminx = (cx * 1.0) / escala;
		vmaxx = (cx + jw * 1.0) / escala;
		vminy = (cy - jh * 1.0) / escala;
		vmaxy = (cy * 1.0) / escala;
		
		mlx = Integer.MIN_VALUE;
		mly = Integer.MIN_VALUE;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void updateMouse( int mouseX, int mouseY )
	{
		mlx = mouseX;
		mly = mouseY;
	}
	
	public void moveCamera( int mouseX, int mouseY )
	{
		if ((mlx != Integer.MIN_VALUE) && (mly != Integer.MIN_VALUE))
		{
			dxp += (mouseX - mlx);
			dyp += (mouseY - mly);
			
			cx = -dxp;
			cy = dyp;
			
			vminx = (cx * 1.0) / escala;
			vmaxx = (cx + jw * 1.0) / escala;
			vminy = (cy - jh * 1.0) / escala;
			vmaxy = (cy * 1.0) / escala;
		}
		
		mlx = mouseX;
		mly = mouseY;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setCameraCenteredAt( double x, double y )
	{
		mlx = 0;
		mly = 0;
		dxp = adjustX( x );
		dyp = adjustY( -y );
		moveCamera( 0, 0 );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getCamX( )
	{
		return cx;
	}
	
	public int getCamY( )
	{
		return cy;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getCamCenterX( )
	{
		return cx + (jw / 2);
	}
	
	public int getCamCenterY( )
	{
		return cy - (jh / 2);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getMinX( )
	{
		return vminx;
	}
	
	public double getMinY( )
	{
		return vminy;
	}
	
	public double getMaxX( )
	{
		return vmaxx;
	}
	
	public double getMaxY( )
	{
		return vmaxy;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getCenterX( )
	{
		return vminx + Math.abs(vmaxx - vminx) / 2.0;
	}
	
	public double getCenterY( )
	{
		return vminy + Math.abs(vmaxy - vminy) / 2.0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getXSpace( )
	{
		return Math.abs(vmaxx - vminx);
	}
	
	public double getYSpace( )
	{
		return Math.abs(vmaxy - vminy);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean inBounds( double x, double y )
	{
		return (vminx <= x && x <= vmaxx && vminy <= y && y <= vmaxy);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getScale( )
	{
		return escala;
	}
	
	public void setScale( int scale )
	{
		escala = scale;
	}
	
	public void modifyScale( int change )
	{
		escala += change;
		
		if (escala < 15)
			escala = 15;
		
		vminx = (cx * 1.0) / escala;
		vmaxx = (cx + jw * 1.0) / escala;
		vminy = (cy - jh * 1.0) / escala;
		vmaxy = (cy * 1.0) / escala;
	}
	
	public void setScaleToMatchInterval( double lenght )
	{
		escala = (int) Math.round( (jw * 1.0) / lenght );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public int adjustX( double x )
	{
		return (int) Math.round((x * escala)) + dxp;
	}
	
	public int adjustY( double y )
	{
		return (int) Math.round(-(y * escala)) + dyp;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public double screenX( int x )
	{
		return (x - dxp) * 1.0 / escala;
	}
	
	public double screenY( int y )
	{
		return (y - dyp) * 1.0 / escala;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
}
