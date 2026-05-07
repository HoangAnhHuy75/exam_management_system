document.addEventListener("DOMContentLoaded", function () {

    lucide.createIcons();

    // =========================
    // CHUYỂN TAB
    // =========================
    window.showTab = function (event, tabId) {
        document.querySelectorAll(".tab-content").forEach(el => el.classList.remove("active"));
        document.querySelectorAll(".tab-btn").forEach(el => el.classList.remove("active"));

        document.getElementById(tabId).classList.add("active");
        event.currentTarget.classList.add("active");
    };

    // =========================
    // LẤY DATA TỪ URL
    // =========================
    const params = new URLSearchParams(window.location.search);

    const data = {
        maNganh: params.get('maNganh') || 'Chưa chọn',
        diemDGNL: parseFloat(params.get('diemDGNL')) || 0,
        diemCong: parseFloat(params.get('diemCong')) || 0,
        khuVuc: params.get('khuVuc') || 'Không',
        doiTuong: params.get('doiTuong') || 'Không',
        toHopGoc: params.get('toHopGoc') || 'Chưa có'
    };

    // =========================
    // ĐỔ DỮ LIỆU VÀO INPUT
    // =========================
    document.getElementById('display-nganh').innerText = data.maNganh;

    document.getElementById('in-maNganh').value = data.maNganh;
    document.getElementById('in-diemDGNL').value = data.diemDGNL;
let khuVucText = "";

switch (Number(data.khuVuc)) {
    case 0.75:
        khuVucText = "KV1";
        break;
    case 0.5:
        khuVucText = "KV2-NT";
        break;
    case 0.25:
        khuVucText = "KV2";
        break;
    case 0:
    default:
        khuVucText = "KV3";
}

document.getElementById('in-khuVuc').value = khuVucText;
let doiTuongText = "";

 switch (Number(data.doiTuong)) {
     case 1:
         doiTuongText = "Ưu tiên 2";
         break;
     case 2:
         doiTuongText = "Ưu tiên 1";
         break;
     case 0:
     default:
         doiTuongText = "Không có";
 }

 document.getElementById('in-doiTuong').value = doiTuongText;
    document.getElementById('in-diemCong').value = data.diemCong;

    // =========================
    // HÀM TÍNH TOÁN CHÍNH (Local)
    // =========================
    async function tinhLocal() {
        const phuongThuc = "DGNL";
        const diem = parseFloat(document.getElementById("in-diemDGNL").value) || 0;
        const diemDoiTuong = parseFloat(document.getElementById("in-doiTuong").value) || 0;
        const diemKhuVuc = parseFloat(document.getElementById("in-khuVuc").value) || 0;
        const maNganh = document.getElementById('in-maNganh').value.trim();
const toHopGoc = data.toHopGoc;
        if (!maNganh || maNganh === 'Chưa chọn') {
            alert("Vui lòng nhập mã ngành!");
            return;
        }

        try {
            const [aRes, bRes, cRes, dRes, uutienRes, nganhRes] = await Promise.all([
                fetch(`/api/a?phuongThuc=${phuongThuc}&diem=${diem}&toHopGoc=${toHopGoc}`),
                fetch(`/api/b?phuongThuc=${phuongThuc}&diem=${diem}&toHopGoc=${toHopGoc}`),
                fetch(`/api/c?phuongThuc=${phuongThuc}&diem=${diem}&toHopGoc=${toHopGoc}`),
                fetch(`/api/d?phuongThuc=${phuongThuc}&diem=${diem}&toHopGoc=${toHopGoc}`),
                fetch(`/api/uutien?phuongThuc=${phuongThuc}&diem=${diem}`
                    + `&toHopThiSinh=A00`
                    + `&toHopGoc=${toHopGoc}`
                    + `&diemDoiTuong=${diemDoiTuong}`
                    + `&diemKhuVuc=${diemKhuVuc}`
                    + `&diemCongThanhTich=${data?.diemCong || 0}`),
                fetch(`/api/nganh?maNganh=${encodeURIComponent(maNganh)}`)
            ]);

            const [a, b, c, d] = await Promise.all([
                aRes.json(), bRes.json(), cRes.json(), dRes.json()
            ]);

            const nganh = await nganhRes.json();
            const diemUuTien = await uutienRes.json();

            const diemTrungTuyen = nganh.nDiemTrungTuyen;
            const diemSan = nganh.nDiemSan;


            let diemQuyDoi;
            if (b === a) {
                diemQuyDoi = c;
            } else {
                const ratio = (diem - a) / (b - a);
                diemQuyDoi = c + ratio * (d - c);
            }

            const tongDiem = diemQuyDoi + data.diemCong + diemUuTien;

            document.getElementById('res-diemDGNL').innerText = diem.toFixed(2);
            document.getElementById('res-diemQuyDoi').innerText = diemQuyDoi.toFixed(2);
            document.getElementById('res-diemCong').innerText = data.diemCong.toFixed(2);
            document.getElementById('res-diemUuTien').innerText = diemUuTien.toFixed(2);
            document.getElementById('res-tongDiem').innerText = tongDiem.toFixed(2);

            const ketquaEl = document.getElementById('ketqua-diemtrungtuyen');
            if (ketquaEl) {
                ketquaEl.innerText = tongDiem >= diemTrungTuyen
                    ? 'Đạt so với điểm trúng tuyển'
                    : 'Không đạt so với điểm trúng tuyển';
                ketquaEl.style.color = tongDiem >= diemTrungTuyen ? 'green' : 'red';
            }

 const ketquaEl1 = document.getElementById('ketqua-diemsan');
            if (ketquaEl1) {
                ketquaEl1.innerText = tongDiem >= diemSan
                    ? 'Đạt so với điểm sàn'
                    : 'Không đạt so với điểm sàn';
                ketquaEl1.style.color = tongDiem >= diemSan ? 'green' : 'red';
            }
            document.getElementById('res-congThuc').innerText =
                `${c} + ((${diem} - ${a}) / (${b} - ${a})) * (${d} - ${c})`;

        } catch (err) {
            console.error("Lỗi gọi API:", err);
            alert("Có lỗi khi tính toán. Vui lòng thử lại!");
        }
    }

    // =========================
    // HÀM TÍNH QUY ĐỔI RIÊNG (nếu cần)
    // =========================
    window.tinhQuyDoi = async function () {
        const phuongThuc = "DGNL";
        const diem = parseFloat(document.getElementById("in-diemDGNL").value);

        if (isNaN(diem)) return;

        try {
            const res = await fetch(`/api/quydoi?phuongThuc=${phuongThuc}&diem=${diem}`);

            if (!res.ok) throw new Error("API lỗi");

            const diemQuyDoi = parseFloat(await res.text());

            document.getElementById("res-diemQuyDoi").innerText = diemQuyDoi.toFixed(2);

            const diemCong = parseFloat(document.getElementById("in-diemCong").value || 0);
            const tong = diemQuyDoi + diemCong;
            document.getElementById("res-tongDiem").innerText = tong.toFixed(2);

        } catch (err) {
            console.error("Lỗi gọi API quy đổi:", err);
        }
    };

    // Gọi hàm tính khi load
    tinhLocal();
});