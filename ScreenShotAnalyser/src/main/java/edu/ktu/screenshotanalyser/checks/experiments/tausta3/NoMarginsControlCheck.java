package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.awt.Rectangle;
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

public class NoMarginsControlCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public NoMarginsControlCheck()
	{
		super(-1, "No margins");
	}

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state)).collect(Collectors.toList());
		var nomargins = new ArrayList<Control>();
		
		for (var control1 : controls)
		{
			
			var found = false;
			
			for (var control2 : controls)
			{
				if (control1 != control2)
				{
					if (control1.getParent() == control2.getParent())
					{
						if (noMargins(control1.getBounds(), control2.getBounds())) 
						{

							if (!control2.getSignature().contains(".View"))
							{	
							nomargins.add(control1);
							nomargins.add(control2);
											
							
							found = true;
							
							break;
							}
						}
					}
				}
			}
			
			if (found)
			{
				break;
			}
		}
		
			if (!nomargins.isEmpty())
			{
				var resultImage = new ResultImage(state.getImageFile());
				var i = 0;
				String message = "";

				for (var control : nomargins)
				{
					if (i++ % 2 == 0)
					{
						resultImage.drawBounds(control.getBounds(), 255, 0, 0);
					}
					else
					{
						resultImage.drawBounds(control.getBounds(), 0, 255, 0);
					}
					message = "No margins " + control.getBounds().toString() + " | " + control.getSignature() + "\n";
				}

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, message, nomargins.size()));

		}
		//return null;
	}
	protected boolean noMargins(Rect a, Rect b)
	{
		if(b.tl().x == a.br().x && ( (b.tl().y >= a.tl().y && b.tl().y <= a.br().y) || (b.tl().y + b.height >= a.tl().y && b.tl().y + b.height <= a.br().y) ))
		{
			return true;
		}
		if(a.tl().x == b.br().x && ( (a.tl().y >= b.tl().y && a.tl().y <= b.br().y) || (a.tl().y + a.height >= b.tl().y && a.tl().y + a.height <= b.br().y) ))
		{
			return true;
		}
		
		if(b.tl().y == a.br().y && ( (b.tl().x >= a.tl().x && b.tl().x <= a.br().x) || (b.tl().x + b.width >= a.tl().x && b.tl().x + b.height <= a.br().x) ))
		{
			return true;
		}
		if(a.tl().y == b.br().y && ( (a.tl().x >= b.tl().x && a.tl().x <= b.br().x) || (a.tl().x + a.width >= b.tl().x && a.tl().x + a.height <= b.br().x) ))
		{
			return true;
		}
		return false;
	}
	
	protected boolean shouldSkipControl(Control control, State state)
	{
		if (!control.isVisible())
		{
			return true;
		}

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

		if ((bounds.x >= state.getImageSize().width) || (bounds.y >= state.getImageSize().height))
		{
			return true;
		}

		if ((bounds.x + bounds.width <= 0) || (bounds.y + bounds.height <= 0))
		{
			return true;
		}

		 if ((control.getBounds().x + control.getBounds().width >= state.getImageSize().width) || (control.getBounds().y + control.getBounds().height >= state.getImageSize().height))
		 {
		 return true;
		 }

		return false;
	}
	
}
