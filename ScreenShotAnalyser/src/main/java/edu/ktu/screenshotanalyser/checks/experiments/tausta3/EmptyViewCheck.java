package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.List;
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

public class EmptyViewCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public EmptyViewCheck()
	{
		super(-1, "Empty View");
	}
	public double MinAllowedControls = 4;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state)).collect(Collectors.toList());;
		

		if (!EnoughControls(controls))
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;

			for (var control : controls)
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
			// System.out.println(state.getStateFile().toString());

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, "1", controls.size()));
		}
		
		//return null;
	}

	protected boolean EnoughControls(List<Control> controls)
	{
		return controls.size() >= MinAllowedControls;
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
