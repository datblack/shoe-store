package com.pro2111.PDF;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pro2111.beans.BankBean;
import com.pro2111.beans.BillDetailReturnCustomerViewBean;
import com.pro2111.beans.BillDetailViewBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.Setting;
import com.pro2111.service.BillDetailService;
import com.pro2111.service.SettingService;
import com.pro2111.utils.Constant;
import com.pro2111.utils.ZXingHelper;

@Service
public class PdfService {
	private static final String FONT_ARIAL = "/font/arial.ttf";

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private SettingService settingService;

	@Autowired
	private BillDetailService billDetailService;

	// tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
	// đơn vị tiền tệ của Việt Nam là đồng
	private Locale localeVN = new Locale("vi", "VN");
	private NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

	public void export(HttpServletResponse response, Bill bill)
			throws DocumentException, IOException, NumberFormatException, ReflectiveOperationException {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		PdfPTable table = new PdfPTable(6);
		BaseFont newFont = BaseFont.createFont(FONT_ARIAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		document.open();

		setBackgroundImg(writer);
		setLogoToPdf(document);
		writeHeaderPage(document, newFont, bill);
		designTable(table, document, newFont, bill);
		writeEndPage(document, newFont, bill);
		writeReturnPolicy(document, newFont);

		document.close();
	}
	/**
	 * Tạo table những sản phẩm khách hàng mua
	 * 
	 * @param table
	 * @param document
	 * @param airal
	 * @throws DocumentException
	 * @throws ReflectiveOperationException
	 * @throws NumberFormatException
	 */
	private void designTable(PdfPTable table, Document document, BaseFont airal, Bill bill)
			throws DocumentException, NumberFormatException, ReflectiveOperationException {

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 5f, 2f, 3.5f, 1.5f, 4f });
		table.setSpacingBefore(10);

