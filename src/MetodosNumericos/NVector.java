package MetodosNumericos;

public class NVector
{
	private double[] e;
	
	
	
	public NVector( double[] v )
	{
		e = new double[ v.length ];
		
		for (int i = 0; i < v.length; i ++)
			e[i] = v[i];
	}
	
	public NVector( int n )
	{
		e = new double[ n ];
	}
	
	
	
	public void setComponent( int i, double v )
	{
		e[i] = v;
	}
	
	public double getComponent( int i )
	{
		return e[i];
	}
	
	
	public int dim( )
	{
		return e.length;
	}
	
	
	public NVector add( NVector v )
	{
		NVector r = null;
		
		if (v.dim() == e.length)
		{
			double[] vr = new double[ e.length ];
			
			for (int i = 0; i < e.length; i ++)
				vr[i] = e[i] + v.getComponent(i);
			
			r = new NVector( vr );
		}
		
		return r;
	}
	
	public NVector substract( NVector v )
	{
		NVector r = null;
		
		if (v.dim() == e.length)
		{
			double[] vr = new double[ e.length ];
			
			for (int i = 0; i < e.length; i ++)
				vr[i] = e[i] - v.getComponent(i);
			
			r = new NVector( vr );
		}
		
		return r;
	}
	
	public NVector multiply( double k )
	{
		double[] vr = new double[ e.length ];
		
		for (int i = 0; i < e.length; i ++)
			vr[i] = e[i] * k;
		
		NVector r = new NVector( vr );
		
		return r;
	}
	
	
	public NVector clone( )
	{
		NVector r = new NVector( e );
		return r;
	}
}
