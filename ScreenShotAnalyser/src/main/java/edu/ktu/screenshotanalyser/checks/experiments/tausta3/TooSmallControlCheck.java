package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.UUID;
import java.util.stream.Collectors;
import edu.ktu.screenshotanalyser.checks.BaseRuleCheck;
import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.IStateRuleChecker;
import edu.ktu.screenshotanalyser.checks.ResultImage;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.checks.StateCheckResults;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;

public class TooSmallControlCheck extends BaseRuleCheck implements IStateRuleChecker
{
	public TooSmallControlCheck()
	{
		super(-1, "Small Control");
	}
	public int NotAllowedSize = 10;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state));
		var smallControls = controls.filter(p -> isTooSmall(p)).collect(Collectors.toList());

		if (!smallControls.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;

			for (var control : smallControls)
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
			failures.addFailure(new CheckResult(state, this, "1", smallControls.size()));
		}
		//return null;
	}

	protected boolean isTooSmall(Control control)
	{
		if ((control.getBounds().width <= NotAllowedSize) || (control.getBounds().height <= NotAllowedSize))
		{
			System.out.println("too small " + control.getBounds().toString() + " | " + control.getSignature() + " | " + control.getBounds().width + "x" + control.getBounds().height);

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

		var bounds = control.getBounds();

		if ((bounds.width <= 0) || (bounds.height <= 0))
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
