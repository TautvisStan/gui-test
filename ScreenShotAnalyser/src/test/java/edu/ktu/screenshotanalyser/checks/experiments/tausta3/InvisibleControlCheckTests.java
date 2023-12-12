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
public class InvisibleControlCheckTests extends InvisibleControlCheck
{
		@Mock
	    State stateMock; 
		@ParameterizedTest
		@MethodSource
		public void testControlIsOutsideScreen(Control control) throws IOException
		{
			when(stateMock.getImageSize()).thenReturn(new Rect(0, 0, 500, 500));
			boolean actual = outsideScreen(stateMock, control);
	
			Assert.assertEquals(true, actual);
		}
	
		private static Stream<Arguments> testControlIsOutsideScreen()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control control = createControl(-100, -100, 50, 50);
			testCases.add(Arguments.of(control));

			control = createControl(0, -200, 50, 50);
			testCases.add(Arguments.of(control));

			control = createControl(-50, -50, 40, 40);
			testCases.add(Arguments.of(control));
			
			control = createControl(600, 600, 20, 20);
			testCases.add(Arguments.of(control));
			
			control = createControl(20, 505, 25, 50);
			testCases.add(Arguments.of(control));
			
			control = createControl(543, -50, 50, 25);
			testCases.add(Arguments.of(control));
			
			control = createControl(550, -150, 100, 100);
			testCases.add(Arguments.of(control));
			
			control = createControl(-150, 0, 100, 100);
			testCases.add(Arguments.of(control));
			
			control = createControl(-200, -200, 100, 300);
			testCases.add(Arguments.of(control));
			
			control = createControl(-200, 500, 52, 42);
			testCases.add(Arguments.of(control));
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testControlInsideAnother(Control control2) throws IOException
		{
			Control control1 = createControl(0, 0, 100, 100);
			final List<Control> controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			
			when(stateMock.getImageControls()).thenReturn(controls);
			boolean actual = CoveredByAnother(control1, control2, stateMock);
	
			Assert.assertEquals(true, actual);
		}
	
