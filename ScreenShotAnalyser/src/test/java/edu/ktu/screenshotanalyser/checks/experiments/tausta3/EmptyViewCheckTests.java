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
public class EmptyViewCheckTests extends EmptyViewCheck
{
		@ParameterizedTest
		@MethodSource
		public void testControlsEnough(List<Control> controls) throws IOException
		{
			boolean actual = EnoughControls(controls);
	
			Assert.assertEquals(true, actual);
		}
	
		private static Stream<Arguments> testControlsEnough()
		{
			List<Arguments> testCases = new ArrayList<>();
			
			List<Control> controls = new ArrayList<>();
			
			
			
			
			controls.add(createControl(0, 0, 100, 100));
			controls.add(createControl(10, 10, 15, 15));
			controls.add(createControl(500, 500, 12, 12));
			controls.add(createControl(80, 80, 40, 11));
			testCases.add(Arguments.of(controls));
			
			controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			controls.add(createControl(10, 10, 15, 15));
			controls.add(createControl(500, 500, 12, 12));
			controls.add(createControl(80, 80, 40, 11));
			controls.add(createControl(68, 45, 11, 14));
			testCases.add(Arguments.of(controls));
			
			controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			controls.add(createControl(10, 10, 15, 15));
			controls.add(createControl(500, 500, 12, 12));
			controls.add(createControl(80, 80, 40, 11));
			controls.add(createControl(68, 45, 11, 14));
			controls.add(createControl(400, 0, 100, 0));
			testCases.add(Arguments.of(controls));
			
			
			return testCases.stream();
		}
		@ParameterizedTest
		@MethodSource
		public void testControlsTooLow(List<Control> controls) throws IOException
		{
			boolean actual = EnoughControls(controls);
			
			Assert.assertEquals(false, actual);
		}
	
		private static Stream<Arguments> testControlsTooLow()
		{
			List<Arguments> testCases = new ArrayList<>();
			
			List<Control> controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			controls.add(createControl(10, 10, 15, 15));
			controls.add(createControl(500, 500, 12, 12));
			testCases.add(Arguments.of(controls));
			
			controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			controls.add(createControl(10, 10, 15, 15));
			testCases.add(Arguments.of(controls));
			
			controls = new ArrayList<>();
			controls.add(createControl(0, 0, 100, 100));
			testCases.add(Arguments.of(controls));
			
			controls = new ArrayList<>();
			testCases.add(Arguments.of(controls));
			
			return testCases.stream();
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
		private static Control createControl(int x, int y, int width, int height)
		{
			return new Control(null, null, null, new Rect(x,y,width,height), null, null, null, true, "");
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
			controls.add(createControl(80, 80, 40, 12));
			controls.add(createControl(200, 200, 50, 5));
			failuresFound = true;
			numFailures = 2;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\EmptyViewTest1.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			controls.add(createControl(80, 80, 40, 12));
			controls.add(createControl(200, 200, 150, 75));
			controls.add(createControl(50, 300, 50, 20));
			failuresFound = true;
			numFailures = 3;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\EmptyViewTest2.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			
			
			state = mock(State.class);
			when(state.getImageSize()).thenReturn(new Rect(0,0, 480, 800));
			controls = new ArrayList<>();
			controls.add(createControl(80, 80, 40, 12));
			controls.add(createControl(200, 200, 50, 5));
			controls.add(createControl(80, 200, 5, 50));
			controls.add(createControl(200, 500, 50, 20));
			controls.add(new Control(null, "Test Ad", null, new Rect(0,0,50,5), null, null, null, true, ""));
			failuresFound = false;
			numFailures = 0;
			when(state.getActualControls()).thenReturn(controls);
			when(state.getImageFile()).thenReturn(new File("C:\\gui\\_test_\\EmptyViewTest3.png"));
			testCases.add(Arguments.of(state, failuresFound, numFailures));
			
			return testCases.stream();
		}
}
