package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ;
import de.androidpit.colorthief.MMCQ.CMap;

import org.colormine.ColorMine;

import edu.ktu.screenshotanalyser.checks.BaseTextRuleCheck;
import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.IStateRuleChecker;
import edu.ktu.screenshotanalyser.checks.ResultImage;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.checks.StateCheckResults;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.Color;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;
import edu.ktu.screenshotanalyser.utils.LazyObject;
import edu.ktu.screenshotanalyser.utils.ImageUtils;

public class HiddenControlCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public HiddenControlCheck()
	{
		super(-1, "Hidden Control");
	}
	public int BackgroundDistance = 25; //TODO: test % based distance
	public double MinAllowedColorDifference = 10;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state));

		var image = ImageUtils.matToBufferedImage(new ResultImage(state.getImageFile()).ReturnMat());
		
		var hiddenControls = controls.filter(p -> isHidden(p, image)).collect(Collectors.toList());

		if (!hiddenControls.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;

			for (var control : hiddenControls)
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
			failures.addFailure(new CheckResult(state, this, "1", hiddenControls.size()));
		}
	}

	protected boolean isHidden(Control control, BufferedImage img)
	{

		Rect bounds = control.getBounds();
		BufferedImage controlImage = ImageUtils.BufferedImageSubimage(img, bounds);
		
		Color controlDominant1 = captureDominantColor(bounds, controlImage);
		
		int backgroundX = Math.max(0, bounds.x - BackgroundDistance);
        int backgroundY = Math.max(0, bounds.y - BackgroundDistance);
        int backgroundWidth = Math.min(img.getWidth() - backgroundX, bounds.width + BackgroundDistance);
        int backgroundHeight = Math.min(img.getHeight() - backgroundY, bounds.height + BackgroundDistance);
        Rect background = new Rect(backgroundX, backgroundY, backgroundWidth, backgroundHeight);
		BufferedImage backImage = ImageUtils.BufferedImageSubimage(img, new Rect(backgroundX, backgroundY, backgroundWidth, backgroundHeight));
		
		int controlx = bounds.x - backgroundX;
		int controly = bounds.y - backgroundY;
		
		Color backgroundDominant = captureDominantColorIgnoreArea(background, backImage, new Rect(controlx, controly, bounds.width, bounds.height));
		
		double difference = ColorDifference(controlDominant1, backgroundDominant);
		
		if(difference < MinAllowedColorDifference) return true;
		else return false;
	}
	public Color captureDominantColor(Rect bounds, BufferedImage image)
	{
		var color = ColorThief.getColor(image);
		return new Color(color[0], color[1], color[2]);
		
	}
	public Color captureDominantColorIgnoreArea(Rect bounds, BufferedImage image, Rect ignore)
	{
		var color = ColorThief.getColorIgnoreArea(image, ignore);
		return new Color(color[0], color[1], color[2]);
	}
	
	public double ColorDifference (Color color1, Color color2) //CIE76
	{
		return ColorMine.calculateSimilarity(new java.awt.Color(color1.r, color1.g, color1.b), new java.awt.Color(color2.r, color2.g, color2.b));
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
