package Graficador2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Animacion.Animacion;
import Animacion.AnimacionBaseGirando;
import Animacion.AnimacionCirculos;
import Animacion.AnimacionDerivada;
import Animacion.AnimacionLimite;
import Animacion.AnimacionRiemann;
import Animacion.AnimacionTransformacionM;
import Figuras.Linea;
import Figuras.Punto;
import Figuras.Texto;
import Figuras.TextoEstatico;
import MetodosNumericos.Base;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class NJPanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{	
	private final int		MODE_REAL		= 0;
	private final int		MODE_COMPLEX	= 1;
	
	private final int		DEFAULT_SCALE	= 130;	// escala por defecto (pixeles por unidad gráfica)
	private final int		ARROW_SIZE		= 12;	// tamaño de la flecha del eje
	
	private Thread			thread;			// thread
	private BufferedImage	image;			// canvas
	private Graphics2D		g;				// paintBrush
	private int				jw, jh;			// width y height del JPanel
	private Camera			camera;
	private int				mode;
	private JPanel			panelPrincipal;
	private Configuracion	cfg;
	private ContAnimaciones	canim;
	
	private Color			color_bg,
							color_axis,
							color_grid,
							color_grid_number;
	
	private Font			font_axis;
	
	private TextoEstatico[]	tE_debug;
	
	private int TEMP_COUNT = 0;

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public NJPanel( int width, int height, JPanel panelPrincipal, ContAnimaciones cAnim )
	{
		super();

		jw = width;
		jh = height;
		
		this.panelPrincipal = panelPrincipal;
		this.cfg			= Configuracion.crear();
		this.canim			= cAnim;

		this.setSize(width, height);
		
		//Base b = new Base(Base.CANONICA);
		double[] v1 = { 1.0, 0.0 };
		double[] v2 = { 0.0, 1.0 };
		Base b = new Base( v1, v2 );
		System.out.println( b );
		cfg.setBase( b );
		
		initializeVariables();
		
		setFocusable(true);
		requestFocus();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void initializeVariables( )
	{
		color_bg			= new Color(0, 0, 0);
		color_axis			= new Color(255, 255, 255);
		color_grid			= new Color(50, 50, 50);
		color_grid_number	= new Color(255, 255, 255);
		
		font_axis	= new Font("Ubuntu", Font.BOLD, 12);
		camera		= new Camera(jw, jh, DEFAULT_SCALE);
		mode		= MODE_REAL;
		
		tE_debug = new TextoEstatico[3];
		tE_debug[0] = new TextoEstatico( 5, 5,  "", new Font("Ubuntu", Font.PLAIN, 10), Texto.ALIGN_LEFT, Color.white );
		tE_debug[1] = new TextoEstatico( 5, 20, "", new Font("Ubuntu", Font.PLAIN, 10), Texto.ALIGN_LEFT, Color.white );
		tE_debug[2] = new TextoEstatico( 5, 35, "", new Font("Ubuntu", Font.PLAIN, 10), Texto.ALIGN_LEFT, Color.white );
		
		preloadAnimations( );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void preloadAnimations( )
	{
		loadAnimation( new AnimacionDerivada("surf derivada", new Function("f", "0.1*(x+2.5)(x+1)x(x-1.5)(x-2)")) );
		loadAnimation( new AnimacionCirculos("circulos") );
		loadAnimation( new AnimacionRiemann("zeta zeros") );
		loadAnimation( new AnimacionLimite("limite", new Function("f", "abs(3x - 6)"), 0.0) );
		loadAnimation( new AnimacionBaseGirando("T.L. girando") );
		
		double[] v1 = { 1.0, 0.0 };
		double[] v2 = { -1.0, -1.0 };
		loadAnimation( new AnimacionTransformacionM("cambio de base", v1, v2) );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addNotify()
	{
		super.addNotify();
		
		// asignar como Thread
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
		
		// asignar Listeners
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void loadAnimation( Animacion a )
	{
		canim.addAnimation( a );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void run()
	{
		//  inicializar canvas y paintbrush  /////////////////////////////
		image	= new BufferedImage( jw, jh, BufferedImage.TYPE_INT_RGB);
		g		= (Graphics2D) image.getGraphics();

		// loop
		while (true)
		{
			preprocess( );
			render();
			draw();
			
			
			if (cfg.getFrames() && canim.isPlaying())
			{
				try
				{
					File outputfile = new File( cfg.getFramesPath() + "_" + TEMP_COUNT + ".png" );

					BufferedImage res = new BufferedImage( 1280, 720, BufferedImage.TYPE_INT_ARGB );
					Graphics g2 = res.getGraphics();
					g2.drawImage( image, 0, 0, 1280, 720, null);
					g2.dispose();
					
					ImageIO.write( res, "PNG", outputfile );
					
					TEMP_COUNT ++;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			
			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void preprocess( )
	{
		canim.preprocess( );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void render()
	{
		long time = System.nanoTime();
		
		int x, y, x2, y2;
		double dx, dy;
		int[] arrowX, arrowY;
		arrowX = new int[3];
		arrowY = new int[3];
		
		
		// RENDER BASIC COMPONENTS ========================
		renderBackground( );
		
		if (cfg.getGrillaEuclidea() || cfg.getGrillaNumeros())
			renderEuclideanGrid( );
		
		if (cfg.getGrilla())
			renderGrid( cfg.getBase() );



		// RENDER ANIMATIONS ==============================
		canim.render( g, camera );
		
		

		// RENDER DEBUG N COMPONENTS ======================
		if (cfg.getEjes())
			renderAxis( );

		if (cfg.getOrigen())
			renderOrigin( );
		
		time = System.nanoTime() - time;

		if (cfg.getDebug())
			renderDebug( time );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderDebug( long time )
	{
		tE_debug[0].setText(
			String.format("x en [%.1f; %.1f]   ;   y en [%.1f; %.1f]   ;   centro = (%.2f, %.2f)   ;   escala = %d px/u",
				camera.getMinX(),
				camera.getMaxX(),
				camera.getMinY(),
				camera.getMaxY(),
				camera.getCenterX(),
				camera.getCenterY(),
				camera.getScale()
			)
		);
		
		tE_debug[1].setText(
			String.format("cam_pos = (%d, %d)  |  cam_center = (%d, %d)",
				camera.getCamX(),
				camera.getCamY(),
				camera.getCamCenterX(),
				camera.getCamCenterY()
			)
		);
		
		tE_debug[2].setText(
			String.format("render time: %.2f ms.", time / 1000000.0)
		);
		
		for (TextoEstatico t : tE_debug)
		{
			t.draw( g, camera );
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderBackground( )
	{
		g.setColor( color_bg );
		g.fillRect(0, 0, jw, jh);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderOrigin( )
	{
		if ((camera.getMinX() < 0.0) && (camera.getMaxX() > 0.0) &&
			(camera.getMinY() < 0.0) && (camera.getMaxY() > 0.0))
		{
			g.setColor( color_grid_number );
			g.setFont( new Font("Ubuntu", Font.BOLD, 10) );
			
			g.drawString("0", camera.adjustX(0) + 3, camera.adjustY(0) + 10);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderEuclideanGrid( )
	{
		int x, y, x2, y2;
		
		g.setFont( new Font("Ubuntu", Font.PLAIN, 10) );
		
		
		// Vertical Stripes
		if (camera.getCamX() <= 0)
			x = Math.abs(camera.getCamX()) % camera.getScale();
		else
			x = camera.getScale() - Math.abs(camera.getCamX()) % camera.getScale();
		
		for ( ; x <= jw; x += camera.getScale())
		{
			if (cfg.getGrillaEuclidea())
			{
				g.setColor( color_grid );
				g.drawLine(x, 0, x, jh);
			}

			if (cfg.getGrillaNumeros())
			{
				g.setColor( color_grid_number );
				x2 = (int) Math.round(camera.screenX(x));
				if (x2 != 0)
					g.drawString("" + x2, x, camera.getCamY());
			}
		}
		
		
		// Horizontal Stripes
		if (camera.getCamY() <= jh)
			y = Math.abs(camera.getCamY() - jh) % camera.getScale();
		else
			y = camera.getScale() - (camera.getCamY() - jh) % camera.getScale();
		
		for ( ; y <= jh; y += camera.getScale())
		{
			if (cfg.getGrillaEuclidea())
			{
				g.setColor( color_grid );
				g.drawLine(0, jh - y, jw, jh - y);
			}

			if (cfg.getGrillaNumeros())
			{
				g.setColor( color_grid_number );
				y2 = (int) Math.round(camera.screenY(jh - y));
				if (y2 != 0)
					g.drawString("" + (-y2), -camera.getCamX(), jh - y);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderGrid( Base b )
	{
		int dim = b.dimension();

		g.setColor( b.getColor() );
		
		if (dim == 0)
		{
			Punto p = new Punto(0.0, 0.0, "", b.getColor() );
			p.draw(g, camera);
		}
		else if (dim == 1)
		{
			double[] v = b.getV1();
			Linea l = new Linea(0.0, 0.0, v[0], v[1], "", b.getColor() );
			l.draw(g, camera);
		}
		else if (dim == 2)
		{
			int xnumber,
				ynumber;
			
			double	gx,
					gy,
					slopeX,
					slopeY,
					xspace = camera.getXSpace(),
					yspace = camera.getYSpace();
			
			boolean xused = false,
					yused = false;
			
			
			slopeX = b.getXSlope();
			slopeY = b.getYSlope();
			
			Linea l;
			
			//System.out.printf("\nmA = %f, mB = %f\n", slopeX, slopeY);
			

			//==[ vector A ]==========================================
			// linea horizontal
			if (slopeX == 0.0)
			{
				yused = true;

				ynumber = (int) Math.floor(camera.getMinY() / b.getAYSpacing());
				gy = ynumber * b.getAYSpacing();
				
				//System.out.printf("1: gy = %f ; ayspacing: %f ; lim: %f\n", gy, b.getAYSpacing(), camera.getMaxY());
				
				for ( ; gy <= camera.getMaxY(); gy += b.getAYSpacing())
					g.drawLine(
							0,
							camera.adjustY(gy),
							jw,
							camera.adjustY(gy));
			}
			// linea vertical
			else if (slopeX == Double.POSITIVE_INFINITY)
			{
				xused = true;

				xnumber = (int) Math.floor(camera.getMinX() / b.getAXSpacing());
				gx = xnumber * b.getAXSpacing();
				
				//System.out.printf("2: gx = %f ; axspacing: %f ; lim: %f\n", gx, b.getAXSpacing(), camera.getMaxX());
				
				for ( ; gx <= camera.getMaxX(); gx += b.getAXSpacing())
					g.drawLine(
							camera.adjustX(gx),
							0,
							camera.adjustX(gx),
							jh);
			}
			// pendiente m
			else
			{
				if (slopeY == Double.POSITIVE_INFINITY)
				{
					ynumber = (int) Math.floor(camera.getMinY() / b.getAYSpacing());
					gy = ynumber * b.getAYSpacing() * 10;

					//System.out.printf("3: gy = %f ; ayspacing: %f ; lim: %f\n", gy, b.getAYSpacing(), camera.getMaxY());
					
					for ( ; gy <= camera.getMaxY() * 10; gy += b.getAYSpacing())
					{
						l = new Linea(slopeX, 0.0, gy, "", b.getColor() );
						l.draw(g, camera);
					}
				}
				else
				{
					xnumber = (int) Math.floor(camera.getMinX() / b.getAXSpacing());
					gx = xnumber * b.getAXSpacing() * 10;

					//System.out.printf("4: gx = %f ; axspacing: %f ; lim: %f\n", gx, b.getAXSpacing(), camera.getMaxX());
					
					for ( ; gx <= camera.getMaxX() * 10; gx += b.getAXSpacing())
					{
						l = new Linea(slopeY, gx, 0.0, "", b.getColor() );
						l.draw(g, camera);
					}
				}
			}
			


			//==[ vector B ]==========================================				
			// linea horizontal
			if (slopeY == 0.0)
			{
				ynumber = (int) Math.floor(camera.getMinY() / b.getBYSpacing());
				gy = ynumber * b.getBYSpacing();

				//System.out.printf("5: gy = %f ; byspacing: %f ; lim: %f\n", gy, b.getBYSpacing(), camera.getMaxY());
				
				for ( ; gy <= camera.getMaxY(); gy += b.getBYSpacing())
					g.drawLine(
							0,
							camera.adjustY(gy),
							jw,
							camera.adjustY(gy));
			}
			// linea vertical
			else if (slopeY == Double.POSITIVE_INFINITY)
			{
				xnumber = (int) Math.floor(camera.getMinX() / b.getBXSpacing());
				gx = xnumber * b.getBXSpacing();

				//System.out.printf("6: gx = %f ; bxspacing: %f ; lim: %f\n", gx, b.getBXSpacing(), camera.getMaxX());
				
				for ( ; gx <= camera.getMaxX(); gx += b.getBXSpacing())
					g.drawLine(
							camera.adjustX(gx),
							0,
							camera.adjustX(gx),
							jh);
			}
			// pendiente m
			else
			{
				if (yused)
				{
					xnumber = (int) Math.floor(camera.getMinX() / b.getBXSpacing());
					gx = xnumber * b.getBXSpacing() * 10;

					//System.out.printf("7: gx = %f ; bxspacing: %f ; lim: %f\n", gx, b.getBXSpacing(), camera.getMaxY());
					
					for ( ; gx <= camera.getMaxY() * 10; gx += b.getBXSpacing())
					{
						l = new Linea(slopeY, gx, 0.0, "", b.getColor() );
						l.draw(g, camera);
					}
				}
				else
				{
					ynumber = (int) Math.floor(camera.getMinY() / b.getBYSpacing());
					gy = ynumber * b.getBYSpacing() * 10;

					//System.out.printf("8: gy = %f ; byspacing: %f ; lim: %f\n", gy, b.getBYSpacing(), camera.getMaxY());
					
					for ( ; gy <= camera.getMaxY() * 10; gy += b.getBYSpacing())
					{
						l = new Linea(slopeX, 0.0, gy, "", b.getColor() );
						l.draw(g, camera);
					}
				}
			}
			
		}
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void renderAxis( )
	{
		g.setColor( color_axis );
		
		int cx = -camera.getCamX(),
			cy = camera.getCamY();
		String sz = "";
		
		// x-axis
		if ((cy >= 0) && (cy <= jh))
		{
			g.drawLine(0, cy, jw, cy);

			if (cx <= (jw - ARROW_SIZE))
			{
				g.fillPolygon( createVectorTip(jw, cy, 0.0, ARROW_SIZE) );
				
				if (mode == MODE_REAL)
					sz = "x";
				else if (mode == MODE_COMPLEX)
					sz = "Re";
				
				TextoEstatico t = new TextoEstatico( jw - ARROW_SIZE, cy, sz, font_axis, TextoEstatico.ALIGN_RIGHT, color_axis );
				t.draw(g, camera);
			}
		}
		
		// y-axis
		if ((cx >= 0) && (cy <= jw))
		{
			g.drawLine(cx, 0, cx, jh);

			if (cy >= (0 + ARROW_SIZE))
			{
				g.fillPolygon( createVectorTip(cx, 0, (Math.PI / 2), ARROW_SIZE) );
				
				if (mode == MODE_REAL)
					sz = "y";
				else if (mode == MODE_COMPLEX)
					sz = "Im";

				TextoEstatico t = new TextoEstatico( cx + ARROW_SIZE, 0, sz, font_axis, TextoEstatico.ALIGN_RIGHT, color_axis );
				t.draw(g, camera);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	private void drawCircle( double centerX, double centerY, double r, Color c )
	{
		g.setColor( c );
		g.drawOval(
			camera.adjustX(centerX - r),
			camera.adjustY(centerY + r),
			(int) Math.round(r * camera.getScale() * 2),
			(int) Math.round(r * camera.getScale() * 2)
		);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void drawVector( double startX, double startY, double theta, double r, Color c)
	{
		drawVectorP( startX, startY, r * Math.cos(theta), r * Math.sin(theta), c);
	}
	
	private void drawVectorP( double startX, double startY, double vecX, double vecY, Color c )
	{		
		g.setColor( c );
		g.drawLine(
			camera.adjustX(startX),
			camera.adjustY(startY),
			camera.adjustX(startX + vecX),
			camera.adjustY(startY + vecY)
		);
		g.fillPolygon(
			createVectorTip(
				camera.adjustX(startX + vecX),
				camera.adjustY(startY + vecY),
				Math.atan2(vecY, vecX),
				ARROW_SIZE
			)
		);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private Polygon createVectorTip( int endX, int endY, double angle, int size )
	{
		Polygon p;
		
		int[] arrowX, arrowY;
		arrowX = new int[3];
		arrowY = new int[3];
		
		double	vecX = Math.cos(angle),
				vecY = Math.sin(angle),
				vecPX = Math.cos(angle + Math.PI / 2),
				vecPY = Math.sin(angle + Math.PI / 2);
		
		arrowX[0] = endX;
		arrowY[0] = endY;
		
		arrowX[1] = (int) Math.round( endX - (size * vecX) + (vecPX * (size / 2.0)) );
		arrowY[1] = (int) Math.round( endY + (size * vecY) - (vecPY * (size / 2.0)) );
		
		arrowX[2] = (int) Math.round( endX - (size * vecX) - (vecPX * (size / 2.0)) );
		arrowY[2] = (int) Math.round( endY + (size * vecY) + (vecPY * (size / 2.0)) );
		
		p = new Polygon(arrowX, arrowY, 3);
		
		return p;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private void draw()
	{
		Graphics g2 = this.getGraphics();
		
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void keyTyped(KeyEvent key)
	{
	}
	
	public void keyPressed(KeyEvent key)
	{
	}

	public void keyReleased(KeyEvent key)
	{		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	
	public void mousePressed(MouseEvent e)
	{
		camera.updateMouse( e.getX(), e.getY() );
	}
	
	public void mouseReleased(MouseEvent e)
	{
	}
	
	public void mouseDragged(MouseEvent e)
	{
		camera.moveCamera( e.getX(), e.getY() );
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		camera.modifyScale( -e.getWheelRotation() );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
}