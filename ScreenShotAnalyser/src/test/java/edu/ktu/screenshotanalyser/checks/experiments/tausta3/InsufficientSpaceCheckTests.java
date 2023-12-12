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

public class InsufficientSpaceCheckTests extends InsufficientSpaceCheck
{
		@ParameterizedTest
		@MethodSource
		public void testControlFitsIn(Control control) throws IOException
		{
			boolean actual = notFitIn(control);
	
			Assert.assertEquals(false, actual);
		}
	
		private static Stream<Arguments> testControlFitsIn()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control Parent = new Control(null, null, null, new Rect(0,0,1000,1000), null, null, null, true, null);
			
			Control Child = createControl(50, 50, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, 0, 10, 10);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(400, 400, 50, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(300, 800, 100, 200);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, 0, 1000, 1000);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testControlDoesNotFitIn(Control control) throws IOException
		{
			boolean actual = notFitIn(control);
	
			Assert.assertEquals(true, actual);
		}
	
		private static Stream<Arguments> testControlDoesNotFitIn()
		{
			List<Arguments> testCases = new ArrayList<>();
			Control Parent = new Control(null, null, null, new Rect(0,0,1000,1000), null, null, null, true, null);
			
			Control Child = createControl(-50, -50, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, 0, 1001, 1001);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(1100, 1100, 10, 10);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(1001, 10, 85, 42);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(456, 1200, 78, 24);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(990, 990, 11, 11);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(-100, 0, 50, 50);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(0, -50, 100, 100);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(-100, 1000, 45, 68);
			Child.setParent(Parent);
			testCases.add(Arguments.of(Child));
			
			Child = createControl(456, 875, 41, 452);
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
			Parent = createControl(35, 80, 250, 150);
			Child = createControl(60, 100, 190, 100);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			controls.add(createControl(50, 275, 185, 70));
			
			failuresFound = false;
			numFailures = 0;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InsufficientSpaceTest1.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			Parent = createControl(70, 100, 180, 60);
			Child = createControl(85, 120, 200, 50);
			Child.setParent(Parent);
			controls.add(Parent);
			controls.add(Child);
			controls.add(new Control(null, "Test Ad", null, new Rect(70,250,200,80), null, null, null, true, ""));
			
			failuresFound = true;
			numFailures = 1;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\InsufficientSpaceTest2.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			
			state = mock(State.class);
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
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			return testCases.stream();
		}
}
