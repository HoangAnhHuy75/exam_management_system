    package admissions.student_system.controller;

    import admissions.student_system.repository.NganhRepository;
    import admissions.student_system.repository.ToHopNganhRepository;
    import admissions.student_system.service.*;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import admissions.student_system.entity.Student;
    import admissions.student_system.entity.Nganh;
    import admissions.student_system.entity.ToHopNganh;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;


    import java.math.BigDecimal;
    import java.util.*;

    @Controller
    public class TraCuuTinhDiemXetTuyenController {

        private final BangChenhLechService bangChenhLechService;
        private final ToHopNganhRepository toHopNganhRepo;
        private final BangQuyDoiService bangQuyDoiService;
        private final NganhRepository nganhRepository;

        private final NganhService nganhService;
        private final ToHopNganhService toHopNganhService;
        private final ChungChiTiengAnhService chungChiTiengAnhService;

        public TraCuuTinhDiemXetTuyenController(BangChenhLechService bangChenhLechService,ToHopNganhRepository toHopNganhRepo,NganhRepository nganhRepository, BangQuyDoiService bangQuyDoiService, NganhService nganhService, ToHopNganhService toHopNganhService, ChungChiTiengAnhService chungChiTiengAnhService) {
            this.nganhService = nganhService;
            this.toHopNganhService = toHopNganhService;
            this.chungChiTiengAnhService = chungChiTiengAnhService;
            this.bangQuyDoiService = bangQuyDoiService;
            this.nganhRepository = nganhRepository;
            this.toHopNganhRepo = toHopNganhRepo;
            this.bangChenhLechService = bangChenhLechService;
        }

        @Autowired
        private NguyenVongService service;
        @GetMapping("/tinhdiemdanhgianangluc")
        public String tinhdiemPage() {
            return "tinhdiemdanhgianangluc";
        }

        @GetMapping("/tinhdiemvsat")
        public String tinhdiemVsatPage() {
            return "tinhdiemvsat";
        }
        @GetMapping("/tinhdiemthpt")
        public String tinhdiemthpt() {
            return "tinhdiemthpt";
        }

        @GetMapping("/api/{maNganh}")
        public String getToHopByNganh(@PathVariable String maNganh, Model model) {
            List<ToHopNganh> listToHop = toHopNganhRepo.findByMaNganh(maNganh);
            model.addAttribute("listToHopFragment", listToHop);

            // Log đầy đủ 3 môn và 3 hệ số của phần tử đầu tiên để kiểm tra
            if(!listToHop.isEmpty()){
                ToHopNganh first = listToHop.get(0);
                System.out.println("--- Thông tin tổ hợp đầu tiên ---");
                System.out.println("Môn 1: " + first.getMon1() + " (HS: " + first.getHsMon1() + ")");
                System.out.println("Môn 2: " + first.getMon2() + " (HS: " + first.getHsMon2() + ")");
                System.out.println("Môn 3: " + first.getMon3() + " (HS: " + first.getHsMon3() + ")");
            }

            return "tracuutinhdiemxettuyen :: danhSachToHop";
        }
        @GetMapping("/tracuutinhdiemxettuyen")
        public String tracuuTinhDiemXetTuyen( Model model) {





            List<Nganh> listNganh = nganhService.getAll();

            List<ToHopNganh> listToHop = toHopNganhService.getAll();


            model.addAttribute("listNganh", listNganh);
            model.addAttribute("listToHop", listToHop);


            return "tracuutinhdiemxettuyen";
        }

        @PostMapping("/tinhdgnl")
        public String xuLy(
                @RequestParam(value = "action", required = false) String action,

                @RequestParam(value = "maNganh", required = false) String maNganh,
                @RequestParam(value = "diemCong", required = false) Double diemCong,
                @RequestParam(value = "khuVuc", required = false) Double khuVuc,
                @RequestParam(value = "doiTuong", required = false) Double doiTuong,
                @RequestParam(value = "diemDGNL", required = false) Double diemDGNL,
                @RequestParam(value = "cc_loai", required = false) String cc_loai,
                @RequestParam(value = "cc_diem", required = false) Double cc_diem,
                @RequestParam(value = "cc_phuongThuc", required = false) String cc_phuongThuc,
                @RequestParam(value = "cc_ketqua", required = false) Double cc_cc,

                Model model
        ) {

            List<Nganh> listNganh = nganhService.getAll();
            List<ToHopNganh> listToHop = toHopNganhService.getAll();

            model.addAttribute("listNganh", listNganh);
            model.addAttribute("listToHop", listToHop);

            if (maNganh == null || maNganh.isBlank() || action == null || action.isBlank()) {
                model.addAttribute("error", "Thiếu dữ liệu đầu vào");
                return "tracuutinhdiemxettuyen";
            }

            Optional<Nganh> opt = nganhRepository.findByMaNganh(maNganh);

            if (opt.isEmpty()) {
                model.addAttribute("error", "Không tìm thấy ngành!");
                return "tracuutinhdiemxettuyen";
            }

            String toHopGoc = opt.map(Nganh::getNToHopGoc).orElse("");
            if (toHopGoc.isBlank()) {
                model.addAttribute("error", "Ngành chưa có tổ hợp gốc!");
                return "tracuutinhdiemxettuyen";
            }

            diemDGNL = (diemDGNL == null) ? 0.0 : diemDGNL;
            diemCong = (diemCong == null) ? 0.0 : diemCong;
            khuVuc = (khuVuc == null) ? 0.0 : khuVuc;
            doiTuong = (doiTuong == null) ? 0.0 : doiTuong;
            cc_diem = (cc_diem == null) ? 0.0 : cc_diem;

            if ("QUYDOI".equals(action)) {
                double ketQua = chungChiTiengAnhService.tinhDiemCong(
                        cc_loai, cc_diem, cc_phuongThuc
                );
                model.addAttribute("maNganh", maNganh);
                model.addAttribute("diemCong", diemCong);
                model.addAttribute("khuVuc", khuVuc);
                model.addAttribute("doiTuong", doiTuong);
                model.addAttribute("diemDGNL", diemDGNL);
                model.addAttribute("toHopGoc", toHopGoc);
                model.addAttribute("cc_loai", cc_loai);
                model.addAttribute("cc_diem", cc_diem);
                model.addAttribute("cc_phuongThuc", cc_phuongThuc);
                model.addAttribute("cc_ketqua", ketQua);
                return "tracuutinhdiemxettuyen";
            }
            Boolean isDatTrungTuyen = false;
            Boolean isDatDiemSan = false;
            if ("TINH".equals(action)) {
                try {
                    diemDGNL = diemDGNL + (cc_cc != null ? cc_cc : 0.0);                    String phuongThuc = "DGNL";
                    Nganh nganh = opt.get();
                    double diemTrungTuyen = nganh.getNDiemTrungTuyen();
                    double diemSan = nganh.getNDiemSan();
                    double diemQuyDoi = 0.0;
                    double diemUuTien = 0.0;
                    try {
                        diemQuyDoi = bangQuyDoiService.tinhDiemQuyDoi(
                                phuongThuc, diemDGNL, "A00", toHopGoc
                        );
                        diemUuTien = bangQuyDoiService.tinhDiemUuTien(
                                phuongThuc, diemDGNL, "A00", toHopGoc,
                                doiTuong, khuVuc, diemCong
                        );
                    } catch (Exception e) {
                        model.addAttribute("error", "Lỗi quy đổi điểm");
                        return "tracuutinhdiemxettuyen";
                    }
                    double diema = bangQuyDoiService.diema(phuongThuc, diemDGNL, toHopGoc);
                    double diemb = bangQuyDoiService.diemb(phuongThuc, diemDGNL, toHopGoc);
                    double diemc = bangQuyDoiService.diemc(phuongThuc, diemDGNL, toHopGoc);
                    double diemd = bangQuyDoiService.diemd(phuongThuc, diemDGNL, toHopGoc);

                    double tongDiem = diemQuyDoi + diemCong + diemUuTien;

                    model.addAttribute("res_diema", diema);
                    model.addAttribute("res_diemb", diemb);
                    model.addAttribute("res_diemc", diemc);
                    model.addAttribute("res_diemd", diemd);
                    model.addAttribute("res_diemDGNL", diemDGNL);
                    model.addAttribute("res_diemQuyDoi", diemQuyDoi);
                    model.addAttribute("res_diemCong", diemCong);
                    model.addAttribute("res_diemUuTien", diemUuTien);
                    model.addAttribute("res_tongDiem", tongDiem);
                    isDatTrungTuyen = tongDiem >= diemTrungTuyen;
                    isDatDiemSan = tongDiem >= diemSan;
                    model.addAttribute("isDatTrungTuyen", isDatTrungTuyen);
                    model.addAttribute("isDatDiemSan", isDatDiemSan);
                    return "tinhdiemdanhgianangluc";
                } catch (Exception e) {
                    model.addAttribute("error", "Lỗi tính toán: " + e.getMessage());
                    return "tracuutinhdiemxettuyen";
                }
            }
            model.addAttribute("isDatTrungTuyen", false);
            model.addAttribute("isDatDiemSan", false);
            return "tracuutinhdiemxettuyen";
        }

        @PostMapping("/tinhthpt")
        public String tinhDiemTHPT(
                @RequestParam(required = false) String maNganhthpt,
                @RequestParam(required = false) String maToHop,
                @RequestParam(required = false) Double diemMon1,
                @RequestParam(required = false) Double diemMon2,
                @RequestParam(required = false) Double diemMon3,
                @RequestParam(required = false) Double diemCongTHPT,
                @RequestParam(required = false) Double khuVucTHPT,
                @RequestParam(required = false) Double doiTuongTHPT,
                @RequestParam(required = false) String cc_loai_vthpt,
                @RequestParam(required = false) Double cc_diem_thpt,
                @RequestParam(required = false) String cc_phuongThuc_thpt,
                @RequestParam(required = false) Double cc_ketquaTHPT,
                @RequestParam String action,
                Model model
        ) {
            // --- GÁN GIÁ TRỊ MẶC ĐỊNH NẾU NULL ---
            diemMon1       = (diemMon1 == null) ? 0.0 : diemMon1;
            diemMon2       = (diemMon2 == null) ? 0.0 : diemMon2;
            diemMon3       = (diemMon3 == null) ? 0.0 : diemMon3;
            diemCongTHPT   = (diemCongTHPT == null) ? 0.0 : diemCongTHPT;
            khuVucTHPT     = (khuVucTHPT == null) ? 0.0 : khuVucTHPT;
            doiTuongTHPT   = (doiTuongTHPT == null) ? 0.0 : doiTuongTHPT;
            cc_diem_thpt   = (cc_diem_thpt == null) ? 0.0 : cc_diem_thpt;
            cc_ketquaTHPT  = (cc_ketquaTHPT == null) ? 0.0 : cc_ketquaTHPT;

            // --- TIẾP TỤC XỬ LÝ LOGIC ---
            model.addAttribute("listNganh", nganhService.getAll());
            model.addAttribute("activeTab", "thpt");

            if (maNganhthpt != null && !maNganhthpt.isEmpty()) {
                model.addAttribute("listToHopFragment", toHopNganhRepo.findByMaNganh(maNganhthpt));
            }

            // Đưa dữ liệu ngược lại Model để giữ giá trị trên Form
            model.addAttribute("maNganhthpt", maNganhthpt);
            model.addAttribute("maToHop", maToHop);
            model.addAttribute("diemMon1", diemMon1);
            model.addAttribute("diemMon2", diemMon2);
            model.addAttribute("diemMon3", diemMon3);
            model.addAttribute("diemCongTHPT", diemCongTHPT);
            model.addAttribute("khuVucTHPT", khuVucTHPT);
            model.addAttribute("doiTuongTHPT", doiTuongTHPT);
            model.addAttribute("cc_loai_vthpt", cc_loai_vthpt);
            model.addAttribute("cc_diem_thpt", cc_diem_thpt);
            model.addAttribute("cc_phuongThuc_thpt", cc_phuongThuc_thpt);
            model.addAttribute("cc_ketquaTHPT", cc_ketquaTHPT);

            if ("QUYDOI".equals(action)) {
                double ketQuaCC = chungChiTiengAnhService.tinhDiemCong(cc_loai_vthpt, cc_diem_thpt, cc_phuongThuc_thpt);
                model.addAttribute("cc_ketquaTHPT", ketQuaCC);
                return "tracuutinhdiemxettuyen";
            }

            if ("TINH".equals(action)) {
                try {
                    Optional<Nganh> optNganh = nganhRepository.findByMaNganh(maNganhthpt);
                    if (optNganh.isPresent()) {
                        Nganh nganh = optNganh.get();
                        List<ToHopNganh> listTH = toHopNganhRepo.findByMaNganh(maNganhthpt);
                        ToHopNganh selectedTH = listTH.stream()
                                .filter(t -> t.getMaToHop().equals(maToHop))
                                .findFirst().orElse(null);

                        if (selectedTH != null) {
                            // Logic quy đổi điểm ngoại ngữ sang thang 10
                            double diemMaxAnh = 0.0;
                            if (cc_ketquaTHPT >= 2.0) diemMaxAnh = 10.0;
                            else if (cc_ketquaTHPT >= 1.5) diemMaxAnh = 9.0;
                            else if (cc_ketquaTHPT >= 1.0) diemMaxAnh = 8.0;

                            String maAnh = "N1";
                            boolean coMonAnh = false;
                            double d1 = diemMon1, d2 = diemMon2, d3 = diemMon3;

                            if (maAnh.equals(selectedTH.getMon1())) { d1 = Math.max(d1, diemMaxAnh); coMonAnh = true; }
                            if (maAnh.equals(selectedTH.getMon2())) { d2 = Math.max(d2, diemMaxAnh); coMonAnh = true; }
                            if (maAnh.equals(selectedTH.getMon3())) { d3 = Math.max(d3, diemMaxAnh); coMonAnh = true; }

                            int h1 = (selectedTH.getHsMon1() != null) ? selectedTH.getHsMon1() : 1;
                            int h2 = (selectedTH.getHsMon2() != null) ? selectedTH.getHsMon2() : 1;
                            int h3 = (selectedTH.getHsMon3() != null) ? selectedTH.getHsMon3() : 1;

                            double dthxt = (d1 * h1 + d2 * h2 + d3 * h3) / (h1 + h2 + h3) * 3;

                            // Nếu có môn Anh thì điểm quy đổi CC đã nằm trong d1,d2,d3 (nên cc_ketquaTHPT = 0)
                            double dcCC = coMonAnh ? 0.0 : cc_ketquaTHPT;
                            double res_tongDiemCong = dcCC + diemCongTHPT;
                            double res_dut = khuVucTHPT + doiTuongTHPT;

                            BigDecimal chenhLech = bangChenhLechService.findChenhLech(nganh.getNToHopGoc(), maToHop);
                            double doLech = (chenhLech != null) ? chenhLech.doubleValue() : 0.0;

                            double tongDiem = dthxt + res_tongDiemCong + res_dut + doLech;
                            tongDiem = Math.round(tongDiem * 100.0) / 100.0;

                            // Chuẩn bị Map cho trang thế số
                            Map<String, Object> kq = new HashMap<>();
                            kq.put("maToHop", maToHop);
                            kq.put("mon1Name", selectedTH.getMon1());
                            kq.put("mon2Name", selectedTH.getMon2());
                            kq.put("mon3Name", selectedTH.getMon3());
                            kq.put("diem1", d1); kq.put("hs1", h1);
                            kq.put("diem2", d2); kq.put("hs2", h2);
                            kq.put("diem3", d3); kq.put("hs3", h3);
                            kq.put("dcCC", dcCC);

                            model.addAttribute("ketQuaToHopTHPT", List.of(kq));
                            model.addAttribute("res_dthxt", dthxt);
                            model.addAttribute("res_doLech", doLech);
                            model.addAttribute("res_dut", res_dut);
                            model.addAttribute("res_tongDiemCong", res_tongDiemCong);
                            model.addAttribute("res_tongDiem", tongDiem);
                            model.addAttribute("nganhDiemChuan", nganh.getNDiemTrungTuyen());
                            model.addAttribute("nganhDiemSan", nganh.getNDiemSan());
                            model.addAttribute("isDatTrungTuyen", tongDiem >= nganh.getNDiemTrungTuyen());
                            model.addAttribute("isDatDiemSan", tongDiem >= nganh.getNDiemSan());

                            return "tinhdiemthpt";
                        }
                    }
                } catch (Exception e) {
                    model.addAttribute("error", "Lỗi: " + e.getMessage());
                }
            }
            return "tracuutinhdiemxettuyen";
        }
        @PostMapping("/tinhvsat")
        public String xuLyVsat(
                @RequestParam(name = "action", required = false) String action,

                @RequestParam(name = "maNganhVSAT", required = false) String maNganh,
                @RequestParam(name = "diemCongVSAT", required = false) Double diemCong,
                @RequestParam(name = "khuVucVSAT", required = false) Double khuVuc,
                @RequestParam(name = "doiTuongVSAT", required = false) Double doiTuong,

                @RequestParam(name = "toan", required = false) Double toan,
                @RequestParam(name = "van", required = false) Double van,
                @RequestParam(name = "ly", required = false) Double ly,
                @RequestParam(name = "hoa", required = false) Double hoa,
                @RequestParam(name = "sinh", required = false) Double sinh,
                @RequestParam(name = "su", required = false) Double su,
                @RequestParam(name = "dia", required = false) Double dia,
                @RequestParam(name = "anh", required = false) Double anh,

                @RequestParam(name = "cc_loai_vsat", required = false) String ccLoai,
                @RequestParam(name = "cc_diem_vsat", required = false) Double ccDiem,
                @RequestParam(name = "cc_phuongThuc_vsat", required = false) String ccPhuongThuc,
                @RequestParam(name = "cc_ketquaVSAT", required = false) Integer cc_cc,
                @RequestParam(required = false) Double cong_toan,
                @RequestParam(required = false) Double cong_van,
                @RequestParam(required = false) Double cong_ly,
                @RequestParam(required = false) Double cong_hoa,
                @RequestParam(required = false) Double cong_sinh,
                @RequestParam(required = false) Double cong_su,
                @RequestParam(required = false) Double cong_dia,
                @RequestParam(required = false) Double cong_anh,
                Model model
        ) {

            List<Nganh> listNganh = nganhService.getAll();
            List<ToHopNganh> listToHop = toHopNganhService.getAll();

            model.addAttribute("listNganh", listNganh);
            model.addAttribute("listToHop", listToHop);

            if (maNganh == null || maNganh.isBlank() || action == null || action.isBlank()) {
                model.addAttribute("error", "Thiếu dữ liệu đầu vào");
                return "tracuutinhdiemxettuyen";
            }

            Optional<Nganh> opt = nganhRepository.findByMaNganh(maNganh);

            if (opt.isEmpty()) {
                model.addAttribute("error", "Không tìm thấy ngành!");
                return "tracuutinhdiemxettuyen";
            }

            String toHopGoc = opt.map(Nganh::getNToHopGoc).orElse("");
            if (toHopGoc.isBlank()) {
                model.addAttribute("error", "Ngành chưa có tổ hợp gốc!");
                return "tracuutinhdiemxettuyen";
            }

            diemCong = (diemCong == null) ? 0.0 : diemCong;
            khuVuc   = (khuVuc == null) ? 0.0 : khuVuc;
            doiTuong = (doiTuong == null) ? 0.0 : doiTuong;
            cc_cc = (cc_cc == null) ? 0 : cc_cc;
            toan = (toan == null) ? 0.0 : toan;
            van  = (van == null) ? 0.0 : van;
            ly   = (ly == null) ? 0.0 : ly;
            hoa  = (hoa == null) ? 0.0 : hoa;
            sinh = (sinh == null) ? 0.0 : sinh;
            su   = (su == null) ? 0.0 : su;
            dia  = (dia == null) ? 0.0 : dia;
            anh  = (anh == null) ? 0.0 : anh;
            cong_toan = cong_toan == null ? 0 : cong_toan;
            cong_van  = cong_van  == null ? 0 : cong_van;
            cong_ly   = cong_ly   == null ? 0 : cong_ly;
            cong_hoa  = cong_hoa  == null ? 0 : cong_hoa;
            cong_sinh = cong_sinh == null ? 0 : cong_sinh;
            cong_su   = cong_su   == null ? 0 : cong_su;
            cong_dia  = cong_dia  == null ? 0 : cong_dia;
            cong_anh  = cong_anh  == null ? 0 : cong_anh;
            ccDiem = (ccDiem == null) ? 0.0 : ccDiem;

            if ("QUYDOI".equals(action)) {
                double ketQua = chungChiTiengAnhService.tinhDiemCong(
                        ccLoai, ccDiem, ccPhuongThuc
                );
                model.addAttribute("cong_toan", cong_toan);
                model.addAttribute("cong_van", cong_van);
                model.addAttribute("cong_ly", cong_ly);
                model.addAttribute("cong_hoa", cong_hoa);
                model.addAttribute("cong_sinh", cong_sinh);
                model.addAttribute("cong_su", cong_su);
                model.addAttribute("cong_dia", cong_dia);
                model.addAttribute("cong_anh", cong_anh);
                model.addAttribute("maNganhVSAT", maNganh);
                model.addAttribute("diemCongVSAT", diemCong);
                model.addAttribute("khuVucVSAT", khuVuc);
                model.addAttribute("doiTuongVSAT", doiTuong);
                model.addAttribute("toan", toan);
                model.addAttribute("van", van);
                model.addAttribute("ly", ly);
                model.addAttribute("hoa", hoa);
                model.addAttribute("sinh", sinh);
                model.addAttribute("su", su);
                model.addAttribute("dia", dia);
                model.addAttribute("anh", anh);
                model.addAttribute("cc_loai_vsat", ccLoai);
                model.addAttribute("cc_diem_vsat", ccDiem);
                model.addAttribute("cc_phuongThuc_vsat", ccPhuongThuc);
                model.addAttribute("cc_ketquaVSAT", ketQua);
                model.addAttribute("activeTab", "vsat");

                return "tracuutinhdiemxettuyen";
            }
            Boolean isDatTrungTuyen = false;
            Boolean isDatDiemSan = false;
            String toHopMax = null;
            double diemMax = Double.NEGATIVE_INFINITY;
            System.out.println("B1 - bắt đầu quy đổi điểm");
            double toan10 = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "TO", toan);
            double van10  = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "VA", van);
            double ly10   = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "LI", ly);
            double hoa10  = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "HO", hoa);
            double sinh10 = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "SI", sinh);
            double su10   = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "SU", su);
            double dia10  = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "DI", dia);
            double anh10  = bangQuyDoiService.tinhDiemMonVSAT("VSAT", "N1", anh);

            double    toan10a = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "TO", toan,"a");
            double    van10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "VA", van,"a");
            double   ly10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "LI", ly,"a");
            double    hoa10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "HO", hoa,"a");
            double   sinh10a = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SI", sinh,"a");
            double   su10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SU", su,"a");
            double  dia10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "DI", dia,"a");
            double   anh10a  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "N1", anh,"a");

            double    toan10b = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "TO", toan,"b");
            double    van10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "VA", van,"b");
            double   ly10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "LI", ly,"b");
            double    hoa10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "HO", hoa,"b");
            double   sinh10b = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SI", sinh,"b");
            double   su10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SU", su,"b");
            double  dia10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "DI", dia,"b");
            double   anh10b  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "N1", anh,"b");

            double    toan10c = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "TO", toan,"c");
            double    van10c = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "VA", van,"c");
            double   ly10c  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "LI", ly,"c");
            double    hoa10c  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "HO", hoa,"c");
            double   sinh10c = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SI", sinh,"c");
            double   su10c  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SU", su,"c");
            double  dia10c  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "DI", dia,"c");
            double   anh10c  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "N1", anh,"c");

            double    toan10d = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "TO", toan,"d");
            double    van10d = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "VA", van,"d");
            double   ly10d  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "LI", ly,"d");
            double    hoa10d  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "HO", hoa,"d");
            double   sinh10d = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SI", sinh,"d");
            double   su10d  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "SU", su,"d");
            double  dia10d  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "DI", dia,"d");
            double   anh10d  = bangQuyDoiService.tinhDiemABCDVSAT("VSAT", "N1", anh,"d");
            if ("TINH".equals(action)) {
                System.out.println("=== BẮT ĐẦU TÍNH VSAT ===");

                try {


                    System.out.println("B2 - quy đổi xong điểm môn");
                    Map<String, Double> diem10 = new HashMap<>();
                    diem10.put("TO", toan10);
                    diem10.put("VA", van10);
                    diem10.put("LI", ly10);
                    diem10.put("HO", hoa10);
                    diem10.put("SI", sinh10);
                    diem10.put("SU", su10);
                    diem10.put("DI", dia10);
                    diem10.put("N1", anh10);

                    Map<String, Double> diem10a = new HashMap<>();
                    diem10a.put("TO", toan10a);
                    diem10a.put("VA", van10a);
                    diem10a.put("LI", ly10a);
                    diem10a.put("HO", hoa10a);
                    diem10a.put("SI", sinh10a);
                    diem10a.put("SU", su10a);
                    diem10a.put("DI", dia10a);
                    diem10a.put("N1", anh10a);

                    Map<String, Double> diem10b = new HashMap<>();
                    diem10b.put("TO", toan10b);
                    diem10b.put("VA", van10b);
                    diem10b.put("LI", ly10b);
                    diem10b.put("HO", hoa10b);
                    diem10b.put("SI", sinh10b);
                    diem10b.put("SU", su10b);
                    diem10b.put("DI", dia10b);
                    diem10b.put("N1", anh10b);

                    Map<String, Double> diem10c = new HashMap<>();
                    diem10c.put("TO", toan10c);
                    diem10c.put("VA", van10c);
                    diem10c.put("LI", ly10c);
                    diem10c.put("HO", hoa10c);
                    diem10c.put("SI", sinh10c);
                    diem10c.put("SU", su10c);
                    diem10c.put("DI", dia10c);
                    diem10c.put("N1", anh10c);
                    Map<String, Double> diemVSAT = new HashMap<>();
                    diemVSAT.put("TO", toan);
                    diemVSAT.put("VA", van);
                    diemVSAT.put("LI", ly);
                    diemVSAT.put("HO", hoa);
                    diemVSAT.put("SI", sinh);
                    diemVSAT.put("SU", su);
                    diemVSAT.put("DI", dia);
                    diemVSAT.put("N1", anh);
                    Map<String, Double> diem10d = new HashMap<>();
                    diem10d.put("TO", toan10d);
                    diem10d.put("VA", van10d);
                    diem10d.put("LI", ly10d);
                    diem10d.put("HO", hoa10d);
                    diem10d.put("SI", sinh10d);
                    diem10d.put("SU", su10d);
                    diem10d.put("DI", dia10d);
                    diem10d.put("N1", anh10d);

                    System.out.println("B3 - tạo map điểm 10");
                    Map<String, Double> diemCongMap = new HashMap<>();
                    diemCongMap.put("TO", cong_toan);
                    diemCongMap.put("VA", cong_van);
                    diemCongMap.put("LI", cong_ly);
                    diemCongMap.put("HO", cong_hoa);
                    diemCongMap.put("SI", cong_sinh);
                    diemCongMap.put("SU", cong_su);
                    diemCongMap.put("DI", cong_dia);
                    diemCongMap.put("N1", cong_anh);
                    System.out.println("B4 - tạo map điểm cộng");
                    List<ToHopNganh> listToHop1 = toHopNganhRepo.findByMaNganh(maNganh);
                    System.out.println("B5 - số tổ hợp = " + listToHop1.size());
                    List<Map<String, Object>> ketQuaToHop = new ArrayList<>();

                    for (ToHopNganh th : listToHop1) {
                        System.out.println("---- Xử lý tổ hợp: " + th.getMaToHop());
                        String mon1 = th.getMon1();
                        String mon2 = th.getMon2();
                        String mon3 = th.getMon3();
                        boolean coTiengAnh = mon1.equals("N1") || mon2.equals("N1") || mon3.equals("N1");
                        double dcLocal;
                        if (coTiengAnh) {
                            dcLocal = diemCong;
                        } else {
                            switch ((Integer) cc_cc) {
                                case 8 -> dcLocal = diemCong + 1.0;
                                case 9 -> dcLocal = diemCong + 1.5;
                                case 10 -> dcLocal = diemCong + 2.0;
                                default -> dcLocal = diemCong;
                            }
                        }

                        int hs1 = th.getHsMon1() != null ? th.getHsMon1() : 1;
                        int hs2 = th.getHsMon2() != null ? th.getHsMon2() : 1;
                        int hs3 = th.getHsMon3() != null ? th.getHsMon3() : 1;

                        double diem1 = tinhMon(mon1, diem10, diemCongMap, cc_cc);
                        double diem2 = tinhMon(mon2, diem10, diemCongMap, cc_cc);
                        double diem3 = tinhMon(mon3, diem10, diemCongMap, cc_cc);

                        double diemA1 = diem10a.getOrDefault(mon1, 0.0);
                        double diemA2 = diem10a.getOrDefault(mon2, 0.0);
                        double diemA3 = diem10a.getOrDefault(mon3, 0.0);

                        double diemB1 = diem10b.getOrDefault(mon1, 0.0);
                        double diemB2 = diem10b.getOrDefault(mon2, 0.0);
                        double diemB3 = diem10b.getOrDefault(mon3, 0.0);

                        double diemC1 = diem10c.getOrDefault(mon1, 0.0);
                        double diemC2 = diem10c.getOrDefault(mon2, 0.0);
                        double diemC3 = diem10c.getOrDefault(mon3, 0.0);

                        double diemD1 = diem10d.getOrDefault(mon1, 0.0);
                        double diemD2 = diem10d.getOrDefault(mon2, 0.0);
                        double diemD3 = diem10d.getOrDefault(mon3, 0.0);
                        double x1 = diemVSAT.getOrDefault(mon1, 0.0);
                        double x2 = diemVSAT.getOrDefault(mon2, 0.0);
                        double x3 = diemVSAT.getOrDefault(mon3, 0.0);




                        double dut = khuVuc + doiTuong;
                        double tongRaw = (diem1 * hs1 + diem2 * hs2 + diem3 * hs3)/(hs1+hs2+hs3)*3;
                        BigDecimal chenhLech = bangChenhLechService.findChenhLech(toHopGoc, th.getMaToHop());

                        double lech = (chenhLech != null) ? chenhLech.doubleValue() : 0.0;
                        System.out.println("Điểm lệch: " + lech + "khuvuc: " + khuVuc + "đói tượng: " + doiTuong +"điểm cc: " +  cc_cc + "moon 3: " +mon3 + " điểm 3: "+ diem3 + "điểm cộng: " +diemCong);
                        double tong = tongRaw + lech + dut + dcLocal;

                        if (tong > diemMax) {
                            diemMax = tong;
                            toHopMax = th.getMaToHop();
                        }

                        Map<String, Object> kq = new HashMap<>();
                        kq.put("doLech", lech);
                        kq.put("maToHop", th.getMaToHop());
                        kq.put("tongDiem", tong);
                        kq.put("diemA1", diemA1);
                        kq.put("diemA2", diemA2);
                        kq.put("diemA3", diemA3);
                        kq.put("x1", x1);
                        kq.put("x2", x2);
                        kq.put("x3", x3);
                        kq.put("diemB1", diemB1);
                        kq.put("diemB2", diemB2);
                        kq.put("diemB3", diemB3);

                        kq.put("diemC1", diemC1);
                        kq.put("diemC2", diemC2);
                        kq.put("diemC3", diemC3);

                        kq.put("diemD1", diemD1);
                        kq.put("diemD2", diemD2);
                        kq.put("diemD3", diemD3);
                        kq.put("dcLocal", dcLocal);
                        kq.put("mon11", diem1);
                        kq.put("mon22", diem2);
                        kq.put("mon33", diem3);

                        kq.put("mona", mon1);
                        kq.put("monb", mon2);
                        kq.put("monc", mon3);

                        kq.put("hs11", hs1);
                        kq.put("hs22", hs2);
                        kq.put("hs33", hs3);

                        System.out.println(
                                "Tổ hợp: " + diem1 + " * " + hs1 +
                                        " + " + diem2 + " * " + hs2 +
                                        " + " + diem3 + " * " + hs3 +khuVuc + doiTuong + lech

                        );

                        ketQuaToHop.add(kq);
                    }
                    System.out.println("B6 - add model view");
                    model.addAttribute("ketQuaToHop", ketQuaToHop);
                    model.addAttribute("listToHop", listToHop1);
                    model.addAttribute("toHopMax", toHopMax);
                    model.addAttribute("diemMax", diemMax);

                    System.out.println("TỔ HỢP CAO NHẤT = " + toHopMax + " | ĐIỂM = " + diemMax);

                    model.addAttribute("diem10", diem10);
                    model.addAttribute("maNganh",
                            nganhRepository.findByMaNganh(maNganh)
                                    .map(Nganh::getTenNganh)
                                    .orElse(null)
                    );

                    model.addAttribute("diemCongVSAT", diemCong);
                    model.addAttribute("khuVucVSAT", khuVuc);
                    model.addAttribute("doiTuongVSAT", doiTuong);
                    Nganh nganh = opt.get();
                    double diemTrungTuyen = nganh.getNDiemTrungTuyen();
                    double diemSan = nganh.getNDiemSan();
                    isDatTrungTuyen = diemMax >= diemTrungTuyen;
                    isDatDiemSan = diemMax >= diemSan;
                    model.addAttribute("diemTrungTuyen", diemTrungTuyen);
                    model.addAttribute("diemSan", diemSan);
                    model.addAttribute("cc_cc", cc_cc);

                    model.addAttribute("isDatTrungTuyen", isDatTrungTuyen);
                    model.addAttribute("isDatDiemSan", isDatDiemSan);
                    return "tinhdiemvsat";

                } catch (Exception e) {
                    System.out.println("❌ LỖI VSAT: " + e.getMessage());
                    e.printStackTrace();

                    model.addAttribute("error", "Lỗi tính toán: " + e.getMessage());
                    return "tracuutinhdiemxettuyen";
                }
            }
            model.addAttribute("isDatTrungTuyen", false);
            model.addAttribute("isDatDiemSan", false);
            return "tracuutinhdiemxettuyen";
        }
        private double tinhMon(
                String mon,
                Map<String, Double> diem10,
                Map<String, Double> diemCongMap,
                double cc_cc
        ) {

            double diemThi = diem10.getOrDefault(mon, 0.0);

            if ("N1".equals(mon)) {

                double diemMoi = Math.max(diemThi, cc_cc);

                diem10.put(mon, diemMoi);

                return diemMoi;
            }

            return diemThi + diemCongMap.getOrDefault(mon, 0.0);
        }
    }

