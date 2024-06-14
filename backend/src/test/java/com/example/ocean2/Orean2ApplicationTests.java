package com.example.ocean2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Orean2ApplicationTests {

//	@Test
//	void addAllData() throws IOException {
//		File fdir = new File("C:\\Users\\lyl\\Desktop\\dataset");
//		File[] fs = fdir.listFiles();
//
//		List<SstObv> obvDataList = new ArrayList<>();
//		for(File af : fs) {
//			String fname = af.getName();
//			String siteName = fname.substring(0, fname.length() - 4);
//			FileInputStream fis = new FileInputStream(af);
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//
//			br.readLine();
//			int cnt = 0;
//			int mySeaSiteID = 0;
//			String sline;
//
//			br.readLine();
//			while ((sline = br.readLine()) != null) {
//				String[] ss = sline.split(",");
//				if (cnt == 0) {
//					SeaSite ssx = new SeaSite(0, 1, siteName,
//							Float.parseFloat(ss[1]), Float.parseFloat(ss[2]));
//					idm.insertSeaSite(ssx);
//					mySeaSiteID = ssx.getSiteID();
//				}
//				SstObv obd = new SstObv(0, mySeaSiteID, LocalDate.parse(ss[0]),
//						Float.parseFloat(ss[3]),
//						Float.parseFloat(ss[4]),
//						Float.parseFloat(ss[5]));
//				obvDataList.add(obd);
//				cnt++;
//			}
//			idm.insertSstObvData(obvDataList);
//			obvDataList.clear();
//			br.close();
//		}
//	}

}
