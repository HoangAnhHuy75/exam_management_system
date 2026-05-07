    package admissions.student_system.service;


    import org.springframework.stereotype.Service;
    import java.math.RoundingMode;
    import java.math.BigDecimal;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.Map;
    //chua ro~ co bang nao lay duoc do lech luon k
    @Service
    public class BangChenhLechService {

        private final Map<String, Map<String, BigDecimal>> data = new HashMap<>();

        public BangChenhLechService() {

            data.put("A00", Map.of(
                    "A01", new BigDecimal("-0.69"),
                    "B00", new BigDecimal("-1.21"),
                    "C00", new BigDecimal("2.32"),
                    "C01", new BigDecimal("0.94"),
                    "D01", new BigDecimal("-0.68"),
                    "D07", new BigDecimal("-1.62")
            ));

            data.put("A01", Map.of(
                    "A00", new BigDecimal("0.69"),
                    "B00", new BigDecimal("3.01"),
                    "C00", new BigDecimal("1.63"),
                    "C01", new BigDecimal("0.01"),
                    "D01", new BigDecimal("-0.93")
            ));

            data.put("B00", Map.of(
                    "A00", new BigDecimal("1.21"),
                    "A01", new BigDecimal("0.52"),
                    "C00", new BigDecimal("2.15"),
                    "C01", new BigDecimal("0.53"),
                    "D01", new BigDecimal("-0.41")
            ));

            data.put("C00", Map.of(
                    "A00", new BigDecimal("-2.32"),
                    "A01", new BigDecimal("-3.01"),
                    "B00", new BigDecimal("-3.53"),
                    "C01", new BigDecimal("-3.00"),
                    "D01", new BigDecimal("-3.94")
            ));

            data.put("C01", Map.of(
                    "A00", new BigDecimal("-0.94"),
                    "A01", new BigDecimal("-1.63"),
                    "B00", new BigDecimal("-2.15"),
                    "C00", new BigDecimal("1.38"),
                    "D01", new BigDecimal("-2.56")
            ));

            data.put("D01", Map.of(
                    "A00", new BigDecimal("0.68"),
                    "A01", new BigDecimal("-0.01"),
                    "B00", new BigDecimal("-0.53"),
                    "C00", new BigDecimal("3.0"),
                    "C01", new BigDecimal("1.62"),
                    "D07", new BigDecimal("-0.94")
            ));
        }

        public BigDecimal findChenhLech(String toHopThiSinh, String toHopGoc) {

            if (toHopThiSinh == null || toHopGoc == null) {
                return BigDecimal.ZERO.setScale(15, RoundingMode.HALF_UP);
            }

            if (toHopThiSinh.equals(toHopGoc)) {
                return BigDecimal.ZERO.setScale(15, RoundingMode.HALF_UP);
            }

            // 1. Thử lấy trực tiếp: thí sinh -> gốc
            Map<String, BigDecimal> row = data.get(toHopThiSinh);
            if (row != null && row.containsKey(toHopGoc)) {
                return row.get(toHopGoc).setScale(15, RoundingMode.HALF_UP);
            }

            // 2. Nếu không có, lấy chiều ngược rồi đảo dấu
            Map<String, BigDecimal> reverseRow = data.get(toHopGoc);
            if (reverseRow != null && reverseRow.containsKey(toHopThiSinh)) {
                return reverseRow.get(toHopThiSinh)
                        .negate()
                        .setScale(15, RoundingMode.HALF_UP);
            }

            // 3. Không có dữ liệu → = 0
            return BigDecimal.ZERO.setScale(15, RoundingMode.HALF_UP);
        }
    }