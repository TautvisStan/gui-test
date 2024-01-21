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

public class InsufficientSpaceCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public InsufficientSpaceCheck()
	{
		super(-1, "Insufficient Space");
	}

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state)).filter(p -> p.getParent() != null);
		var insufficientSpaceControls = controls.filter(p -> notFitIn(p)).collect(Collectors.toList());

		if (!insufficientSpaceControls.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;
			String message = "";
			for (var control : insufficientSpaceControls)
			{
				if (i++ % 2 == 0)
				{
					resultImage.drawBounds(control.getBounds(), 255, 0, 0);
				}
				else
				{
					resultImage.drawBounds(control.getBounds(), 0, 255, 0);
				}
				message += "not fit in " + control.getBounds().toString() + " | " + control.getSignature() + " | " + control.getParent().getBounds().toString() + " | " + control.getParent().getSignature() + "/n";

			}
			// System.out.println(state.getStateFile().toString());

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, message, insufficientSpaceControls.size()));
		}
	//	return null;
	}

	protected boolean notFitIn(Control control)
	{
		if (!isInside(control.getParent().getBounds(), control.getBounds()))
		{

			return true;
		}

		return false;
	}
	public boolean contains(Rect r, Point p)
	{
		return r.x <= p.x && p.x <= r.x + r.width && r.y <= p.y && p.y <= r.y + r.height;
	}
	
	
	private boolean isInside(Rect a, Rect b)
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
