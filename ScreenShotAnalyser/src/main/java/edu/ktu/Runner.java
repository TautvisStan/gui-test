package edu.ktu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import edu.ktu.screenshotanalyser.checks.DataBaseResultsCollector;
import edu.ktu.screenshotanalyser.checks.RulesSetChecker;
import edu.ktu.screenshotanalyser.checks.experiments.tausta3.*;
import edu.ktu.screenshotanalyser.tools.Settings;
import edu.ktu.screenshotanalyser.StartUp;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException
	{
		if(args[0].equals("Analyze"))
		{
			var checker = new RulesSetChecker();
			var appDirectory = args[1];
			var app = new File(appDirectory);
			for(int i = 2; i < args.length; i++)
			{
				if(args[i].equals("TooSmallControlCheck")) checker.addRule(new TooSmallControlCheck());
				if(args[i].equals("TooLargeControlCheck")) checker.addRule(new TooLargeControlCheck());
				if(args[i].equals("HiddenControlCheck")) checker.addRule(new HiddenControlCheck());
				if(args[i].equals("InsufficientSpaceCheck")) checker.addRule(new InsufficientSpaceCheck());
				if(args[i].equals("InvisibleControlCheck")) checker.addRule(new InvisibleControlCheck());
				if(args[i].equals("NoMarginsControlCheck")) checker.addRule(new NoMarginsControlCheck());
				if(args[i].equals("PoorChoiceOfColorsCheck")) checker.addRule(new PoorChoiceOfColorsCheck());
				if(args[i].equals("LowContrastCheck")) checker.addRule(new LowContrastCheck());
				if(args[i].equals("EmptyViewCheck")) checker.addRule(new EmptyViewCheck());
				if(args[i].equals("NonCenteredCheck")) checker.addRule(new NonCenteredCheck());
			}
			RunAnalyze(app, checker);
		}
		
		if(args[0].equals("DroidBot"))
		{
			DroidBotRunner.main(null);
		}

	}
	private static void RunAnalyze(File app, RulesSetChecker checker) throws IOException, InterruptedException, URISyntaxException
	{
		StartUp.enableLogs();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
		LocalDateTime now = LocalDateTime.now(); 
		
		String path = Runner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File jarFile = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String jarDir = jarFile.getParentFile().getAbsolutePath();
		
		File ssfolder = new File(jarDir + "/" + dtf.format(now));
		if(ssfolder.mkdir()) System.out.println("TRUE");
		else System.out.println("FALSE");
		
		
		Settings.debugFolder = ssfolder + "/";
		
		System.out.println(Settings.debugFolder);
		var failures = new DataBaseResultsCollector("sdssss", false);
		

		//checker.addRule(new UnalignedControlsCheck());    +
		//checker.addRule(new ClippedControlCheck());       +
		//checker.addRule(new ObscuredControlCheck());      +
		//checker.addRule(new WrongLanguageCheck());        +
		//checker.addRule(new ObscuredTextCheck());         +
		//checker.addRule(new GrammarCheck());              +
		//checker.addRule(new WrongEncodingCheck());        +
		//checker.addRule(new ClippedTextCheck());          +
		//checker.addRule(new UnlocalizedIconsCheck());     +
		//checker.addRule(new MissingTranslationCheck());   +
		//checker.addRule(new MixedLanguagesStateCheck());  +
		//checker.addRule(new MixedLanguagesAppCheck());    +
		//checker.addRule(new OffensiveMessagesCheck());    + 
		//checker.addRule(new UnreadableTextCheck());       +
		//checker.addRule(new TooHardToUnderstandCheck());  +
	//	checker.addRule(new MissingTextCheck());          //+
		
		
		var exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());		
		StartUp.runChecks(app, exec, checker, failures);
		
		exec.shutdown();
		exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		
	}
}