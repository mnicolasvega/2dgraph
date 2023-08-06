package Graficador2D;

import java.awt.Graphics2D;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import Animacion.Animacion;
import Definiciones.Variable;


public class ContAnimaciones
{
	private DefaultListModel	modeloLista;
	private JList				listaAnimaciones;
	private JLabel				animacionesEstado;
	private Configuracion		cfg;
	private List<Animacion>		animaciones;
	
	
	public ContAnimaciones( JList lista, DefaultListModel modelo, JLabel estados )
	{
		animaciones			= new LinkedList<Animacion>( );
		modeloLista			= modelo;
		listaAnimaciones	= lista;
		animacionesEstado	= estados;
		cfg					= Configuracion.crear();
	}
	
	
	// Añadir y remover animaciones del listado ===================================================
	public void addAnimation( Animacion a )
	{
		animaciones.add( a );
		a.setHidden(true);
		a.setPlaying(false);
		modeloLista.addElement( a.getName() );
		updateLabel( );
	}
	
	public void removeAnimation( Animacion a )
	{
		animaciones.remove( a );
		modeloLista.removeElement( a.getName() );
		updateLabel( );
	}
	
	
	// Hay algo reproduciendose? ==================================================================
	public boolean isPlaying( )
	{
		boolean res = false;
		
		for (Animacion a : animaciones)
		{
			if (a.isPlaying())
			{
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	
	// Añadir y remover animaciones del listado ===================================================
	public void preprocess( )
	{
		for (Animacion a : animaciones)
		{
			if (a.isPlaying())
			{
				for (Map.Entry<String, Variable> e : a.variables().entrySet())
				{
					e.getValue().update();
				}
			}
		}
	}
	
	public void render( Graphics2D g, Camera camera )
	{
		for (Animacion a : animaciones)
		{
			a.run( g, camera );
		}
	}
	
	
	// Manejo del GUI para inicir/detener, mostrar/ocultar una animación ==========================
	public void startAction( )
	{
		if (listaAnimaciones.getSelectedValue() != null)
		{
			Animacion anim = null;
			
			for (Animacion a : animaciones)
			{
				if (a.getName().equals(listaAnimaciones.getSelectedValue()))
				{
					anim = a;
					break;
				}
			}
			
			if (anim != null)
			{
				File folder = new File( cfg.getFramesPath() );
				
				if (folder.exists() && !folder.isFile())
				{
					anim.play();
				}
				else
				{
					System.out.println("INVALID FRAME OUTPUT FOLDER");
				}
			}
		}
		
		updateLabel( );
	}
	
	public void hideAction( )
	{
		if (listaAnimaciones.getSelectedValue() != null)
		{
			Animacion anim = null;
			
			for (Animacion a : animaciones)
			{
				if (a.getName().equals(listaAnimaciones.getSelectedValue()))
				{
					anim = a;
					break;
				}
			}
			
			if (anim != null)
			{
				anim.hide();
			}
		}
		
		updateLabel( );
	}
	
	private void updateLabel( )
	{
		String sz = "<html>[reprod., visible] nombre<br/>";
		
		for (Animacion a : animaciones)
		{
			sz += "[" + (a.isPlaying() ? "si" : "no") + ", " + (a.isHidden() ? "no" : "si") + "] " + a.getName() + "<br/>";
		}
		
		sz += "</html>";
		
		animacionesEstado.setText( sz );
	}
}