		writeTableHeader(table, airal, document);
		writeTableData(table, bill, airal, document);

	}

	/**
	 * Vùng header table những sản phẩm khách hàng mua
	 * 
	 * @param table
	 * @param arial
	 * @throws DocumentException
	 */
	private void writeTableHeader(PdfPTable table, BaseFont arial, Document document) throws DocumentException {
		Paragraph productRefund = new Paragraph("DANH SÁCH SẢN PHẨM KHÁCH HÀNG MUA", new Font(arial, 12, Font.BOLD));
		productRefund.setAlignment(Paragraph.ALIGN_CENTER);
		productRefund.setSpacingBefore(15.0f);
		document.add(productRefund);
		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);
		cell.setPhrase(new Phrase("STT", new Font(arial, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("Sản phẩm", new Font(arial, 12, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Số lượng", new Font(arial, 12, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Đơn giá", new Font(arial, 12, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Thuế", new Font(arial, 12, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Thành tiền", new Font(arial, 12, Font.BOLD)));
		table.addCell(cell);
	}

	/**
	 * Vùng data table những sản phẩm khách hàng mua
	 * 
	 * @param table
	 * @param details
	 * @param arial
	 * @param document
	 * @throws DocumentException
	 * @throws ReflectiveOperationException
	 * @throws NumberFormatException
	 */
	private void writeTableData(PdfPTable table, Bill bill, BaseFont arial, Document document)
			throws DocumentException, NumberFormatException, ReflectiveOperationException {
		PdfPCell cell = new PdfPCell();
		PdfPCell cellTotal = new PdfPCell();
		Double total = 0.0;

		List<BillDetailViewBean> details = billDetailService.findByBillDetailCustomerBuy(bill);
		for (int i = 0; i < details.size(); i++) {

			BillDetailViewBean detail = details.get(i);

			Double intoMoney = detail.getPrice().doubleValue() * detail.getQuantity();
			intoMoney = intoMoney + (intoMoney * detail.getTax().doubleValue()) / 100;
			cell.setPhrase(new Phrase((i + 1) + ""));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell.setPhrase(new Phrase(detail.getProductVariant().getProducts().getProductName(), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell.setPhrase(new Phrase(detail.getQuantity() + "", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(detail.getPrice()), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.format("%,.0f", detail.getTax()) + "%", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(intoMoney), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			total += intoMoney;
		}
		cellTotal.setPhrase(new Phrase("Tổng tiền:", new Font(arial, 12, Font.BOLD)));
		cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTotal.setColspan(5);
		cell.setPhrase(new Phrase(currencyVN.format(total), new Font(arial, 12)));

		table.addCell(cellTotal);
		table.addCell(cell);
		document.add(table);
		writeTableReturn(bill, arial, document);
		writeTableReturnCustomer(bill, arial, document);

	}

	/**
	 * Tạo table những sản phẩm khách hàng trả
	 * 
	 * @param details
	 * @param arial
	 * @param document
	 * @throws DocumentException
	 * @throws ReflectiveOperationException
	 * @throws NumberFormatException
	 */
	private void writeTableReturn(Bill bill, BaseFont arial, Document document)
			throws DocumentException, NumberFormatException, ReflectiveOperationException {
		List<BillDetailViewBean> details = billDetailService.findByBillDetailCustomerReturn(bill);
		if (details.size() != 0) {
			Paragraph productRefund = new Paragraph("DANH SÁCH SẢN PHẨM KHÁCH HÀNG TRẢ",
					new Font(arial, 12, Font.BOLD));
			productRefund.setAlignment(Paragraph.ALIGN_CENTER);
			productRefund.setSpacingBefore(15.0f);
			document.add(productRefund);
			PdfPTable tableReturn = new PdfPTable(8);
			tableReturn.setWidthPercentage(100f);
			tableReturn.setWidths(new float[] { 1.5f, 5f, 2f, 3.5f, 2f, 4f, 4f, 4f });
			tableReturn.setSpacingBefore(10);
			writeTableReturnHeader(tableReturn, arial);
			writeTableReturnData(tableReturn, details, arial);
			document.add(tableReturn);
		}
	}

	/**
	 * Vùng data table những sản phẩm khách hàng trả
	 * 
	 * @param tableReturn
	 * @param details
	 * @param arial
	 */
	private void writeTableReturnData(PdfPTable tableReturn, List<BillDetailViewBean> details, BaseFont arial) {
		PdfPCell cell = new PdfPCell();
		PdfPCell cellTotal = new PdfPCell();
		Double total = 0.0;
		for (int i = 0; i < details.size(); i++) {
			BillDetailViewBean detail = details.get(i);
			Double intoMoney = detail.getPrice().doubleValue() * detail.getQuantity();
			intoMoney = intoMoney + (intoMoney * detail.getTax().doubleValue()) / 100;
			String hadlingStaff = "";
			if (detail.getUserConfirm() != null) {
				hadlingStaff = detail.getUserConfirm().getFullName();
			}
			cell.setPhrase(new Phrase((i + 1) + ""));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);
			cell.setPhrase(new Phrase(detail.getProductVariant().getProducts().getProductName(), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(detail.getQuantity() + "", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(detail.getPrice()), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(String.format("%,.0f", detail.getTax()) + "%", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(intoMoney), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(hadlingStaff, new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(detail.getNote(), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			total += intoMoney;
		}
		cellTotal.setPhrase(new Phrase("Tổng tiền:", new Font(arial, 12, Font.BOLD)));
		cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTotal.setColspan(7);
		cell.setPhrase(new Phrase(currencyVN.format(total), new Font(arial, 12)));
		tableReturn.addCell(cellTotal);
		tableReturn.addCell(cell);

	}

	/**
	 * Vùng header table những sản phẩm khách hàng trả
	 * 
	 * @param tableReturn
	 * @param arial
	 */
	private void writeTableReturnHeader(PdfPTable tableReturn, BaseFont arial) {
		// TODO Auto-generated method stub
		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);
		cell.setPhrase(new Phrase("STT", new Font(arial, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableReturn.addCell(cell);
//		cell.setPhrase(new Phrase("SKU-ID", new Font(arial, 12, Font.BOLD)));
//		table.addCell(cell);
		cell.setPhrase(new Phrase("Sản phẩm", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Số lượng", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Đơn giá", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Thuế", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Thành tiền", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Nhân viên xử lý", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Ghi chú", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
	}

	/**
	 * Tạo table những sản phẩm đổi trả cho khách hàng
	 * 
	 * @param details
	 * @param arial
	 * @param document
	 * @throws DocumentException
	 * @throws ReflectiveOperationException
	 * @throws NumberFormatException
	 */
	private void writeTableReturnCustomer(Bill bill, BaseFont arial, Document document)
			throws DocumentException, NumberFormatException, ReflectiveOperationException {
		List<BillDetailReturnCustomerViewBean> details = billDetailService.findByBillDetailStoreReturn(bill);
		if (details.size() != 0) {
			Paragraph productRefund = new Paragraph("DANH SÁCH SẢN PHẨM ĐỔI TRẢ CHO KHÁCH HÀNG",
					new Font(arial, 12, Font.BOLD));
			productRefund.setAlignment(Paragraph.ALIGN_CENTER);
			productRefund.setSpacingBefore(15.0f);
			document.add(productRefund);
			PdfPTable tableReturn = new PdfPTable(8);
			tableReturn.setWidthPercentage(100f);
			tableReturn.setWidths(new float[] { 1.5f, 5f, 2f, 3.5f, 2f, 4f, 4f, 4f });
			tableReturn.setSpacingBefore(10);
			writeTableReturnCustomerHeader(tableReturn, arial);
			writeTableReturnCustomerData(tableReturn, details, arial);
			document.add(tableReturn);
		}
	}

	/**
	 * Vùng header table những sản phẩm đổi trả cho khách hàng
	 * 
	 * @param tableReturn
	 * @param arial
	 */
	private void writeTableReturnCustomerHeader(PdfPTable tableReturn, BaseFont arial) {
		// TODO Auto-generated method stub
		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);
		cell.setPhrase(new Phrase("STT", new Font(arial, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		tableReturn.addCell(cell);
//		cell.setPhrase(new Phrase("SKU-ID", new Font(arial, 12, Font.BOLD)));
//		table.addCell(cell);
		cell.setPhrase(new Phrase("Sản phẩm", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Số lượng", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Đơn giá", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Thuế", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Thành tiền", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Nhân viên xử lý", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
		cell.setPhrase(new Phrase("Ghi chú", new Font(arial, 12, Font.BOLD)));
		tableReturn.addCell(cell);
	}

	/**
	 * Vùng data table những sản phẩm đổi trả cho khách hàng
	 * 
	 * @param tableReturn
	 * @param details
	 * @param arial
	 */
	private void writeTableReturnCustomerData(PdfPTable tableReturn, List<BillDetailReturnCustomerViewBean> details,
			BaseFont arial) {
		PdfPCell cell = new PdfPCell();
		PdfPCell cellTotal = new PdfPCell();
		PdfPCell cellDetailParent = new PdfPCell();
		Double total = 0.0;

		for (int i = 0; i < details.size(); i++) {
			BillDetailReturnCustomerViewBean detail = details.get(i);

			Double intoMoney = detail.getPrice().doubleValue() * detail.getQuantity();
			intoMoney = intoMoney + (intoMoney * detail.getTax().doubleValue()) / 100;
			String hadlingStaff = "";
			if (detail.getUserConfirm() != null) {
				hadlingStaff = detail.getUserConfirm().getFullName();
			}
			cellDetailParent.setPhrase(new Phrase(
					"Đổi trả cho sản phẩm "
							+ detail.getBillDetailParent().getProductVariants().getProducts().getProductName(),
					new Font(arial, 11)));
			cellDetailParent.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellDetailParent.setColspan(8);
			tableReturn.addCell(cellDetailParent);

			cell.setPhrase(new Phrase((i + 1) + ""));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);
			cell.setPhrase(new Phrase(
					detail.getProductVariant() != null ? detail.getProductVariant().getProducts().getProductName() : "",
					new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(detail.getQuantity() + "", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(detail.getPrice()), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(String.format("%,.0f", detail.getTax()) + "%", new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(currencyVN.format(intoMoney), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(hadlingStaff, new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			cell.setPhrase(new Phrase(detail.getNote(), new Font(arial, 11)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableReturn.addCell(cell);

			total += intoMoney;
		}
		cellTotal.setPhrase(new Phrase("Tổng tiền:", new Font(arial, 12, Font.BOLD)));
		cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTotal.setColspan(7);
		cell.setPhrase(new Phrase(currencyVN.format(total), new Font(arial, 12)));
		tableReturn.addCell(cellTotal);
		tableReturn.addCell(cell);

	}
	/**
	 * 
	 * @param document
	 * @param newFont
	 * @throws DocumentException 
	 */
	private void writeReturnPolicy(Document document, BaseFont arial) throws DocumentException {
		document.newPage();
		Paragraph title = new Paragraph("CHÍNH SÁCH ĐỔI TRẢ", new Font(arial, 25, Font.BOLD));
		Paragraph indexA = new Paragraph("I. Quy định đổi trả", new Font(arial, 15, Font.BOLD));
		Paragraph subIndexA = new Paragraph("  1. Những trường hợp được đổi trả:", new Font(arial, 13, Font.BOLD));
		Paragraph contentA = new Paragraph(
				  "    •  Hàng bị lỗi kỹ thuật và lỗi do nhà sản xuất.\r\n"
				+ "    •  Hàng bị hư hỏng do quá trình vận chuyển hàng cho khách.\r\n"
				+ "    •  Hàng giao không đúng mẫu mã, loại mà khách đã đặt.\r\n"
				+ "    •  Hàng không vừa size.\r\n"
		, new Font(arial, 12));
		Paragraph subIndexAB = new Paragraph("  2. Điều kiện và quy định đổi trả chung:", new Font(arial, 13, Font.BOLD));
		Paragraph contentAB = new Paragraph(
				  "    •  Đối với sản phẩm lỗi, khách hàng có thể đổi sang sản phẩm khác trong vòng 10 ngày kể từ \r\nkhi nhận hàng.\r\n"
				+ "    •  Đối với sản phẩm khách hàng muốn đổi trả, khách hàng cũng có thể đổi sang sản phẩm khác trong vòng 10 ngày kể từ khi nhận hàng.\r\n"
				+ "    •  Sản phẩm phải còn đầy đủ gói hộp, phụ kiện đi kèm và chưa qua sử dụng, không bị dơ bẩn. Khi thử sản phẩm giày dép, quý khách nên mang tất và thử giày trên nền sạch để đảm bảo sản phẩm không bi lấm bẩn.\r\n"
				+ "    •  Đối với khách hàng mua hàng tại cửa hàng, phải còn đầy đủ hóa đơn bán hàng hoặc mã QR code hóa đơn của cửa hàng.\r\n"
				+ "    •  Đối với việc khách hàng đổi sang sản phẩm có giá trị cao hơn thì khách hàng phải phụ thêm tiền cho bằng giá trị sản phẩm mới\r\n"
				+ "    •  Đối với việc khách hàng đổi sang sản phẩm có giá trị thấp hơn, chúng tôi sẽ gửi lại cho khách hàng số tiền thừa đó.\r\n"
				+ ""
		, new Font(arial, 12));
		
		Paragraph indexB = new Paragraph("II. Quy Định Hoàn tiền", new Font(arial, 15, Font.BOLD));
		Paragraph subIndexB = new Paragraph("  1. Những trường hợp được hoàn tiền:", new Font(arial, 13, Font.BOLD));
		Paragraph contentB = new Paragraph(
			    "   •  Hàng bị lỗi kỹ thuật và lỗi do nhà sản xuất.\r\n"
			  + "   •  Hàng bị hư hỏng do quá trình vận chuyển hàng cho khách.\r\n"
			  + "   •  Hàng giao không đúng mẫu mã, loại mà khách đã đặt.\r\n"
			  + "   •  Hết hàng, hết size ưng ý để đổi trả.\r\n"
			  + ""
		, new Font(arial, 12));
		Paragraph subIndexBB = new Paragraph("  2. Điều kiện và quy định hoàn tiền:", new Font(arial, 13, Font.BOLD));
		Paragraph contentBB = new Paragraph(
				"    •  Đối với sản phẩm lỗi, khách hàng sẽ được nhận hoàn tiền trong vòng 10 ngày kể từ khi nhận hàng.\r\n"
			  + "    •  Đối với sản phẩm khách hàng đổi trả, khách hàng sẽ được hoàn tiền nếu sản phẩm đó trong kho hết hàng, hết size thì khách hàng sẽ được nhận lại số tiền của sản phẩm đó trong vòng 10 ngày kể từ khi nhận hàng.\r\n"
			  + "    •  Sản phẩm phải còn đầy đủ gói hộp, phụ kiện đi kèm và chưa qua sử dụng, không bị dơ bẩn..\r\n"
			  + "    •  Đối với khách hàng mua hàng tại cửa hàng, phải còn đầy đủ hóa đơn bán hàng hoặc mã QR code hóa đơn của cửa hàng.\r\n"
			  + "    •  Đối với việc đổi sản phẩm có giá trị thấp hơn, khách hàng có thể nhận lại số tiền dư bằng 2 phương thức là tiền mặt hoặc chuyển khoản. Về việc này chúng tôi sẽ làm việc và trao đổi riêng trực tiếp hoặc các chat qua các trang mạng xã hội để giải quyết.\r\n"
			  + "    •  Đối với việc đổi sản phẩm có giá trị cao hơn, khách hàng cũng có thể phụ thêm cho chúng tôi bằng 2 phương thức là tiền mặt hoặc chuyển khoản. Về việc này chúng tôi sẽ làm việc và trao đổi riêng trực tiếp hoặc các chat qua các trang mạng xã hội để giải quyết.\r\n"
			  + ""
		, new Font(arial, 12));
		
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.setSpacingBefore(5);
		document.add(title);
		document.add(indexA);
		document.add(subIndexA);
		document.add(contentA);
		document.add(subIndexAB);
		document.add(contentAB);
		
		document.add(indexB);
		document.add(subIndexB);
		document.add(contentB);
		document.add(subIndexBB);
		document.add(contentBB);
	}
	/**
	 * tạo phần đầu cho trang pdf
	 * @param document
	 * @param arial
	 * @param bill
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private void writeHeaderPage(Document document, BaseFont arial, Bill bill)
			throws DocumentException, MalformedURLException, IOException {
		String customer = "";
		String addressString = "";
		String phoneCus = "";
		String staffOder = "";
		StringJoiner addressShop = new StringJoiner(" - ");
		Setting setting = settingService.getAllInforSetting();
		Gson gson = new Gson();
		BankBean bankBean = gson.fromJson(setting.getBank(), BankBean.class);
		StringBuffer bankInfor = new StringBuffer();
		bankInfor.append("Ngân hàng: ");
		bankInfor.append(bankBean.getBankName());
		bankInfor.append(" - ");
		bankInfor.append("Số tài khoản: ");
		bankInfor.append(bankBean.getAccountNumber());
		bankInfor.append(" - ");
		bankInfor.append("Chủ tài khoản: ");
		bankInfor.append(bankBean.getAccountHolder());
		if (!setting.getAddressDetail().isBlank()) {
			addressShop.add(setting.getAddressDetail());
		}
		addressShop.add(setting.getWardName());
		addressShop.add(setting.getDistrictName());
		addressShop.add(setting.getDivisionName());
		if (bill.getCustomers() != null) {
			customer = bill.getCustomers().getFullName();
		}

		if (bill.getUsers() != null) {
			staffOder = bill.getUsers().getFullName();
		}

		// addressBill
		StringBuffer addressBuffer = new StringBuffer();
		if (bill.getAddress() != null && !bill.getAddress().isBlank()) {
			addressBuffer.append(bill.getAddress());
			addressBuffer.append(" - ");
		}
		if (bill.getWardName() != null && !bill.getWardName().isBlank()) {
			addressBuffer.append(bill.getWardName());
			addressBuffer.append(" - ");
		}
		if (bill.getDistrictName() != null && !bill.getDistrictName().isBlank()) {
			addressBuffer.append(bill.getDistrictName());
			addressBuffer.append(" - ");
		}

		if (bill.getDivisionName() != null && !bill.getDivisionName().isBlank()) {
			addressBuffer.append(bill.getDivisionName());
		}
		addressString = addressBuffer.toString();

		if (bill.getPhone() != null) {
			phoneCus = bill.getPhone();
		}
		Image image = Image
				.getInstance(ZXingHelper.getQRCodeImage(httpServletRequest.getRequestURL().toString(), 82, 82));
		DateTimeFormatter sm = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		Paragraph titleName = new Paragraph("Bluv.snk", new Font(arial, 25, Font.BOLD));
		Paragraph phoneNumber = new Paragraph("Số điện thoại: " + setting.getPhoneShop(),
				new Font(arial, 12, Font.BOLD));
		Paragraph email = new Paragraph("Email: " + setting.getEmail(), new Font(arial, 12, Font.BOLD));
		Paragraph address = new Paragraph("Địa chỉ: " + addressShop, new Font(arial, 12, Font.BOLD));
		Paragraph bankInforText = new Paragraph(bankInfor.toString(), new Font(arial, 12, Font.BOLD));
		Paragraph billName = new Paragraph("HÓA ĐƠN BÁN HÀNG", new Font(arial, 20, Font.BOLD));
		Paragraph maHoaDon = new Paragraph(bill.getBillId(), new Font(arial));
		Paragraph dayCreate = new Paragraph("Ngày mua: " + bill.getCreatedDate().format(sm), new Font(arial, 12));
		Paragraph employeeName = new Paragraph("Khách hàng: " + customer, new Font(arial, 12));
		Paragraph addressEmployee = new Paragraph("Địa chỉ: " + addressString, new Font(arial, 12));
		Paragraph phoneNumEmplye = new Paragraph("Số điện thoại: " + phoneCus, new Font(arial, 12));
		Paragraph staff = new Paragraph("Nhân viên bán hàng: " + staffOder, new Font(arial, 12));
		Paragraph qrCode = new Paragraph(new Chunk(image, 0, 0, true));

		titleName.setAlignment(Paragraph.ALIGN_CENTER);
		phoneNumber.setAlignment(Paragraph.ALIGN_CENTER);
		email.setAlignment(Paragraph.ALIGN_CENTER);
		address.setAlignment(Paragraph.ALIGN_CENTER);
		bankInforText.setAlignment(Paragraph.ALIGN_CENTER);
		billName.setAlignment(Paragraph.ALIGN_CENTER);
		maHoaDon.setAlignment(Paragraph.ALIGN_CENTER);
		employeeName.setAlignment(Paragraph.ALIGN_LEFT);
		employeeName.setSpacingBefore(5);
		addressEmployee.setAlignment(Paragraph.ALIGN_LEFT);
		addressEmployee.setSpacingBefore(5);
		phoneNumEmplye.setAlignment(Paragraph.ALIGN_LEFT);
		phoneNumEmplye.setSpacingBefore(5);
		staff.setAlignment(Paragraph.ALIGN_LEFT);
		staff.setSpacingBefore(5);
		dayCreate.setAlignment(Paragraph.ALIGN_LEFT);
		dayCreate.setSpacingBefore(10);

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1f, 3f, 1f });
		table.setSpacingBefore(20);

		PdfPCell cell = new PdfPCell();
		PdfPCell cellQR = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(0);
		cellQR.setBorder(0);
		cellQR.setPhrase(new Phrase(qrCode));
		cellQR.setRowspan(4);
		table.addCell(cellQR);

		cell.setPhrase(new Phrase(phoneNumber));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase(email));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase(address));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		cell.setPhrase(new Phrase(bankInforText));
		table.addCell(cell);

		cell.setPhrase(new Phrase(""));
		table.addCell(cell);

		document.add(titleName);
		document.add(table);
		document.add(billName);
		document.add(maHoaDon);
		document.add(dayCreate);
		document.add(employeeName);
		document.add(addressEmployee);
		document.add(phoneNumEmplye);
		document.add(staff);
	}

	/**
	 * tạo phần cuối trang pdf
	 * @param document
	 * @param arial
	 * @param bill
	 * @throws DocumentException
	 */
	private void writeEndPage(Document document, BaseFont arial, Bill bill) throws DocumentException {
		int statusBillInt = bill.getStatus();
		String statusBill = "";
		if (Constant.STATUS_BILL_WAIT_FOR_PAY == statusBillInt) {
			statusBill = Constant.BILL_WAIT_FOR_PAY;
		} else if (Constant.STATUS_BILL_WAIT_FOR_CONFIRMATION == statusBillInt) {
			statusBill = Constant.BILL_WAIT_FOR_CONFIRMATION;
		} else if (Constant.STATUS_BILL_WAIT_FOR_DELIVERY == statusBillInt) {
			statusBill = Constant.BILL_WAIT_FOR_DELIVERY;
		} else if (Constant.STATUS_BILL_WAIT_FOR_DELIVERING == statusBillInt) {
			statusBill = Constant.BILL_WAIT_FOR_DELIVERING;
		} else if (Constant.STATUS_BILL_SUCCESS == statusBillInt) {
			statusBill = Constant.BILL_SUCCESS;
		} else if (Constant.STATUS_BILL_CANCEL == statusBillInt) {
			statusBill = Constant.BILL_CANCEL;
		}

		PdfPTable table = new PdfPTable(6);
		PdfPCell cell = new PdfPCell();

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1f, 3f, 4f, 2f, 3.5f, 4f });
		table.setSpacingBefore(10);

		cell.setBorder(0);
		cell.setPhrase(new Phrase("Chiết khấu: ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(bill.getDiscount() + " %", new Font(arial, 13, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Phí ship: ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(currencyVN.format(bill.getShippingFee()), new Font(arial, 13, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tổng tiền phải thanh toán: ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(currencyVN.format(bill.getTotalMoney()), new Font(arial, 13, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Trạng thái đơn hàng: ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(statusBill, new Font(arial, 13, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		Paragraph askThank = new Paragraph("----Cảm ơn quý khách !----", new Font(arial, 12, Font.ITALIC));

		askThank.setSpacingBefore(50);
		askThank.setAlignment(Element.ALIGN_CENTER);

		document.add(table);
		document.add(askThank);
	}

	private void setLogoToPdf(Document document) throws MalformedURLException, IOException, DocumentException {
//		Image logo = Image.getInstance("classpath:font/bill.png");
//
//		logo.scaleAbsolute(70, 70);
//		logo.setAlignment(Image.LEFT | Image.TEXTWRAP);
//
//		document.add(logo);
	}

	/**
	 * set background cho trang pdf
	 * @param writer
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void setBackgroundImg(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
//		PdfContentByte canvas = writer.getDirectContentUnder();
//		Image image = Image
//				.getInstance("classpath:font/bill.png");
//		image.scaleAbsolute(420, 595);
//		image.setAbsolutePosition(0, 0);
//
//		canvas.addImage(image);
		Image image = Image.getInstance("classpath:font/logo.jpg");
		image.scaleToFit(200, 200);
		image.setAbsolutePosition((PageSize.A4.getWidth() - 200) / 2, (PageSize.A4.getHeight() - 200) / 2);

		writer.setPageEvent(new ImageBackgroundHelper(image));
	}

	/**
	 * tìm kiếm máy in
	 * @param printerName
	 * @return
	 */
	public final PrintService findPrintService(String printerName) {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : printServices) {
			if (printService.getName().trim().equals(printerName)) {
				return printService;
			}
		}
		return null;
	}

	/**
	 * xuất ra file excel
	 * @param response
	 * @param bill
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ReflectiveOperationException
	 */
	public PDDocument exportPDdocument(HttpServletResponse response, Bill bill)
			throws DocumentException, IOException, NumberFormatException, ReflectiveOperationException {
		PDDocument document = new PDDocument();
		export(response, bill);

		document.save(response.getOutputStream());
		document.close();
		return document;
	}
}
