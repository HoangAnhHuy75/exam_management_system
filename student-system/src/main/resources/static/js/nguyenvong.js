let listNV = [];
console.log("RAW window.listNV từ server:", window.listNV);
if (window.listNV && Array.isArray(window.listNV)) {
    listNV = window.listNV.map(nv => ({
        thuTu: nv.nvTt,
        nganh: nv.maNganh,
        phuongthuc: nv.ttPhuongThuc
    }));
}

let editIndex = -1;

document.addEventListener("DOMContentLoaded", function () {

    renderList();
    syncToForm();
    lucide.createIcons();

    const form = document.getElementById("nvForm");

    if (form) {
        form.addEventListener("submit", function (e) {

            if (listNV.length === 0) {
                alert("Chưa có nguyện vọng!");
                e.preventDefault();
                return;
            }

            const set = new Set();
            for (let nv of listNV) {
                if (set.has(nv.thuTu)) {
                    alert("Trùng thứ tự nguyện vọng!");
                    e.preventDefault();
                    return;
                }
                set.add(nv.thuTu);
            }
        });
    }
});

// MODAL
window.openModal = function(index = -1) {

    editIndex = index;
    document.getElementById("modal").style.display = "flex";

    if (index >= 0) {
        const nv = listNV[index];

        document.getElementById("thuTu").value = nv.thuTu;
        document.getElementById("nganh").value = nv.nganh;
        document.getElementById("phuongthuc").value = nv.phuongthuc;
    } else {
        document.getElementById("thuTu").value = "";
        document.getElementById("nganh").value = "";
        document.getElementById("phuongthuc").value = "";
    }
}

window.closeModal = function() {
    document.getElementById("modal").style.display = "none";
}

// SAVE
window.saveNV = function() {

    const thuTu = parseInt(document.getElementById("thuTu").value);
    const nganh = document.getElementById("nganh").value;
    const phuongthuc = document.getElementById("phuongthuc").value;

    if (!thuTu || !nganh || !phuongthuc) {
        alert("Vui lòng nhập đầy đủ!");
        return;
    }

    const nv = { thuTu, nganh, phuongthuc };

    if (editIndex >= 0) {
        listNV[editIndex] = nv;
    } else {
        listNV.push(nv);
    }

    editIndex = -1;

    renderList();
    syncToForm();
    closeModal();
}

// DELETE
window.deleteNV = function(index) {
    listNV.splice(index, 1);
    renderList();
    syncToForm();
}

// SORT
window.sortNV = function() {
    listNV.sort((a, b) => a.thuTu - b.thuTu);
    renderList();
    syncToForm();
}

// RENDER
function renderList() {

    const container = document.getElementById("nvList");
    container.innerHTML = "";

    listNV.forEach((nv, index) => {

        const div = document.createElement("div");
        div.className = "nv-item";

        div.innerHTML = `
            <div class="nv-left">NV ${nv.thuTu}</div>

            <div class="nv-center">
                <div><b>Ngành:</b> ${nv.nganh}</div>
                <div><b>Phương thức:</b> ${nv.phuongthuc}</div>
            </div>

            <div class="nv-right">
                <button type="button" onclick="openModal(${index})">✏️</button>
                <button type="button" onclick="deleteNV(${index})">🗑️</button>
            </div>
        `;

        container.appendChild(div);
    });
}


function syncToForm() {

    const container = document.getElementById("hiddenInputs");
    container.innerHTML = "";

    listNV.forEach(nv => {
        container.appendChild(createInput("maNganh[]", nv.nganh));
        container.appendChild(createInput("nvTt[]", nv.thuTu));
        container.appendChild(createInput("ttPhuongThuc[]", nv.phuongthuc));
    });
}

function createInput(name, value) {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = name;
    input.value = value;
    return input;
}