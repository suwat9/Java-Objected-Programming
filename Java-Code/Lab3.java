import java.time.LocalDate;

/**
 * Lab3 - ปฏิทินจีน ปี 2570 (ค.ศ. 2027)
 * ปีระกาธาตุไฟ (丁未年 - Year of the Fire Goat)
 * 
 * แสดงปฏิทินจีน 12 เดือน พร้อมวันที่จันทรคติจีนและเทศกาลสำคัญ
 * 
 * ข้อมูลอ้างอิง:
 * - ตรุษจีน 2027: 6 กุมภาพันธ์ 2027 (วันขึ้น 1 ค่ำ เดือน 1)
 * - ปี 2027 ไม่มีเดือนอธิกสุรทิน (leap month)
 */

public class Lab3 {
    
    static final String[] MONTH_TH_FULL = {
        "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน",
        "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"
    };
    
    // ชื่อเดือนจันทรคติจีน
    static final String[] LUNAR_MONTH_CN = {
        "正月", "二月", "三月", "四月", "五月", "六月",
        "七月", "八月", "九月", "十月", "十一月", "十二月"
    };
    
    // ตัวเลขวันจันทรคติจีน
    static final String[] LUNAR_DAY_CN = {
        "", "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
        "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
        "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
    };
    
    // เทศกาลสำคัญ: {เดือน(1-12), วันที่}
    static final int[][] FESTIVAL_DATES = {
        {2, 6},   // ตรุษจีน
        {2, 11},  // เทศกาลโคมไฟ
        {4, 5},   // เชงเม้ง
        {6, 9},   // เทศกาลเรือมังกร
        {8, 4},   // ชีซี
        {8, 11},  // สารทจีน
        {9, 15},  // ไหว้พระจันทร์
        {10, 8},  // ฉงหยาง
        {12, 22}  // ตงจื่อ
    };
    
    static final String[] FESTIVAL_TH = {
        "ตรุษจีน", "โคมไฟ", "เชงเม้ง", "เรือมังกร", "ชีซี", "สารทจีน", "ไหว้พระจันทร์", "ฉงหยาง", "ตงจื่อ"
    };
    
    static final String[] FESTIVAL_CN = {
        "春节", "元宵节", "清明节", "端午节", "七夕节", "中元节", "中秋节", "重阳节", "冬至"
    };
    
    // วันขึ้น 1 ค่ำ (New Moon) ของแต่ละเดือนจันทรคติ ปี 2027
    // {เดือน(1-12), วันที่, เลขเดือนจันทรคติ(1-12), ปีจีน(0=丙午, 1=丁未)}
    // ข้อมูลอ้างอิงจากปฏิทินจีนจริง
    static final int[][] NEW_MOON = {
        // เดือน 12 ปีมะเมีย (丙午年十二月) เริ่ม 29 ม.ค. 2027
        {1, 29, 12, 0},  // 29 ม.ค. = 初一 (เดือน 12)
        // เดือน 1 ปีระกา (丁未年正月) เริ่ม 6 ก.พ. 2027 (ตรุษจีน)
        {2, 6,  1, 1},   // 6 ก.พ. = 初一 (正月)
        // เดือน 2 เริ่ม 8 มี.ค. 2027
        {3, 8,  2, 1},   // 8 มี.ค. = 初一 (二月)
        // เดือน 3 เริ่ม 6 เม.ย. 2027
        {4, 6,  3, 1},   // 6 เม.ย. = 初一 (三月)
        // เดือน 4 เริ่ม 6 พ.ค. 2027
        {5, 6,  4, 1},   // 6 พ.ค. = 初一 (四月)
        // เดือน 5 เริ่ม 4 มิ.ย. 2027
        {6, 4,  5, 1},   // 4 มิ.ย. = 初一 (五月)
        // เดือน 6 เริ่ม 4 ก.ค. 2027
        {7, 4,  6, 1},   // 4 ก.ค. = 初一 (六月)
        // เดือน 7 เริ่ม 2 ส.ค. 2027
        {8, 2,  7, 1},   // 2 ส.ค. = 初一 (七月)
        // เดือน 8 เริ่ม 1 ก.ย. 2027
        {9, 1,  8, 1},   // 1 ก.ย. = 初一 (八月)
        // เดือน 9 เริ่ม 30 ก.ย. 2027
        {9, 30, 9, 1},   // 30 ก.ย. = 初一 (九月)
        // เดือน 10 เริ่ม 29 ต.ค. 2027
        {10, 29, 10, 1}, // 29 ต.ค. = 初一 (十月)
        // เดือน 11 เริ่ม 28 พ.ย. 2027
        {11, 28, 11, 1}, // 28 พ.ย. = 初一 (十一月)
        // เดือน 12 เริ่ม 28 ธ.ค. 2027
        {12, 28, 12, 1}  // 28 ธ.ค. = 初一 (十二月)
    };
    
