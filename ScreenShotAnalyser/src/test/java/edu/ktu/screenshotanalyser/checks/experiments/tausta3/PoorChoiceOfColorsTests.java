package edu.ktu.screenshotanalyser.checks.experiments.tausta3;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opencv.core.Rect;

import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;

@ExtendWith(MockitoExtension.class)
public class PoorChoiceOfColorsTests extends PoorChoiceOfColorsCheck
{
		@ParameterizedTest
		@MethodSource
		public void testBadColors(int[] color2) throws IOException
		{
			int[] color1 = new int[3];
			color1[0] = 150;
			color1[1] = 50;
			color1[2] = 200;
			boolean actual = CIEDE2000DifferenceBad(color1, color2);
	
			Assert.assertEquals(true, actual);
		}
		private static Stream<Arguments> testBadColors()
		{
			List<Arguments> testCases = new ArrayList<>();
			int[] color2 = new int[3];
			color2[0] = 150;
			color2[1] = 50;
			color2[2] = 200;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 150;
			color2[1] = 0;
			color2[2] = 200;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 0;
			color2[1] = 50;
			color2[2] = 200;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 100;
			color2[1] = 25;
			color2[2] = 150;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 50;
			color2[1] = 50;
			color2[2] = 255;
			testCases.add(Arguments.of(color2));
			
						
			return testCases.stream();
		}
	
	
	
		@ParameterizedTest
		@MethodSource
		public void testGoodColors(int[] color2) throws IOException
		{
			int[] color1 = new int[3];
			color1[0] = 150;
			color1[1] = 50;
			color1[2] = 200;
			boolean actual = CIEDE2000DifferenceBad(color1, color2);
	
			Assert.assertEquals(false, actual);
		}
		private static Stream<Arguments> testGoodColors()
		{
			List<Arguments> testCases = new ArrayList<>();
			int[] color2 = new int[3];
			color2[0] = 255;
			color2[1] = 255;
			color2[2] = 255;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 0;
			color2[1] = 0;
			color2[2] = 0;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 0;
			color2[1] = 255;
			color2[2] = 0;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 75;
			color2[1] = 75;
			color2[2] = 75;
			testCases.add(Arguments.of(color2));
			color2 = new int[3];
			color2[0] = 210;
			color2[1] = 71;
			color2[2] = 35;
			testCases.add(Arguments.of(color2));
			
						
			return testCases.stream();
		}
		

		@ParameterizedTest
		@MethodSource
		public void testRuleCheck(State state, boolean failuresFound, long numFailures)
		{
			ResultsCollector failures = mock(ResultsCollector.class);
			analyze(state, failures);
			if(failuresFound)
			{
				 ArgumentCaptor<CheckResult> checkResultCaptor = ArgumentCaptor.forClass(CheckResult.class);
				 verify(failures, times(1)).addFailure(checkResultCaptor.capture());
				 CheckResult capturedCheckResult = checkResultCaptor.getValue();
				 assertEquals(capturedCheckResult.getDefectsCount(), numFailures);
			}
			else verify(failures,times(0)).addFailure(any(CheckResult.class));
			
		}
		private static Stream<Arguments> testRuleCheck() throws IOException
		{
			nu.pattern.OpenCV.loadLocally();
			List<Arguments> testCases = new ArrayList<>();
			long numFailures;
			boolean failuresFound;
			
			State state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			failuresFound = false;
			numFailures = 0;
			when(state.getImage()).thenReturn(ImageIO.read(new File("C:\\gui\\_test_\\PoorChoiceOfColorsTest1.png")));
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\PoorChoiceOfColorsTest1.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			failuresFound = true;
			numFailures = 1;
			when(state.getImage()).thenReturn(ImageIO.read(new File("C:\\gui\\_test_\\PoorChoiceOfColorsTest2.png")));
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\PoorChoiceOfColorsTest2.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			return testCases.stream();
		}
}

