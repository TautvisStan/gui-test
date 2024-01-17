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
import edu.ktu.screenshotanalyser.checks.experiments.*;
import edu.ktu.screenshotanalyser.tools.Settings;
import edu.ktu.screenshotanalyser.StartUp;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, ExecutionException
	{
		Settings.JarFolder = GetJarFolder();
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
				
				if(args[i].equals("MissingTextCheck")) checker.addRule(new MissingTextCheck());
				if(args[i].equals("UnreadableTextCheck")) checker.addRule(new UnreadableTextCheck());
				if(args[i].equals("OffensiveMessagesCheck")) checker.addRule(new OffensiveMessagesCheck());
				if(args[i].equals("UnalignedControlsCheck")) checker.addRule(new UnalignedControlsCheck());
				if(args[i].equals("ClippedControlCheck")) checker.addRule(new ClippedControlCheck());
				if(args[i].equals("ObscuredControlCheck")) checker.addRule(new ObscuredControlCheck());
				if(args[i].equals("WrongLanguageCheck")) checker.addRule(new WrongLanguageCheck());
				if(args[i].equals("ObscuredTextCheck")) checker.addRule(new ObscuredTextCheck());
				if(args[i].equals("GrammarCheck")) checker.addRule(new GrammarCheck());
				if(args[i].equals("WrongEncodingCheck")) checker.addRule(new WrongEncodingCheck());
				if(args[i].equals("UnlocalizedIconsCheck")) checker.addRule(new UnlocalizedIconsCheck());
				if(args[i].equals("MissingTranslationCheck")) checker.addRule(new MissingTranslationCheck());
				if(args[i].equals("MixedLanguagesStateCheck")) checker.addRule(new MixedLanguagesStateCheck());
				if(args[i].equals("MixedLanguagesAppCheck")) checker.addRule(new MixedLanguagesAppCheck());
				if(args[i].equals("TooHardToUnderstandCheck")) checker.addRule(new TooHardToUnderstandCheck());
			}
			RunAnalyze(app, checker);
		}
		
		if(args[0].equals("DroidBot"))
		{
			File APK = new File(args[1]);
			File Folder = new File(GetJarFolder());
			DroidBotRunner.runDroidBot(Folder, APK);
		}

	}
	private static String GetJarFolder() throws URISyntaxException
	{
		
		File jarFile = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String jarDir = jarFile.getParentFile().getAbsolutePath();
		
		
		return jarDir;
	}
	private static void RunAnalyze(File app, RulesSetChecker checker) throws IOException, InterruptedException, URISyntaxException
	{
		StartUp.enableLogs();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
		LocalDateTime now = LocalDateTime.now(); 
				
		File ssfolder = new File(GetJarFolder() + "/AnalyzeResults/" + dtf.format(now));
		ssfolder.mkdirs();
		
		Settings.debugFolder = ssfolder + "/";
		
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