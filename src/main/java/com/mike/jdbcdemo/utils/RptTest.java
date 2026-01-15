import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
 
public class RptTest {
	public static void main(String[] args) {
		JasperDesign design;
		try {
			design = JRXmlLoader.load("C:\\template\\test.jrxml");
			HashMap<String, Object> paramsMap = new HashMap<String, Object>();
 
			// 加载模板
			JasperReport report = JasperCompileManager.compileReport(design);
 
			List<RptData> detailList = new ArrayList<RptData>();
			RptData mainData = new RptData();
			mainData.setMakeUser("admin");
			mainData.setDocNo("2019文字-00001");
			mainData.setTotalFee("伍佰圆整");
			mainData.setImageUrl("C:\\images\\coffee.jpg");
			detailList.add(mainData);
 
			List<Fields> list1 = new ArrayList<Fields>();
			for (int i = 1; i <= 5; i++) {
				Fields data = new Fields();
				data.setField1("项目" + i);
				data.setField2("台");
				data.setField3("" + i);
				data.setField4("100.00");
				data.setField5(String.valueOf(i * 100));
				list1.add(data);
			}
			mainData.setTableData(list1);
 
			List<Fields> list2 = new ArrayList<Fields>();
			for (int i = 1; i <= 5; i++) {
				Fields data = new Fields();
				data.setField1("材料" + i);
				data.setField2("商标" + i);
				data.setField3("生产厂家" + i);
				data.setField4("规格型号");
				data.setField5("个");
				list2.add(data);
			}
			mainData.setTableData2(new JRBeanCollectionDataSource(list2));
 
			JRBeanCollectionDataSource jbds = new JRBeanCollectionDataSource(detailList);
			// 填充数据
			JasperPrint print = JasperFillManager.fillReport(report, paramsMap, jbds);
 
//				// 预览显示
//			JasperViewer.viewReport(print);
 
			JRPdfExporter exporter = new JRPdfExporter(); // 指定要导出的jrprit数据
			// 指定导出文件的文件名
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
 
			// 指定导出文件的文件名
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("C:\\pdf\\test.pdf");
			exporter.setExporterOutput(exporterOutput);
 
			// 实现报表的导出
			exporter.exportReport();
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
