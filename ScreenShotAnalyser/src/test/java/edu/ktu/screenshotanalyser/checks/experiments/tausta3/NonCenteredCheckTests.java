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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.opencv.core.Rect;

import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;

public class NonCenteredCheckTests extends NonCenteredCheck
{
		@ParameterizedTest
		@MethodSource
		public void testControlCentered(Control control) throws IOException
		{
			boolean actual = NonCentered(control);
	
			Assert.assertEquals(false, actual);
		}
	
		private static Stream<Arguments> testControlCentered()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control Parent = new Control(null, null, null, new Rect(0,0,1000,1000), null, null, null, true, null);
			
			Control Child = createControl(200, 200, 600, 600);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, 0, 1000, 1000);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(210, 210, 600, 600);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(450, 450, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(480, 480, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testControlNotCentered(Control control) throws IOException
		{
			boolean actual = NonCentered(control);
	
			Assert.assertEquals(true, actual);
		}
	
		private static Stream<Arguments> testControlNotCentered()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control Parent = new Control(null, null, null, new Rect(0,0,1000,1000), null, null, null, true, null);
			
			Control Child = createControl(200, 200, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, 0, 800, 800);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(150, 150, 600, 600);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(600, 600, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(80, 80, 10, 10);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
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
			
			control = createControl(500, 500, 12, 9);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 500, 12, 9);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(500, 0, 12, 9);
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, null, null, new Rect(0,0,50,50), null, null, null, false, "");
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 2, 50);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 50, 2);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 2, 2);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(-51, 0, 50, 50);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, -51, 50, 50);
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, "Test Ad", null, new Rect(0,0,50,50), null, null, null, true, "");
			testCases.add(Arguments.of(control, true));
			
			control = new Control(null, null, null, new Rect(0,0,50,50), null, null, null, true, "addview");
			testCases.add(Arguments.of(control, true));
						
			control = createControl(0, 0, 50, 501);
			testCases.add(Arguments.of(control, true));
			
			control = createControl(0, 0, 501, 50);
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
			Control Parent;
			Control Child;
			State state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			List<Control> controls = new ArrayList<>();
			Parent = createControl(30, 40, 220, 150);
			Child = createControl(65, 80, 150, 70);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			controls.add(createControl(30, 240, 100, 50));
			
			failuresFound = false;
			numFailures = 0;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\NonCenteredTest1.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			Parent = createControl(35, 25, 345, 170);
			Child = createControl(50, 40, 312, 40);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			
			failuresFound = true;
			numFailures = 1;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\NonCenteredTest2.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			
			/*	state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			Parent = createControl(70, 100, 180, 60);
			Child = createControl(85, 120, 200, 50);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			Parent = createControl(70, 250, 200, 80);
			Child = createControl(40, 270, 204, 43);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			failuresFound = true;
			numFailures = 2;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InsufficientSpaceTest3.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));*/
			
			return testCases.stream();
		}
}
