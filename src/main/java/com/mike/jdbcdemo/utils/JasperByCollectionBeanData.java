import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class JasperByCollectionBeanData {

	public static void main(String[] args) throws JRException, FileNotFoundException {		
		
        /* Output file location to create report in pdf form */
        String outputFile = "C:\\Users\\amitk\\Desktop\\JASPER\\" + "JasperReportExample.pdf";

        /* List to hold Items */
        List<Employee> listItems = new ArrayList<Employee>();

        /* Create Employee objects */
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        Employee emp3 = new Employee();
        
        /*first employee object*/
        emp1.setId(101);
        emp1.setFirstName("SAM");
        emp1.setLastName("Smith");
        emp1.setAddress("6th Avenue Dalton Road");
        emp1.setSalary(10000.0);
        
        
        /*second employee object*/
        emp2.setId(101);
        emp2.setFirstName("JOHN");
        emp2.setLastName("Williams");
        emp2.setAddress("4th Square Down Town");
        emp2.setSalary(17000.0);
        
        /*third employee object*/
        emp3.setId(101);
        emp3.setFirstName("JACOB");
        emp3.setLastName("Wilson");
        emp3.setAddress("19th Zygon Square, Middle Town");
        emp3.setSalary(22000.0);
        
        
        /* Add Items to List */
        listItems.add(emp1);
        listItems.add(emp2);
        listItems.add(emp3);

        /* Convert List to JRBeanCollectionDataSource */
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("CollectionBeanParam", itemsJRBean);
        
        //read jrxml file and creating jasperdesign object
        InputStream input = new FileInputStream(new File("C:\\Users\\amitk\\Desktop\\JASPER\\JasperReport_A4.jrxml"));
                            
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        
        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        /*call jasper engine to display report in jasperviewer window*/
        JasperViewer.viewReport(jasperPrint);
        
        
        /* outputStream to create PDF */
        //OutputStream outputStream = new FileOutputStream(new File(outputFile));
        
        
        /* Write content to PDF file */
        //JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        System.out.println("File Generated");	
	
	}
	
}


