package edu.ktu.screenshotanalyser.checks.experiments;

import java.util.UUID;
import java.util.stream.Collectors;
import edu.ktu.screenshotanalyser.checks.BaseTextRuleCheck;
import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.IStateRuleChecker;
import edu.ktu.screenshotanalyser.checks.ResultImage;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;

public class ClippedControlCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public ClippedControlCheck()
	{
		super(11, "Clipped Control");
	}

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state));
		var clippedControls = controls.filter(p -> isClipped(p, state)).collect(Collectors.toList());

		if (!clippedControls.isEmpty())
		{
			
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;
			String message = "";
			for (var control : clippedControls)
			{
				if (i++ % 2 == 0)
				{
					resultImage.drawBounds(control.getBounds(), 255, 0, 0);
				}
				else
				{
					resultImage.drawBounds(control.getBounds(), 0, 255, 0);
				}
				message += "clipped " + control.getBounds().toString() + " | " + control.getSignature() + " | " + (control.getBounds().x + control.getBounds().width) + " > " + state.getImageSize().width + " | " + (control.getBounds().y + control.getBounds().height) + " > " + state.getImageSize().height +"\n";
				
			}
			// System.out.println(state.getStateFile().toString());
			failures.addFailure(new CheckResult(state, this, message, clippedControls.size()));
			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
		}
	}

	private boolean isClipped(Control control, State state)
	{
		if ((control.getBounds().x + control.getBounds().width > state.getImageSize().width + 5) || (control.getBounds().y + control.getBounds().height > state.getImageSize().height + 5))
		{

			return true;
		}

		return false;
	}

	private boolean shouldSkipControl(Control control, State state)
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

		// if ((control.getBounds().x + control.getBounds().width >= state.getImageSize().width) || (control.getBounds().y + control.getBounds().height >= state.getImageSize().height))
		// {
		// return true;
		// }

		return false;
	}
}
