package com.pro2111.PDF;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.pro2111.entities.Bill;
import com.pro2111.service.BillService;

@CrossOrigin("*")
@RestController
@RequestMapping("pdf")
public class PdfRestController {
	@Autowired
	private PdfService pdfService;

	@Autowired
	private BillService billService;

	/**
	 * in hóa đơn khi khách hàng mua onl
	 * @param response
	 * @param billId
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ReflectiveOperationException
	 */
	@GetMapping("/export/{billId}")
	public String donwloadBill(HttpServletResponse response, @PathVariable("billId") String billId)
			throws DocumentException, IOException, NumberFormatException, ReflectiveOperationException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + billId + LocalDateTime.now().format(formatter) + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader(headerKey, headerValue);
		Bill bill = billService.findById(billId);
		pdfService.export(response, bill);
		return "Đã xuất";
	}

	/**
	 * in hóa đơn ở tại shop
	 * @param response
	 * @param billId
	 * @return
	 * @throws InvalidPasswordException
	 * @throws IOException
	 * @throws PrintException
	 * @throws DocumentException
	 */
	@GetMapping("/print/{billId}")
	public String PrintBillInShop(HttpServletResponse response, @PathVariable("billId") String billId)
			throws InvalidPasswordException, IOException, PrintException, DocumentException {
		try {
			response.setContentType("application/pdf");

			PrintService myPrintService = pdfService.findPrintService("My Windows printer Name");
			PrinterJob job = PrinterJob.getPrinterJob();
			Bill bill = billService.findById(billId);
			job.setPageable(new PDFPageable(pdfService.exportPDdocument(response, bill)));
			job.printDialog();
			job.setPrintService(myPrintService);
			job.print();

		} catch (Exception e) {
			return "Lỗi hóa đơn";
		}
		return "Đã in";
	}
}
