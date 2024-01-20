package edu.ktu;

import java.io.IOException;

import edu.ktu.screenshotanalyser.checks.CheckResult;
import edu.ktu.screenshotanalyser.checks.ResultsCollector;

import java.io.File;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.PdfWriter;  

public class PDFGenerator {

	
	public static void GeneratePdf(File ssfolder, String date, File app, String SelectedRules, ResultsCollector results) throws IOException
	{
		Document doc = new Document();  
		try  
		{  
			//generate a PDF at the specified location  
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(ssfolder.getAbsolutePath() + "\\test.pdf"));  
			System.out.println("PDF created.");  
			//opens the PDF  
			doc.open();  
			//adds paragraph to the PDF file  
			doc.add(new Paragraph("Analysis results: " + app.getAbsolutePath()));
			doc.add(new Paragraph("Date " + date));   
			doc.add(new Paragraph("Selected rules: " + SelectedRules));     
			doc.add(new Paragraph("---------------------------")); 
			for(CheckResult result : results.Results)
			{
				Paragraph checkParagraph = new Paragraph();
				checkParagraph.add("Rule: " + result.getRule().getRuleCode() + "\n");
				if(result.getState() != null)
				{
					checkParagraph.add("State file: " + result.getState().getStateFile() + "\n");
					checkParagraph.add("State image: " + result.getState().getImageFile() + "\n");
					
				}
				checkParagraph.add("Result message: " + result.getMessage() +"\n");
				checkParagraph.add("----------");
				doc.add(checkParagraph);
				
			}
			
			//close the PDF file  
			doc.close();  
			//closes the writer  
			writer.close();  
		}   
		catch (DocumentException e)  
		{  
			e.printStackTrace();  
		}   
		catch (FileNotFoundException e)  
		{  
			e.printStackTrace();  
		}  
	}
}
