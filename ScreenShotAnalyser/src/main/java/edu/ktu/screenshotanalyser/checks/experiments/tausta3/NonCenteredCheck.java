package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

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

public class NonCenteredCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public NonCenteredCheck()
	{
		super(-1, "Non-Centered");
	}
	public double MaxCenterDistance = 50;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state)).filter(p -> p.getParent() != null);
		var NonCenteredControls = controls.filter(p -> NonCentered(p)).collect(Collectors.toList());

		if (!NonCenteredControls.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;
			String message = "";
			for (var control : NonCenteredControls)
			{
				if (i++ % 2 == 0)
				{
					resultImage.drawBounds(control.getBounds(), 255, 0, 0);
				}
				else
				{
					resultImage.drawBounds(control.getBounds(), 0, 255, 0);
				}
				message += "Non centered " + control.getBounds().toString() + " | " + control.getSignature() + "\n";
			}

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, message, NonCenteredControls.size()));
		}
		
		//return null;
	}

	protected boolean NonCentered(Control control)
	{
		Control p = control.getParent();
		Point pcenter = CalculateCentroid(p.getBounds());
		Point ccenter = CalculateCentroid(control.getBounds());
		double xDiff = pcenter.x - ccenter.x;
        double yDiff = pcenter.y - ccenter.y;
        double distance = Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff, 2));
        return distance > MaxCenterDistance;
	}
	public Point CalculateCentroid(Rect rect)
	{
		double centerX = rect.x + 0.5 * rect.width;
		double centerY = rect.y + 0.5 * rect.height;
		return new Point(centerX, centerY);
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

		var bounds = control.getBounds();


		if ((bounds.width <= 3) || (bounds.height <= 3))
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
