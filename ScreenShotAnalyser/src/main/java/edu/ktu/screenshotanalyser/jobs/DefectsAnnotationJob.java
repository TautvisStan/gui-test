package edu.ktu.screenshotanalyser.jobs;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.opencv.core.Core;
import edu.ktu.screenshotanalyser.checks.IStateRuleChecker;
import edu.ktu.screenshotanalyser.checks.ResultImage;
import edu.ktu.screenshotanalyser.checks.IResultsCollector;
import edu.ktu.screenshotanalyser.context.DefaultContextProvider;
import edu.ktu.screenshotanalyser.context.State;
import edu.ktu.screenshotanalyser.tools.Settings;

public class DefectsAnnotationJob implements Runnable
{
	public static void main(String[] args) throws IOException, SQLException
	{
		new DefectsAnnotationJob().run();
	}
	
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);				
	}	
	
	private DefectsAnnotationJob() throws IOException, SQLException
	{
		 this.contextProvider = new DefaultContextProvider(Settings.appImagesFolder);		
	}
	
	public void run()
	{
		while (this.running)
		{
			try
			{
				var task = getAnnotationTask();
				
				if (null != task)
				{
		/////			var collector = new ImagesLogger(task.testRunDefectImageId());
					
		/////////////			var result = task.checker().analyze(task.state());
					
	///////////////////////				if (null != result)
					{
			//////////////			collector.addFailure(result);
					}
				}
				else
				{
					System.out.println("idle...");
					Thread.sleep(1000);
				}
			}
			catch (InterruptedException | IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private record AnnotationTask(IStateRuleChecker checker, State state, Long testRunDefectImageId)
	{
	}
	
	private AnnotationTask getAnnotationTask() throws IOException
	{
    try (var connection = DriverManager.getConnection(this.connectionUrl)) 
    {
    	try (var statement = connection.prepareStatement(""
    			+ ""
    			+ ""
    			+ "SELECT TOP 1 TestRunDefect.DefectTypeId, [Application].ApkFile, ScreenShot.FileName, TestRunDefectImage.Id "
    			+ "\r\n"
    			+ "\r\n"
    			+ "\r\n"
    			+ "FROM TestRunDefectImage \r\n"
    			+ "LEFT JOIN TestRunDefect ON TestRunDefectImage.TestRunDefectId = TestRunDefect.Id\r\n"
    			+ "LEFT JOIN ScreenShot ON ScreenShot.Id = TestRunDefect.ScreenShotId\r\n"
    			+ "LEFT JOIN TestDevice ON TestDevice.Id = ScreenShot.TestDeviceId\r\n"
    			+ "LEFT JOIN [Application] ON [Application].Id = ScreenShot.ApplicationId\r\n"
    			+ "\r\n"
    			+ "\r\n"
    			+ "WHERE TestRunDefectImage.ImageData IS NULL\r\n"
    			+ ""
    			+ ""
    			+ ""))
    	{
    		try (ResultSet resultSet = statement.executeQuery())
    		{
    			if (resultSet.next())
    			{
    				var apkFile = resultSet.getString("ApkFile");
    				apkFile = apkFile.substring(0, apkFile.length() - 4);
    				var imageFile = resultSet.getString("FileName");
    				
    				var ff = new File(Settings.appsFolder + apkFile);
    				
    				if (false == ff.exists())
    				{
    					ff = new File(Settings.appsFolder + apkFile.substring(0, apkFile.length() - 3)); 
    				}
    				
    				/*
    				
   					var context = this.contextProvider.getContext(ff);		
   					var state = context.getStates().stream().filter(p -> p.getImageFile().getAbsolutePath().substring(Settings.appImagesFolder.getAbsolutePath().length() + 1).equals(imageFile)).findFirst().get();
   					
   					var check = switch (resultSet.getInt("DefectTypeId"))
   					{
//							case 11:
	//							yield new ClippedControlCheck();
							case 5:
								yield new ClippedTextCheck();
							case 7:
								yield new MixedLanguagesStateCheck();
							case 33:
								yield new UnlocalizedIconsCheck();
							default:
								yield null;
						};

   					return new Tuple<>(check, state, resultSet.getLong("Id")); */
    				
    				return null;
    			}
    		}
    	}
    }
    catch (SQLException ex)
    {
    	ex.printStackTrace();
    }
    
    return null;
	}

	/*
	class ImagesLogger extends IResultsCollector
	{
		public ImagesLogger(long testRunDefectImageId)
		{
			super(true);
			
			this.testRunDefectImageId = testRunDefectImageId;
		}
*/
		/*
		
 */

	//	@Override
//		public boolean wasChecked(State state)
//		{//
//			return false;
//		}
/*
		@Override
		public void finishRun()
		{
		}
		
		private final long testRunDefectImageId;
	}	
	*/
	private final DefaultContextProvider contextProvider;
	private boolean running = true;
	protected String connectionUrl = "jdbc:sqlserver://localhost;database=defects-db;integratedSecurity=true;";
}
