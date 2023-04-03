const deleteBtnList = document.querySelectorAll("#deleteBtn");
const confirmDialog = document.getElementById("dialog-confirm-delete");
const confirmBtn = document.getElementById("confirmBtn");
const cancelBtn = document.getElementById("cancelBtn");
const ids = document.querySelectorAll('#id_delete_product')
const textMessage = document.getElementById('content-message-delete')
const names = document.querySelectorAll('#name_product_delete')
const textTitleDialog = document.getElementById('text-title-dialog')

const http = 'http://192.168.1.10:3000/api/data'

var idTemp = ""

const _findId = (indexInput) => {
    for (let i = 0; i < ids.length; i++) {
        if (i == indexInput) {
            const value = ids[i].value
            return ids[i].value
        }
    }
}

const _findName = (indexInput) => {
    for (let i = 0; i < names.length; ++i) {
        if (i == indexInput) {
            const value = names[i]
            return names[i].value
        }
    }
}

// show dialog confirm
deleteBtnList.forEach((deleteBtn, index) => {
    deleteBtn.addEventListener("click", function () {
        confirmDialog.style.display = "flex";
        const id = String(_findId(index))
        idTemp = id
        const name = String(_findName(index))
        console.log(`>>>>> Check index button: ${index}`);
        console.log(`>>>>> Check id found: ${id}`);
        console.log(`>>>>> Check name found: ${name}`);
        textMessage.innerHTML = `Do you want to delete ${name}?`
    });
})

confirmBtn.addEventListener("click", function () {
    const urlDelete = `${http}/handle-delete-product/${idTemp}`

    fetch(urlDelete, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        }
    })
        .then(res => {
            if (res.code == 200) {
                confirmDialog.style.display = "none";
            }
        })
        .catch(err => {
            textTitleDialog.inner = 'Opps'
            textMessage.inner = 'Something went wrong'
        })
});

cancelBtn.addEventListener("click", function () {
    confirmDialog.style.display = "none";
});

// Ẩn dialog khi click bên ngoài
// window.addEventListener("click", function (event) {
//     if (event.target == confirmDialog) {
//         confirmDialog.style.display = "none";
//     }
// });