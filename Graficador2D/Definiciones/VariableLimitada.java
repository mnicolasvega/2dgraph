package Definiciones;

public class VariableLimitada extends Variable
{
	private double endValue;

	public VariableLimitada(double initialValue, double endValue, double fixedChange)
	{
		super(initialValue, fixedChange);
		
		this.endValue = endValue;
	}
	
	public void update( )
	{
		if (fixedChange != 0)
		{
			value += fixedChange;
			
			if (fixedChange > 0 && value >= endValue)
			{
				value = endValue;
				fixedChange = 0;
			}
			else if (fixedChange < 0 && value <= endValue)
			{
				value = endValue;
				fixedChange = 0;
			}
		}
	}

}
