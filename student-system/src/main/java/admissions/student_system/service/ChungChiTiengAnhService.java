package admissions.student_system.service;

import org.springframework.stereotype.Service;

@Service
public class ChungChiTiengAnhService {

    public double tinhDiemCong(String chungChi, double diem, String phuongThuc) {

        int muc = getMuc(chungChi, diem); // 1,2,3

        if (muc == 0) return 0;

        switch (phuongThuc) {
            case "DGNL": // thang 1200
                if (muc == 1) return 40;
                if (muc == 2) return 60;
                return 80;

            case "VSAT": // thang 450
                if (muc == 1) return 8;
                if (muc == 2) return 9;
                return 10;

            case "THPT": // thang 30
                if (muc == 1) return 1.0;
                if (muc == 2) return 1.5;
                return 2.0;
        }

        return 0;
    }

    private int getMuc(String chungChi, double diem) {

        switch (chungChi) {

            case "IELTS":
                if (diem >= 4.0 && diem <= 5.0) return 1;
                if (diem >= 5.5 && diem <= 6.5) return 2;
                if (diem >= 7.0) return 3;
                break;

            case "TOEFL_IBT":
                if (diem >= 30 && diem <= 45) return 1;
                if (diem >= 46 && diem <= 93) return 2;
                if (diem >= 94) return 3;
                break;

            case "TOEFL_ITP":
                if (diem >= 450 && diem <= 499) return 1;
                if (diem >= 500 && diem <= 626) return 2;
                if (diem >= 627) return 3;
                break;

            case "TOEIC":
                if (diem >= 275 && diem <= 399) return 1;
                if (diem >= 400 && diem <= 489) return 2;
                if (diem >= 490) return 3;
                break;

            case "PTE":
                if (diem >= 43 && diem <= 58) return 1;
                if (diem >= 59 && diem <= 75) return 2;
                if (diem >= 76) return 3;
                break;

            case "LINGUASKILL":
                if (diem >= 140 && diem <= 159) return 1;
                if (diem >= 160 && diem <= 179) return 2;
                if (diem >= 180) return 3;
                break;

            case "APTIS_GENERAL":
            case "APTIS_ADVANCED":
                if (diem == 1) return 1; // B1
                if (diem == 2) return 2; // B2
                if (diem == 3) return 3; // C/C1
                break;

            case "VSTEP":
                if (diem == 3) return 1;
                if (diem == 4) return 2;
                if (diem == 5) return 3;
                break;
        }

        return 0;
    }
}