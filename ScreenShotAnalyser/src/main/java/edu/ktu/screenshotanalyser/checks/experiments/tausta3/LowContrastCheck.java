package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
import edu.ktu.screenshotanalyser.utils.ImageUtils;

public class LowContrastCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public LowContrastCheck()
	{
		super(-1, "Low Contrast");
	}
	public int BackgroundDistance = 10; //TODO: test % based distance
	public double MinContrastRatio = 1.5;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var controls = state.getActualControls().stream().filter(p -> !shouldSkipControl(p, state));

		var image = ImageUtils.matToBufferedImage(new ResultImage(state.getImageFile()).ReturnMat());
		
		var lowContrast = controls.filter(p -> hasLowContrast(p, image)).collect(Collectors.toList());

		if (!lowContrast.isEmpty())
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			int i = 0;

			for (var control : lowContrast)
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
			failures.addFailure(new CheckResult(state, this, "1", lowContrast.size()));
		}
	}

	
	protected boolean hasLowContrast(Control control, BufferedImage img)
	{
		Rect bounds = control.getBounds();
		BufferedImage controlImage = ImageUtils.BufferedImageSubimage(img, bounds);
		
		Color controlDominant = captureDominantColor(bounds, controlImage);
		double controlLuminance = getLuminance(controlDominant);

		int backgroundX = Math.max(0, bounds.x - BackgroundDistance);
        int backgroundY = Math.max(0, bounds.y - BackgroundDistance);
        int backgroundWidth = Math.min(img.getWidth() - backgroundX, bounds.width + BackgroundDistance);
        int backgroundHeight = Math.min(img.getHeight() - backgroundY, bounds.height + BackgroundDistance);
        Rect background = new Rect(backgroundX, backgroundY, backgroundWidth, backgroundHeight);
		BufferedImage backImage = ImageUtils.BufferedImageSubimage(img, new Rect(backgroundX, backgroundY, backgroundWidth, backgroundHeight));
		
		int controlx = bounds.x - backgroundX;
		int controly = bounds.y - backgroundY;
		
		Color backgroundDominant = captureDominantColorIgnoreArea(background, backImage, new Rect(controlx, controly, bounds.width, bounds.height));

		double backLuminance = getLuminance(backgroundDominant);
		
		double ratio = GetLuminanceRatio(controlLuminance, backLuminance);		if(ratio < MinContrastRatio) return true;
		else return false;
		
	}
	public double GetLuminanceRatio(double L1, double L2)
	{
		if(L1 > L2) return ((L1 + 0.05) / (L2 + 0.05));
		else return ((L2 + 0.05) / (L1 + 0.05));
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
	
	public double getLuminance(Color color) //WCAG2.1 regulations https://www.w3.org/TR/WCAG21/#dfn-relative-luminance
	{
		double r = colorInt_sRGB(color.r);
		double g = colorInt_sRGB(color.g);
		double b = colorInt_sRGB(color.b);
		return (r * 0.2126) + (g * 0.7152) + (b * 0.0722);
	}
	public double colorInt_sRGB(double channel)
	{
		
		//double oldcoef = 0.03928;
		double newcoef = 0.04045;
		
		double newchannel = channel/255;
		if ( newchannel <= newcoef ) {
            return newchannel / 12.92;
        } else {
            return Math.pow((( newchannel + 0.055)/1.055),2.4);
        }
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
