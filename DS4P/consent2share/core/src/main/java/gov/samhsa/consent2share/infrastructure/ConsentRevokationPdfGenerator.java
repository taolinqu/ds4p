/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.infrastructure;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import gov.samhsa.consent2share.domain.consent.Consent;

/**
 * The Class ConsentRevokationPdfGenerator.
 */
@Component
public class ConsentRevokationPdfGenerator {
	
	/**
	 * Generate consent revokation pdf.
	 *
	 * @param consent the consent
	 * @return the byte[]
	 */
	public byte[] generateConsentRevokationPdf(Consent consent){
		Assert.notNull(consent, "Consent is required.");
		Document document = new Document();
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		try{
			Font fontheader = FontFactory.getFont("Helvetica", 19, Font.BOLD);
//			Font fontnotification = FontFactory.getFont("Helvetica", 16, Font.ITALIC);
			PdfWriter.getInstance(document, pdfOutputStream);
			document.open();
			
			Paragraph referenceNumberParagraph=new Paragraph();
			referenceNumberParagraph.setAlignment(Element.ALIGN_LEFT);
			Chunk referenceNumberchunk=new Chunk("Consent Reference Number: "+consent.getConsentReferenceId());
			referenceNumberParagraph.add(referenceNumberchunk);
			
			
			Paragraph patientNameParagraph=new Paragraph(20);
			patientNameParagraph.setAlignment(Element.ALIGN_RIGHT);
			Chunk patientNameChunk=new Chunk();
			patientNameChunk.append(consent.getPatient().getFirstName()+" "+consent.getPatient().getLastName());
			patientNameChunk.setUnderline(1, -2);
			Chunk patientNameChunk2=new Chunk("\nPrinted Name of Patient");
			patientNameParagraph.add(patientNameChunk);
			patientNameParagraph.add(patientNameChunk2);
			
			Paragraph titleParagraph=new Paragraph();
			titleParagraph.setAlignment(Element.ALIGN_CENTER);
			titleParagraph.add(new Chunk("Withdrawal of Consent to Participate in Health Information Exchange\n\n",fontheader));
			
			Paragraph paragraph1=new Paragraph();
			paragraph1.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph1.add(new Chunk("I have previously signed a Patient Consent form allowing my Providers to " +
					"access my electronic health records through the Consent 2 Share system and now want to withdraw" +
					" that consent. If I sign this form as the Patient's Legal Representative, I understand that all" +
					" references in this form to \"me\" or \"my\" refer to the Patient."));
			
			Paragraph paragraph2=new Paragraph();
			paragraph2.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph2.add(new Chunk("By withdrawing my Consent, I understand that:"));
			
			Paragraph paragraph3=new Paragraph();
			
			Chunk chunk1=new Chunk("1) I have two options for denying consent\n");	
			paragraph3.setAlignment(Element.ALIGN_JUSTIFIED);
			paragraph3.add(chunk1);
			paragraph3.add(createTable(consent));
			
			Paragraph paragraph4=new Paragraph();
			paragraph4.add("2) Health care provider and health insurers that I am enrolled with will no longer be able to" +
					" access health information about me through Consent 2 Share, except in an emergency unless I choose the " +
					"No Never option above.\n");
			paragraph4.add("3) The Withdrawal of Consent will not affect the exchange of my health information while my Consent was in effect.");
			paragraph4.add("4) No Consent 2 Share participating provider will deny me medical care and my insurance eligibility " +
					"will not be affected based on my Withdrawal of Consent.");
			paragraph4.add("5) If I wish to reinstate Consent, I may do so by signing and completing a new Patient Consent form and returning" +
					" it to a participating provider or payer.");
			paragraph4.add("6) Withdrawing my consent does not prevent my health care provider from submitting claims to my health insurer for " +
					"reimbursement for services rendered to me.");
			paragraph4.add("7) I understand that I will get a copy of this form after I sign it.");
			
			
			document.add(referenceNumberParagraph);
			document.add(patientNameParagraph);
			document.add(titleParagraph);
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			
			document.close();
			
		}catch (Throwable e) {
			e.printStackTrace();
			throw new ConsentPdfGenerationException(
					"Excecption when trying to generate pdf", e);
		}
		
		byte[] pdfBytes = pdfOutputStream.toByteArray();

		return pdfBytes;
		
	}
	
	/**
	 * Creates the table.
	 *
	 * @param consent the consent
	 * @return the pdf p table
	 */
	private PdfPTable createTable(Consent consent) {
		Font fontbold = FontFactory.getFont("Helvetica", 15, Font.BOLD);
		
		PdfPTable table = new PdfPTable(new float[]{0.2f,0.8f});
		table.setWidthPercentage(100f);
		table.getDefaultCell().setBorder(0);
		
		
		Chunk chunk2=new Chunk("NO EXCEPT",fontbold);
		Chunk chunk3=new Chunk("NO NEVER",fontbold);
		if(consent.getConsentRevokationType().equals("EMERGENCY ONLY")){
			chunk2.append("\n(This is the option you chose.)");
		}
		else if(consent.getConsentRevokationType().equals("NO NEVER")){
			chunk3.append("\n(This is the option you chose.)");
		}
		
		table.addCell(new PdfPCell(new Phrase(chunk2)));
        table.addCell(new PdfPCell(new Phrase("I Deny Consent for all Participants to access " +
        		"my electronic health information through Consent 2 Share for any purpose, EXCEPT " +
        		"in a medical emergency. By checking this box you agree, \"No, none of the Participants" +
        		" may be given access to my medical records through Consent 2 Share unless it is a medical emergency.\"")));
        table.addCell(new PdfPCell(new Phrase(chunk3)));
        table.addCell(new PdfPCell(new Phrase("I Deny Consent for all Participants to access my electronic health information" +
        		" through Consent 2 Share for any purpose, INCLUDING in a medical emergency.")));
        
        return table;
	}
}
