package edu.ktu.screenshotanalyser.checks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import edu.ktu.screenshotanalyser.context.Control;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;

public abstract class BaseRuleCheck
{
	protected BaseRuleCheck(long id, String ruleCode)
	{
		this.id = id;
		this.ruleCode = ruleCode;
	}
	
	public void logMessage(String message)
	{
		message = message.trim() + "\n";
		
		try
		{
			Path logFile = Paths.get("e:/log/" + this.ruleCode + ".txt");
			
			Files.write(logFile, message.getBytes(Charset.forName("utf-8")), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		}
		catch (IOException ex)
		{
			ex.printStackTrace(System.err);
		}
	}
	
	public String getRuleCode()
	{
		return this.ruleCode;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	protected Set<String> loadLastRun(String fileName, String prefix)
	{
		HashSet<String> lastFunFiles = new HashSet<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
		{
			String line = reader.readLine();

			while (line != null)
			{
				if (line.startsWith(prefix))
				{
					String file = line.substring(prefix.length());

					file = file.split(" ")[0];

					lastFunFiles.add(file);
				}

				line = reader.readLine();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();

			return null;
		}

		if (lastFunFiles.size() > 0)
		{
			return lastFunFiles;
		}
		else
		{
			return null;
		}
	}
	
	protected static String executeShellCommand(String... command)
	{
		var result = "";

		try
		{
			var process = Runtime.getRuntime().exec(command, new String[] { "PYTHONIOENCODING=utf8" }, null);

			try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
			{
				String line = null;

				while ((line = reader.readLine()) != null)
				{
					result += line;
				}
			}
			
			try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream())))
			{
				String line = null;

				while ((line = reader.readLine()) != null)
				{
					result += line;
				}
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

//		System.out.println("[" + result + "]");
		
		return result;
	}
	
	protected static boolean isAd(Control control)
	{
		if (null == control)
		{
			return false;
		}
		
		if (control.getSignature().contains("addview"))
		{
			return true;
		}
		else
		{
			return isAd(control.getParent());
		}
	}
	
	protected static void annotateDefectImage(State state, List<Control> controls)
	{
		var resultImage = new ResultImage(state.getImageFile());								
		
		for (var control : controls)
		{
			resultImage.drawBounds(control.getBounds());
		}
		
		resultImage.save(Settings.debugFolder + "a_" + UUID.randomUUID().toString() + "1.png");
	}
	
	private final String ruleCode;
	private final long id;
}
