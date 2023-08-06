package Graficador2D;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class GUI extends JFrame
{
	private final int	C_WIDTH			= 1280,
						C_HEIGHT		= 720,
						SCROLL_SPACE	= 3,
						PANEL_SPACE		= 30,
						MENU_BAR_SPACE	= 49;
	
	private Configuracion			cfg;
	private ContAnimaciones	canim;

	private Dimension			JFrameSize;
	private JPanel				contentPane;
	private JList				list;
	private DefaultListModel	listModel;



	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}


	public GUI()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrameSize = new Dimension(	C_WIDTH + (PANEL_SPACE * 2) + (SCROLL_SPACE * 2),
									C_HEIGHT + PANEL_SPACE + 40 + 200 );
				
		setPreferredSize( JFrameSize );
		pack();
			
		setLocationRelativeTo( null );
		
		cfg = Configuracion.crear();
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		crearBarraMenu( );
		
		crearListaAnimaciones( );
		
		crearConfiguracion( );
		
		crearCanvas( );
	}
	
	
	private void crearCanvas( )
	{
		JScrollPane scrollGraph = new JScrollPane();
		scrollGraph.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollGraph.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollGraph.setBounds(PANEL_SPACE, JFrameSize.height - (C_HEIGHT + SCROLL_SPACE + PANEL_SPACE + MENU_BAR_SPACE), C_WIDTH + SCROLL_SPACE, C_HEIGHT + SCROLL_SPACE);
		contentPane.add(scrollGraph);
		
		NJPanel njp = new NJPanel(C_WIDTH, C_HEIGHT, contentPane, canim);
		scrollGraph.setViewportView(njp);
	}
	
	
	private void crearConfiguracion( )
	{
		JLabel lblFrames = new JLabel("Frames output:");
		lblFrames.setBounds(10, 10, 500, 15);
		contentPane.add(lblFrames);
		
		JTextField txtFrames = new JTextField();
		txtFrames.setBounds(10, 25, 500, 20);
		contentPane.add(txtFrames);
		txtFrames.setColumns(10);
		cfg.setFramesPathObject( txtFrames );
	}
	
	
	private void crearListaAnimaciones( )
	{
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(1151, 11, 164, 163);
		contentPane.add(scrollPane);
		
		listModel	= new DefaultListModel();
		list		= new JList( listModel );
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(878, 11, 164, 163);
		contentPane.add(scrollPane_1);
		
		JLabel lblAnimaciones = new JLabel("");
		lblAnimaciones.setFont(new Font("Ubuntu", Font.PLAIN, 12));
		lblAnimaciones.setVerticalAlignment(SwingConstants.TOP);
		scrollPane_1.setViewportView(lblAnimaciones);
		
		canim = new ContAnimaciones( list, listModel, lblAnimaciones );
		
		
		JButton btnAnimStart = new JButton("Comenzar");
		btnAnimStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				canim.startAction( );
			}
		});
		btnAnimStart.setBounds(1052, 11, 89, 23);
		contentPane.add(btnAnimStart);
		
		
		JButton btnNewButton_1 = new JButton("Ocultar");
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				canim.hideAction();
			}
		});
		btnNewButton_1.setBounds(1052, 39, 89, 23);
		contentPane.add(btnNewButton_1);
	}
	
	
	private void crearBarraMenu( )
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		
		JMenu mnVista = new JMenu("Vista");
		mnVista.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnVista);
		
		JCheckBoxMenuItem mchkDebug = new JCheckBoxMenuItem("Debug");
		mchkDebug.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				cfg.setDebug( mchkDebug.isSelected() );
			}
		});
		mchkDebug.doClick();
		mnVista.add(mchkDebug);
		
		JCheckBoxMenuItem mchkGrillaE = new JCheckBoxMenuItem("Grilla Euclidea");
		mchkGrillaE.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cfg.setGrillaEuclidea( mchkGrillaE.isSelected() );
			}
		});
		mchkGrillaE.doClick();
		mnVista.add(mchkGrillaE);
		
		JCheckBoxMenuItem mchkGrillaNumeros = new JCheckBoxMenuItem("Grilla N�meros");
		mchkGrillaNumeros.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cfg.setGrillaNumeros( mchkGrillaNumeros.isSelected() );
			}
		});
		mchkGrillaNumeros.doClick();
		mnVista.add(mchkGrillaNumeros);
		
		JCheckBoxMenuItem mchkGrilla = new JCheckBoxMenuItem("Grilla");
		mchkGrilla.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				cfg.setGrilla( mchkGrilla.isSelected() );
			}
		});
		mchkGrilla.doClick();
		mnVista.add(mchkGrilla);
		
		JCheckBoxMenuItem mchkEjes = new JCheckBoxMenuItem("Ejes");
		mchkEjes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cfg.setEjes( mchkEjes.isSelected() );
			}
		});
		mchkEjes.doClick();
		mnVista.add(mchkEjes);
		
		JCheckBoxMenuItem mchkOrigen = new JCheckBoxMenuItem("Origen");
		mchkOrigen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cfg.setOrigen( mchkOrigen.isSelected() );
			}
		});
		mchkOrigen.doClick();
		mnVista.add(mchkOrigen);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setMaximumSize(new Dimension(60, 32767));
		mntmSalir.setBackground(Color.LIGHT_GRAY);
		mntmSalir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		JMenu mnCfg = new JMenu("Configuraci\u00F3n");
		menuBar.add(mnCfg);
		
		JCheckBoxMenuItem mntmFrames = new JCheckBoxMenuItem("Guardar Frames de la Animaci\u00F3n");
		mntmFrames.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cfg.setFrames( mntmFrames.isSelected() );
			}
		});
		mntmFrames.doClick();
		mnCfg.add(mntmFrames);
		menuBar.add(mntmSalir);
	}
}
