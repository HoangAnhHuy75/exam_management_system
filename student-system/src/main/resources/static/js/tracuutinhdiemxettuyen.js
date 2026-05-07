async function tinhDiemDGNL() {
    const maNganh = document.getElementById("maNganh")?.value;
    const diemCong = parseFloat(document.getElementById("diemCong")?.value) || 0;
    const khuVuc = parseFloat(document.getElementById("khuVuc")?.value) || 0;
    const doiTuong = parseFloat(document.getElementById("doiTuong")?.value) || 0;

    let diemDGNL = parseFloat(document.getElementById("diemDGNL")?.value) || 0;

    const diemQuyDoiTA = parseFloat(document.getElementById("cc_ketqua")?.innerText) || 0;

    diemDGNL = diemDGNL + diemQuyDoiTA;

    if (!maNganh) {
        alert("Chưa chọn ngành");
        return;
    }

    try {
        const nganhRes = await fetch(`/api/nganh?maNganh=${encodeURIComponent(maNganh)}`);

        if (!nganhRes.ok) {
            throw new Error("Không lấy được dữ liệu ngành");
        }

        const nganh = await nganhRes.json();
        const toHopGoc = nganh?.nToHopGoc;

        if (!toHopGoc) {
            alert("Không tìm thấy tổ hợp gốc của ngành");
            return;
        }

        const params = new URLSearchParams({
            maNganh,
            diemCong,
            khuVuc,
            doiTuong,
            diemDGNL,
            toHopGoc
        });

        window.location.href = `/tinhdiemdanhgianangluc?${params.toString()}`;

    } catch (err) {
        console.error(err);
        alert("Có lỗi xảy ra khi tính điểm DGNL");
    }
}
async function tinhdiemVSAT() {
   const maNganh = document.getElementById("maNganhVSAT")?.value;
    const diemCong = parseFloat(document.getElementById("diemCongVSAT")?.value) || 0;
    const khuVuc = parseFloat(document.getElementById("khuVucVSAT")?.value) || 0;
    const doiTuong = parseFloat(document.getElementById("doiTuongVSAT")?.value) || 0;
        const toan = parseFloat(document.getElementById("toan")?.value) || 0;
                const van = parseFloat(document.getElementById("van")?.value) || 0;
        const ly = parseFloat(document.getElementById("ly")?.value) || 0;
        const hoa = parseFloat(document.getElementById("hoa")?.value) || 0;
        const sinh = parseFloat(document.getElementById("sinh")?.value) || 0;
        const su = parseFloat(document.getElementById("su")?.value) || 0;
        const dia = parseFloat(document.getElementById("dia")?.value) || 0;
        const anh = parseFloat(document.getElementById("anh")?.value) || 0;

    const diemQuyDoiTA = parseFloat(document.getElementById("cc_ketqua_vsat")?.innerText) || 0;


    if (!maNganh) {
        alert("Chưa chọn ngành");
        return;
    }

    try {
        const nganhRes = await fetch(`/api/nganh?maNganh=${encodeURIComponent(maNganh)}`);

        if (!nganhRes.ok) {
            throw new Error("Không lấy được dữ liệu ngành");
        }

        const nganh = await nganhRes.json();

        const toHopGoc = nganh?.nToHopGoc;

        if (!toHopGoc) {
            alert("Không tìm thấy tổ hợp gốc của ngành");
            return;
        }

        const params = new URLSearchParams({
            maNganh,
            diemCong,
            khuVuc,
            doiTuong,
            toHopGoc,
            toan,
            van,
            ly,
            hoa,
            sinh,
            su,
        dia,
        anh,
        diemQuyDoiTA
        });

        window.location.href = `/tinhdiemvsat?${params.toString()}`;

    } catch (err) {
        console.error(err);
        alert("Có lỗi xảy ra khi tính điểm VSAT");
    }

}
    async function quyDoiTiengAnhDGNL() {
        const btn = event.target;
        btn.disabled = true;

        const chungChi = document.getElementById("cc_loai").value;
        const diem = document.getElementById("cc_diem").value;

        if (!diem) {
            alert("Vui lòng nhập điểm chứng chỉ!");
            btn.disabled = false;
            return;
        }

        try {
            const url = `/api/diemcongtienganh`
                + `?chungChi=${chungChi}`
                + `&diem=${diem}`
                + `&phuongThuc=DGNL`
                + `&_=${Date.now()}`; // 🔥 chống cache

            const res = await fetch(url);

            if (!res.ok) throw new Error();

            const data = await res.text();


            document.getElementById("cc_ketqua").innerText = data;

        } catch (err) {
            console.error(err);
            alert("Lỗi API ĐGNL");
        }

        btn.disabled = false;
    }


    // ====== V-SAT ======
    async function quyDoiTiengAnhVSAT() {
        const btn = event.target;
        btn.disabled = true;

        const chungChi = document.getElementById("cc_loai_vsat").value;
        const diem = document.getElementById("cc_diem_vsat").value;

        if (!diem) {
            alert("Nhập điểm!");
            btn.disabled = false;
            return;
        }

        try {
            const url = `/api/diemcongtienganh`
                + `?chungChi=${chungChi}`
                + `&diem=${diem}`
                + `&phuongThuc=VSAT`
                + `&_=${Date.now()}`;

            const res = await fetch(url);
            const data = await res.text();

            document.getElementById("cc_ketqua_vsat").innerText = data;

        } catch (err) {
            console.error(err);
        }

        btn.disabled = false;
    }

    // ====== THPT ======
async function quyDoiTiengAnhTHPT() {
    const btn = event.target;
    btn.disabled = true;

    const chungChi = document.getElementById("cc_loai_thpt").value;
    const diem = document.getElementById("cc_diem_thpt").value;

    if (!diem) {
        alert("Nhập điểm!");
        btn.disabled = false;
        return;
    }

    try {
        const url = `/api/diemcongtienganh`
            + `?chungChi=${chungChi}`
            + `&diem=${diem}`
            + `&phuongThuc=THPT`
            + `&_=${Date.now()}`;

        const res = await fetch(url);
        const data = await res.text();

        document.getElementById("cc_ketqua_thpt").innerText = data;

    } catch (err) {
        console.error(err);
    }

    btn.disabled = false;
}