    // คำนวณวันจันทรคติ: return {เดือนจันทรคติ(1-12), วันที่จันทรคติ(1-30), ปีจีน(0=丙午,1=丁未)}
    static int[] getLunarDate(int month, int day) {
        int bestLunarMonth = 0;
        int bestLunarDay = 1;
        int bestYear = 0;
        int bestNmMonth = -1;
        int bestNmDay = -1;
        
        // หา New Moon ล่าสุดก่อนหรือตรงกับวันที่นี้
        for (int[] nm : NEW_MOON) {
            int nmMonth = nm[0];
            int nmDay = nm[1];
            int lunarM = nm[2];
            int yearType = nm[3];
            
            // ตรวจสอบว่า New Moon นี้เกิดขึ้นก่อนหรือตรงกับวันที่ที่ต้องการ
            if (nmMonth < month || (nmMonth == month && nmDay <= day)) {
                bestLunarMonth = lunarM;
                bestLunarDay = day - nmDay + 1;
                bestYear = yearType;
                bestNmMonth = nmMonth;
                bestNmDay = nmDay;
            }
        }
        
        // กรณีวันที่ใน ม.ค. ก่อนวันที่ 29 (ก่อน New Moon แรกของปี)
        if (bestLunarMonth == 0) {
            // วันที่ 1-28 ม.ค. 2027 ยังเป็นเดือน 11 ปีมะเมีย (丙午年)
            // ข้อมูล: วันที่ 30 ธ.ค. 2026 = 初一 (เดือน 11)
            // ดังนั้น 1 ม.ค. 2027 = 初三 (วันที่ 3)
            // 28 ม.ค. 2027 = 三十 (วันที่ 30)
            int lunarDay = day + 2; // 1 ม.ค. = วันที่ 3, 28 ม.ค. = วันที่ 30
            if (lunarDay > 30) lunarDay = 30;
            return new int[]{11, lunarDay, 0}; // เดือน 11 ปีมะเมีย
        }
        
        // ถ้าวันจันทรคติ > 30 ให้ปรับเป็นเดือนถัดไป
        if (bestLunarDay > 30) {
            bestLunarDay -= 30;
            bestLunarMonth++;
            if (bestLunarMonth > 12) bestLunarMonth = 1;
        }
        
        return new int[]{bestLunarMonth, bestLunarDay, bestYear};
    }
    