		private static Stream<Arguments> testControlInsideAnother()
		{
			List<Arguments> testCases = new ArrayList<>();

			Control control2 = createControl(0, 0, 50, 50);
			testCases.add(Arguments.of(control2));

			control2 = createControl(25, 25, 50, 50);
			testCases.add(Arguments.of(control2));

			control2 = createControl(10, 80, 50, 20);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(0, 0, 10, 10);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(80, 0, 20, 20);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(50, 50, 25, 25);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(41, 89, 2, 5);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(0, 5, 74, 13);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(35, 74, 2, 5);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(5, 2, 1, 2);
			testCases.add(Arguments.of(control2));
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testRectInsideAnother(Rect rect2, boolean expected) throws IOException
		{
			Rect rect1 = new Rect(0, 0, 100, 100);
			boolean actual = isInside(rect1, rect2);
	
			Assert.assertEquals(expected, actual);
		}
	
		private static Stream<Arguments> testRectInsideAnother()
		{
			List<Arguments> testCases = new ArrayList<>();

			Rect rect2 = new Rect(0, 101, 100, 100);
			testCases.add(Arguments.of(rect2, false));
			
			rect2 = new Rect(1, 1, 99, 99);
			testCases.add(Arguments.of(rect2, true));
			
			rect2 = new Rect(-1, -1, 100, 100);
			testCases.add(Arguments.of(rect2, false));
			
		    rect2 = new Rect(1, 1, 100, 100);
			testCases.add(Arguments.of(rect2, false));
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testControlAllFine(Control control2) throws IOException
		{
			Control control1 = createControl(0, 0, 100, 100);
			final List<Control> controls = new ArrayList<>();
			controls.add(control2);
			
			lenient().when(stateMock.getImageControls()).thenReturn(controls);
			when(stateMock.getImageSize()).thenReturn(new Rect(0, 0, 500, 500));
			boolean actual = CoveredByAnother(control1, control2, stateMock);
	
			Assert.assertEquals(false, actual);
			
			actual = outsideScreen(stateMock, control2);
			
			Assert.assertEquals(false, actual);
		}
	
		private static Stream<Arguments> testControlAllFine()
		{
			List<Arguments> testCases = new ArrayList<>();

			Control control2 = createControl(250, 250, 50, 50);
			testCases.add(Arguments.of(control2));

			control2 = createControl(0, 105, 50, 50);
			testCases.add(Arguments.of(control2));

			control2 = createControl(105, 0, 50, 20);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(50, 50, 10, 10);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(80, 0, 20, 20);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(0, 50, 25, 25);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(200, 200, 20, 50);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(125, 75, 74, 13);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(35, 74, 2, 5);
			testCases.add(Arguments.of(control2));
			
			control2 = createControl(5, 2, 1, 2);
			testCases.add(Arguments.of(control2));
			
			return testCases.stream();
		}
		private static Control createControl(int x, int y, int width, int height)
		{
			return new Control(null, null, null, new Rect(x,y,width,height), null, null, null, true, "");
		}
		@ParameterizedTest
		@MethodSource
		public void testShouldSkip(Control control, boolean result) throws IOException
		{
			State stateMock = mock(State.class);
			lenient().when(stateMock.getImageSize()).thenReturn(new Rect(0, 0, 500, 500));
			boolean actual = shouldSkipControl(control, stateMock);
	
			Assert.assertEquals(result, actual);
		}
		private static Stream<Arguments> testShouldSkip()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control control = createControl(0, 0, 10, 10);
			testCases.add(Arguments.of(control, false));

			control = createControl(10, 10, 9, 15);
			testCases.add(Arguments.of(control, false));
			
			control = createControl(80, 80, 8, 40);
			testCases.add(Arguments.of(control, false));
			
			control = createControl(68, 45, 83, 8);
			testCases.add(Arguments.of(control, false));
			
			control = createControl(0, 0, 2, 50);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 50, 2);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 2, 2);
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, "Test Ad", null, new Rect(0,0,50,50), null, null, null, true, "");
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, null, null, new Rect(0,0,50,50), null, null, null, true, "addview");
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, null, null, new Rect(0,0,50,50), null, null, null, true, "Layout");
			testCases.add(Arguments.of(control, true));
			
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
		private static Stream<Arguments> testRuleCheck()
		{
			nu.pattern.OpenCV.loadLocally();
			List<Arguments> testCases = new ArrayList<>();
			long numFailures;
			boolean failuresFound;
			
			State state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			List<Control> controls = new ArrayList<>();
			List<Control> ImageControls = new ArrayList<>();
			controls.add(createControl(40, 60, 200, 80));
			ImageControls.add(createControl(40, 60, 200, 80));
			controls.add(new Control(null, null, null, new Rect(270, 60, 201, 112), null, null, null, true, "addview"));
			ImageControls.add(new Control(null, null, null, new Rect(270, 60, 201, 112), null, null, null, true, "addview"));
			controls.add(createControl(40, 235, 200, 88));
			ImageControls.add(createControl(40, 235, 200, 88));
			failuresFound = false;
			numFailures = 0;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageControls()).thenReturn(ImageControls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InvisibleControlTest1.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			ImageControls = new ArrayList<>();
			controls.add(createControl(40, 60, 200, 80));
			ImageControls.add(createControl(40, 60, 200, 80));
			controls.add(new Control(null, null, null, new Rect(270, 60, 201, 112), null, null, null, true, "addview"));
			ImageControls.add(new Control(null, null, null, new Rect(270, 60, 201, 112), null, null, null, true, "addview"));
			controls.add(createControl(40, 235, 200, 88));
			ImageControls.add(createControl(40, 235, 200, 88));
			controls.add(createControl(-100, -50, 80, 20));
			controls.add(createControl(500, 200, 120, 40));
			failuresFound = true;
			numFailures = 2;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageControls()).thenReturn(ImageControls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InvisibleControlTest2.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
		
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			ImageControls = new ArrayList<>();
			controls.add(createControl(50, 180, 300, 100));
			ImageControls.add(createControl(50, 180, 300, 100));
			controls.add(createControl(20, 350, 400, 150));
			ImageControls.add(createControl(20, 350, 400, 150));
			controls.add(createControl(60, 390, 272, 79));
			failuresFound = true;
			numFailures = 1;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageControls()).thenReturn(ImageControls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InvisibleControlTest3.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			return testCases.stream();
		}
}

