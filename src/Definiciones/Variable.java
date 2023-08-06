package Definiciones;

public class Variable
{
	protected double	value,
						fixedChange;
	
	
	public Variable( double initialValue, double fixedChange )
	{
		this.value			= initialValue;
		this.fixedChange	= fixedChange;
	}


	public double getValue( )
	{
		return value;
	}
	
	public void modifyValue( double change )
	{
		value += change;
	}
	
	public void setValue( double value )
	{
		this.value = value;
	}
	
	
	public void setFixedChange( double val )
	{
		fixedChange = val;
	}
	
	public void update( )
	{
		value += fixedChange;
	}
}
