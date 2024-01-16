package edu.ktu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DroidBotRunner
	{
		private static boolean skipRestart = true;
		
		private static void uninstall(String name)
		{
			String command = "adb uninstall " + name;

			System.out.println("---------- unnstalling [" + command + "]");
			
			try
			{
				Process p = Runtime.getRuntime().exec(command);

				try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
				{
					String line;

					while ((line = input.readLine()) != null)
					{
						System.out.println(line);
					}
				}

			}
			catch (Throwable e1)
			{
				e1.printStackTrace();

				return;
			}
		}

		public static void runDroidBot(File folder, File apkFile) throws InterruptedException, ExecutionException
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now(); 
			

			File resultsFolder = new File(folder.getAbsolutePath() + "/DroidBotResults/" + dtf.format(now) + "/");

			if (false == resultsFolder.exists())
			{
				resultsFolder.mkdirs();
			}
			else if (new File(resultsFolder.getAbsolutePath() + "/states/").exists())
			{
				if (new File(resultsFolder.getAbsolutePath() + "/states/").list(new FilenameFilter()
				{
					@Override
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".jpg") || name.endsWith(".png");
					}
				}).length > 0)
				{	
					return;
				}



				
			}

			System.out.println("===== running " + apkFile.getName());

			try
			{


				if (false == skipRestart)
				{
					runCommand("adb reboot bootloader");
					Thread.sleep(60000);
				
					runCommand("adb kill-server");
					Thread.sleep(2000);

					runCommand("adb start-server");
					Thread.sleep(3000);
				}
				
				skipRestart = false;
				
				String command = "python \"" + folder.getAbsolutePath() + "\\droidbot\\start.py\" -a \"" + apkFile.getAbsolutePath().replaceAll("[ ]", "\\ ") + "\" -o \"" + resultsFolder.getAbsolutePath().replaceAll("[ ]", "\\ ") + "\" -keep_env -ignore_ad -timeout 300 -is_emulator";
				
				Process p = Runtime.getRuntime().exec(command);

				System.out.println(command);
				
				try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream())))
				{
					String line;

					while ((line = input.readLine()) != null)
					{
						System.out.println(line);

						if (line.contains("INSTALL_FAILED_NO_MATCHING_ABIS") || line.contains("DroidBot Stopped") || line.contains("Failed to disconnect DroidBotIME!"))
						{
							if (line.contains("INSTALL_FAILED_NO_MATCHING_ABIS"))
							{
								skipRestart = true;
		
							}

							System.out.println("------ terminate ------");

							new Thread(new Runnable()
							{
								public void run()
								{
									try
									{
										Thread.sleep(10000);

										p.destroy();
									}
									catch (Throwable ex)
									{
										ex.printStackTrace();
									}
								}
							}
							).start();
						}
					}
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			 
		}


	  // Implementing Fisher–Yates shuffle
	  static <T> T[] sa(T[] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      T a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }

	return ar;
	  }
	  
	  
		private static void runCommand(String command)
		{
			try
			{
			Process p = Runtime.getRuntime().exec(command);

			System.out.println(command);

			try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream())))
			{
				String line;

				while ((line = input.readLine()) != null)
				{
					System.out.println(line);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		}
	}