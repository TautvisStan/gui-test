package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import edu.ktu.screenshotanalyser.checks.BaseTextRuleCheck;
import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.IStateRuleChecker;
import edu.ktu.screenshotanalyser.checks.ResultImage;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.checks.StateCheckResults;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;
import edu.ktu.screenshotanalyser.tools.TextExtractor;

public class InvisibleControlCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public InvisibleControlCheck()
	{
		super(-1, "Invisible Control");
	}

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state)).collect(Collectors.toList());
		var invisible = new ArrayList<Control>();
		
		for (var control1 : controls)
		{
			if (outsideScreen(state, control1))
			{
				invisible.add(control1);
			}
			var found = false;
			
			for (var control2 : controls)
			{
				if (control1 != control2)
				{
					if (control1.getParent() == control2.getParent())
					{
						if (CoveredByAnother(control1, control2, state)) 
						{
							invisible.add(control2);
							found = true;
							break;
						}
					}
				}
			}
			if (found)
			{
				break;
			}
		}
		
		if (!invisible.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;

			for (var control : invisible)
			{
				if (i++ % 2 == 0)
				{
					resultImage.drawBounds(control.getBounds(), 255, 0, 0);
				}
				else
				{
					resultImage.drawBounds(control.getBounds(), 0, 255, 0);
				}
			}

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, "1", invisible.size()));
		}
		//return null;
	}

	public boolean contains(Rect r, Point p)
	{
		return r.x <= p.x && p.x <= r.x + r.width && r.y <= p.y && p.y <= r.y + r.height;
	}
	
	protected boolean outsideScreen(State state, Control control)
	{
		var screen = state.getImageSize();
		if(control.getBounds().tl().x >= screen.br().x)
		{
			return true;
		}
		if(control.getBounds().br().x <= screen.tl().x)
		{
			return true;
		}
		if(control.getBounds().tl().y >= screen.br().y)
		{
			return true;
		}
		if(control.getBounds().br().y <= screen.tl().y)
		{
			return true;
		}
		
		return false;
	}
	
	protected boolean CoveredByAnother(Control control1, Control control2, State state)
	{
		if (isInside(control1.getBounds(), control2.getBounds()))
		{
			if(state.getImageControls().stream().filter(p -> p.getBounds() == control2.getBounds()).collect(Collectors.toList()).isEmpty())
				return true;
		}
		return false;
		
	}
	
	protected boolean isInside(Rect a, Rect b)
	{
		if (!contains(a, b.tl()))
		{
			return false;
		}

		if (!contains(a, b.br()))
		{
			return false;
		}
		
		return true;
	}
	
	protected boolean shouldSkipControl(Control control, State state)
	{

		if (("Test Ad".equals(control.getText())) || (isAd(control)))
		{
			return true;
		}

		if (control.getSignature().contains("Layout"))
		{
			return true;
		}

		var bounds = control.getBounds();

		if ((bounds.width <= 3) || (bounds.height <= 5))
		{
			return true;
		}

		return false;
	}
		
}
