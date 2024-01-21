package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.androidpit.colorthief.ColorThief;

import org.colormine.ColorMine;
import org.colormine.colorspace.ColorSpaceConverter;

import org.openimaj.image.analysis.colour.CIEDE2000;

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

public class PoorChoiceOfColorsCheck extends BaseTextRuleCheck implements IStateRuleChecker
{
	public PoorChoiceOfColorsCheck()
	{
		super(-1, "Poor Choice Of Colors");
	}
	public double MinAllowedColorDifference = 20;
	public int MainColors = 5;

	@Override
	public void analyze(State state, ResultsCollector failures)
	{
		var image = state.getImage();
		if (BadColorsFound(image))
		{
			ResultImage resultImage = new ResultImage(state.getImageFile());

			resultImage.save(Settings.debugFolder + this.getRuleCode() + UUID.randomUUID().toString() + "1.png");
			failures.addFailure(new CheckResult(state, this, "Found bad colors", 1));
		}
		
	}

	protected boolean BadColorsFound(BufferedImage img)
	{
		var colors = ColorThief.getPalette(img, MainColors);
		
		for(var color1 : colors)
		{

			for(var color2 : colors)
			{
				if(color1 != color2)
				{
					if(CIEDE2000DifferenceBad(color1, color2))
					{
						return true;
					}
					
				}
			}
		}
		return false;
	}
	public boolean CIEDE2000DifferenceBad (int[] color1, int[] color2)
	{
		var lab1 = ColorSpaceConverter.colorToLab(new java.awt.Color(color1[0], color1[1], color1[2]));
		var lab2 = ColorSpaceConverter.colorToLab(new java.awt.Color(color2[0], color2[1], color2[2]));
		double difference = CIEDE2000.calculateDeltaE(lab1.L, lab1.A, lab1.B, lab2.L, lab2.A, lab2.B);
		if(difference < MinAllowedColorDifference) return true;
		
		else return false;
	}
}
