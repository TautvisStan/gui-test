package edu.ktu.screenshotanalyser.checks;

import java.util.ArrayList;

import edu.ktu.screenshotanalyser.context.State;

/**
 * Collects all analysis results in a thread safe manner.
 */
public abstract class ResultsCollector
{
	protected ResultsCollector(boolean acceptsResultImages)
	{
		this.acceptsResultImages = acceptsResultImages;
		this.Results = new ArrayList<CheckResult>();
	}
	
	public synchronized void addFailure(CheckResult result)
	{
		if ((result.getMessage() != null) && (result.getMessage().length() > 0))
		{
			System.out.println(result.getMessage());
		}
		Results.add(result);
	}
	
	public abstract void addFailureImage(ResultImage image);

	public abstract boolean wasChecked(State state);

	public abstract void finishRun();
	
	public final boolean acceptsResultImages;
	
	public ArrayList<CheckResult> Results;
}
