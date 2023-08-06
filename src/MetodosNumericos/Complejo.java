package MetodosNumericos;

public class Complejo
{
	////////////////////////////////////////////////////////////////////////////////
	
	protected double	re,
						im;

	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo( double re, double im )
	{
		this.re = re;
		this.im = im;
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public double re()
	{
		return re;
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public double im()
	{
		return im;
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo conjugado( )
	{
		return new Complejo(re, -im);
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo suma( Complejo c )
	{
		return new Complejo( re + c.re(), im + c.im() );
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo resta( Complejo c )
	{
		return new Complejo( re - c.re(), im - c.im() );
	}

	////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * (c1 * bi)(c + di) = ac * adi + bci + bdi*i = (ac - bd) + i*(ad + bc)
	 * 
	 * this:  a + b*i
	 * other: c + d*i
	 */
	public Complejo mult( Complejo c )
	{
		return new Complejo( (re * c.re() - im * c.im()), (re * c.im() + im * c.re()) );
	}

	////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * comp
	 *   this/c
	 */
	public Complejo div( Complejo c )
	{
		Complejo denominador = c.conjugado().mult( c );
		
		return new Complejo( (re * c.re() - im * c.im())  /  denominador.re(),
							(re * c.im() + im * c.re())  /  denominador.re() );
	}

	////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * computa
	 *   e ^ exp
	 *   
	 * e^(a+bi)  =  e^a * e^bi  =  (e^a) * (cos(b) + i*sen(b))
	 */
	public Complejo exp( Complejo exp )
	{
		double modulo = Math.exp( exp.re() );
		
		return new Complejo( modulo * Math.cos(exp.im()), modulo * Math.sin(exp.im()) );
	}

	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo exp( )
	{
		return exp( this );
	}
	

	////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * B^(a + bi)  =  e^ln(B^(a+bi))  =  e^ln(B)*(a+bi)  =  e^(ln(B)*a + ln(B)*b*i)
	 */
	public Complejo exp( double base, Complejo exp )
	{
		return exp( new Complejo(  Math.log(base)*exp.re(), Math.log(base)*exp.im()  ) );
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo sin( Complejo s )
	{
		Complejo res = exp( s.mult(new Complejo(0, 1))).resta(  exp(s.mult( new Complejo(0, -1) ))  );
		
		return res.div( new Complejo(0, 2) );
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public double modulo( )
	{
		return Math.sqrt( re*re + im*im );
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo gamma( Complejo s, int terminos )
	{
		Complejo res = new Complejo(1, 0).div(s),
				num,
				den;
		double n;
		
		for (int i = 1; i <= terminos; i ++)
		{
			n = 1.0 + (1.0 / i);
			num = exp(n, s);
			den = new Complejo( 1, 0 ).suma( s.div( new Complejo(i * 1.0, 0) ) );
			
			res = res.mult( num.div(den) );
			//System.out.printf("%.6f  /  %.6f  =  %.6f\n", num.re(), den.re(), num.div(den).re());
		}
		
		return res;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public Complejo zeta( Complejo s, int terminos )
	{
		Complejo res = new Complejo(0, 0);
		
		for (int i = 1; i <= terminos; i++)
		{
			res = res.suma( exp(1.0 / i, s) );
		}
		
		return res;
	}
	
	public Complejo zetaExtended( Complejo s, int terminos )
	{
		//							1    / (          1          -        2  ^  (         1 - s       ) )
		Complejo res = new Complejo(1, 0).div( new Complejo(1, 0).resta( exp(2, new Complejo(1, 0).resta(s)) ) ),
				sum = new Complejo(0, 0);
		
		int i	= terminos,
			sgn	= (terminos % 2 == 0) ? (-1) : (1);
		for ( ; i >= 1; i --)
		{
			if (sgn > 0)
				sum = sum.suma( exp(1.0 / i, s) );
			else
				sum = sum.resta( exp(1.0 / i, s) );
			
			sgn *= -1;
		}
		
		return res.mult(sum);
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public String toString( )
	{
		final int precision = 18;
		
		return String.format("%." + precision + "f + i*%." + precision + "f", re, im);
	}
	
	////////////////////////////////////////////////////////////////////////////////
}