    // ตรวจสอบเทศกาล
    static String getFestivalName(int month, int day) {
        for (int i = 0; i < FESTIVAL_DATES.length; i++) {
            if (FESTIVAL_DATES[i][0] == month && FESTIVAL_DATES[i][1] == day) {
                return FESTIVAL_TH[i] + "(" + FESTIVAL_CN[i] + ")";
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        printHeader();
        for (int month = 1; month <= 12; month++) {
            printMonth(month, 2027);
            System.out.println();
        }
        printFestivalSummary();
    }
    
    static void printHeader() {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       ปฏิทินจีน ปี 2570 (ค.ศ. 2027)                            ║");
        System.out.println("║              Chinese Lunar Calendar - Year of the Fire Goat                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║     丁未年 (Ding Wei)  |  ปีระกาธาตุไฟ  |  Fire Yin  |  ทิศ: ใต้ (South)         ║");
        System.out.println("║      ตรุษจีน: 6 กุมภาพันธ์ 2027 | ปีนักษัตร: มะแม (Goat/Sheep)                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    static void printMonth(int month, int year) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int daysInMonth = firstDay.lengthOfMonth();
        int startDow = firstDay.getDayOfWeek().getValue() % 7; // 0=Sun, 1=Mon, ..., 6=Sat
        int buddhistYear = year + 543;
        
        // หาปีจีนสำหรับเดือนนี้
        int[] lunarInfo = getLunarDate(month, 1);
        int lunarMonthNum = lunarInfo[0];
        int yearType = lunarInfo[2];
        
        String yearCN = (yearType == 0) ? "丙午年 (Bing Wu - Fire Horse)" : "丁未年 (Ding Wei - Fire Goat)";
        
        System.out.println("┌────┬────┬────┬────┬────┬────┬────┬────────────────────────────────────────────────┐");
        System.out.printf("│ %s %d %-30s│%n", MONTH_TH_FULL[month - 1], buddhistYear, yearCN);
        System.out.println("├────┼────┼────┼────┼────┼────┼────┼────────────────────────────────────────────────┤");
        System.out.println("│ อา│ จ │ อ │ พ │ พฤ│ ศ │ ส │ วันจันทรคติ / เทศกาล                                │");
        System.out.println("├────┼────┼────┼────┼────┼────┼────┼────────────────────────────────────────────────┤");
        
        int day = 1;
        for (int row = 0; row < 6 && day <= daysInMonth; row++) {
            StringBuilder rowLine = new StringBuilder("│");
            
            for (int col = 0; col < 7; col++) {
                if ((row == 0 && col < startDow) || day > daysInMonth) {
                    rowLine.append("    │");
                } else {
                    int currentDay = day;
                    String festName = getFestivalName(month, currentDay);
                    int[] lunar = getLunarDate(month, currentDay);
                    int lunarD = lunar[1];
                    
                    String marker = " ";
                    if (festName != null) {
                        marker = "*";
                    } else if (lunarD == 1) {
                        marker = "+";
                    } else if (lunarD == 15) {
                        marker = "○";
                    }
                    
                    rowLine.append(String.format(" %2d%s", currentDay, marker));
                    if (col < 6) rowLine.append("│");
                    else rowLine.append("│");
                    day++;
                }
            }
            
            // ข้อมูลจันทรคติของแถวนี้
            int firstDayInRow = row * 7 - startDow + 2;
            if (firstDayInRow < 1) firstDayInRow = 1;
            
            StringBuilder infoLine = new StringBuilder("│");
            if (firstDayInRow <= daysInMonth) {
                int[] lunar = getLunarDate(month, firstDayInRow);
                int lunarM = lunar[0];
                int lunarD = lunar[1];
                
                String lunarMonthStr = (lunarM >= 1 && lunarM <= 12) ? LUNAR_MONTH_CN[lunarM - 1] : "";
                String lunarDayStr = (lunarD >= 1 && lunarD <= 30) ? LUNAR_DAY_CN[lunarD] : "";
                
                // ตรวจสอบเทศกาลในแถวนี้
                StringBuilder events = new StringBuilder();
                for (int d = firstDayInRow; d <= Math.min(firstDayInRow + 6, daysInMonth); d++) {
                    String f = getFestivalName(month, d);
                    if (f != null) {
                        if (events.length() > 0) events.append(", ");
                        events.append(f);
                    }
                }
                
                String infoStr;
                if (events.length() > 0) {
                    infoStr = String.format(" %s %s  %s", lunarMonthStr, lunarDayStr, events.toString());
                } else {
                    infoStr = String.format(" %s %s", lunarMonthStr, lunarDayStr);
                }
                
                infoLine.append(String.format(" %-68s│", infoStr));
            } else {
                infoLine.append("                                                                     │");
            }
            
            System.out.println(rowLine.toString());
            System.out.println(infoLine.toString());
        }
        
        System.out.println("└────┴────┴────┴────┴────┴────┴────┴────────────────────────────────────────────────┘");
    }
    
    static void printFestivalSummary() {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      เทศกาลสำคัญ ปี 2570                                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  6 ก.พ.  (วันเสาร์)  - ตรุษจีน (春节) - วันขึ้นปีใหม่จีน ปีระกาธาตุไฟ           ║");
        System.out.println("║  11 ก.พ. (พฤหัส)    - เทศกาลโคมไฟ (元宵节) - วันขึ้น 15 ค่ำ เดือน 1            ║");
        System.out.println("║  5 เม.ย. (จันทร์)    - เชงเม้ง (清明节) - วันไหว้บรรพบุรุษ                      ║");
        System.out.println("║  9 มิ.ย. (พุธ)      - เทศกาลเรือมังกร (端午节) - วันไหว้ขนมจ้าง                ║");
        System.out.println("║  4 ส.ค. (พุธ)       - เทศกาลชีซี (七夕节) - วันแห่งความรัก                      ║");
        System.out.println("║  11 ส.ค. (พุธ)      - สารทจีน (中元节) - วันไหว้ผี                              ║");
        System.out.println("║  15 ก.ย. (พุธ)      - เทศกาลไหว้พระจันทร์ (中秋节) - วันไหว้ขนมไหว้พระจันทร์   ║");
        System.out.println("║  8 ต.ค. (ศุกร์)     - เทศกาลฉงหยาง (重阳节) - วันไหว้บรรพบุรุษ                 ║");
        System.out.println("║  22 ธ.ค. (พุธ)      - ตงจื่อ (冬至) - วันเหมายัน                                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  สัญลักษณ์:  * = เทศกาล  |  + = วันขึ้น 1 ค่ำ  |  ○ = วันเพ็ญ 15 ค่ำ            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝");
    }
}