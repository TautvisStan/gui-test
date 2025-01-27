package edu.ktu;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.ktu.screenshotanalyser.checks.DataBaseResultsCollector;
import edu.ktu.screenshotanalyser.checks.RulesSetChecker;
import edu.ktu.screenshotanalyser.checks.experiments.tausta3.*;
import edu.ktu.screenshotanalyser.checks.experiments.*;
import edu.ktu.screenshotanalyser.tools.Settings;
import edu.ktu.screenshotanalyser.StartUp;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import edu.ktu.PDFGenerator;
public class Runner {

	
	
	// move apk to droidbot folder; GENERATE dev.txt IN DROIDBOT FOLDER
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, ExecutionException
	{
		Settings.JarFolder = GetJarFolder();
		if(args[0].equals("Analyze"))
		{
			var checker = new RulesSetChecker();
			var appDirectory = args[1];
			var app = new File(appDirectory);
			System.out.println("Analyzing " + appDirectory + " with these rules:");
			String rules = "";
			for(int i = 2; i < args.length; i++)
			{
				System.out.println(args[i]);
				rules += args[i] + " ";
				if(args[i].equals("TooSmallControlCheck")) checker.addRule(new TooSmallControlCheck());//++
				if(args[i].equals("TooLargeControlCheck")) checker.addRule(new TooLargeControlCheck());//++
				if(args[i].equals("HiddenControlCheck")) checker.addRule(new HiddenControlCheck());//++
				if(args[i].equals("InsufficientSpaceCheck")) checker.addRule(new InsufficientSpaceCheck());//++
				if(args[i].equals("InvisibleControlCheck")) checker.addRule(new InvisibleControlCheck());//++
				if(args[i].equals("NoMarginsControlCheck")) checker.addRule(new NoMarginsControlCheck());//++
				if(args[i].equals("PoorChoiceOfColorsCheck")) checker.addRule(new PoorChoiceOfColorsCheck());//++
				if(args[i].equals("LowContrastCheck")) checker.addRule(new LowContrastCheck());//++
				if(args[i].equals("EmptyViewCheck")) checker.addRule(new EmptyViewCheck());//++
				if(args[i].equals("NonCenteredCheck")) checker.addRule(new NonCenteredCheck());//++
				
				//PUT APK IN DROIDBOT RESULTS FOLDER
				if(args[i].equals("MissingTextCheck")) checker.addRule(new MissingTextCheck());//++
				if(args[i].equals("UnreadableTextCheck")) checker.addRule(new UnreadableTextCheck());//++
				if(args[i].equals("OffensiveMessagesCheck")) checker.addRule(new OffensiveMessagesCheck());//~~~~ Too long cmd python command
				if(args[i].equals("UnalignedControlsCheck")) checker.addRule(new UnalignedControlsCheck());//++       high memory usage
				if(args[i].equals("ClippedControlCheck")) checker.addRule(new ClippedControlCheck());//++
				if(args[i].equals("ObscuredControlCheck")) checker.addRule(new ObscuredControlCheck());//++ ?
				if(args[i].equals("WrongLanguageCheck")) checker.addRule(new WrongLanguageCheck());//++ high mem usage
				if(args[i].equals("ObscuredTextCheck")) checker.addRule(new ObscuredTextCheck());//++
				
				if(args[i].equals("GrammarCheck")) checker.addRule(new GrammarCheck());  //++ 
				if(args[i].equals("WrongEncodingCheck")) checker.addRule(new WrongEncodingCheck()); //++
				if(args[i].equals("UnlocalizedIconsCheck")) checker.addRule(new UnlocalizedIconsCheck()); //++?   modified langs at the bottom
				if(args[i].equals("MissingTranslationCheck")) checker.addRule(new MissingTranslationCheck()); //++   mid mem usage
				if(args[i].equals("MixedLanguagesStateCheck")) checker.addRule(new MixedLanguagesStateCheck());//++ high mem usage
				if(args[i].equals("MixedLanguagesAppCheck")) checker.addRule(new MixedLanguagesAppCheck());//++ high mem
				if(args[i].equals("TooHardToUnderstandCheck")) checker.addRule(new TooHardToUnderstandCheck());//++ check the metrics to improve?
			}
			RunAnalyze(app, checker, rules);

			
		}
		
		if(args[0].equals("DroidBot"))
		{
			
			File APK = new File(args[1]);
			File Folder = new File(GetJarFolder());
			System.out.println("Running DroidBot with this apk: " + APK);
			int DPI = Integer.parseInt(args[2]);
			int Width = Integer.parseInt(args[3]);
			int Height = Integer.parseInt(args[4]);
			int i = 5;
			boolean emulator = false;
			int timeoutseconds = 0;
			if(args[i].equals("-Emulator"))
			{
				emulator = true;
				i++;
			}
			if(args[i].equals("-Timeout"))
			{
				i++;
				timeoutseconds = Integer.parseInt(args[i]);
				i++;
			}
			DroidBotRunner.runDroidBot(Folder, APK, DPI, Width, Height, emulator, timeoutseconds);
			
		}
		System.out.println("The process has ended. Press Enter to close it.");
		System.in.read();

	}
	private static String GetJarFolder() throws URISyntaxException
	{
		
		File jarFile = new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String jarDir = jarFile.getParentFile().getAbsolutePath();
		
		
		return jarDir;
	}
	private static void RunAnalyze(File app, RulesSetChecker checker, String rules) throws IOException, InterruptedException, URISyntaxException
	{
		StartUp.enableLogs();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
		LocalDateTime now = LocalDateTime.now(); 
				
		File ssfolder = new File(GetJarFolder() + "/AnalyzeResults/" + dtf.format(now));
		ssfolder.mkdirs();
		
		Settings.debugFolder = ssfolder + "/";
		Settings.appImagesFolder = app;
		var failures = new DataBaseResultsCollector("sdssss", false);
		
		var exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());		
		StartUp.runChecks(app, exec, checker, failures);
		
		exec.shutdown();
		exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		Desktop.getDesktop().open(ssfolder);
		
		//String SelectedRules
		PDFGenerator.GeneratePdf(ssfolder, dtf.format(now), app, rules, failures);
		
	}
}